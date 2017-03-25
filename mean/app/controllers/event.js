angular.module('MyApp')
  .controller('EventCtrl', function($scope, $filter, Event) {
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
            $scope.$apply();
          }
        }
      }
    });
    // types
    $scope.types = ['fun','more fun','more more fun'];

    $scope.sendEventForm = function() {
      //$scope.event.date = $filter('date')($scope.event.date, "dd/MM/yyyy");
      Event.save($scope.event)
        .$promise
        .then(function(response) {
          ngToast.success("Event has been created successfully.");
          $scope.event = {};
        })
        .catch(function(response) {
          ngToast.danger(response.data.msg);
        });
    };
  });
