angular.module('MyApp')
  .factory('Event', function($resource) {
    return $resource('/event/:eventId', { eventId: '@id' });
  });
