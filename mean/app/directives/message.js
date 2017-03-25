angular.module('MyApp')
  .directive('message', function($rootScope) {
    return {
      restrict: 'E',
      replace: true,
      template: 'partials/message.html',
      scope: {
        from: '=',
        message: '='
      },
      link: function(scope, element, attributes) {
        scope.isMyMessage = $rootScope.currentUser._id === from._id;
      }
    }
  });
