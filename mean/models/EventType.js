var mongoose = require('mongoose');

var schemaOptions = {
  timestamps: true,
  toJSON: {
    virtuals: true
  }
};

var eventTypeSchema = new mongoose.Schema({
  name: String,
  count: Number
}, schemaOptions);

var EventType = mongoose.model('EventType', eventTypeSchema);

module.exports = EventType;
