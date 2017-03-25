angular.module('MyApp').run(['$templateCache', function($templateCache) {$templateCache.put('partials/404.html','<div class="container text-center">\n  <h1>404</h1>\n  <p>Page Not Found</p>\n</div>');
$templateCache.put('partials/contact.html','<div class="container">\n  <div class="panel">\n    <div class="panel-heading">\n      <h3 class="panel-title">Contact Form</h3>\n    </div>\n    <div class="panel-body">\n      <div ng-if="messages.error" role="alert" class="alert alert-danger">\n        <div ng-repeat="error in messages.error">{{error.msg}}</div>\n      </div>\n      <div ng-if="messages.success" role="alert" class="alert alert-success">\n        <div ng-repeat="success in messages.success">{{success.msg}}</div>\n      </div>\n      <form ng-submit="sendContactForm()" class="form-horizontal">\n        <div class="form-group">\n          <label for="name" class="col-sm-2">Name</label>\n          <div class="col-sm-8">\n            <input type="text" name="name" id="name" class="form-control" ng-model="contact.name" autofocus>\n          </div>\n        </div>\n        <div class="form-group">\n          <label for="email" class="col-sm-2">Email</label>\n          <div class="col-sm-8">\n            <input type="email" name="email" id="email" class="form-control" ng-model="contact.email">\n          </div>\n        </div>\n        <div class="form-group">\n          <label for="message" class="col-sm-2">Body</label>\n          <div class="col-sm-8">\n            <textarea name="message" id="message" rows="7" class="form-control" ng-model="contact.message"></textarea>\n          </div>\n        </div>\n        <div class="form-group">\n          <div class="col-sm-offset-2 col-sm-8">\n            <button type="submit" class="btn btn-success">Send</button>\n          </div>\n        </div>\n      </form>\n    </div>\n  </div>\n</div>');
$templateCache.put('partials/event.html','<div class="container">\n  <div class="panel">\n    <div class="panel-heading">\n      <h3 class="panel-title">Add Event</h3>\n    </div>\n    <div class="panel-body">\n      <div ng-if="messages.error" role="alert" class="alert alert-danger">\n        <div ng-repeat="error in messages.error">{{error.msg}}</div>\n      </div>\n\n      <div ng-if="messages.success" role="alert" class="alert alert-success">\n        <div ng-repeat="success in messages.success">{{success.msg}}</div>\n      </div>\n\n      <form ng-submit="sendEventForm()" class="form-horizontal">\n        <div class="form-group">\n          <label for="name" class="col-sm-2">Type</label>\n          <div class="col-sm-8">\n            <input type="text" name="type" id="type" class="form-control" ng-model="event.type" autofocus>\n          </div>\n        </div>\n\n        <div class="form-group">\n          <label for="name" class="col-sm-2">Name</label>\n          <div class="col-sm-8">\n            <input type="text" name="name" id="name" class="form-control" ng-model="event.location.name">\n          </div>\n        </div>\n\n        <div class="form-group">\n          <label for="name" class="col-sm-2">Latitude</label>\n          <div class="col-sm-8">\n            <input type="text" name="latitude" id="latitude" class="form-control" ng-model="event.location.latitude">\n          </div>\n        </div>\n\n        <div class="form-group">\n          <label for="name" class="col-sm-2">Longitude</label>\n          <div class="col-sm-8">\n            <input type="text" name="longitude" id="longitude" class="form-control" ng-model="event.location.longitude">\n          </div>\n        </div>\n\n        <div class="form-group">\n          <div class="col-sm-offset-2 col-sm-8">\n            <button type="submit" class="btn btn-success">Send</button>\n          </div>\n        </div>\n      </form>\n    </div>\n  </div>\n</div>\n');
$templateCache.put('partials/footer.html','<footer>\n  <p>\xA9 2016 Wow Team.</p>\n</footer>\n');
$templateCache.put('partials/forgot.html','<div class="container">\n  <div class="panel">\n    <div class="panel-body">\n      <div ng-if="messages.error" role="alert" class="alert alert-danger">\n        <div ng-repeat="error in messages.error">{{error.msg}}</div>\n      </div>\n      <div ng-if="messages.success" role="alert" class="alert alert-success">\n        <div ng-repeat="success in messages.success">{{success.msg}}</div>\n      </div>\n      <form ng-submit="forgotPassword()">\n        <legend>Forgot Password</legend>\n        <div class="form-group">\n          <p>Enter your email address below and we\'ll send you password reset instructions.</p>\n          <label for="email">Email</label>\n          <input type="email" name="email" id="email" placeholder="Email" class="form-control" ng-model="user.email" autofocus>\n        </div>\n        <button type="submit" class="btn btn-success">Reset Password</button>\n      </form>\n    </div>\n  </div>\n</div>');
$templateCache.put('partials/header.html','<nav ng-controller="HeaderCtrl" class="navbar navbar-default navbar-static-top">\n  <div class="container">\n    <div class="navbar-header">\n      <button type="button" data-toggle="collapse" data-target="#navbar" class="navbar-toggle collapsed">\n        <span class="sr-only">Toggle navigation</span>\n        <span class="icon-bar"></span>\n        <span class="icon-bar"></span>\n        <span class="icon-bar"></span>\n      </button>\n      <a href="/" class="navbar-brand">WowMeet</a>\n    </div>\n    <div id="navbar" class="navbar-collapse collapse">\n      <ul class="nav navbar-nav">\n        <li ng-class="{ active: isActive(\'/\')}"><a href="/">Home</a></li>\n        <li ng-class="{ active: isActive(\'/contact\')}"><a href="/contact">Contact</a></li>\n        <li ng-class="{ active: isActive(\'/event\')}"><a href="/event">Event</a></li>\n      </ul>\n      <ul ng-if="isAuthenticated()" class="nav navbar-nav navbar-right">\n        <li class="dropdown">\n          <a href="#" data-toggle="dropdown" class="navbar-avatar dropdown-toggle">\n            <img ng-src="{{currentUser.picture || currentUser.gravatar}}"> {{currentUser.name || currentUser.email || currentUser.id}} <i class="caret"></i>\n          </a>\n          <ul class="dropdown-menu">\n            <li><a href="/account">My Account</a></li>\n            <li class="divider"></li>\n            <li><a href="#" ng-click="logout()"}>Logout</a></li>\n          </ul>\n        </li>\n      </ul>\n      <ul ng-if="!isAuthenticated()" class="nav navbar-nav navbar-right">\n        <li ng-class="{ active: isActive(\'/login\')}"><a href="/login">Log in</a></li>\n        <li ng-class="{ active: isActive(\'/signup\')}"><a href="/signup">Sign up</a></li>\n      </ul>\n    </div>\n  </div>\n</nav>\n');
$templateCache.put('partials/home.html','<div class="container-fluid">\n  <div class="row">\n    <ui-gmap-google-map center=\'map.center\' zoom=\'map.zoom\' options=\'map.options\'>\n    \t<ui-gmap-marker idKey=\'marker.id\'\n\t        \t\t\tcoords=\'marker.location\'\n\t        \t\t\tclick=\'marker.isClicked = true\'\n\t        \t\t\tng-repeat=\'marker in markers\'>\n\t        <ui-gmap-window coords=\'marker.location\'\n\t        \t\t\t\tshow=\'marker.isClicked\'\n\t\t\t\t\t        closeClick=\'marker.isClicked = false\'>\n\t\t        <div id="content">\n\t\t\t\t\t<div id="siteNotice"></div>\n\t\t\t\t\t<h1 id="firstHeading" class="firstHeading">{{ marker.name\xA0}}</h1>\n\t\t\t\t\t<div id="bodyContent">\n\t\t\t\t\t\t<p>Wow</p>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</ui-gmap-window>\n\t\t</ui-gmap-marker>\n    </ui-gmap-google-map>\n  </div>\n</div>\n');
$templateCache.put('partials/login.html','<div class="container login-container">\n  <div class="panel">\n    <div class="panel-body">\n      <div ng-if="messages.error" role="alert" class="alert alert-danger">\n        <div ng-repeat="error in messages.error">{{error.msg}}</div>\n      </div>\n      <form ng-submit="login()">\n        <legend>Log In</legend>\n        <div class="form-group">\n          <label for="email">Email</label>\n          <input type="email" name="email" id="email" placeholder="Email" class="form-control" ng-model="user.email" autofocus>\n        </div>\n        <div class="form-group">\n          <label for="password">Password</label>\n          <input type="password" name="password" id="password" placeholder="Password" class="form-control" ng-model="user.password">\n        </div>\n        <div class="form-group"><a href="/forgot"><strong>Forgot your password?</strong></a></div>\n        <button type="submit" class="btn btn-success">Log in</button>\n      </form>\n      <div class="hr-title"><span>or</span></div>\n      <div class="btn-toolbar text-center">\n        <button class="btn btn-facebook" ng-click="authenticate(\'facebook\')">Sign in with Facebook</button>\n        <button class="btn btn-twitter" ng-click="authenticate(\'twitter\')">Sign in with Twitter</button>\n        <button class="btn btn-google" ng-click="authenticate(\'google\')">Sign in with Google</button>\n        <button class="btn btn-github" ng-click="authenticate(\'github\')">Sign in with Github</button>\n      </div>\n    </div>\n  </div>\n  <p class="text-center">\n    Don\'t have an account? <a href="/signup"><strong>Sign up</strong></a>\n  </p>\n</div>\n');
$templateCache.put('partials/profile.html','<div class="container">\n  <div class="panel">\n    <div class="panel-body">\n      <div ng-if="messages.error" role="alert" class="alert alert-danger">\n        <div ng-repeat="error in messages.error">{{error.msg}}</div>\n      </div>\n      <div ng-if="messages.success" role="alert" class="alert alert-success">\n        <div ng-repeat="success in messages.success">{{success.msg}}</div>\n      </div>\n      <form ng-submit="updateProfile()" class="form-horizontal">\n        <legend>Profile Information</legend>\n        <div class="form-group">\n          <label for="email" class="col-sm-3">Email</label>\n          <div class="col-sm-7">\n            <input type="email" name="email" id="email" class="form-control" ng-model="profile.email">\n          </div>\n        </div>\n        <div class="form-group">\n          <label for="name" class="col-sm-3">Name</label>\n          <div class="col-sm-7">\n            <input type="text" name="name" id="name" class="form-control" ng-model="profile.name">\n          </div>\n        </div>\n        <div class="form-group">\n          <label class="col-sm-3">Gender</label>\n          <div class="col-sm-4">\n            <label class="radio-inline radio col-sm-4">\n              <input type="radio" name="gender" value="male" ng-checked="profile.gender === \'male\'"><span>Male</span>\n            </label>\n            <label class="radio-inline col-sm-4">\n              <input type="radio" name="gender" value="female" ng-checked="profile.gender === \'female\'"><span>Female</span>\n            </label>\n          </div>\n        </div>\n        <div class="form-group">\n          <label for="location" class="col-sm-3">Location</label>\n          <div class="col-sm-7">\n            <input type="text" name="location" id="location" class="form-control" ng-model="profile.location">\n          </div>\n        </div>\n        <div class="form-group">\n          <label for="website" class="col-sm-3">Website</label>\n          <div class="col-sm-7">\n            <input type="text" name="website" id="website" class="form-control" ng-model="profile.website">\n          </div>\n        </div>\n        <div class="form-group">\n          <label class="col-sm-3">Gravatar</label>\n          <div class="col-sm-4">\n            <img ng-src="{{profile.gravatar}}" class="profile" width="100" height="100">\n          </div>\n        </div>\n        <div class="form-group">\n          <div class="col-sm-offset-3 col-sm-4">\n            <button type="submit" class="btn btn-success">Update Profile</button>\n          </div>\n        </div>\n      </form>\n    </div>\n  </div>\n  <div class="panel">\n    <div class="panel-body">\n      <form ng-submit="changePassword()" class="form-horizontal">\n        <legend>Change Password</legend>\n        <div class="form-group">\n          <label for="password" class="col-sm-3">New Password</label>\n          <div class="col-sm-7">\n            <input type="password" name="password" id="password" class="form-control" ng-model="profile.password">\n          </div>\n        </div>\n        <div class="form-group">\n          <label for="confirm" class="col-sm-3">Confirm Password</label>\n          <div class="col-sm-7">\n            <input type="password" name="confirm" id="confirm" class="form-control" ng-model="profile.confirm">\n          </div>\n        </div>\n        <div class="form-group">\n          <div class="col-sm-4 col-sm-offset-3">\n            <button type="submit" class="btn btn-success">Change Password</button>\n          </div>\n        </div>\n      </form>\n    </div>\n  </div>\n  <div class="panel">\n    <div class="panel-body">\n      <div class="form-horizontal">\n        <legend>Linked Accounts</legend>\n        <div class="form-group">\n          <div class="col-sm-offset-3 col-sm-4">\n            <p>\n              <a href="#" ng-click="unlink(\'facebook\')" ng-if="currentUser.facebook" class="text-danger">Unlink your Facebook account</a>\n              <a href="#" ng-click="link(\'facebook\')" ng-if="!currentUser.facebook">Link your Facebook account</a>\n            </p>\n            <p>\n              <a href="#" ng-click="unlink(\'twitter\')" ng-if="currentUser.twitter" class="text-danger">Unlink your Twitter account</a>\n              <a href="#" ng-click="link(\'twitter\')" ng-if="!currentUser.twitter">Link your Twitter account</a>\n            </p>\n            <p>\n              <a href="#" ng-click="unlink(\'google\')" ng-if="currentUser.google" class="text-danger">Unlink your Google account</a>\n              <a href="#" ng-click="link(\'google\')" ng-if="!currentUser.google">Link your Google account</a>\n            </p>\n            <p>\n              <a href="#" ng-click="unlink(\'github\')" ng-if="currentUser.github" class="text-danger">Unlink your Github account</a>\n              <a href="#" ng-click="link(\'github\')" ng-if="!currentUser.github">Link your Github account</a>\n            </p>\n          </div>\n        </div>\n      </div>\n    </div>\n  </div>\n  <div class="panel">\n    <div class="panel-body">\n      <form ng-submit="deleteAccount()" class="form-horizontal">\n        <legend>Delete Account</legend>\n        <div class="form-group">\n          <p class="col-sm-offset-3 col-sm-9">You can delete your account, but keep in mind this action is irreversible.</p>\n          <div class="col-sm-offset-3 col-sm-9">\n            <button type="submit" class="btn btn-danger">Delete my account</button>\n          </div>\n        </div>\n      </form>\n    </div>\n  </div>\n</div>\n');
$templateCache.put('partials/reset.html','<div class="container">\n  <div class="panel">\n    <div class="panel-body">\n      <div ng-if="messages.error" role="alert" class="alert alert-danger">\n        <div ng-repeat="error in messages.error">{{error.msg}}</div>\n      </div>\n      <div ng-if="messages.success" role="alert" class="alert alert-success">\n        <div ng-repeat="success in messages.success">{{success.msg}}</div>\n      </div>\n        <form ng-submit="resetPassword()">\n          <legend>Reset Password</legend>\n          <div class="form-group">\n            <label for="password">New Password</label>\n            <input type="password" name="password" id="password" placeholder="New password" class="form-control" ng-model="user.password" autofocus>\n          </div>\n          <div class="form-group">\n            <label for="confirm">Confirm Password</label>\n            <input type="password" name="confirm" id="confirm" placeholder="Confirm password" class="form-control" ng-model="user.confirm">\n          </div>\n          <div class="form-group">\n            <button type="submit" class="btn btn-success">Change Password</button>\n          </div>\n        </form>\n    </div>\n  </div>\n</div>\n');
$templateCache.put('partials/signup.html','<div class="container login-container">\n  <div class="panel">\n    <div class="panel-body">\n      <div ng-if="messages.error" role="alert" class="alert alert-danger">\n        <div ng-repeat="error in messages.error">{{error.msg}}</div>\n      </div>\n      <form ng-submit="signup()">\n        <legend>Create an account</legend>\n        <div class="form-group">\n          <label for="email">Email</label>\n          <input type="email" name="email" id="email" placeholder="Email" class="form-control" ng-model="user.email">\n        </div>\n        <div class="form-group">\n          <label for="password">Password</label>\n          <input type="password" name="password" id="password" placeholder="Password" class="form-control" ng-model="user.password">\n        </div>\n        <div class="form-group">\n          <small class="text-muted">By signing up, you agree to the <a href="/">Terms of Service</a>.</small>\n        </div>\n        <button type="submit" class="btn btn-success">Create an account</button>\n      </form>\n      <div class="hr-title"><span>or</span></div>\n      <div class="btn-toolbar text-center">\n        <button class="btn btn-facebook" ng-click="authenticate(\'facebook\')">Sign in with Facebook</button>\n        <button class="btn btn-twitter" ng-click="authenticate(\'twitter\')">Sign in with Twitter</button>\n        <button class="btn btn-google" ng-click="authenticate(\'google\')">Sign in with Google</button>\n        <button class="btn btn-github" ng-click="authenticate(\'github\')">Sign in with Github</button>\n      </div>\n    </div>\n  </div>\n  <p class="text-center">\n    Already have an account? <a href="/login"><strong>Log in</strong></a>\n  </p>\n</div>\n');}]);