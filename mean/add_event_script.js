#!/usr/local/bin/node

const axios = require('axios');
const eventTypes = [
  "58d6be769fced9f69a929ae2", "58d6be789fced9f69a929ae3", "58d6be7b9fced9f69a929ae4",
  "58d6be7f9fced9f69a929ae5", "58d6be849fced9f69a929ae6", "58d6be879fced9f69a929ae7"
];
const token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnZXRpci1oYWNrYXRob24tMjAxNy13b3ctdGVhbS5oZXJva3VhcHAuY29tIiwic3ViIjoiNThkNmJlNGM5ZmNlZDlmNjlhOTI5YWUxIiwiaWF0IjoxNDkwNDc0MDYwLCJleHAiOjE0OTEwNzg4NjB9.FYhCACgBr0W4mA7-GjliYrzr6kRwH0m-Ak2Ppd0bdVk";
let i = 0;

const getRandomFloat = (min, max) => Math.random() * (max - min) + min;
const getRandomSign = () => Math.random() > 0.5 ? -1 : 1;
const getRandomLatitude = () => getRandomFloat(41.025, 41.25);
const getRandomLongitude = () => getRandomFloat(28.5, 29.38);
const getRandomEventType = () => eventTypes[(Math.random() * (eventTypes.length - 1)) | 0];
const getRandomStartTime = () => (new Date(Date.now() - Math.random() * 1000)).toISOString();
const getRandomEndTime = () => (new Date(Date.now() + Math.random() * 1000)).toISOString();

const getRandomLocation = () => {
  return new Promise(function(resolve, reject) {
    const lat = getRandomLatitude();
    const lng = getRandomLongitude();

    const placesApiLink = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
      lat + "," + lng + "&key=AIzaSyBx2deDpY-SRqsh9lfNO8y47YLIrStwalk";

    axios.get(placesApiLink).then(function(resp) {
      // If location has no name, try again with another coordinate.
      if (!resp.data.results[0]) {
        return getRandomLocation().then(resolve).catch(reject);
      }

      // Resolve the promise with location data.
      resolve({
        latitude: lat,
        longitude: lng,
        name: resp.data.results[0].address_components[0].long_name
      });
    }).catch(reject);
  });
};

// Create an instance with authorization token and base url.
var http = axios.create({
  baseURL: 'http://localhost:3000/',
  headers: { 'Authorization': token }
});

const createRandomEvents = (numberOfEventsToCreate) => {
  getRandomLocation()
    .then(function(location) {
      http.post('/event', {
        type: getRandomEventType(),
        startTime: getRandomStartTime(),
        endTime: getRandomEndTime(),
        location: location
      }).then(function(resp) {
        console.log(resp.data);
        console.log("\n" + i + "-------- " + i + "\n\n\n");
        if (++i < numberOfEventsToCreate) {
          createRandomEvents(numberOfEventsToCreate);
        }
      });
    })
    .catch(console.log);
};

createRandomEvents(100);
