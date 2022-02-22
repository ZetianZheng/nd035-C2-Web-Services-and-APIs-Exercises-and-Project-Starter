# ND035-P02-VehiclesAPI-Project

Project repository for JavaND Project 2, where students implement a Vehicles API using Java and Spring Boot that can communicate with separate location and pricing services.

## Instructions

Check each component to see its details and instructions. Note that all three applications
should be running at once for full operation. Further instructions are available in the classroom.  

# Boogle Maps
You'll find the code related to our location service in the boogle-maps folder.   
It serves as a Mock to simulate a Maps WebService where, given a latitude and longitude, will return a random address.
- [Boogle Maps](boogle-maps/README.md)

# Pricing Service Code
the pricing-service serves as a REST WebService that simulates a backend   
to store and retrieve the price of a given vehicle.   
In the project, you will convert it to be a microservice registered through a Eureka server.  
- [Pricing Service](pricing-service/README.md)

# Vehicles API
It serves as a REST API  
to maintain vehicle data  
and to provide a complete view of vehicle details including price and address  
(obtained from the location and pricing services).
- [Vehicles API](vehicles-api/README.md)

## Dependencies

The project requires the use of Maven and Spring Boot, along with Java v11.
