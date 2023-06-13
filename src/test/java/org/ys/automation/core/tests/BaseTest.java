package org.ys.automation.core.tests;

import org.ys.automation.core.driver.DriverSingleton;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod()
    public void browserSetup() {
        driver = DriverSingleton.getWebDriverInstance();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverSingleton.closeDriver();
    }

}
