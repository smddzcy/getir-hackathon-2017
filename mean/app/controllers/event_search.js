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
    	console.log($scope.event.type.name);
    	$http.get('/event/search/type/'+$scope.event.type.name)
	      .then(function(eventTypes) {
	      	$scope.events = eventTypes.data;
	      })
	}
	$scope.searchTime = function(){
    	console.log($scope.startTime);
    	console.log($scope.endTime);
    	$http.get('/event/search/interval/'+$scope.startTime+'/'+$scope.endTime)
	      .then(function(eventTypes) {
	      	$scope.events = eventTypes.data;
	      })
	}
});
