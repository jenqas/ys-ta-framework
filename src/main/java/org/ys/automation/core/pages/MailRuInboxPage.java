package org.ys.automation.core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.ys.automation.core.util.MyLogger;

public class MailRuInboxPage extends AbstractPage {

    private String subject;

    @FindBy(xpath = "//span[@class='compose-button__wrapper']")
    private WebElement createNewEmailButton;
    @FindBy(xpath = "//div[@data-type='to']/descendant::input[@type='text']")
    private WebElement addressInput;
    @FindBy(name = "Subject")
    private WebElement subjectInput;
    @FindBy(xpath = "//div[@role='textbox']/div[1]")
    private WebElement bodyTextArea;
    @FindBy(xpath = "//*[@title='Сохранить']")
    private WebElement saveDraftButton;
    @FindBy(xpath = "//*[@title='Закрыть']")
    private WebElement closeEmailButton;
    @FindBy(xpath = "//*[@title='Отправить']")
    private WebElement sendEmailButton;

    protected MailRuInboxPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MailRuInboxPage createNewDraftEmail(String address, String subject, String bodyMessage) {
        waitForElementBecomesClickable(createNewEmailButton).click();

        new Actions(driver)
                .sendKeys(waitForElementBecomesClickable(addressInput), address)
                .sendKeys(subjectInput, subject)
                .sendKeys(bodyTextArea, bodyMessage)
                .click(waitForElementBecomesClickable(saveDraftButton))
                .click(closeEmailButton)
                .build().perform();

        this.subject = subject;

        MyLogger.info("Draft email was created with subject '" + subject +
                "', address '" + address +
                "', body message '" + bodyMessage + "'");
        return this;
    }

    public MailRuInboxPage sendDraftEmail() {

        jsExecute("document.getElementsByClassName('llc__subject')[0].click()");
        waitForElementBecomesClickable(sendEmailButton).click();
        waitForElementBecomesClickable(closeEmailButton).click();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.invisibilityOfElementLocated(getLocatorOfEmailBySubject()));

        MyLogger.info("Draft email was sent");
        return this;
    }

    public MailRuInboxPage deleteEmailByContextMenu() {
        By locatorOfEmail = getLocatorOfEmailBySubject();

        new Actions(driver)
                .contextClick(waitForElementBecomesClickable(locatorOfEmail))
                .build().perform();

        waitForElementBecomesClickable(By.xpath("//span[@class='list-item__text' and text()='Удалить']")).click();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.invisibilityOfElementLocated(locatorOfEmail));

        MyLogger.info("Email with subject '" + subject + "'was deleted");
        return this;
    }

    public String getAddressOfLastEmailInCurrentFolder() {
        MyLogger.info("Getting address of last email in current folder");
        return waitForElementBecomesVisibleByLocator(By.xpath("//a[1]/descendant::span[@class='ll-crpt']")).getText();
    }

    public String getSubjectOfLastEmailInCurrentFolder() {
        MyLogger.info("Getting subject of last email in current folder");
        return waitForElementBecomesVisibleByLocator(By.xpath("//a[1]/descendant::span[@class='llc__subject']")).getText();
    }

    public String getBodyMessageOfEmailInCurrentFolder() {
        MyLogger.info("Getting Email Body message of last email in current folder");
        return waitForElementBecomesVisibleByLocator(By.xpath("//a[1]/descendant::span[@class='llc__snippet']")).getText();
    }

    public By getLocatorOfEmailBySubject() {
        return By.xpath(String.format("//span[@class='ll-sj__normal' and contains(normalize-space(),'%s')]", subject));
    }

    public MailRuInboxPage navigateToFolderByTitle(String title) {
        waitForElementBecomesClickable(By.partialLinkText(title)).click();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.attributeContains(
                        By.partialLinkText(title),
                        "class",
                        "nav__item_active"));

        MyLogger.info("Navigation to '" + title + "' folder was performed");
        return this;
    }

    public String getLoggedInAccountDetails() {
        MyLogger.info("Getting Logged In account details");
        return waitForElementBecomesVisibleByLocator(
                By.xpath("//div[@data-testid='whiteline-account']"))
                .getText();
    }

    public boolean isElementPresent(By by) {
        return !driver.findElements(by).isEmpty();
    }
}
