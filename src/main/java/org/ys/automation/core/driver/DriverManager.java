package org.ys.automation.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;

public class DriverManager {

    private static final int WAIT_TIMEOUT = 15;
    private static final String CHROME_DRIVER_PATH = "./src/test/resources/chromedriver.exe";
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void setupDriver() {
        WebDriver driver = getChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(WAIT_TIMEOUT));
        threadLocalDriver.set(driver);
    }

    private static ChromeDriver getChromeDriver() {
        System.setProperty(CHROME_DRIVER_EXE_PROPERTY, CHROME_DRIVER_PATH);
        return new ChromeDriver();
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void quitDriver() {
        Optional.ofNullable(getDriver()).ifPresent(driver -> {
            driver.quit();
            threadLocalDriver.remove();
        });
    }
}
