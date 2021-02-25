# Prerequisites

- Java 11
- Maven

# Architecture decisions

- Clean architecture aka Port and Adapters
- Rest API (not RestFull -> missing hypermedia)
- A modular monolith with modularization on package level. That provides high cohesion, low coupling and great options
  for the journey to microservices.

# Build and Run

## There are two options to build and run the application:

- using account_script.sh script
    * build: `account_script.sh -build`
    * run: `account_script -run`
- manually executing the following commands in the account-app directory:
    * build: `mvn clean install`
    * run: `mvn spring-boot:run`

# Unit and integration test

- Tests are written in the best test framework for JVM applications - spock
- Test code coverage around 95% (measured with Intellij plugin)
- If you want you can run only unit tests using:
  * script: `account_script.sh -unit`
  * maven: `mvn clean test -pl account-app -am`

- Integration tests are included from maven `verify` phase

# Architecture tests

- Architecture test are supported by ArchUnit.
- The main purpose is to test high cohesion and low coupling between modules

# E2E tests

## There is a separate module called e2e-test. Before you run it, make sure the application is up and running on port 8080.

## (I'm aware that it could be more parametrized.)

## There are two options to run e2e test:

- using account_script.sh script
  * `account_script.sh -e2e`
- manually executing the following commands in e2e-test directory:
  * `mvn clean test`
- e2e test covers a happy scenario- the most important for our business. Alternative paths are covered mostly by unit
  and integration tests

# Manual tests

- On the application startup two accounts are created with id's:
  *`b1b22e76-382b-4b89-8bee-0e16b309c008`
  *`999f2144-493e-4e49-9407-3fc79506ef52`
  Feel free to use them.

- The API provides endpoint to create new account with initial balance. The example request body:
  `{
  "amount": 100,
  "currency": "PLN"
  }`

# Conscious decisions:

- Skipped API authentication/authorization. Security is a huge topic and implementing it in the production mode could
  take too much time.
- Skipped infrastructure part. The application uses h2 in memory db and that's the only external dependency.
- A simple pipeline with one build stage
- Lack of performance tests. In the real life with tests environments I would definitely use gatling for it.
- Rest API versioning through url prefix and not a header