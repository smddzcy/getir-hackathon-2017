var mongoose = require('mongoose');

var schemaOptions = {
  timestamps: true,
  toJSON: {
    virtuals: true
  }
};

var messageSchema = new mongoose.Schema({
  from: { type: mongoose.Schema.Types.ObjectId, ref: 'User', index: true },
  to: { type: String, index: true },
  message: String
}, schemaOptions);

var Message = mongoose.model('Message', messageSchema);

module.exports = Message;
