package org.ys.automation.core.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.ys.automation.core.util.MyLogger;

import java.time.Duration;

public class GCloudHomePage extends AbstractPage{

    private static final String HOMEPAGE_URL = "https://cloud.google.com/";

    @FindBy(name = "q")
    private WebElement searchField;
    @FindBy(xpath = "//a[@class='gs-title']/b[text()='Google Cloud Pricing Calculator']")
    private WebElement linkToCalculator;

    public GCloudHomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public GCloudHomePage openPage(){
        super.openPage(HOMEPAGE_URL);
        MyLogger.info("Google homepage is opened");
        return this;
    }

    public GCloudPricingCalcPage navigateToPricingCalculator(){
        searchField.sendKeys("Google Cloud Platform Pricing Calculator");
        searchField.sendKeys(Keys.ENTER);

        waitForElementBecomesClickable(linkToCalculator, Duration.ofSeconds(10));
        linkToCalculator.click();

        MyLogger.info("Navigation to Pricing Calcuator was performed");
        return new GCloudPricingCalcPage(driver);
    }

}
