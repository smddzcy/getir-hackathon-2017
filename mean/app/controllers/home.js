angular.module('MyApp')
	.controller('HomeCtrl', function($scope, Event) {
		$scope.map = {
			center: { latitude: 11, longitude: 11 },
			zoom: 8,
			options: { scrollwheel: false }
		};
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