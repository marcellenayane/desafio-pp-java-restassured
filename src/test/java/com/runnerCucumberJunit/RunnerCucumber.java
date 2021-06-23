package com.runnerCucumberJunit;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", glue = "com.steps", tags = "@desafioPP")
public class RunnerCucumber {
}
