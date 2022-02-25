# Vehicles API

A REST API to maintain vehicle data and to provide a complete
view of vehicle details including price and address.

## Features

- REST API exploring the main HTTP verbs and features
- Hateoas
- Custom API Error handling using `ControllerAdvice`
- Swagger API docs
- HTTP WebClient
- MVC Test
- Automatic model mapping

## Instructions

#### TODOs

- Implement the `TODOs` within the `CarService.java` and `CarController.java`  files
- Add additional tests to the `CarControllerTest.java` file based on the `TODOs`
- Implement API documentation using Swagger

# [CarService.java](./src/main/java/com/udacity/vehicles/service/CarService.java)
1. reactive programming, mono and flux: [Java反应式框架Reactor中的Mono和Flux - SegmentFault 思否](https://segmentfault.com/a/1190000024499748###)
    1. webclient: [Spring Boot WebClient Cheat Sheet | by Stanislav Vain | The Startup | Medium](https://medium.com/swlh/spring-boot-webclient-cheat-sheet-5be26cfa3e)
    2. uri builder: [使用UriBuilder快速创建URI_最佳 Java 编程-CSDN博客](https://blog.csdn.net/dnc8371/article/details/106699735)
2. optional to solve NPE(null point exception), .orElseThrow: [Throw Exception in Optional in Java 8 | Baeldung](https://www.baeldung.com/java-optional-throw-exception)

we can also use PriceClient and MapsClient to use WebClient to get our data(recommend) 

# CarController.java
- [controller](src/main/java/com/udacity/vehicles/api/CarController.java)
- @RestController

# CarControllerTest.jav additional test
- [CarControllerTest](src/test/java/com/udacity/vehicles/api/CarControllerTest.java)

# documentation
- [pom.xml](./pom.xml)
- [SwaggerConfig](src/main/java/com/udacity/vehicles/config/SwaggerConfig.java)
- [controller](src/main/java/com/udacity/vehicles/api/CarController.java)

#### Run the Code

To properly run this application you need to start the Orders API and
the Service API first.


```
$ mvn clean package
```

```
$ java -jar target/vehicles-api-0.0.1-SNAPSHOT.jar
```

Import it in your favorite IDE as a Maven Project.



## Operations

Swagger UI: http://localhost:8080/swagger-ui.html

### Create a Vehicle

`POST` `/cars`
```json
{
   "condition":"USED",
   "details":{
      "body":"sedan",
      "model":"Impala",
      "manufacturer":{
         "code":101,
         "name":"Chevrolet"
      },
      "numberOfDoors":4,
      "fuelType":"Gasoline",
      "engine":"3.6L V6",
      "mileage":32280,
      "modelYear":2018,
      "productionYear":2018,
      "externalColor":"white"
   },
   "location":{
      "lat":40.73061,
      "lon":-73.935242
   }
}
```

### Retrieve a Vehicle

`GET` `/cars/{id}`

This feature retrieves the Vehicle data from the database
and access the Pricing Service and Boogle Maps to enrich 
the Vehicle information to be presented

### Update a Vehicle

`PUT` `/cars/{id}`

```json
{
   "condition":"USED",
   "details":{
      "body":"sedan",
      "model":"Impala",
      "manufacturer":{
         "code":101,
         "name":"Chevrolet"
      },
      "numberOfDoors":4,
      "fuelType":"Gasoline",
      "engine":"3.6L V6",
      "mileage":32280,
      "modelYear":2018,
      "productionYear":2018,
      "externalColor":"white"
   },
   "location":{
      "lat":40.73061,
      "lon":-73.935242
   }
}
```

### Delete a Vehicle

`DELETE` `/cars/{id}`
