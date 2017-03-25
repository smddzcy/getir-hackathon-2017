var Event = require('../models/Event');
var Message = require('../models/Message');
var LinearRegression = require('../linear_regression').LinearRegression;
var redis = require('../redis');
var async = require('async');

/**
 * Train the model of user with given events.
 */
var trainModel = function(userId, events) {
  // If exists, get the model from Redis.
  // Otherwise, create a new model.
  var lrModel = redis.hget('lr_model', userId);
  if (lrModel) {
    lrModel = LinearRegression.fromJSON(lrModel);
  } else {
    lrModel = new LinearRegression({
      algorithm: GradientDescent
    });
  }

  // Build the train inputs and outputs.
  var x = [];
  var y = [];
  [].concat(events).forEach(function(event) {
    x.push([event.location.latitude, event.location.longitude,
              toEpoch(event.startTime), toEpoch(event.endTime),
              event.type.name]);

    // Since this is not a real train data, give a good random
    // y value which represents how much user likes this event.
    // It's between 0.75 and 1.
    y.push(Math.random() / 4 + 0.75);
  })

  // Train the model.
  lrModel.train(x, y);

  // Save the trained model to Redis.
  redis.hset('lr_model', userId, lrModel.toJSON());

  // Return the trained model.
  return lrModel;
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
    messages: req.body.message
  });

  event.save(function(err) {
    if (err) {
      return res.status(400).send({ msg: 'Event couldn\'t be created.' })
    }

    // Add the event to the user
    req.user.events.addToSet(event);
    req.user.save();

    // Train the user's lr model.
    setTimeout(function(){
      trainModel(req.user._id, event);
    });

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
      setTimeout(function(){
        trainModel(req.user._id, event);
      });

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
      setTimeout(function(){
        trainModel(req.user._id, event);
      });

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

  // Get the trained model from Redis
  var lrModel = LinearRegression.fromJSON(redis.hget('lr_model', req.user._id));

  Event.find({})
    .populate('creator', ['_id', 'name', 'email', 'picture'])
    .populate('type', ['_id', 'name', 'count'])
    .exec(function(err, events) {
      var y = [];

      filterEventsInRadius(events, lat, lng, radius).forEach(function(event) {
        // Don't count user's own events
        if (event.creator._id === req.user._id) return;

        // Build the prediction input.
        var x = [event.location.latitude, event.location.longitude,
                  toEpoch(event.startTime), toEpoch(event.endTime),
                  event.type.name];

        // Suggest the event if it's a +75% match for the user.
        if (lrModel.predict(x) > 0.75) {
          y.push(event);
        }
      });

      res.send(y);
    });
}
