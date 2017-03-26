angular.module('MyApp')
  .controller('PublicProfileCtrl', function($scope, $routeParams, $http, ngToast) {

    $http.get("/user/" + $routeParams.id)
      .then(function(response) {
          $scope.targetUser = response.data;
        },
        function(err) {
          ngToast.danger(err.data.msg);
        });

    $scope.sendRankForm = function() {

      $http.post("/user/rank/" + $routeParams.id, { 'rank': $scope.rank })
        .then(function(response) {
            $scope.targetUser.rank = response.data.user.rank;
            ngToast.success("Your rate has been successfully added.");
          },
          function(err) {
            ngToast.danger(err.data.msg);
          });
    }
  });
