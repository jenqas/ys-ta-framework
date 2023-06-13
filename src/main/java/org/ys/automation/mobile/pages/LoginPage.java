package org.ys.automation.mobile.pages;

import org.ys.automation.mobile.driver.DriverManager;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Set;

public class LoginPage extends BasePage {

    @AndroidFindBy(accessibility = "onBoarding-btn-skip")
    private WebElement skipButton;

    @AndroidFindBy(accessibility = "login-btn-login")
    private WebElement loginButton;

    @AndroidFindBy(accessibility = "Sign in with EPAM")
    private WebElement signInWithEpamOption;

    @FindBy(xpath = "//*[@id='userNameInput']")
    private WebElement usernameField;

    @FindBy(xpath = "//*[@id='passwordInput']")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id='submitButton']")
    private WebElement clickSubmitButton;

    @FindBy(xpath = "//*[@id='errorText']")
    private WebElement errorTextMessage;

    public LoginPage clickSkipButton(){
        waitForElementBecomesClickable(skipButton, 10).click();
        return this;
    }

    public LoginPage clickLogInButton(){
        waitForElementBecomesClickable(loginButton, 10).click();
        return this;
    }

    public LoginPage clickSignInWithEpamOption(){
        waitForElementBecomesClickable(signInWithEpamOption, 20).click();
        return this;
    }

    public LoginPage enterUserName(String username){
        changeContextToTheWebView();

        waitForElementBecomesClickable(usernameField).sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password){
        passwordField.sendKeys(password);
        return this;
    }

    public LoginPage clickSubmitButton(){
        waitForElementBecomesClickable(clickSubmitButton).click();
        return this;
    }

    public String getErrorMessageText(){
        return errorTextMessage.getText();
    }

    public void changeContextToTheWebView(){
        Set<String> contextNames = DriverManager.getDriver().getContextHandles();
        DriverManager.getDriver().context(contextNames.toArray()[1].toString()); // set context to WEBVIEW_chrome
    }

}
