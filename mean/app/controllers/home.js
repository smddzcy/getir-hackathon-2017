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

	// $http.get("http://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyBKls60hmP1Q1DLkVrt1qMMUcF2tsFJ7CQ")
	// 	.then(function(response){
	// 		console.log(response);
	// 	},
	// 	function(err){
	// 		console.log(err);
	// 		console.log("asd");
	// 	});
});
