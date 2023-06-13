package org.ys.automation.mobile;

import org.ys.automation.mobile.driver.DriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void createSession(){
        DriverManager.getDriver();
    }

//    @AfterMethod
//    public void resetApp(){
//        DriverManager.getDriver().resetApp();
//    }

    @AfterClass
    public void closeSession(){
        DriverManager.closeDriver();
        DriverManager.closeAppium();
        DriverManager.closeEmulator();
    }

}
