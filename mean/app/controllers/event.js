angular.module('MyApp')
  .controller('EventCtrl', function($scope, Event) {
    $scope.sendEventForm = function() {
      Event.save($scope.event)
        .$promise
        .then(function(response) {
          $scope.messages = {
            success: [{ msg:"Event has been created successfully." }]
          };
        })
        .catch(function(response) {
          $scope.messages = {
            error: [response.data.msg]
          };
        });
    };
  });
