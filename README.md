hub-content-feed
====

REST microservice for the The Hub.
 
This service is responsible for content metadata delivery to the consumers.

Ministry of Justice.
National Offender Management Service.

Get Content Item endpoint
----
Get /content-items 
Retrieve a ContentItem which is currently made up of just a title.

Examples
----
Request:

```curl -v http://localhost:8080/hub-content-feed/content-items/<id>```

Response:
```
*   Trying ::1...
* Connected to localhost (::1) port 8080 (#0)
> GET /hub-content-feed/content-items/587cd2f44c64771de682d0d1 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.43.0
> Accept: */*
>
< HTTP/1.1 200
< X-Application-Context: application
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Mon, 16 Jan 2017 14:19:27 GMT
<
* Connection #0 to host localhost left intact
{"title":"hub-content-feed-Call Rest Service"}%
```

Monitoring endpoint
----
GET /health

e.g.
```
http://localhost:8080/hub-content-feed/health
http://hub-content-feed.herokuapp.com/hub-content-feed/health
```
Environment variable required by the application
----
```
hubcontentfeed.mongodb.connectionurl - The MongoDb connection string. Defaults to mongodb://localhost:27017
e.g. mongodb:foo:<key>==@bar.documents.azure.com:10250/?ssl=true
```

Environment variable required by the functional tests
----
```
deployedURL - The hostname that the application is running on. Defaults to 'localhost'
e.g. hub-content_feed.herokuapp.com
```
```
mongoDbURL - The mongo database url for the application. Defaults to mongodb://localhost:27017
e.g. mongodb:<user>:<key>==@bar.documents.azure.com:10250/?ssl=true
``` 