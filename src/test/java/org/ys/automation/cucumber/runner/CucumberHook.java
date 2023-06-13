package org.ys.automation.cucumber.runner;


import org.ys.automation.core.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class CucumberHook {

    @Before
    public void setupDriver(){
        DriverManager.setupDriver();
    }

    @After
    public void quitDriver(){
        DriverManager.quitDriver();
    }
}
