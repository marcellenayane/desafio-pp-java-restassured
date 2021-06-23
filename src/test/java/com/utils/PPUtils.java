package com.utils;

import com.github.javafaker.Faker;
import com.steps.StepDefinitions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class PPUtils {

    public static Response getUserID(String userId) {
        Assert.assertFalse(userId.isEmpty());
        String url = "https://gorest.co.in/public-api/users/" + userId;
        Response responseGetUser = RestAssured.given()
                        .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                        .when()
                        .get(url)
                        .then()
                        .extract()
                        .response();
        Assert.assertEquals(200, responseGetUser.getStatusCode());
        return responseGetUser;
    }

    public static Response createUser() {
        Faker faker = new Faker();
        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String email = name.replaceAll("\\s+", "") + "@gmail.com"; // Emory

        JSONObject jsonObj = new JSONObject()
                .put("name", name)
                .put("email", email)
                .put("gender", "Male")
                .put("status", "Active");
        Response ResponseCreateUser = given()
                .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                .header("Content-Type", "application/json")
                .and()
                .body(jsonObj.toString())
                .when()
                .post("https://gorest.co.in/public-api/users")
                .then()
                .extract().response();
        System.out.println("Response: " + ResponseCreateUser.asString());
        Assert.assertEquals(200, ResponseCreateUser.getStatusCode());
        return ResponseCreateUser;
    }

    public static Response deleteUser(String userId) {
        Assert.assertFalse(userId.isEmpty());
        String url = "https://gorest.co.in/public-api/users/" + userId;
        System.out.println("URL: " + url);
        Response responseDeleteUser = RestAssured.given()
                .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
        Assert.assertEquals(200, responseDeleteUser.getStatusCode());
        return responseDeleteUser;
    }

    public static Response putUser(String userId) {
        Assert.assertFalse(userId.isEmpty());
        String url = "https://gorest.co.in/public-api/users/" + userId;
        Faker faker = new Faker();
        String name = faker.name().fullName(); // Miss Samanta Schmidt

        JSONObject jsonObj = new JSONObject()
                .put("name", name);

        Response responsePutUser = RestAssured.given()
                .contentType("application/json")  //another way to specify content type
                .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                .body(jsonObj.toString())   // use jsonObj toString method
                .when()
                .put(url)
                .then().extract().response();
        Assert.assertEquals(200 /*expected value*/, responsePutUser.getStatusCode());
        return responsePutUser;
    }

    public static String getAllTheList() {
        Response responseAllList = RestAssured.given()
       // Response responseAllList = StepDefinitions.request
                .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                .when()
                .get()
                .then()
                .extract()
                .response();
        System.out.println(responseAllList.body());
        Assert.assertEquals(responseAllList.jsonPath().getString("code"), "200");
        System.out.println("total é: " + responseAllList.jsonPath().get("meta.pagination.total").toString());
        StepDefinitions.response = responseAllList;
        Assert.assertEquals(200, StepDefinitions.response.getStatusCode());
        return responseAllList.jsonPath().get("meta.pagination.total").toString();
    }
}
