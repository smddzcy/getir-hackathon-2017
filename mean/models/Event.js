var mongoose = require('mongoose');

var schemaOptions = {
  timestamps: true,
  toJSON: {
    virtuals: true
  }
};

var eventSchema = new mongoose.Schema({
  creator: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
  type: { type: mongoose.Schema.Types.ObjectId, ref: 'EventType' },
  startTime: Date,
  endTime: Date,
  location: {},
  users: [{ type: mongoose.Schema.Types.ObjectId, ref: 'User', unique: true }],
  messages: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Message', unique: true }]
}, schemaOptions);

var Event = mongoose.model('Event', eventSchema);

module.exports = Event;
