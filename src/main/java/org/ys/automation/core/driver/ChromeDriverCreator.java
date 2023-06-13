package org.ys.automation.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverCreator implements WebDriverCreator{

    @Override
    public WebDriver createWebDriver() {
        System.setProperty("webdriver.chrome.driver", RESOURCES_PATH + "chromedriver.exe");
        return new ChromeDriver();
    }
}
