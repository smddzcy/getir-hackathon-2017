var Event = require('../models/Event');
var Message = require('../models/Message');

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
    .populate('messages.from', ['_id', 'name', 'email', 'picture'])
    .populate('users', ['_id', 'name', 'email', 'picture'])
    .populate('type', ['_id', 'name', 'count'])
    .exec(function(err, events) {
      if (lat && lng && radius) {
        events = events.filter(function(event) {
          if (!event.location) {
            return false;
          }

          return Math.sqrt(
            Math.pow(event.location.latitude - lat, 2) +
            Math.pow(event.location.longitude - lng, 2)) < radius;
        })
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
