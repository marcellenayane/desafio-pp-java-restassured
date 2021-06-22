package com.steps;

import com.github.javafaker.Faker;
import com.utils.PPUtils;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;


public class StepDefinitions {

    public static Response response;
    public static RequestSpecification request;
    public static String idCode;
    public static String totalUsers;
    public static String finalTotal;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://gorest.co.in/public-api";
        RestAssured.basePath = "/users/";
    }

    @Given("^The endpoint is already configured$")
    public void the_endpoint_is_already_configured() throws Throwable {
        request = RestAssured.given();
    }

    @Then("^I should have the status code \"([^\"]*)\"$")
    public void i_should_have_the_status_code(String statusCode) throws Throwable {
        response.then().statusCode(Integer.parseInt(statusCode));
    }

    @When("^I get all the list from the users$")
    public void iGetAllTheListFromTheUsers() {
        totalUsers = PPUtils.getAllTheList();
        System.out.println("O total inicial é: " + totalUsers);
    }

    @When("^I create a new user$")
    public void iCreateANewUserWithAnd() throws Throwable {
        response = PPUtils.createUser();
        System.out.println("RESPONSE DO CREATE USER: " +
                response.asString());
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(response.jsonPath().getString("code"), "201");

        idCode = response.jsonPath().getString("data.id");
        System.out.println("codeId: " + idCode);
    }


    @And("^the list number have been increased$")
    public void theListNumberHaveBeenIncreased() {
        finalTotal = PPUtils.getAllTheList();
        System.out.println("Total Before: " + totalUsers);
        System.out.println("Total After: " + finalTotal);
        int inicial = Integer.parseInt(totalUsers);
        int finalT = Integer.parseInt(finalTotal);
        Assert.assertTrue(finalT > inicial);

    }

    @When("^I update the name of the user to \"([^\"]*)\"$")
    public void iUpdateTheNameOfTheUserTo(String newName) throws Throwable {
        if (!idCode.isEmpty()) {
            JSONObject jsonObjNew = new JSONObject()
                    .put("name", newName);
            response = request
                    .contentType("application/json")  //another way to specify content type
                    .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                    .body(jsonObjNew.toString())   // use jsonObj toString method
                    .when()
                    .put(idCode)
                    .then().extract().response();
            System.out.println(response.toString());
            int size = response.jsonPath().getList("data.id").size();
        }
    }


    @And("^the name was really update to \"([^\"]*)\"$")
    public void theNameWasReallyUpdateTo(String newName) throws Throwable {
        Assert.assertFalse(idCode.isEmpty());
        response = request
                .contentType("application/json")  //another way to specify content type
                .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                .when().get(idCode)
                .then().extract().response();
        System.out.println(response.toString());
        Assert.assertEquals(response.jsonPath().getString("data.name"), newName.trim());
    }

}
