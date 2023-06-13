package org.ys.automation.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverCreator implements WebDriverCreator{

    @Override
    public WebDriver createWebDriver() {
        System.setProperty("webdriver.chrome.driver", RESOURCES_PATH + "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
}
