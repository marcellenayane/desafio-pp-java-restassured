# Java test framework:

This is an project to solve the challenge using Java and Rest Assured.

## Scenarios:

/POST /GET:
* Criar um usuário válido e validar se o mesmo foi criado no endpoint "Listar Todos Usuários": 

/PUT /GET:
* Alterar o nome deste usuário criado com o endpoint "Alterar dados do Usuário" e então validar as alterações no endpoint "Listar um unico usuário":

/DELETE /GET:
* Deletar o usuário criado no endpoint "Deletar Usuário" e validar se o mesmo foi removido no endpoint "Listar todos Usuários":

## Project Structure:
* Programming Language: JAVA
* IDE: IntelliJ IDEA
* Create a Maven project to build the program;
* Add all dependencies in pom.xml regarding the frameworks used such as Cucumber to manage BDD, Rest-assured to call the REST API;

## BDD (Feature file / Step definition)
BDD requires a feature file to invoke the step definitions:

* Create the scenarios in feature file containing the steps to perform the tests;
* Following the BDD practices for coding;
* Each step should have an implementation in the StepDefinitions file;
* I used an Hooks file to manage the @AfterScenario and @BeforeScenario;
* I used a utils class to create the methods that should be used in the StepDefinitions file;
* Using the special annotation like "@Before" which is the first method to run for each scenario. Moreover, this is the right place to set up the URI (endpoint) which will be called by HTTP request;

## REST API
In order to test REST APIs, I used REST Assured library. 

## Test Suite:

The tests related to this challenge can be find in "RunnerCucumber.java" file.

## Tools used:
- [IntelliJ IDEA](https://www.jetbrains.com/pt-br/idea/ "IntelliJ IDEA")
- [POSTMAN] (https://www.postman.com/ "POSTMAN")


## Libraries and plugins used
* Java
* Rest Assured
* Maven

## Dependencies:
All the dependencies are in the pom.xml file.

## How to install

Run "mvn clean install -DskipTests"

### Running the tests: 

* To run the tests on your terminal, run:
```
mvn test -Dcucumber.options="--tags @desafioPP"
```
* To run the tests from the IntelliJ or another IDE, run the "RunAllTests.java" file via Maven plugin installed