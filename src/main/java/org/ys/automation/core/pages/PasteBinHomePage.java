package org.ys.automation.core.pages;

import org.ys.automation.core.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.ys.automation.core.util.MyLogger;

public class PasteBinHomePage extends AbstractPage {

    private static final String HOMEPAGE_URL = "https://pastebin.com/";

    @FindBy(id = "postform-text")
    private WebElement codeInputField;

    @FindBy(xpath = "//span[@aria-labelledby='select2-postform-format-container']")
    private WebElement syntaxHighlightField;

    @FindBy(xpath = "//span[@aria-labelledby='select2-postform-expiration-container']")
    private WebElement pasteExpirationField;

    @FindBy(id = "postform-name")
    private WebElement pasteNameField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement createNewPasteButton;

    public PasteBinHomePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public PasteBinHomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public PasteBinHomePage openPage() {
        super.openPage(HOMEPAGE_URL);
        MyLogger.info("Login page is opened");
        return this;
    }

    public void openHomePage() {
        DriverManager.getDriver().get(HOMEPAGE_URL);
    }

    public PasteBinResultPage createNewPaste(String code, String pasteExpiration, String pasteName) {

        enterCode(code);
        enterPasteExpiration(pasteExpiration);
        enterPasteName(pasteName);

        createNewPasteButton.click();

        MyLogger.info("New Paste was created with name '" + pasteName +
                "', Paste Expiration '" + pasteExpiration +
                "', code '" + code + "'");
        return new PasteBinResultPage(driver, code, pasteExpiration, pasteName);
    }

    public PasteBinResultPage createNewPaste(String code, String pasteExpiration, String pasteName, String syntaxHighlight) {

        enterCode(code);
        enterSyntaxHighlight(syntaxHighlight);
        enterPasteExpiration(pasteExpiration);
        enterPasteName(pasteName);

        createNewPasteButton.click();

        MyLogger.info("New Paste was created with name '" + pasteName +
                "', Paste Expiration '" + pasteExpiration +
                "', code '" + code + "'" +
                "', syntaxHighlight '" + syntaxHighlight + "'");
        return new PasteBinResultPage(driver, code, pasteExpiration, pasteName, syntaxHighlight);
    }

    public PasteBinHomePage enterCode(String code) {
        codeInputField.sendKeys(code);
        MyLogger.info("Code is entered");
        return this;
    }

    public PasteBinHomePage enterPasteExpiration(String pasteExpiration) {
        waitForElementBecomesClickable(By.xpath("//span[@aria-labelledby='select2-postform-format-container']"));

        pasteExpirationField.click();
        driver.findElement(buildDynamicLocatorForSelectionFields(pasteExpiration)).click();
        MyLogger.info("Paste Expiration is entered");
        return this;
    }

    public PasteBinHomePage enterPasteName(String pasteName) {
        pasteNameField.sendKeys(pasteName);
        MyLogger.info("Paste Name is entered");
        return this;
    }

    public PasteBinHomePage enterSyntaxHighlight(String syntaxHighlight) {
        syntaxHighlightField.click();
        driver.findElement(buildDynamicLocatorForSelectionFields(syntaxHighlight)).click();
        MyLogger.info("Syntax Highlight is entered");
        return this;
    }

    public By buildDynamicLocatorForSelectionFields(String textPartOfXpath) {
        return By.xpath("//li[text()='%']".replaceAll("%", textPartOfXpath));
    }

    public WebElement getWebElementByDynamicLocator(String textPartOfXpath) {
        return DriverManager.getDriver().findElement(buildDynamicLocatorForSelectionFields(textPartOfXpath));
    }

    public WebElement getCodeInputField() {
        return codeInputField;
    }

    public WebElement getSyntaxHighlightField() {
        return syntaxHighlightField;
    }

    public WebElement getPasteExpirationField() {
        return pasteExpirationField;
    }

    public WebElement getPasteNameField() {
        return pasteNameField;
    }

    public WebElement getPasteCreateButton() {
        return createNewPasteButton;
    }

}
