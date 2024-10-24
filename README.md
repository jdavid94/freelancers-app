# Freelancer Management System

## Description
This application is a freelancer management system developed with Spring Boot. It allows for the registration, update, and deletion of freelancers, while also sending asynchronous notifications for each operation. Staff users can approve registered freelancers.

## Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Spring Events
- Swagger/OpenAPI
- JUnit and Mockito for unit testing

## Prerequisites
- Java 
- Maven

## Project Setup
1. Clone the repository:
    ```bash
    git clone https://github.com/jdavid94/freelancers-app.git
    ```
2. Navigate to the project directory:
    ```bash
    cd freelancers-app
    ```
3. Build and run tests:
    ```bash
    mvn clean test
    ```

## Running the Application
1. Run the application:
    ```bash
    mvn spring-boot:run
    ```
2. Access the H2 console:
    ```
    http://localhost:8080/h2-console
    ```
3. Access the Swagger documentation:
    ```
    http://localhost:8080/swagger-ui.html
    ```

## Design Decisions
- **Spring Boot**: Chosen for its quick setup and ease of use.
- **Spring Data JPA**: Used to simplify CRUD operations and database access.
- **H2 Database**: Selected for its simplicity and utility in development and testing.
- **Spring Events**: Used for handling asynchronous notifications without tightly coupling services.
- **Swagger/OpenAPI**: Used to document and interactively test the API.
- **JUnit and Mockito**: Used to ensure code quality through unit testing.

## Project Structure

/src/main/java/com/freelancers/frelancers_app
|-- /controller
|   |-- FreelancerController.java
|-- /service
|   |-- FreelancerService.java
|   |-- NotificationService.java
|   |-- StaffUserService.java
|-- /repository
|   |-- FreelancerRepository.java
|   |-- NotificationRepository.java
|   |-- StaffUserRepository.java
|-- /model
|   |-- Freelancer.java
|   |-- Notification.java
|   |-- StaffUser.java
|-- /dto
|   |-- FreelancerDTO.java
|   |-- StaffUserDTO.java
|-- /event
|   |-- FreelancerEvent.java
|-- /config
|   |-- SwaggerConfig.java