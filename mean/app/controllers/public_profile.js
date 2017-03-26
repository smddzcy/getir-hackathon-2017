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

    $scope.sendRankForm = function(){
      // star view for visual part
      if($scope.rank > 5){
        return console.log("Not Successfully added");
      }

      $http.post("/user/rank/"+$routeParams.id,{'rank':$scope.rank})
        .then(function(response){
          $scope.targetUser.rank = response.data.user.rank;
          console.log("Successfully added");
        },
        function(err){
          console.log("Not Successfully added");
        });      
    }
});