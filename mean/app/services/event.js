angular.module('MyApp')
  .factory('Event', function($http) {
    return {
      send: function(data) {
        return $http.post('/event', data);
      }
    };
  });
