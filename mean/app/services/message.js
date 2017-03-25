angular.module('MyApp')
  .factory('Message', function($resource) {
    return $resource('/message/:messageId', { messageId: '@id' });
  });
