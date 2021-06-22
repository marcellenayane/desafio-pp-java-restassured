package com.utils;

import com.github.javafaker.Faker;
import com.steps.StepDefinitions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class PPUtils {

    public static Response getAllUsers() {
        String url = "https://gorest.co.in/public-api/users/";
        Response responseGetAll =
                StepDefinitions.request
                        .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                        .when()
                        .get(url)
                        .then()
                        .extract()
                        .response();
        Assert.assertEquals(200 /*expected value*/, responseGetAll.getStatusCode());
        return responseGetAll;
    }

    public static Response getUserID(String userId) {
        String url = "https://gorest.co.in/public-api/users/" + userId;
        Response responseGetUser =
                StepDefinitions.request
                        .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                        .when()
                        .get(url)
                        .then()
                        .extract()
                        .response();
        Assert.assertEquals(200 /*expected value*/, responseGetUser.getStatusCode());
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
        String url = "https://gorest.co.in/public-api/users" + userId;
        Faker faker = new Faker();
        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String email = name.replaceAll("\\s+", "") + "@gmail.com"; // Emory

        JSONObject jsonObj = new JSONObject()
                .put("name", name)
                .put("email", email)
                .put("gender", "Male")
                .put("status", "Active");

        Response responseDeleteUser = StepDefinitions.request
                .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                .body(jsonObj.toString())   // use jsonObj toString method
                .when()
                .delete(url)
                .then().extract().response();
        Assert.assertEquals(200 /*expected value*/, responseDeleteUser.getStatusCode());
        return responseDeleteUser;
    }

    public static Response putUser(String userId) {
        String url = "https://gorest.co.in/public-api/users" + userId;
        Faker faker = new Faker();
        String name = faker.name().fullName(); // Miss Samanta Schmidt

        JSONObject jsonObj = new JSONObject()
                .put("name", name);

        Response responsePutUser = StepDefinitions.request
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
        Response responseAllList = StepDefinitions.request
                .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                .when()
                .get()
                .then()
                .extract()
                .response();
        System.out.println(responseAllList.body());
        Assert.assertEquals(responseAllList.jsonPath().getString("code"), "200");
        System.out.println("total é: " + responseAllList.jsonPath().get("meta.pagination.total").toString());
        int size = responseAllList.jsonPath().getList("data.id").size();
        System.out.println("ID SIZE EHHH: " + size);
        StepDefinitions.response = responseAllList;
        Assert.assertEquals(200, StepDefinitions.response.getStatusCode());
        return responseAllList.jsonPath().get("meta.pagination.total").toString();
    }

    public static Response createUserNew() {
        JSONObject jsonObj = new JSONObject()
                .put("name", "Asss")
                .put("email", "assssss@a.com.br")
                .put("gender", "Male")
                .put("status", "Active");
        Response arreda = given()
                .header("Authorization", "Bearer 2275e2cbbf8dc1d113b25fb018cdb2e07e088b35bb5f7b7c13ca160ed96a82ba")
                .header("Content-Type", "application/json")
                .and()
                .body(jsonObj.toString())
                .when()
                .post("https://gorest.co.in/public-api/users")
                .then()
                .extract().response();
        System.out.println("Arreda: " + arreda.asString());
        Assert.assertEquals(200, arreda.getStatusCode());
        return arreda;
    }

}
