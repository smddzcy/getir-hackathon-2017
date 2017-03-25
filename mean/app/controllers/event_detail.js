angular.module('MyApp')
  .controller('EventDetailCtrl', function($http, $scope, $route, $location, Event) {
    var eventId = $route.current.params.id;

    if ($location.search().join) {
      $http.post('/event/' + eventId + '/join')
        .then(function(res) {
          // Redirect the user to event details page
          $location.search({});
          $location.path('/event-details/' + eventId);
        })
        .catch(function(err) {
          console.log("Event couldn't be joined.");
        });
    } else {
      Event.get({}, {id: eventId})
        .$promise
        .then(function(event) {
          $scope.event = event;
        })
        .catch(function(response) {
          console.log(response.data);
        });
    }

  });
