
TASK MANAGEMENT API WITH CRUD + TDD + VALIDATION + GLOBAL EXCEPTION HANDLING + SWAGER + H2IMDB
***************************************************************************************

A SpringBoot application for managing Task API with RESTful endpoints.

FEATURES
*********************
Retrieve all Tasks
Create new Task
Delete a Task by Id
Patch update a Task (Update the status of a task)
JPA repository for data persistence
DTO pattern for request/response
Comprehensive test coverage
Validation
Swager for API end points (http://localhost:8081/swagger-ui/index.html)
Unit Tests with mockito
************************

Technologies
Java 17
Spring Boot 3.x
H2 Database (in-memory)
JPA/Hibernate
Maven
Prerequisites
JDK 17 or later
Maven 3.6.0 or later
Installation & Running the Application
Clone the repository:

git clone https://github.com/kishorjoseph/task-management-api.git
cd task-management-api
Build the application: mvn clean install

Run the application: The application will start on port 8081.

mvn spring-boot:run

API end points Get All Tasks URL: GET /api/task

Response: List of all Tasks

Add New Task URL: POST /api/task

Get a single Task  GET  /api/task/{id}

Update a task status  PATCH /api/task/{id}/status

Delete a task   DELETE  /api/task/{id}

Testing mvn test

H2 IMDB DETAILS
*****************

Accessing H2 Database Console

a) Navigate to http://localhost:8081/h2-console

b) Use the following credentials:

JDBC URL: jdbc:h2:mem:testdb

User Name: sa

Password: (leave empty)