package org.ys.automation.core.pages;

import org.ys.automation.core.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.ys.automation.core.util.MyLogger;

import java.time.Duration;

public class PasteBinResultPage extends AbstractPage {

    private String code;
    private String syntaxHighlight;
    private String pasteExpiration;
    private String pasteName;
    private static final String SHARE_BUTTON = "//span[text()='SHARE']";

    @FindBy(xpath = "//textarea")
    private WebElement savedCode;

    @FindBy(xpath = "//div[@class='highlighted-code']/descendant::a[text()='Bash']")
    private WebElement savedSyntaxHighlight;

    public PasteBinResultPage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public PasteBinResultPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public PasteBinResultPage(WebDriver driver, String code, String pasteExpiration, String pasteName) {
        super(driver);
        this.code = code;
        this.pasteExpiration = pasteExpiration;
        this.pasteName = pasteName;
        PageFactory.initElements(driver, this);
    }

    public PasteBinResultPage(WebDriver driver, String code, String pasteExpiration, String pasteName, String syntaxHighlight) {
        super(driver);
        this.code = code;
        this.pasteExpiration = pasteExpiration;
        this.pasteName = pasteName;
        this.syntaxHighlight = syntaxHighlight;
        PageFactory.initElements(driver, this);
    }

    public PasteBinResultPage waitForPageToBeLoaded() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//span[text()='SHARE']")));
        MyLogger.info("Wait for page to be loaded");
        return this;
    }

    public WebElement waitForPresenceOfShareButton(){
        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(8))
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath(SHARE_BUTTON)));

        return DriverManager.getDriver().findElement(By.xpath(SHARE_BUTTON));
    }

    public Boolean browserTitleEqualsPasteName() {
        String titleTrimmed = driver.getTitle().replaceAll(" - Pastebin.com", "");
        MyLogger.info("Checking if browsers title equals Paste Name");
        return pasteName.equals(titleTrimmed);
    }

    public Boolean savedCodeEqualsInputCode() {
        MyLogger.info("Checking if saved code equals input code");
        return code.equals(savedCode.getText());
    }

    public Boolean syntaxIsHighlightedForBash() {
        MyLogger.info("Checking if saved syntax highlight value equals input value");
        return syntaxHighlight.equals(savedSyntaxHighlight.getText());
    }

    public Boolean checkIfBrowserTitleContainsPasteName(String pasteName) {
        return DriverManager.getDriver().getTitle().contains(pasteName);
    }

    public Boolean checkIfSavedCodeEqualsInputCode(String inputCode) {
        return inputCode.equals(savedCode.getText());
    }

    public Boolean checkIfSyntaxIsHighlightedForBash(String syntaxHighlight) {
        return syntaxHighlight.equals(savedSyntaxHighlight.getText());
    }

}
