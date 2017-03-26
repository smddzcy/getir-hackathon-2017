angular.module('MyApp')
  .controller('EventSearcrhCtrl', function($scope, $http , Event) {
    Event.query(function(events) {
      $scope.events = events;
      console.log(events);
    });
    // Get event types
    $http.get('/event-type')
      .then(function(eventTypes) {
        $scope.types = eventTypes.data;
  	})
	.catch(function(err) {
		ngToast.danger(err.msg);
	});

    $scope.searchType = function(){
    	$http.get('/event/search/type/'+$scope.requestedType)
	      .then(function(eventTypes) {
	      	console.log(eventTypes);
	        $scope.events = eventTypes.data;
	      })
	}
});
