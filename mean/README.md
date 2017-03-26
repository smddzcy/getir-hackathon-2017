# Web Application

This is the web application of WowMeet.

## REST API Endpoints

### User Endpoints

#### Login
Method Type: `POST`  
Endpoint: `/login`  
Body Params: `{
  email: "wow@meet.com",
  password: "123123"
}`  
Output: `{
  token: TOKEN_OF_THE_USER,
  user: {
    ... (fields of the user)
  }
}`

#### Signup
Method Type: `POST`  
Endpoint: `/signup`  
Body Params: `{
  email: "wow@meet.com",
  password: "123123",
  name: "Wow Meet",
  picture: PICTURE_URL
}`  
Output: `{
  token: TOKEN_OF_THE_USER,
  user: {
    ... (fields of the user)
  }
}`

#### Update Account
Method Type: `PUT`  
Endpoint: `/account`  
Headers: `Authorization: Bearer TOKEN_OF_THE_USER`  
Body Params: `{
  ... (fields to update)
}`  
Output: `{
  user: USER_ID,
  msg: STATUS_MESSAGE
}`

#### Delete Account
Method Type: `DELETE`  
Endpoint: `/account`  
Headers: `Authorization: Bearer TOKEN_OF_THE_USER`  
Body Params: `{}`  
Output: `{
  msg: STATUS_MESSAGE
}`

#### Forgot Password
Method Type: `POST`  
Endpoint: `/account`  
Body Params: `{
  email: "wow@meet.com"
}`  
Output: `{
  msg: STATUS_MESSAGE
}`


## Technologies Used
- Backend API: **Node.js**
- Frontend Framework: **AngularJS**
- CSS Framework: **Bootstrap**
- CSS Preprocessor: **Less**
- Database: **MongoDB**
- Cache: **Redis**
- Socket: **socket.io**
- Task Runner: **Gulp**
- Testing: **Karma**, **Mocha**, **Chai**
- 3rd Party APIs: **Google Maps API**, **Google Places API**

## Team
- [Samed Düzçay](https://github.com/smddzcy)
- [Murat Buldu](https://github.com/muratbuldu)
