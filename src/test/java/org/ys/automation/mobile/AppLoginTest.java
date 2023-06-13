package org.ys.automation.mobile;

import org.ys.automation.mobile.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppLoginTest extends BaseTest {

    private static final String USER_NAME = "Lorem@epam.com";
    private static final String PASSWORD = "123ds!dasQ<$!@$!";

    @Test
    public static void checkLoginFormWithWrongCredentials(){

        LoginPage page = new LoginPage();

        String textOfErrorMessage = page
                .clickSkipButton()
                .clickLogInButton()
                .clickSignInWithEpamOption()
                .enterUserName(USER_NAME)
                .enterPassword(PASSWORD)
                .clickSubmitButton()
                .getErrorMessageText();

        Assert.assertEquals(
                textOfErrorMessage,
                "Incorrect user ID or password. Type the correct user ID and password, and try again.",
                "Expected error message should be displayed after entering wrong credentials");
    }

}
