angular.module('MyApp')
  .controller('ContactCtrl', function($scope, Contact, ngToast) {
    $scope.sendContactForm = function() {
      Contact.send($scope.contact)
        .then(function(response) {
          ngToast.success(response.data.msg);
          $scope.contact = {};
        })
        .catch(function(response) {
          [].concat(response.data).forEach(function(msg) {
            ngToast.danger(response.data.msg);
          });
        });
    };
  });
