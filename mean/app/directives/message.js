angular.module('MyApp')
  .directive('message', function($rootScope) {
    return {
      restrict: 'E',
      replace: true,
      templateUrl: 'partials/message.html',
      scope: {
        from: '=',
        message: '='
      },
      link: function(scope, element, attributes) {
        scope.isMyMessage = (function() {
          if (typeof scope.from === 'object') {
            var fromId = scope.from._id;
          }
          return $rootScope.currentUser._id == fromId;
        })();
      }
    }
  });
