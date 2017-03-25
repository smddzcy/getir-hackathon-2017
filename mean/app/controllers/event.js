angular.module('MyApp')
  .controller('EventCtrl', function($scope, Event) {
    angular.extend($scope, {
      map: {
        center: { latitude: 41.0728162, longitude: 29.0089026 },
        zoom: 11,
        events: {
          click: function(map, eventName, originalEventArgs) {
            var e = originalEventArgs[0];
            var lat = e.latLng.lat();
            var lng = e.latLng.lng();
            $scope.marker = {
              id: Date.now(),
              coords: {
                latitude: lat,
                longitude: lng
              }
            };

            if (!$scope.event) {
              $scope.event = {};
            }

            if (!$scope.event.location) {
              $scope.event.location = {};
            }

            $scope.event.location.latitude = lat;
            $scope.event.location.longitude = lng;
            console.log($scope.marker);
            $scope.$apply();
          }
        }
      }
    });

    $scope.sendEventForm = function() {
      Event.save($scope.event)
        .$promise
        .then(function(response) {
          $scope.messages = {
            success: [{ msg: "Event has been created successfully." }]
          };
        })
        .catch(function(response) {
          $scope.messages = {
            error: [response.data.msg]
          };
        });
    };
  });
