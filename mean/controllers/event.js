var async = require('async');
var crypto = require('crypto');
var nodemailer = require('nodemailer');
var mg = require('nodemailer-mailgun-transport');
var jwt = require('jsonwebtoken');
var moment = require('moment');
var request = require('request');
var qs = require('querystring');
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

  Event.find({})
    .populate('creator', ['_id', 'name', 'email', 'picture'])
    .populate('messages', ['_id', 'from', 'to', 'message'])
    .populate('users', ['_id', 'name', 'email', 'picture'])
    .exec(function(err, events) {
      if (lat && lng && radius) {
        return res.send(events.filter(function(event) {
          if (!event.location) {
            return false;
          }

          return Math.sqrt(
            Math.pow(event.location.latitude - lat, 2) +
            Math.pow(event.location.longitude - lng, 2)) < radius;
        }));
      }

      Event.populate(events, {
        path: 'messages.from',
        model: 'User'
      }, function(err, events) {
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
    .exec(function(err, event) {
      Event.populate(event, {
        path: 'messages.from',
        model: 'User'
      }, function(err, event) {
        res.send(event);
      });
    })
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
    date: req.body.date,
    location: req.body.location,
    users: req.body.users,
    messages: req.body.message
  });

  event.save(function(err) {
    if (err) {
      return res.status(500).send({ msg: 'Event couldn\'t be created.' })
    }
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
        return res.status(500).send({ msg: 'Event couldn\'t be updated.' });
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
        return res.status(500).send({ msg: 'Event couldn\'t be joined.' });
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
        return res.status(500).send({ msg: 'Event couldn\'t be unjoined.' });
      }
      res.send({ event: event, msg: 'Event has been unjoined successfully.' });
    })
  });
}
