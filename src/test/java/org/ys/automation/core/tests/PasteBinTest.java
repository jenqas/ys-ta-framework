package org.ys.automation.core.tests;

import org.ys.automation.core.pages.PasteBinHomePage;
import org.ys.automation.core.pages.PasteBinResultPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PasteBinTest extends BaseTest {

    @Test(description = "New Paste test - Creation Test")
    public void newPasteCreationTest() {

        new PasteBinHomePage(driver)
                .openPage()
                .createNewPaste("Hello from WebDriver", "10 Minutes", "helloweb")
                .waitForPageToBeLoaded();

        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='SHARE']")).isDisplayed(), "New paste should be created");
    }

    @Test(description = "New Paste test - Syntax Highlight Test")
    public void newPasteSyntaxTest() {

        SoftAssert softAssert = new SoftAssert();

        PasteBinResultPage resultsObjectPage = new PasteBinHomePage(driver)
                .openPage()
                .createNewPaste("git config --global user.name  \"New Sheriff in Town\"\ngit reset $(git commit-tree HEAD^{tree} -m \"Legacy code\"\ngit push origin master --force",
                        "10 Minutes",
                        "how to gain dominance among developers",
                        "Bash")
                .waitForPageToBeLoaded();

        softAssert.assertTrue(
                resultsObjectPage.browserTitleEqualsPasteName(),
                "Browser Title should be equal to input Paste Name");
        softAssert.assertTrue(
                resultsObjectPage.savedCodeEqualsInputCode(),
                "Saved Code should be equal input code");
        softAssert.assertTrue(
                resultsObjectPage.syntaxIsHighlightedForBash(),
                "Saved Code should have syntax highlight as per input parameter");

        softAssert.assertAll();
    }
}
