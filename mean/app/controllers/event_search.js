angular.module('MyApp')
  .controller('EventSearcrhCtrl', function($scope, $http, Event, ngToast) {
    Event.query(function(events) {
      $scope.events = events;
    });
    // Get event types
    $http.get('/event-type')
      .then(function(eventTypes) {
        $scope.types = eventTypes.data;
      })
      .catch(function(err) {
        ngToast.danger(err.msg);
      });

    $scope.searchType = function() {
      $http.get('/event/search/type/' + $scope.event.type.name)
        .then(function(eventTypes) {
            $scope.events = eventTypes.data;
          },
          function(err) {
            ngToast.danger(err.data.msg);
          });
    }
    $scope.searchTime = function() {
      if (!$scope.startTime ||  !$scope.endTime) {
        $scope.messages = {
          error: [{ msg: "Both dates should be filled!" }]
        };
      }
      $http.get('/event/search/interval/' + $scope.startTime + '/' + $scope.endTime)
        .then(function(eventTypes) {
            $scope.events = eventTypes.data;
          },
          function(err) {
            ngToast.danger(err.data.msg);
          });
    }
  });
