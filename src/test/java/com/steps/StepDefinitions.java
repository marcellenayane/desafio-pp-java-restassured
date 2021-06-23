package com.steps;

import com.utils.PPUtils;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;


public class StepDefinitions {

    public static Response response;
    public static RequestSpecification request;
    public static String idCode;
    public static String totalUsers;
    public static String finalTotal;

    @Before
    public void setUp() {
        RestAssured.baseURI = Hooks.prop.getProperty("baseURI");
        RestAssured.basePath = Hooks.prop.getProperty("basePath");
    }

    @Given("^The endpoint is already configured$")
    public void the_endpoint_is_already_configured() {
        request = RestAssured.given();
    }

    @Then("^I should have the status code \"([^\"]*)\"$")
    public void i_should_have_the_status_code(String statusCode) {
        response.then().statusCode(Integer.parseInt(statusCode));
    }

    @When("^I get all the list from the users$")
    public void iGetAllTheListFromTheUsers() {
        totalUsers = PPUtils.getAllTheList();
        System.out.println("The initial total: " + totalUsers);
    }

    @When("^I create a new user$")
    public void iCreateANewUserWithAnd() {
        response = PPUtils.postUser();
        System.out.println("RESPONSE DO CREATE USER: " +
                response.asString());
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("201", response.jsonPath().getString("code"));
        idCode = response.jsonPath().getString("data.id");
        System.out.println("codeId: " + idCode);
    }


    @And("^the list number have been increased$")
    public void theListNumberHaveBeenIncreased() {
        finalTotal = PPUtils.getAllTheList();
        int inicial = Integer.parseInt(totalUsers);
        int finalT = Integer.parseInt(finalTotal);
        Assert.assertTrue(finalT > inicial);
    }

    @When("^I update user's name to \"([^\"]*)\"$")
    public void i_Update_The_Name_Of_TheUserTo(String newName) {
        response = PPUtils.putUser(newName, idCode);
        Assert.assertEquals(200, response.statusCode());
        System.out.println(response.asString());
    }


    @And("^the name was really update to \"([^\"]*)\"$")
    public void the_NameWas_Really_Update_To(String newName) {
        Assert.assertFalse(idCode.isEmpty());
        response = PPUtils.getUserID(idCode);
        System.out.println(response.asString());
        Assert.assertEquals(response.jsonPath().getString("data.name"), newName.trim());
    }

    @When("^I delete the user$")
    public void iDeleteTheUser() {
        response = PPUtils.deleteUser(idCode);
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("204", response.jsonPath().getString("code"));
    }

    @And("^the user was really deleted$")
    public void theUserWasReallyDeleted() {
        response = PPUtils.getUserID(idCode);
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("404", response.jsonPath().getString("code"));
        Assert.assertEquals("Resource not found", response.jsonPath().getString("data.message"));
    }
}
