package org.ys.automation.core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.ys.automation.core.util.MyLogger;

import java.time.Duration;

public class EmailGeneratorPage extends AbstractPage {

    private String emailWindow;

    @FindBy(xpath = "//form[@class='flex justify-center mb-1']/input")
    private WebElement generateRandomEmailButton;
    @FindBy(xpath = "//div[text()='Google Cloud Price Estimate']")
    private WebElement incomingEmailItem;

    public EmailGeneratorPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getEstimatedCostsFromIncomingEmail() {

        navigateToEmailTab(emailWindow);
        waitForElementBecomesClickable(incomingEmailItem, Duration.ofSeconds(90)).click();

        selectFrame();
        WebElement estimatedEmailedCosts = waitForElementBecomesVisibleByLocator(By.xpath("//h2[contains(text(),'Estimated Monthly Cost')]"));

        MyLogger.info("getting estimated costs from incoming email");

        return estimatedEmailedCosts.getText().replace("Estimated Monthly Cost: ", "");
    }

    public String generateEmail() {

        openEmailGeneratorOnNewTab();

        waitForElementBecomesClickable(generateRandomEmailButton).click();
        WebElement generatedEmailField = waitForElementBecomesVisibleByLocator(
                By.xpath("//form[@class='max-w-screen-lg mx-auto']//div[contains(text(),'@')]"));

        MyLogger.info("Generating temporary email");
        return generatedEmailField.getText();
    }

    private void selectFrame() {
        driver.switchTo().frame(
                driver.findElement(
                        By.xpath("//iframe[@class='w-full flex flex-grow min-h-tm-half']")));
        MyLogger.debug("Selecting frame");
    }

    public void openEmailGeneratorOnNewTab() {
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://www.10minuteemails.com/");
        driver.switchTo().defaultContent();
        emailWindow = driver.getWindowHandle();
        MyLogger.info("Opened email generator page on new tab");
    }

    public void navigateToEmailTab(String originalWindow) {
        driver.switchTo().window(emailWindow);
        driver.switchTo().defaultContent();
        MyLogger.info("Navigating to email generator page tab");
    }
}
