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
      
      $http.post("/user/rank/"+$routeParams.id,{'rank':$scope.rank})
        .then(function(response){
          $scope.targetUser.rank = response.data.user.rank;
          $scope.messages = {
            success: [{ msg: "Successfully added"}]
          };
          console.log("Successfully added");
        },
        function(err){
          console.log(err);
          $scope.messages = {
              error: [{ msg: err.data.msg }]
            };
        });      
    }
});