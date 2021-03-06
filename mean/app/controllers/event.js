angular.module('MyApp')
  .controller('EventCtrl', function($scope, $filter, Event, $http, uiGmapGoogleMapApi, ngToast) {
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

            if (!$scope.search) {
              $scope.search = {};
            }

            $scope.event.location.latitude = lat;
            $scope.event.location.longitude = lng;

            uiGmapGoogleMapApi.then(function(maps) {
              latlng = new maps.LatLng(lat, lng);
              geocoder = new maps.Geocoder();
              geocoder.geocode({ 'latLng': latlng }, function(results, status) {
                $scope.$apply(function() {
                  $scope.event.location.name = results[0].formatted_address;
                  $scope.search = results[0].formatted_address;
                });
              });
            });

            $scope.$apply();
          }
        }
      },
      searchbox: {
        template: 'searchbox.tpl.html',
        events: {
          places_changed: function(searchBox) {
            var place = searchBox.getPlaces()[0];
            if (!place) return;

            if (!$scope.event.location) {
              $scope.event.location = {};
            }

            // Set input values automatically and put a marker.
            $scope.event.location.name = place.formatted_address;
            $scope.event.location.latitude = place.geometry.location.lat();
            $scope.event.location.longitude = place.geometry.location.lng();

            $scope.marker = {
              id: Date.now(),
              coords: $scope.event.location
            };
          }
        }
      },
    });
    $scope.search = '';

    // Get event types
    $http.get('/event-type')
      .then(function(eventTypes) {
        $scope.types = eventTypes.data;
      })
      .catch(function(err) {
        ngToast.danger(err.msg);
      });

    // Initialize the empty event object
    $scope.event = { location: {} };

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
