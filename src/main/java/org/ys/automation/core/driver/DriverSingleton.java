package org.ys.automation.core.driver;

import org.ys.automation.core.util.HighlightDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class DriverSingleton {

    private static WebDriver driver;

    private DriverSingleton(){}

    public static WebDriver getWebDriverInstance(){
        if (driver == null){
            switch (System.getProperty("browser")){
                case "msedge": {
                    WebDriverCreator creator = new EdgeDriverCreator();
                    driver = creator.createWebDriver();
                    break;
                }
                default: {
                    WebDriverCreator creator = new ChromeDriverCreator();
                    driver = creator.createWebDriver();
                    break;
                }
            }
            driver.manage().window().maximize();
        }

        HighlightDecorator listener = new HighlightDecorator();
        EventFiringWebDriver decoratedDriver = new EventFiringWebDriver(driver);
        decoratedDriver.register(listener);

        return decoratedDriver;
    }

    public static void closeDriver(){
        driver.quit();
        driver = null;
    }
}
