var EventType = require('../models/EventType');
var Message = require('../models/Message');

/**
 * GET /event-type/
 * Lists all the event types.
 */
exports.eventTypeGetAll = function(req, res, next) {
  EventType.find({}, function(err, eventTypes) {
    if (err) {
      return res.status(500).send({ msg: 'Event types couldn\'t be retrieved.' })
    }

    res.send(eventTypes);
  });
}

/**
 * GET /event-type/:id
 * Retrieves an event type with the given id.
 */
exports.eventTypeGet = function(req, res, next) {
  var eventTypeId = req.params.id;

  EventType.findById(eventTypeId, function(err, eventType) {
    if (!eventType) {
      return res.status(404).send({ msg: 'Event type couldn\'t be found.' })
    }

    if (err) {
      return res.status(400).send({ msg: 'Event type couldn\'t be retrieved.' })
    }

    res.send(eventType);
  })
}

/**
 * POST /event-type
 * Creates an event type with the given values.
 */
exports.eventTypePost = function(req, res, next) {
  req.assert('name', 'Name cannot be blank').notEmpty();

  var eventType = new EventType({
    name: req.body.name,
    count: req.body.count || 0
  });

  eventType.save(function(err) {
    if (err) {
      return res.status(400).send({ msg: 'Event type couldn\'t be created.' })
    }
    res.send(eventType);
  });
}

/**
 * PUT /event-type/:id
 * Updates an event type with the given values.
 */
exports.eventTypePut = function(req, res, next) {
  var eventTypeId = req.params.id;

  EventType.findById(eventTypeId, function(err, eventType) {
    if (err) {
      return res.status(400).send({ msg: 'Event type couldn\'t be found.' });
    }

    if (req.body.name) {
      eventType.name = req.body.name;
    }

    if (req.body.count) {
      eventType.count = req.body.count;
    }

    eventType.save(function(err) {
      if (err) {
        return res.status(400).send({ msg: 'Event type couldn\'t be updated.' });
      }
      res.send(eventType);
    })
  });
}

/**
 * DELETE /event-type/:id
 * Deletes an event type with the given id.
 */
exports.eventTypeDelete = function(req, res, next) {
  EventType.remove({ _id: req.params.id }, function(err) {
    if (err) {
      return res.status(400).send({ msg: 'Event type couldn\'t be deleted.' });
    }
    res.send({ msg: 'Event type has been permanently deleted.' });
  });
}
