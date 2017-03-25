angular.module('MyApp')
	.controller('HomeCtrl', function($scope, Event) {
<<<<<<< HEAD
		$scope.map = {
			center: { latitude: 11, longitude: 11 },
			zoom: 8,
			options: { scrollwheel: false }
		};
=======
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

>>>>>>> 56ac0db2601cfa9556177f68b135e4b3db550b6d
		$scope.markers = [];

		Event.query(function(events) {
			events.forEach(function(e) {
				var marker = {};
				marker.id = e._id;
				marker.location = {
					latitude: Number(e.location.latitude),
					longitude: Number(e.location.longitude)
				};
				marker.name = "Type: " + (e.type ? e.type : 'No type') + ", Name: " + (e.location.name ? e.location.name : 'No name');
				marker.date = e.date;
				$scope.markers.push(marker);
			});
		});

	});