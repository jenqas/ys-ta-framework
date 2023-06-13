package org.ys.automation.core.driver;

import org.openqa.selenium.WebDriver;

public interface WebDriverCreator {

    String RESOURCES_PATH = "src\\test\\resources\\";
    WebDriver createWebDriver();

}
