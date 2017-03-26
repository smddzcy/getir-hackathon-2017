var Event = require('../models/Event');
var Message = require('../models/Message');
var regression = require('../linear_regression');
var redis = require('../redis');
var async = require('async');

/**
 * Train the model of user with given events.
 */
var trainModel = function(userId, events, cb) {
  // If exists, get the model from Redis.
  // Otherwise, create a new model.
  redis.hget('lr_model', userId, function(err, jsonString) {
    var oldModel;
    if (jsonString) {
      oldModel = JSON.parse(jsonString);
    } else {
      oldModel = { x: [] };
    }

    // Build the train inputs.
    var x = oldModel.x;
    [].concat(events).forEach(function(event) {
      x.push([event.location.latitude, event.location.longitude,
        toEpoch(event.startTime), toEpoch(event.endTime),
        parseInt(event.type, 16)
      ]);
    })

    // Train the model.
    var model = regression('polynomial', x, 5);

    // Persist the model in Redis.
    redis.hset('lr_model', userId, JSON.stringify({
      eq: model.equation,
      x: x
    }));
  });
}

/**
 * Get epoch time of a valid time string.
 */
var toEpoch = function(timeStr) {
  return (new Date(timeStr)).getTime();
}

/**
 * Get events in a radius.
 */
var filterEventsInRadius = function(events, lat, lng, radius) {
  return events.filter(function(event) {
    if (!event.location) {
      return false;
    }

    return Math.sqrt(
      Math.pow(event.location.latitude - lat, 2) +
      Math.pow(event.location.longitude - lng, 2)) < radius;
  });
}

/**
 * GET /event/:lat?/:lng?/:radius?
 * Lists all the events.
 */
exports.eventGetAll = function(req, res, next) {
  var lat = req.params.lat;
  var lng = req.params.lng;
  var radius = req.params.radius;
  if (!radius) { radius = 0.5; }

  Event.find({})
    .populate('creator', ['_id', 'name', 'email', 'picture'])
    .populate('messages', ['_id', 'from', 'to', 'message'])
    .populate('users', ['_id', 'name', 'email', 'picture'])
    .populate('type', ['_id', 'name', 'count'])
    .exec(function(err, events) {
      if (lat && lng && radius) {
        events = filterEventsInRadius(events, lat, lng, radius);
      }

      Event.populate(events, {
        path: 'messages.from',
        model: 'User'
      }, function(err, events) {
        if (err) {
          return res.status(500).send({ msg: 'Events couldn\'t be retrieved.' })
        }

        res.send(events);
      });
    });
}

/**
 * GET /event/:id
 * Retrieves an event with the given id.
 */
exports.eventGet = function(req, res, next) {
  var eventId = req.params.id;

  Event.findById(eventId)
    .populate('creator', ['id', 'name', 'email', 'picture'])
    .populate('messages', ['_id', 'from', 'to', 'message'])
    .populate('users', ['_id', 'name', 'email', 'picture'])
    .populate('type', ['_id', 'name', 'count'])
    .exec(function(err, event) {
      Event.populate(event, {
        path: 'messages.from',
        model: 'User'
      }, function(err, event) {
        if (!event) {
          return res.status(404).send({ msg: 'Event couldn\'t be found.' })
        }

        if (err) {
          return res.status(500).send({ msg: 'Event couldn\'t be retrieved.' })
        }

        res.send(event);
      });
    });
}

/**
 * GET /event/search/type/:typeName
 * Retrieves all the events that has the given type name.
 */
exports.eventSearchTypeGet = function(req, res, next) {
  var typeName = req.params.typeName;

  Event.find({})
    .populate({
      path: 'type',
      match: {
        'name': typeName
      }
    })
    .populate('creator', ['_id', 'name', 'email', 'picture'])
    .populate('messages', ['_id', 'from', 'to', 'message'])
    .populate('users', ['_id', 'name', 'email', 'picture'])
    .exec(function(err, events) {
      Event.populate(events, {
        path: 'messages.from',
        model: 'User'
      }, function(err, events) {
        if (err) {
          return res.status(400).send({ msg: 'Events couldn\'t be retrieved' });
        }
        events = events.filter(function(event) {
          return event.type; 
        });
        res.send(events);
      });
    });
}

/**
 * GET /event/search/interval/:startTime/:endTime
 * Retrieves all the events that is in the given interval.
 */
exports.eventSearchIntervalGet = function(req, res, next) {
  var startTime = req.params.startTime;
  var endTime = req.params.endTime;

  Event.find({
      startTime: { $gte: startTime },
      endTime: { $lt: endTime }
    })
    .populate('creator', ['_id', 'name', 'email', 'picture'])
    .populate('messages', ['_id', 'from', 'to', 'message'])
    .populate('users', ['_id', 'name', 'email', 'picture'])
    .populate('type', ['_id', 'name', 'count'])
    .exec(function(err, events) {
      Event.populate(events, {
        path: 'messages.from',
        model: 'User'
      }, function(err, events) {
        if (err) {
          return res.status(400).send({ msg: 'Events couldn\'t be retrieved' });
        }
        res.send(events);
      });
    });
}

/**
 * POST /event
 * Creates an event with the given values.
 */
exports.eventPost = function(req, res, next) {
  req.assert('location', 'Location cannot be blank').notEmpty();

  var event = new Event({
    creator: req.user,
    type: req.body.type,
    startTime: req.body.startTime,
    endTime: req.body.endTime,
    location: req.body.location,
    users: req.body.users,
    messages: req.body.messages
  });

  event.save(function(err) {
    if (err) {
      console.log(err);
      return res.status(400).send({ msg: 'Event couldn\'t be created.' })
    }

    // Add the event to the user
    req.user.events.addToSet(event);
    req.user.save();

    // Train the user's lr model.
    trainModel(req.user._id, event);

    res.send(event);
  });
}

/**
 * PUT /event/:id
 * Updates an event with the given values.
 */
exports.eventPut = function(req, res, next) {
  var eventId = req.params.id;

  Event.findById(eventId, function(err, event) {
    if (req.body.location) {
      event.location = req.body.location;
    }

    if (req.body.type) {
      event.type = req.body.type;
    }

    if (req.body.date) {
      event.date = req.body.date;
    }

    event.save(function(err) {
      if (err) {
        return res.status(400).send({ msg: 'Event couldn\'t be updated.' });
      }

      // Train the user's lr model.
      trainModel(req.user._id, event);

      res.send({ event: event, msg: 'Event has been updated successfully.' });
    })
  });
}

/**
 * DELETE /event/:id
 * Deletes an event with the given id.
 */
exports.eventDelete = function(req, res, next) {
  Event.remove({ _id: req.params.id }, function(err) {
    // Remove the event from user too.
    req.user.events.pull(req.params.id);
    req.user.save();

    res.send({ msg: 'Event has been permanently deleted.' });
  });
}

/**
 * POST /event/:id/join
 * Joins the event.
 */
exports.eventJoinPost = function(req, res, next) {
  var eventId = req.params.id;

  Event.findById(eventId, function(err, event) {
    event.users.addToSet(req.user);

    event.save(function(err) {
      if (err) {
        return res.status(400).send({ msg: 'Event couldn\'t be joined.' });
      }

      // Train the user's lr model.
      trainModel(req.user._id, event);

      res.send({ event: event, msg: 'Event has been joined successfully.' });
    })
  });
}

/**
 * DELETE /event/:id/join
 * Removes the user from an event.
 */
exports.eventJoinDelete = function(req, res, next) {
  var eventId = req.params.id;

  Event.findById(eventId, function(err, event) {
    event.users.pull(req.user);

    event.save(function(err) {
      if (err) {
        return res.status(400).send({ msg: 'Event couldn\'t be unjoined.' });
      }
      res.send({ event: event, msg: 'Event has been unjoined successfully.' });
    })
  });
}

/**
 * GET /event/suggested/:lat/:lng/:radius?
 * Get suggested events for the user.
 */
exports.eventSuggestedGet = function(req, res, next) {
  var lat = req.params.lat;
  var lng = req.params.lng;
  var radius = req.params.radius || 1;

  var normalize = function(obj, field) {
    // First reduce, then map.
    var sum = 0;
    for (var i in obj) {
      sum += obj[i][field];
    }
    for (var i in obj) {
      obj[i][field] = obj[i][field] / sum;
    }
    return obj;
  };

  // Get the trained model from Redis
  redis.hget('lr_model', req.user._id.toString(), function(err, jsonString) {
    var eq = JSON.parse(jsonString).eq;

    Event.find({})
      .populate('creator', ['_id', 'name', 'email', 'picture'])
      .populate('type', ['_id', 'name', 'count'])
      .exec(function(err, events) {
        var predictions = [];

        filterEventsInRadius(events, lat, lng, radius).forEach(function(event) {
          // Don't count user's own events
          if (event.creator._id === req.user._id) return;

          // Build the prediction.
          predictions.push({
            point: Math.pow(event.location.latitude, 5) * eq[0] +
              Math.pow(event.location.longitude, 4) * eq[1] +
              Math.pow(toEpoch(event.startTime), 3) * eq[2] +
              Math.pow(toEpoch(event.endTime), 2) * eq[3] +
              parseInt(event.type._id, 16) * eq[4] + eq[5],
            event: event
          });
        });

        // Normalize the values
        predictions = normalize(predictions, 'point');

        // Collect the suggestions.
        var suggestions = [];
        for (var i in predictions) {
          if (predictions[i].point >= 1 / predictions.length * 10) {
            suggestions.push(predictions[i].event);
          }
        }

        res.send(suggestions);
      });
  });
}
