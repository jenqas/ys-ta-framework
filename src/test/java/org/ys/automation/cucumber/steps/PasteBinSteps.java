package org.ys.automation.cucumber.steps;

import org.ys.automation.core.pages.PasteBinHomePage;
import org.ys.automation.core.pages.PasteBinResultPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.ys.automation.core.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PasteBinSteps {

    private final PasteBinHomePage homepage = new PasteBinHomePage();
    private final PasteBinResultPage resultPage = new PasteBinResultPage();

    @Given("the user opens Paste Bin website")
    public void openPasteBinWebsite() {
        homepage.openHomePage();
    }

    @And("the user scrolls down homepage")
    public void scrollDownPage() {
        homepage.jsExecuteCucumber("window.scrollBy(0,400);");
    }

//  @When("^the user enters paste code \"([^\"]*)\"$")
    @When("the user enters paste code {string}")
    public void enterPasteCode(String pasteCode) {
        homepage.getCodeInputField().sendKeys(pasteCode);
    }

    @And("the user enters paste syntax highlight {string}")
    public void enterPasteSyntaxHighlight(String syntaxHighlight) {
        homepage.getSyntaxHighlightField().click();
        homepage.getWebElementByDynamicLocator(syntaxHighlight).click();
    }

    @And("the user enters paste expiration {string}")
    public void enterPasteExpiration(String pasteExpiration) {
        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(3))
                .until(ExpectedConditions
                        .elementToBeClickable(By.xpath("//span[@aria-labelledby='select2-postform-format-container']")));

        homepage.getPasteExpirationField().click();
        homepage.getWebElementByDynamicLocator(pasteExpiration).click();
    }

    @And("the user enters paste name {string}")
    public void enterPasteName(String pastName) {
        homepage.getPasteNameField().sendKeys(pastName);
    }

    @And("the user clicks create button")
    public void clickCreateButton() {
        homepage.getPasteCreateButton().click();
    }

    @Then("created paste is saved as Share button becomes visible")
    public void verifyThatCreatedPasteIsSaved() {
        Assertions.assertThat(resultPage.waitForPresenceOfShareButton().isDisplayed())
                .overridingErrorMessage("Created paste is saved - Share button should be displayed")
                .isTrue();
    }

    @Then("browser's title should contain paste name {string}")
    public void verifyThatBrowsersTitleContainsPasteName(String pasteName) {
        resultPage.waitForPresenceOfShareButton();
        Assertions.assertThat(resultPage.checkIfBrowserTitleContainsPasteName(pasteName))
                .overridingErrorMessage("browser's title should contain paste name")
                .isTrue();
    }

    @Then("saved code should be equal to input code {string}")
    public void verifyThatSavedCodeEqualsToInputCode(String inputCode) {
        Assertions.assertThat(resultPage.checkIfSavedCodeEqualsInputCode(inputCode))
                .overridingErrorMessage("saved code should be equal to input code {string}")
                .isTrue();
    }

    @Then("saved code should have syntax highlight {string}")
    public void verifyThatSavedCodeHasSyntaxHighlight(String syntaxHighlight) {
        Assertions.assertThat(resultPage.checkIfSyntaxIsHighlightedForBash(syntaxHighlight))
                .overridingErrorMessage("saved code should have syntax highlight {string}")
                .isTrue();
    }

}
