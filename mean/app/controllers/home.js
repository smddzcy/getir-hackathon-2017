angular.module('MyApp')
	.controller('HomeCtrl', function($scope, $http, $location, Event) {
    $scope.map = {
      center: { latitude: 41.0728162, longitude: 29.0089026 },
      zoom: 12,
      options: { scrollwheel: false }
    };

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position){
        $scope.map.center = {
          latitude: position.coords.latitude,
          longitude: position.coords.longitude
        };
      });
    }

	$scope.markers = [];

	Event.query(function(events) {
		events.forEach(function(e) {
      console.log(123);
			var marker = {};
			marker.id = e._id;
      if(!e.location){
        return;
      }
			marker.location = {
				latitude: Number(e.location.latitude),
				longitude: Number(e.location.longitude)
			};
      marker.event = e;
			$scope.markers.push(marker);
		});
	});
});
