angular.module('MyApp', ['ngRoute', 'ngResource', 'satellizer',
  'uiGmapgoogle-maps', 'ngSanitize', 'ngAnimate', 'ngToast', 'btford.socket-io'])
  .config(function($routeProvider, $locationProvider, $authProvider) {
    $locationProvider.html5Mode(true);

    $routeProvider
      .when('/', {
        templateUrl: 'partials/home.html',
        controller: 'HomeCtrl'
      })
      .when('/contact', {
        templateUrl: 'partials/contact.html',
        controller: 'ContactCtrl'
      })
      .when('/create-event', {
        templateUrl: 'partials/event.html',
        controller: 'EventCtrl'
      })
      .when('/login', {
        templateUrl: 'partials/login.html',
        controller: 'LoginCtrl',
        resolve: { skipIfAuthenticated: skipIfAuthenticated }
      })
      .when('/signup', {
        templateUrl: 'partials/signup.html',
        controller: 'SignupCtrl',
        resolve: { skipIfAuthenticated: skipIfAuthenticated }
      })
      .when('/account', {
        templateUrl: 'partials/profile.html',
        controller: 'ProfileCtrl',
        resolve: { loginRequired: loginRequired }
      })
      .when('/forgot', {
        templateUrl: 'partials/forgot.html',
        controller: 'ForgotCtrl',
        resolve: { skipIfAuthenticated: skipIfAuthenticated }
      })
      .when('/reset/:token', {
        templateUrl: 'partials/reset.html',
        controller: 'ResetCtrl',
        resolve: { skipIfAuthenticated: skipIfAuthenticated }
      })
      .when('/event-details/:id/', {
        templateUrl: 'partials/event_details.html',
        controller: 'EventDetailCtrl'
      })
      .when('/public-profile/:id', {
        templateUrl: 'partials/public_profile.html',
        controller: 'PublicProfileCtrl',
        resolve: { loginRequired: loginRequired }
      })
      .otherwise({
        templateUrl: 'partials/404.html'
      });

    $authProvider.loginUrl = '/login';
    $authProvider.signupUrl = '/signup';
    $authProvider.facebook({
      url: '/auth/facebook',
      clientId: '543731272417479',
      redirectUri: 'https://getir-hackathon-2017-wow-team.herokuapp.com/auth/facebook/callback'
    });
    $authProvider.google({
      url: '/auth/google',
      clientId: '7480545046-eb71sosc5ut720e7tcj87cgs5cqkl9ns.apps.googleusercontent.com'
    });
    $authProvider.twitter({
      url: '/auth/twitter'
    });
    $authProvider.github({
      url: '/auth/github',
      clientId: '205acb3615ae3357127e'
    });

    function skipIfAuthenticated($location, $auth) {
      if ($auth.isAuthenticated()) {
        $location.path('/');
      }
    }

    function loginRequired($location, $auth) {
      if (!$auth.isAuthenticated()) {
        $location.path('/login');
      }
    }
  })
  .run(function($rootScope, $window) {
    if ($window.localStorage.user) {
      $rootScope.currentUser = JSON.parse($window.localStorage.user);
    }
  })
  .config(['ngToastProvider', function(ngToastProvider) {
    ngToastProvider.configure({
      animation: 'fade'
    });
  }])
  .config(function(uiGmapGoogleMapApiProvider) {
    uiGmapGoogleMapApiProvider.configure({
      key: 'AIzaSyBupogsGuOJ1ckMMIM9K4JsrSl8vksNwG4',
      v: '3.20', //defaults to latest 3.X anyhow
      libraries: 'geometry,visualization'
    });
  })
  .factory('socket', function (socketFactory) {
    return socketFactory();
  });
