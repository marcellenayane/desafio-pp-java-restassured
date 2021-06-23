package com.utils;

import com.github.javafaker.Faker;
import com.steps.Hooks;
import com.steps.StepDefinitions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;


import static io.restassured.RestAssured.given;

public class PPUtils {

    public static io.restassured.specification.RequestSpecification requestspecs;
    public static Response response;

    public static Response getUserID(String userId) {
        String urlId = Hooks.urlBase + userId;
        Response responseGetUser = request(urlId, "", "GET");
        Assert.assertEquals(200, responseGetUser.getStatusCode());
        return responseGetUser;
    }

    public static Response postUser() {
        String urlId = Hooks.urlBase;
        Faker faker = new Faker();
        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String email = name.replaceAll("\\s+", "") + "@gmail.com"; // Emory

        JSONObject jsonObj = new JSONObject()
                .put("name", name)
                .put("email", email)
                .put("gender", "Male")
                .put("status", "Active");

        Response responseCreateUser = request(urlId, jsonObj.toString(),"POST");
        Assert.assertEquals(200, responseCreateUser.getStatusCode());
        return responseCreateUser;
    }

    public static Response deleteUser(String userId) {
        Assert.assertFalse(userId.isEmpty());
        String urlId = Hooks.urlBase + userId;
        Response responseDeleteUser = request(urlId, "", "DELETE");
        Assert.assertEquals(200, responseDeleteUser.getStatusCode());
        return responseDeleteUser;
    }

    public static Response putUser(String newName, String userId) {
        Assert.assertFalse(userId.isEmpty());
        String urlId = Hooks.urlBase + userId;
        JSONObject jsonObj = new JSONObject()
                .put("name", newName);
        Response responseCreateUser = request(urlId, jsonObj.toString(),"PUT");
        Assert.assertEquals(200, responseCreateUser.getStatusCode());
        return responseCreateUser;
    }

    public static Response getAllUsers() {
        Response responseAllList = request(Hooks.urlBase, "", "GET");
        Assert.assertEquals("200", responseAllList.jsonPath().getString("code"));
        StepDefinitions.response = responseAllList;
        Assert.assertEquals(200, StepDefinitions.response.getStatusCode());
        return responseAllList;
    }

    public static String getAllTheList() {
        Response responseAllList = request(Hooks.urlBase, "", "GET");
        Assert.assertEquals("200", responseAllList.jsonPath().getString("code"));
        StepDefinitions.response = responseAllList;
        Assert.assertEquals(200, StepDefinitions.response.getStatusCode());
        return responseAllList.jsonPath().get("meta.pagination.total").toString();
    }


    public static Response request(String endpoint, String body, String httpmethod){
        requestspecs = setupHeaders("Authorization", Hooks.token);
        response = restAssuredRequest(httpmethod, body, endpoint);
        validaResponse(response);
        return response;
    }

    public static void validaResponse(Response restAssuredResponse) {
        Assert.assertNotNull(restAssuredResponse);
        validaResponseCode(200, restAssuredResponse.getStatusCode(), restAssuredResponse);// VALIDA O RESPONSE // CODE
    }

    public static Response restAssuredRequest(String httpMethod, String body, String url) {
        Response restAssuredResponse = null;
        switch (httpMethod.toUpperCase()) {
            case "GET":
                restAssuredResponse = requestspecs.get(url);
                break;
            case "POST":
                restAssuredResponse = requestspecs.body(body).post(url);
                break;
            case "PUT":
                restAssuredResponse = requestspecs.body(body).put(url);
                break;
            case "DELETE":
                restAssuredResponse = requestspecs.delete(url);
                break;
            default:
                System.out.println("This method no exist!");
        }
        return restAssuredResponse;
    }

    public static io.restassured.specification.RequestSpecification setupHeaders(String header, String value) {
        RestAssured.baseURI = Hooks.prop.getProperty("baseURI");
        RestAssured.basePath = Hooks.prop.getProperty("basePath");
        io.restassured.specification.RequestSpecification requestspecs = given();
        requestspecs.log();
        requestspecs.contentType("application/json");
        requestspecs.header(header, value);
        requestspecs.relaxedHTTPSValidation();
        return requestspecs;
    }

    public static void validaResponseCode(int expectedHTTPResponseCode, int responseCode, Response response) {
        if (responseCode != expectedHTTPResponseCode) {
            Assert.fail("StatusCode expected : " + expectedHTTPResponseCode + " and StatusCode received : "
                    + responseCode + " are not the same. Response: " + response.prettyPrint());
        }
    }
}
