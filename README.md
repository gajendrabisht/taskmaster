# Task Master or Task Manager

 - Get a task by id
 - Get all tasks
 - Add a task
 - Update a task
 - Delete a task 

## Source code

- Using Kotlin

## Database (Prod)

- H2 Database using file persistence ../data/
- To use existing data update datasource.url with checkout directory in 'src/main/resources/application.yml'
- To switch to MySql
    - Run MySql Docker image locally following steps https://hub.docker.com/_/mysql
    - Ensure -p 3306:3306 is exposed for the running container
    - Create a database called 'taskmaster'
    - Uncomment MySql connection settings in 'src/main/resources/application.yml'
    - Comment out connection setting for H2 persistent file store in the above file

## Tests

- TDD by Unit tests - Spock tests written in Groovy
- BDD by Cucumber tests - Glue using Kotlin
- Test DB is in memory H2 

## Swagger Documentation

* API documentation on swagger 'http://localhost:5000/swagger-ui.html'
