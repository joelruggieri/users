# Spring Boot App Example

### Requirements
* Gradle 6.8 or better
* Java 13 or better
* Mysql 8.0 or greater

### Before building and starting app
* You should start your mysql service
* In _src/main/resources/application.properties_ you can set your mysql credentials properly updating fields 
  _spring.datasource.username_ and _spring.datasource.password_
* According to settings in this file you should:
  1. Login to mysql with your user -> _mysql -u < user > -p_
  2. Create database -> _create database db_example;_
  3. Create credentials -> _create user 'exampleUser'@'%' identified by 'SpringBoot2.0';_
  4. Give all privileges to the user to the new database -> _grant all on db_example.* to 'exampleUser'@'%';_
  5. Create database used for integration tests -> _create database db_integration_test;_
  6. Give all privileges to the user to the database for integration tests -> _grant all on db_integration_test.* to 'exampleUser'@'%';_

### Building App (with Tests)
./gradlew clean build

### Starting App
./gradlew bootRun

NOTE: when you run app, _schema.sql_ and _data.sql_ are executed to create schemas and populate tables for the first time.

### Using Api
You can for example retrieve user with id 2 executing curl in you command line:
_curl http://localhost:8080/users/2_