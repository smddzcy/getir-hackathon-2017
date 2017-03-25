angular.module('MyApp')
  .controller('EventCtrl', function($scope, Event) {
    angular.extend($scope, {
        map: {
            center: {
                latitude: 42.3349940452867,
                longitude:-71.0353168884369
            },
            zoom: 11,
            markers: [],
            events: {
            click: function (map, eventName, originalEventArgs) {
                var e = originalEventArgs[0];
                var lat = e.latLng.lat(),lon = e.latLng.lng();
                var marker = {
                    id: Date.now(),
                    coords: {
                        latitude: lat,
                        longitude: lon
                    }
                };
                $scope.map.markers = [];
                $scope.map.markers.push(marker);
                console.log($scope.map.markers);
                $scope.$apply();
            }
        }
        }
    });
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position){
        $scope.map.center = {
          latitude: position.coords.latitude,
          longitude: position.coords.longitude
        };
      });
    }
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
