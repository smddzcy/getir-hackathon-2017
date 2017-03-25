angular.module('MyApp')
  .controller('PublicProfileCtrl', function($scope, $routeParams, $http) {
      
      $http.get("/user/"+$routeParams.id)
        .then(function(response){
          $scope.targetUser = response.data;
          console.log(response.data);
        },
        function(err){
          // invalid user id
          console.log(err);
        });

});