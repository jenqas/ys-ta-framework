package org.ys.automation.core.pages;

import org.ys.automation.core.model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.ys.automation.core.util.MyLogger;

public class MailRuHomePage extends AbstractPage {

    private static final String HOMEPAGE_URL = "https://mail.ru/";

    @FindBy(name = "login")
    private WebElement loginInput;
    @FindBy(xpath = "//button[@data-testid='enter-password']")
    private WebElement enterPasswordButton;
    @FindBy(name = "password")
    private WebElement passwordInput;
    @FindBy(xpath = "//button[@data-testid='login-to-mail']")
    private WebElement loginButton;

    public MailRuHomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MailRuHomePage openPage() {
        super.openPage(HOMEPAGE_URL);
        MyLogger.info("Login page is opened");
        return this;
    }

    public MailRuInboxPage loginToMail(User testUser) {

        loginInput.sendKeys(testUser.getUserEmail());
        waitForElementBecomesClickable(enterPasswordButton).click();
        waitForElementBecomesClickable(passwordInput).sendKeys(testUser.getPassword());
        waitForElementBecomesClickable(loginButton).click();

        MyLogger.info("Login performed");
        return new MailRuInboxPage(driver);
    }

}
