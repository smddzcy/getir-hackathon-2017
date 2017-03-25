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
  time: Date,
  location: {}
}, schemaOptions);

var Event = mongoose.model('Event', eventSchema);

module.exports = Event;
