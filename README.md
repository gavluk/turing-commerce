TURING BACKEND CHALLENGE "ECOMMERCE"
====================================

# Summary and Architecture review

Project architecture is built as MVC but mostly oriented to "Clean Architecture", having principle of every layer do not
know about upper layer.

In this project just three layers:

* CORE: all in package `ua.com.gavluk.turing.ecommerce.core`, contains domain entities and services (business logic);
  does not contain anything related to logic delivery channel (HTTP/WEB/REST...), so this layer could be used separately
  if, e.g. we need CLI application to manage business-entities
  * PERSISTENCE: all package `ua.com.gavluk.turing.ecommerce.core.repo`, contains repository interfaces (at this
    time as well implementation, cause there is apring-boot-data used, which creates implementations automatically)
  * STRIPE: all in package `ua.com.gavluk.turing.ecommerce.core.stripe`, contains stripe implementation for CORE's
    payment facilities; this helps easily replace stripe to some other payment provider
* API: all in package `ua.com.gavluk.turing.ecommerce.api`, contains everything related to WEB/HTTP: API controllers,
  Data Transfer Objects (DTO), JWT-token generator, error handlers (adapting exceptions to API requirements), etc.

Summary:
* CORE does not know about API, so could be used with any delivery channel
* API does not know about PERSISTENCE, so it works exclusively with business-model
* PERSISTENCE does not know about API (of course, no comments)
* CORE does not know about STRIPE, so payment provider implementation could be changed just by implementing interfaces
  and injecting implementation into CORE

**Tests**
* there are some unit-test, which run on "build" (for now not all services covered)
* there is Postman collection, which could be used both for integration testing and as easy demo for frontend developer

**API DOCS**
* [is here](./docs/API-docs.odt), left unchanged from challenge requirements 

# Install

## Building the project 
```
./gradlew clean build
```

## Running app 

### Running for development with docker-compose

```
# do it only AFTER you build the project!
docker-compose up
```

It will run three containers:
* backend app itself, which will be built from `Dockerfile` at the root of project
* db server with initial dump (original + fields length fixes for password and cart id)
* fake smtp server

Health check:

```
# backend is up and connected to DB
curl http://localhost:8080/actuator/health

# open in browser fake-smtp console
curl http://localhost:5080
telnet localhost 5025
```

Integration test: use [Postman collection](./docs/Turing-ECommerce-Challenge.postman_collection.json) using
[template for localhost](./docs/Turing-LOCAL.postman_environment.json) postman environment. On **fresh** started
docker-composer on localhost, ALL postman tests must be "green".

*You must call `docker-compose rm ; docker-compose up` to make all postman tests be "green".*


### Custom deployment / running

Set essential environment variables for DB connection and server port (default 8080)
```
DB_URL=jdbc:mysql://localhost:3306/turing?autoreconnect=true
DB_USER=turing
DB_PASS=****
SERVER_PORT=8080
SMTP_HOST=localhost
SMTP_PORT=25
SMTP_USER=
SMTP_PASSWORD=
STRIPE_API_KEY=sk_test_lomdOfxbm7QDgZWvR82UhV6D
```

see [./src/main/resources/application.properties](./src/main/resources/application.properties) for advanced settings,
e.g. if you need change SMTP timeout, set `SPRING_MAIL_PROPERTIES_MAIL_SMTP_TIMEOUT`.

Run app
```
java -jar ./build/libs/ecommerce-*.jar
```

Setup MySQL DB and user.

Run original DB script. Fix password field length:
```
ALTER TABLE customer MODIFY COLUMN password varchar(100) NOT NULL;
```

Fix cart ID length (for passing standard UUID): 
```
ALTER TABLE shopping_cart MODIFY COLUMN cart_id CHAR(36) NOT NULL;
```

## Smoke integration test

When deployed, use [Postman collection](./docs/Turing-ECommerce-Challenge.postman_collection.json) for integration test.
You need to setup Postman environment using [template for localhost](./docs/Turing-LOCAL.postman_environment.json).

It is not complete / all-covering / comprehensive test suit, but all test should be "green" when you run it by
docker-compose using "Turing-LOCALHOST" environment.


# Releases

## RELEASE 0.1.0

**Changelog**

* Implemented requirement functionality

**Install**

* See main Install section above