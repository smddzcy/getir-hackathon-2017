angular.module('MyApp')
  .controller('EventCtrl', function($scope, Event) {
    $scope.sendEventForm = function() {
      Event.send($scope.event)
        .then(function(response) {
          $scope.messages = {
            success: "Event has been created successfully."
          };
        })
        .catch(function(response) {
          $scope.messages = {
            error: [response.data.msg]
          };
        });
    };
  });
