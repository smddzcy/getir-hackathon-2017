var crypto = require('crypto');
var bcrypt = require('bcrypt-nodejs');
var mongoose = require('mongoose');

var schemaOptions = {
  timestamps: true,
  toJSON: {
    virtuals: true
  }
};

var eventSchema = new mongoose.Schema({
  creator: {type: mongoose.Schema.Types.ObjectId, ref: 'User'},
  type: String,
  date: Date,
  location: {},
  joinRequests: [{type: mongoose.Schema.Types.ObjectId, ref: 'User'}],
  users: [{type: mongoose.Schema.Types.ObjectId, ref: 'User'}],
  messages: [{type: mongoose.Schema.Types.ObjectId, ref: 'Message'}]
}, schemaOptions);

var Event = mongoose.model('Event', eventSchema);

module.exports = Event;
