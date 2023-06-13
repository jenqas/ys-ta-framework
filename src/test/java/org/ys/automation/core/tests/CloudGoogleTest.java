package org.ys.automation.core.tests;

import org.ys.automation.core.pages.GCloudHomePage;
import org.ys.automation.core.pages.GCloudPricingCalcPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Locale;

public class CloudGoogleTest extends BaseTest {

    private String[] dataProvider = {
            "4",
            "",
            "Free: Debian, CentOS, CoreOS, Ubuntu or BYOL (Bring Your Own License)",
            "Regular",
            "N1",
            "n1-standard-8 (vCPUs: 8, RAM: 30GB)",
            "NVIDIA Tesla T4",
            "1",
            "2x375 GB",
            "Frankfurt (europe-west3)",
            "1 Year"};

    @Test(description = "Google Cloud - Compare saved and input values after estimating VM")
    public void googleCloudPricingCalculatorTest() {

        SoftAssert softAssert = new SoftAssert();

        GCloudPricingCalcPage resultPage = new GCloudHomePage(driver)
                .openPage()
                .navigateToPricingCalculator()
                .fillingComputeEngineForm(dataProvider);

        softAssert.assertEquals(
                resultPage.getSavedVMCLassText(),
                dataProvider[3].toLowerCase(Locale.ROOT),
                "Saved VM Class doesn't match entered value.");
        softAssert.assertEquals(
                resultPage.getSavedInstanceTypeText(),
                dataProvider[5].replace(" (vCPUs: 8, RAM: 30GB)", ""),
                "Saved Instance Type doesn't match entered value.");
        softAssert.assertEquals(
                resultPage.getSavedLocationText(),
                dataProvider[9].replace(" (europe-west3)", ""),
                "Saved Location doesn't match entered value.");
        softAssert.assertEquals(resultPage.getSavedLocalSSDText(),
                dataProvider[8], "Saved Local SSD doesn't match entered value.");
        softAssert.assertEquals(resultPage.getSavedCommitmentTermText(),
                dataProvider[10], "Saved CommitmentTermText doesn't match entered value.");

        softAssert.assertTrue(resultPage.getTotalCostsAfterEstimating().equals("USD 1,841.97"),
                "Total costs amount doesn't equal to the amount, which was calculated by manual try");
        softAssert.assertAll();
    }

    @Test(description = "Google Cloud - Email estimated VM and check total costs")
    public void googleCloudHardcoreTest() {

        String estimatedCostsFromIncomingEmail = new GCloudHomePage(driver)
                .openPage()
                .navigateToPricingCalculator()
                .fillingComputeEngineForm(dataProvider)
                .sendEmailEstimate()
                .getEstimatedCostsFromIncomingEmail();

        Assert.assertEquals(estimatedCostsFromIncomingEmail, "USD 1,841.97", "Emailed estimated costs do not match expected");
    }

}
