var async = require('async');
var crypto = require('crypto');
var nodemailer = require('nodemailer');
var mg = require('nodemailer-mailgun-transport');
var jwt = require('jsonwebtoken');
var moment = require('moment');
var request = require('request');
var qs = require('querystring');
var Message = require('../models/Message');

/**
 * GET /message/
 * Retrieves all messages of the user.
 */
exports.messageGetAll = function(req, res, next) {
  Message.find({ from: req.user })
    .populate('from', ['id', 'name', 'email', 'picture'])
    .populate('to', ['id', 'name', 'email', 'picture'])
    .exec(function(err, messages) {
      res.send(messages);
    });
}

/**
 * GET /message/:id
 * Retrieves a message with the given id.
 */
exports.messageGet = function(req, res, next) {
  var msgId = req.params.id;

  Message.findById(msgId)
    .populate('from', ['id', 'name', 'email', 'picture'])
    .populate('to', ['id', 'name', 'email', 'picture'])
    .exec(function(err, message) {
      res.send(message);
    });
}

/**
 * POST /message
 * Creates a message with the given values.
 */
exports.messagePost = function(req, res, next) {
  req.assert('to', 'To cannot be blank').notEmpty();
  req.assert('message', 'Message cannot be blank').notEmpty();

  var message = new Message({
    from: req.user._id,
    to: req.body.to,
    message: req.body.message
  });

  message.save(function(err) {
    if (err) {
      res.status(500).send({ msg: 'Message couldn\'t be created.' })
    }
    res.send(message);
  });
}

/**
 * DELETE /message/:id
 * Deletes a message with the given id.
 */
exports.messageDelete = function(req, res, next) {
  var msgId = req.params.id;

  Message.findById(msgId, function(err, message) {
    if (message.from._id === req.user._id) {
      msg.remove(function(err) {
        if (err) {
          res.status(500).send({ msg: 'Message couldn\'t be deleted.' })
        }
        res.send({ msg: 'Message has been permanently deleted.' });
      });
    } else {
      res.status(403).send({ msg: 'Unauthorized.' });
    }
  });
}
