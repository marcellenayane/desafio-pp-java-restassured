#Author: Marcelle Nayane Marques Muniz
Feature: Validate APIs for PicPay Challenge

  Background: Endpoint Configuration
    Given The endpoint is already configured

     #Usar o endpoint de 'Criar um Usuario' para criar um usuário válido, e então validar se o mesmo foi criado no endpoint 'Listar todos Usuarios';
  @desafioPP
  Scenario: Create an user and validate if the user was created
    When I get all the list from the users
    Then I should have the status code "200"
    When I create a new user
    Then I should have the status code "200"
    And the list number have been increased

    #Alterar o nome deste usuário criado com o endpoint 'Alterar dados do Usuario' e então validar as alterações no endpoint 'Listar um unico Usuario';
  @desafioPP
  Scenario: Update the user created and validate if it was really updated
    When I update the name of the user to "Daniel Filho"
    Then I should have the status code "200"
    And the name was really update to "Daniel Filho"

    #Deletar o usuário criado no endpoint 'Deletar Usuario' e validar se o mesmo foi removido no endpoint 'Listar todos Usuarios'';
  @desafioPP
  Scenario: Delete the user created and validate if it was really deleted
    When I delete the user
    Then I should have the status code "200"
    And the user was really deleted
