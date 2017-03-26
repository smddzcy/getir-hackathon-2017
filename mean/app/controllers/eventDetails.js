angular.module('MyApp')
  .controller('EventDetailsCtrl', function($http, $rootScope, $scope, $route,
    $location, Event, Message, socket, ngToast) {
    var eventId = $route.current.params.id;

    if ($location.search().join) {
      $http.post('/event/' + eventId + '/join')
        .then(function(res) {
          // Redirect the user to event details page
          $location.search({});
          $location.path('/event-details/' + eventId);
        })
        .catch(function(err)  {
          ngToast.danger("Event couldn't be joined.");
        });
    } else {
      Event.get({}, { id: eventId })
        .$promise
        .then(function(event) {
          $scope.event = event;
        })
        .catch(function(response) {
          ngToast.danger(response.msg);
        });
    }

    $scope.removeJoinedUser = function() {
      $http.delete('/event/' + eventId + '/join')
        .then(function(res) {
          // Redirect the user to event details page
          $location.search({});
          $location.path('/event-details/' + eventId);
        })
        .catch(function(err)  {
          ngToast.danger("Event couldn't be unjoined.");
        });
    }

    $scope.sendMessage = function(msg, scope) {
      scope.sendingMessage = true;
      Message.save({
        from: $rootScope.currentUser._id,
        to: eventId,
        message: msg
      }).$promise.then(function(message) {
        scope.message = null;
        socket.emit('message', eventId, message);
      }).catch(function(err) {
        ngToast.danger(err.msg);
      }).finally(function() {
        scope.sendingMessage = false;
      });
    }

    socket.emit('join', eventId);

    socket.on('message', function(message) {
      $scope.event.messages.push(message);
    });
  });
