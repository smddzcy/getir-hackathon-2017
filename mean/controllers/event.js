var async = require('async');
var crypto = require('crypto');
var nodemailer = require('nodemailer');
var mg = require('nodemailer-mailgun-transport');
var jwt = require('jsonwebtoken');
var moment = require('moment');
var request = require('request');
var qs = require('querystring');
var Event = require('../models/Event');

/**
 * GET /event
 * Lists all the events.
 */
exports.eventGetAll = function(req, res, next) {
  Event.find({}, function(err, users) {
    res.send(users);
  });
}

/**
 * GET /event/:id
 * Retrieves an event with the given id.
 */
exports.eventGet = function(req, res, next) {
  var eventId = req.params.id;

  Event.findById(eventId, function(err, event) {
    res.send({ event: event });
  })
}

/**
 * POST /event
 * Create an event with the given values.
 */
exports.eventPost = function(req, res, next) {
  req.assert('location', 'Location cannot be blank').notEmpty();

  var event = new Event({
    creator: req.user,
    location: req.body.location,
    type: req.body.type
  });

  event.save(function(err) {
    if (err) {
      res.status(500).send({ msg: 'Event couldn\'t be created.' })
    }
    res.send({ event: event });
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