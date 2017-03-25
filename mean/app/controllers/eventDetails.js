angular.module('MyApp')
  .controller('EventDetailsCtrl', function($http, $rootScope, $scope, $route,
    $location, Event, Message, socket) {
    var eventId = $route.current.params.id;

    if ($location.search().join) {
      $http.post('/event/' + eventId + '/join')
        .then(function(res) {
          // Redirect the user to event details page
          $location.search({});
          $location.path('/event-details/' + eventId);
        })
        .catch(function(err)  {
          console.log("Event couldn't be joined.");
        });
    } else {
      Event.get({}, { id: eventId })
        .$promise
        .then(function(event) {
          $scope.event = event;
        })
        .catch(function(response) {
          console.log(response.data);
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
          console.log("Event couldn't be unjoined.");
        });
    }

    $scope.sendMessage = function(msg) {
      $scope.sendingMessage = true;

      Message.save({
        from: $rootScope.currentUser._id,
        to: eventId,
        message: msg
      }).$promise.then(function(message) {
        socket.emit('message', eventId, message);
        $scope.message = '';
      }).catch(function(err) {
        console.log(err);
      }).finally(function() {
        $scope.sendingMessage = false;
      });
    }

    socket.emit('join', eventId);

    socket.on('message', function(message) {
      $scope.event.messages.push(message);
    });
  });
