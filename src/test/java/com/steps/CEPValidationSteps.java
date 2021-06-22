package com.steps;

import com.github.javafaker.Faker;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CEPValidationSteps {

    private Response response;
    private RequestSpecification request;
    private static String idCode;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://gorest.co.in/public-api";
        RestAssured.basePath = "/users";
    }

    @Given("^The endpoint is already configured$")
    public void the_endpoint_is_already_configured() throws Throwable {
        request = RestAssured.given();
    }

    @When("^I input a valid CEP \"([^\"]*)\"$")
    public void i_input_a_valid_CEP(String cep) throws Throwable {
        response = request.when().get("/" + cep);
        response.then().log().all();
    }

    @Then("^I should have the status code \"([^\"]*)\"$")
    public void i_should_have_the_status_code(String statusCode) throws Throwable {
        response.then().statusCode(Integer.parseInt(statusCode));
    }

    @Then("^content type should be in \"([^\"]*)\" format$")
    public void content_type_should_be_in_format(String format) throws Throwable {

        if (format.equals("JSON")) {
            response.then().assertThat().contentType(ContentType.JSON).and()
                    .body(matchesJsonSchemaInClasspath("schema-json.json"));
        }
    }

    @Then("^the body response content should be matched$")
    public void the_body_response_content_should_be_matched(DataTable table) throws Throwable {

        List<List<String>> data = table.raw();

        for (int i = 1; i < data.size(); i++) {
            response.then().assertThat().body(data.get(i).get(0), equalTo(data.get(i).get(1)));
        }
    }

    @When("^I get all the list from the users$")
    public void iGetAllTheListFromTheUsers() {
        response = request.when().get("/");
        response.then().log().all();
        System.out.println(response.body());
    }

    @When("^I create a new user$")
    public void iCreateANewUserWithAnd() throws Throwable {
        Faker faker = new Faker();
        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String email = name.replaceAll("\\s+","") + "@gmail.com"; // Emory
        System.out.println("Email: " + email);
        JSONObject jsonObj = new JSONObject()
                .put("name", name)
                .put("email", email)
                .put("gender", "Male")
                .put("status", "Active");
        response = request
                .contentType("application/json")  //another way to specify content type
                .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                .body(jsonObj.toString())   // use jsonObj toString method
                .when()
                .post()
                .then().extract().response();
        Assert.assertEquals(response.jsonPath().getString("code"), "201");

        idCode = response.jsonPath().getString("data.id");
        System.out.println("codeId: " + idCode);


    }

    @When("^I create a new user with \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\"$")
    public void iCreateANewUserWithAnd(String name, String email, String gender, String status) throws Throwable {
        JSONObject jsonObj = new JSONObject()
                .put("name", name)
                .put("email", email)
                .put("gender", gender)
                .put("status", status);
        response = request
                .contentType("application/json")  //another way to specify content type
                .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                .body(jsonObj.toString())   // use jsonObj toString method
                .when()
                .post()
                .then().extract().response();

        System.out.println(response.toString());
        List<String> jsonResponse = response.jsonPath().getList("$");
        System.out.println(jsonResponse.size());
        String codes = response.jsonPath().getString("code");
        System.out.println(codes);

    }
}
