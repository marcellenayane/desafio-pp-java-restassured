package com.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.restassured.RestAssured;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Hooks {

    public static Properties prop = new Properties();
    public static String token, urlBase;

    @Before
    public void beforeScenario(Scenario scenario) {
        RestAssured.baseURI = Hooks.prop.getProperty("baseURI");
        RestAssured.basePath = Hooks.prop.getProperty("basePath");
        Hooks.loadProperties(); // lod the properties file
        token = prop.getProperty("authorization");
        urlBase = prop.getProperty("baseURI") + prop.getProperty("basePath");
    }

    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("Finishing the scenario...");
    }

    public static void loadProperties() {
        InputStream is = Hooks.class.getResourceAsStream("/application.properties");
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}