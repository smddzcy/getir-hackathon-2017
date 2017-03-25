angular.module('MyApp')
  .controller('EventDetailCtrl', function($scope, $route, Event) {
    var eventId = $route.current.params.id;

    Event.get({}, {id: eventId})
      .$promise
      .then(function(event) {
        $scope.event = event;
      })
      .catch(function(response) {
        console.log(response.data);
      });
  });
