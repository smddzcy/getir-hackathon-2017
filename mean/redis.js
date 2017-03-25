var client = require('redis').createClient({
  url: process.env.REDIS_URL
});

/**
 * Sets a user as online in redis.
 */
client.setUserAsOnline = function(user) {
  // User must be an object
  if (typeof user !== 'object') {
    return false;
  }
  console.log("Setting user as online: " + user._id);
  return client.hset('onlineUsers', user._id.toString(), JSON.stringify(user));
};

/**
 * Sets a user as offline in redis.
 */
client.setUserAsOffline = function(userId) {
  // If a user object is given, convert it to user id.
  if (typeof userId === 'object') {
    userId = userId._id;
  }

  // Check if the user id is valid.
  if (!userId || userId.length > 5) {
    return false;
  }
  console.log("Setting user as offline: " + userId);
  return client.hdel('onlineUsers', userId.toString());
};

/**
 * Gives all the online users.
 */
client.getOnlineUsers = function() {
  return client.hgetall('onlineUsers');
};

module.exports = client;
