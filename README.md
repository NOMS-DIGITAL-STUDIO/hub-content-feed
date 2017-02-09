hub-content-feed
====

REST microservice for the The Hub.
 
This service is responsible for content metadata delivery to the consumers.

Ministry of Justice.
National Offender Management Service.

Build
----
```
./gradlew clean build
```

Run
----
```
./gradlew bootRun
```


Get Content Item for a given ID endpoint
----
GET /hub-content-feed/content-items/<id> 
Retrieve a ContentItem for a given ID which is currently made up of just a title and a media uri.

Examples
----
Request:

```curl -v http://localhost:8080/hub-content-feed/content-items/<id>```

Response:
```
Status: 200
{
  "title": "hub-feature-specs:Upload Course Prospectus",
  "uri": "http://<host>/content-items/hub-feature-specs-test-prospectus1.pdf",
  "id": "589c25c1d12d27267cc7b94a"
}
```

Get Content Item endpoint
----
GET /hub-content-feed/content-items 
Retrieve a single ContentItem which is currently made up of just a title and media uri.

Examples
----
Request:

```curl -v http://localhost:8080/hub-content-feed/content-items```

Response:
```
Status: 200

{
  "contentItems": [
    {
      "title": "hub-feature-specs:Upload Course Prospectus",
      "uri": "http://<host>/content-items/hub-feature-specs-test-prospectus1.pdf",
      "id": "589c25c1d12d27267cc7b94a"
    }
  ]
}
```

Monitoring endpoint
----
GET /hub-content-feed/health

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

```
hubcontentfeed.mongodb.database - The MongoDb connection database name.
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