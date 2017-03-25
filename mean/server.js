var express = require('express');
var path = require('path');
var logger = require('morgan');
var compression = require('compression');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var expressValidator = require('express-validator');
var dotenv = require('dotenv');
var mongoose = require('mongoose');
var jwt = require('jsonwebtoken');
var moment = require('moment');
var request = require('request');

// Load environment variables from .env file
dotenv.load();

// Models
var User = require('./models/User');

// Controllers
var userController = require('./controllers/user');
var contactController = require('./controllers/contact');
var eventController = require('./controllers/event');
var messageController = require('./controllers/message');

var app = express();

/**
 * Database connection
 */
mongoose.connect(process.env.MONGOLAB_URI);
mongoose.connection.on('error', function() {
  console.log('MongoDB Connection Error. Please make sure that MongoDB is running.');
  process.exit(1);
});

/**
 * Express settings
 */
app.set('port', process.env.PORT || 3000);
app.use(compression());
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(expressValidator());
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

/**
 * Authenticate the user by token.
 * First decrypt the authorization toket by the TOKEN_SECRET,
 * then query the user and put it to request for the next handlers.
 */
app.use(function(req, res, next) {
  req.isAuthenticated = function() {
    var token = (req.headers.authorization && req.headers.authorization.split(' ')[1]) || req.cookies.token;
    try {
      return jwt.verify(token, process.env.TOKEN_SECRET);
    } catch (err) {
      return false;
    }
  };

  if (req.isAuthenticated()) {
    var payload = req.isAuthenticated();
    User.findById(payload.sub, function(err, user) {
      req.user = user;
      next();
    });
  } else {
    next();
  }
});

// User endpoints
app.post('/login', userController.loginPost);
app.post('/signup', userController.signupPost);
app.put('/account', userController.ensureAuthenticated, userController.accountPut);
app.delete('/account', userController.ensureAuthenticated, userController.accountDelete);
app.post('/forgot', userController.forgotPost);
app.post('/reset/:token', userController.resetPost);
app.get('/unlink/:provider', userController.ensureAuthenticated, userController.unlink);
app.post('/auth/facebook', userController.authFacebook);
app.get('/auth/facebook/callback', userController.authFacebookCallback);
app.post('/auth/google', userController.authGoogle);
app.get('/auth/google/callback', userController.authGoogleCallback);
app.post('/auth/twitter', userController.authTwitter);
app.get('/auth/twitter/callback', userController.authTwitterCallback);
app.post('/auth/github', userController.authGithub);
app.get('/auth/github/callback', userController.authGithubCallback);

app.get('/user/:id', userController.userGet);

// Event endpoints
app.get('/event/:id', eventController.eventGet);
app.post('/event/:id/join', userController.ensureAuthenticated, eventController.eventJoinPost);
app.delete('/event/:id/join', userController.ensureAuthenticated, eventController.eventJoinDelete);
app.post('/event', userController.ensureAuthenticated, eventController.eventPost);
app.put('/event/:id', userController.ensureAuthenticated, eventController.eventPut);
app.delete('/event/:id', userController.ensureAuthenticated, eventController.eventDelete);
app.get('/event/:lat?/:lng?/:radius?', eventController.eventGetAll);

// Contact endpoints
app.post('/contact', contactController.contactPost);

// Message endpoints
app.get('/message/:id', messageController.messageGet);
app.post('/message', userController.ensureAuthenticated, messageController.messagePost);
app.delete('/message/:id', userController.ensureAuthenticated, messageController.messageDelete);

app.get('/', function(req, res) {
  res.sendFile(path.join(__dirname, 'app', 'index.html'));
});

/**
 * Leave other endpoints to AngularJS
 */
app.get('*', function(req, res) {
  res.redirect('/#' + req.originalUrl);
});

// Production error handler
if (app.get('env') === 'production') {
  app.use(function(err, req, res, next) {
    console.error(err.stack);
    res.sendStatus(err.status || 500);
  });
}

app.listen(app.get('port'), function() {
  console.log('Express server listening on port ' + app.get('port'));
});

module.exports = app;
