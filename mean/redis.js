var client = require('redis').createClient({
  url: process.env.REDIS_URL
});

/**
 * The missing `hvals` function in node-redis.
 */
client.hvals = function(key, callback) {
  client.hgetall(key, function(obj) {
    callback(Object.values(obj));
  });
}

/**
 * Set a user as online in redis.
 */
client.setUserAsOnline = function(user) {
  return client.hset('onlineUsers', user._id.toString(), JSON.stringify(user));
};

/**
 * Set a user as offline in redis.
 */
client.setUserAsOffline = function(userId) {
  return client.hdel('onlineUsers', userId.toString());
};

/**
 * Get all the online users.
 */
client.getOnlineUsers = function() {
  return client.hgetall('onlineUsers');
};

/**
 * Add an event to cache.
 */
client.addEvent = function(event) {
  return client.hset('events', event._id.toString(), JSON.stringify(event));
}

/**
 * Add multiple events to cache.
 */
client.addEvents = function(events) {
  return [].concat(events).forEach(client.addEvent);
}

/**
 * Delete an event from cache by id.
 */
client.addEvent = function(id) {
  return client.hdel('events', id);
}

/**
 * Get a single event by id.
 */
client.getEventById = function(id) {
  return client.hget('events', id);
}

/**
 * Get all events or the events in a radius.
 * If `lat` and `lng` are not given, it gives all the events.
 * Returns a Promise that resolves with the events.
 */
client.getEvents = function(lat, lng, radius) {
  return new Promise(function(resolve, reject) {
    // If no lat and lng is given, return all the events.
    if (!lat && !lng) {
      return client.hvals('events', resolve);
    }

    // Find the events in the given area.
    radius = radius || 0.5;
    client.hvals('events', function(events) {
      // Get events in the radius.
      var eventsInRadius = events.filter(function(event) {
        // If event has no location info, do not include it.
        if (!event.location) { return false; }
        return Math.sqrt(Math.pow(event.location.latitude - lat, 2) +
                         Math.pow(event.location.longitude - lng, 2)) < radius;
      });

      // Resolve the promise with filtered events.
      resolve(eventsInRadius);
    });
  });
}

module.exports = client;
