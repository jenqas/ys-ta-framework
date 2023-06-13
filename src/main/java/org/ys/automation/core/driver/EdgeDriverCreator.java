package org.ys.automation.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeDriverCreator implements WebDriverCreator {
    @Override
    public WebDriver createWebDriver() {
        System.setProperty("webdriver.edge.driver", RESOURCES_PATH + "msedgedriver.exe");
        return new EdgeDriver();
    }
}
