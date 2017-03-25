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
				console.log(e);
				var marker = {};
				marker.id = e._id;
				marker.location = {
					latitude: Number(e.location.latitude),
					longitude: Number(e.location.longitude)
				};
	      marker.event = e;
				$scope.markers.push(marker);
			});
		});

		$scope.joinEvent = function(eventId) {
			console.log(eventId);
			// $http.post('/event/:id/join', { id: eventId })
			// 	.$promise
			// 	.then(function(res) {
			// 		// Redirect the user to event details page
			// 		$location.path('/event-details/' + eventId);
			// 	})
			// 	.catch(function(err) {
			// 		console.log("Event couldn't be joined.");
			// 	});
		};
});
