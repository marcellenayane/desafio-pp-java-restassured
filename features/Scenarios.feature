#Author: Marcelle Nayane Marques Muniz
Feature: Validate APIs for PicPay Challenge

  Background: Endpoint Configuration
    Given The endpoint is already configured

  @teste1
  Scenario: List all users
    When I get all the list from the users
    Then I should have the status code "200"

  @teste
  Scenario: Create an user and validate if the user was created
    When I create a new user
    Then I should have the status code "200"
