# Random Data Generator for Sales Orders

This project generates random data for sales orders and posts that data in JSON format to RabbitMQ streams. It includes fields such as product, price, quantity, shipTo, and payment method (cash, check, credit, debit). The application also logs the generated sales order information.

## Project Structure

```
random-data-generator
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── randomdatagenerator
│   │   │               ├── RandomDataGeneratorApplication.java
│   │   │               ├── config
│   │   │               │   └── RabbitMQConfig.java
│   │   │               ├── model
│   │   │               │   └── SalesOrder.java
│   │   │               ├── service
│   │   │               │   └── SalesOrderService.java
│   │   │               ├── util
│   │   │               │   └── RandomDataGenerator.java
│   │   │               └── logging
│   │   │                   └── LoggingService.java
│   │   └── resources
│   │       └── application.properties
├── pom.xml
└── README.md
```

## Setup Instructions

### Prerequisites
```
docker network create rmq-network

docker run -d  --rm --name rabbitmq --network rmq-network  -p 5552:5552 -p 15672:15672 -p 5672:5672   -e RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS='-rabbitmq_stream advertised_host localhost' rabbitmq:4-management
```
#### Run rabbitmq in docker
```
docker run -d  --rm --name rabbitmq -p 5552:5552 -p 15672:15672 -p 5672:5672  \
    -e RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS='-rabbitmq_stream advertised_host localhost' \
    rabbitmq:4-management
```
#### Enable RabbitMQ Streams Plugin
```
docker exec rabbitmq rabbitmq-plugins enable rabbitmq_stream 
```
#### sample output
```
rabbitmq_stream_management
Enabling plugins on node rabbit@d212bb73a912:
rabbitmq_stream
rabbitmq_stream_management
The following plugins have been configured:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_prometheus
  rabbitmq_stream
  rabbitmq_stream_management
  rabbitmq_web_dispatch
Applying plugin configuration to rabbit@d212bb73a912...
The following plugins have been enabled:
  rabbitmq_stream
  rabbitmq_stream_management

started 2 plugins.
```

1. **Clone the repository:**
   ```
   git clone <repository-url>
   cd random-data-generator
   ```

2. **Build the project:**
   ```
   mvn clean install
   ```

3. **Configure RabbitMQ:**
   Update the `application.properties` file with your RabbitMQ connection details.

4. **Run the application:**
   ```
   mvn spring-boot:run
   ```

5. **Build image**
   ```
    mvn spring-boot:build-image
   ```

6. **Run on Docker**
   ```
   docker run --rm -p 8080:8080 docker.io/library/random:0.0.1-SNAPSHOT
   ```
## Usage

The application will generate random sales orders and send them to RabbitMQ. The generated orders will include the following fields:
- Product
- Price
- Quantity
- Ship To
- Payment Method (cash, check, credit, debit)

The application also logs the details of each generated order.

## Dependencies

This project uses the following dependencies:
- Spring Boot
- RabbitMQ
- Logging frameworks

## License

This project is licensed under the MIT License.# spring-boot-random-data-generator
