# random-data-generator/random-data-generator/README.md

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
