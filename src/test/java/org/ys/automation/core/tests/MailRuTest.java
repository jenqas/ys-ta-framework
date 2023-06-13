package org.ys.automation.core.tests;

import org.ys.automation.core.pages.MailRuInboxPage;
import org.ys.automation.core.pages.MailRuHomePage;
import org.apache.commons.lang3.RandomStringUtils;
import org.ys.automation.core.model.User;
import org.ys.automation.core.service.UserCreator;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class MailRuTest extends BaseTest {

    private final User TEST_USER = UserCreator.withCredentialsFromProperty();
    private String subject = RandomStringUtils.randomAlphanumeric(10);
    private final String BODY_MESSAGE = "Email body message";

    @Test(description = "Mail Ru Draft Email - Creation test")
    public void mailRuDraftEmailCreationTest() {

        SoftAssert softAssert = new SoftAssert();

        MailRuInboxPage inbox = new MailRuHomePage(driver)
                .openPage()
                .loginToMail(TEST_USER)
                .createNewDraftEmail(TEST_USER.getUserEmail(), subject, BODY_MESSAGE);

        softAssert.assertEquals(
                inbox.getLoggedInAccountDetails(),
                TEST_USER.getUserEmail(),
                "Logged In Account should be the same as entered user email-address"
        );

        inbox.navigateToFolderByTitle("Черновики");
        softAssert.assertEquals(
                inbox.getAddressOfLastEmailInCurrentFolder(),
                TEST_USER.getUserEmail(),
                "Email address of the last email in draft folder is not equal to address used by draft email creation"
        );
        softAssert.assertEquals(
                inbox.getSubjectOfLastEmailInCurrentFolder(),
                subject,
                "Subject of the last email in draft folder is not equal to subject used by draft email creation");
        softAssert.assertEquals(
                inbox.getBodyMessageOfEmailInCurrentFolder(),
                BODY_MESSAGE,
                "Body Message of the last email in draft folder is not equal to the email text used by draft email creation"
        );

        softAssert.assertAll();
    }

    @Test(description = "Mail Ru Draft Email - Test Folders: Draft, Sent")
    public void mailRuDraftEmailFoldersTest() {

        subject = RandomStringUtils.randomAlphanumeric(10);

        SoftAssert softAssert = new SoftAssert();

        MailRuInboxPage inbox = new MailRuHomePage(driver)
                .openPage()
                .loginToMail(TEST_USER)
                .createNewDraftEmail(TEST_USER.getUserEmail(), subject, BODY_MESSAGE)
                .navigateToFolderByTitle("Черновики")
                .sendDraftEmail();

        softAssert.assertFalse(
                inbox.isElementPresent(inbox.getLocatorOfEmailBySubject()),
                "Sent email should not be present in Drafts folder"
        );

        inbox.navigateToFolderByTitle("Отправленные");
        softAssert.assertTrue(
                inbox.isElementPresent(inbox.getLocatorOfEmailBySubject()),
                "Sent email should be present in Sent folder"
        );

        softAssert.assertFalse(
                inbox.deleteEmailByContextMenu().isElementPresent(inbox.getLocatorOfEmailBySubject()),
                "Deleted email should not be present in 'Sent' folder - as email was deleted"
        );

        softAssert.assertAll();
    }
}
