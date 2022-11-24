# Find Nearest Stores Service

## Prerequisites:
1) Java 17
2) Maven
3) Docker
4) Docker Compose

## Run
To Run the project execute the following commands:

```shell
./mvnw clean install
docker-compose up
```

## Frameworks and Tools

* Spring Boot
* Spring Actuator
* Mongo DB
* Mapstruct
* Lombok
* Test Container

## Sample Api Calls:
### Get Nearest Stores:
```
curl -XGET 'http://localhost:8085/jumbo/stores/search?lat=51.778461&lon=4.615551&distance=20&count=20'
```
### Open API Documentation:
```
http://localhost:8085/jumbo/swagger-ui/index.html
```

