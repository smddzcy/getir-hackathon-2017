## Android Application
With this application, users can register, login, create events, see created events, 
chat with people in the same event and join event.

### Usage
Here is the steps for the typical user who uses the application in normal usecase.
1 - Login / Register
2 - Main Screen
In here our user can see the events created by others users in a list or in the map.
Also, the user can filter the result by place and radius.
From here, user can go to two screens.
  2.1 - Create Event Screen
  In here, user can create a new event by choosing a place, date, time and activity type.
  2.2 - Event Info Screen
  In here, user can see the info of the event and chat with other users that looking the same event like a group chat.
  Also, user can "Join" the event which means user is also joining the event. After joining, user can see other
  users who also joined the same event.
  
In main screen, user can use the navigation drawer and can log out.

### Application Architecture
This application uses Model View Presenter (MVP) architecture. 

### Third Party Libraries
1 - OkHttp 3.6.0 for http requests  
2 - RxAndroid 2.0.1 and RxJava 2.0.1 for server connection and better error handling  
3 - Gson 2.6.2 for parsing json data to POJO  
4 - ButterKnife 8.5.1 for injecting views  
5 - Picasso 2.5.2 for handling images from the server  
6 - Google Play Services for maps, location and places apis.  
7 - Android support library for backwards compatibility.  

