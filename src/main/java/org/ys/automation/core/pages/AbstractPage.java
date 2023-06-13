package org.ys.automation.core.pages;

import org.ys.automation.core.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractPage {

    protected WebDriver driver;

    protected AbstractPage openPage(String homepageUrl) {
        driver.get(homepageUrl);
        driver.manage().timeouts().pageLoadTimeout(WAIT_TIMEOUT_SECONDS);
        return this;
    }

    protected static final Duration WAIT_TIMEOUT_SECONDS = Duration.ofSeconds(7);

    protected AbstractPage() {
    }

    protected AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForElementBecomesVisibleByLocator(By locator) {
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementBecomesVisibleByLocator(By locator, Duration durationOfSeconds) {
        return new WebDriverWait(driver, durationOfSeconds)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementBecomesVisible(WebElement element) {
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .visibilityOf(element));
    }

    protected WebElement waitForElementBecomesClickable(By locator) {
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .elementToBeClickable(locator));
    }

    protected WebElement waitForElementBecomesClickable(By locator, Duration durationOfSeconds) {
        return new WebDriverWait(driver, durationOfSeconds)
                .until(ExpectedConditions
                        .elementToBeClickable(locator));
    }

    protected WebElement waitForElementBecomesClickable(WebElement element) {
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .elementToBeClickable(element));
    }

    protected WebElement waitForElementBecomesClickable(WebElement element, Duration durationOfSeconds) {
        return new WebDriverWait(driver, durationOfSeconds)
                .until(ExpectedConditions
                        .elementToBeClickable(element));
    }

    public void jsExecute(String script){
        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        jsExec.executeScript(script);
    }

    public void jsExecuteCucumber(String script){
        JavascriptExecutor jsExec = (JavascriptExecutor) DriverManager.getDriver();
        jsExec.executeScript(script);
    }

}
