package org.ys.automation.mobile.pages;

import org.ys.automation.mobile.driver.DriverManager;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    public BasePage(){
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    protected WebElement waitForElementBecomesClickable(WebElement element) {
        return new WebDriverWait(DriverManager.getDriver(), 6)
                .until(ExpectedConditions
                        .elementToBeClickable(element));
    }

    protected WebElement waitForElementBecomesClickable(WebElement element, long seconds) {
        return new WebDriverWait(DriverManager.getDriver(), seconds)
                .until(ExpectedConditions
                        .elementToBeClickable(element));
    }
}
