package org.ys.automation.core.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.ys.automation.core.util.MyLogger;

import java.time.Duration;

public class GCloudPricingCalcPage extends AbstractPage {

    private String virtualMachineClass;
    private String instanceType;
    private String location;
    private String localSSD;
    private String commitmentTerm;

    @FindBy(xpath = "//div[@title='Compute Engine']/ancestor::md-tab-item")
    private WebElement computeEngineCategoryTab;
    @FindBy(xpath = "//input[@ng-model='listingCtrl.computeServer.label']")
    private WebElement whatAreTheseInstanceForField;
    @FindBy(xpath = "//input[@ng-model='listingCtrl.computeServer.quantity']")
    private WebElement numberOfInstancesField;
    @FindBy(xpath = "//md-select[@ng-model='listingCtrl.computeServer.os']")
    private WebElement operatingSystemField;
    @FindBy(xpath = "//md-select[@ng-model='listingCtrl.computeServer.class']")
    private WebElement virtualMachineClassField;
    @FindBy(xpath = "//md-select[@ng-model='listingCtrl.computeServer.series']")
    private WebElement instanceSeriesField;
    @FindBy(xpath = "//md-select[@ng-model='listingCtrl.computeServer.instance']")
    private WebElement instanceTypeField;
    @FindBy(xpath = "//md-checkbox[@ng-model='listingCtrl.computeServer.addGPUs']")
    private WebElement addGpuCheckbox;
    @FindBy(xpath = "//md-select[@ng-model='listingCtrl.computeServer.gpuCount']")
    private WebElement numberOfGpuField;
    @FindBy(xpath = "//md-select[@ng-model='listingCtrl.computeServer.gpuType']")
    private WebElement gpuTypeField;
    @FindBy(xpath = "//md-select[@ng-model='listingCtrl.computeServer.ssd']")
    private WebElement localSsdField;
    @FindBy(xpath = "//md-select[@ng-model='listingCtrl.computeServer.location']")
    private WebElement datacenterLocationField;
    @FindBy(xpath = "//md-select[@ng-model='listingCtrl.computeServer.cud']")
    private WebElement committedUsageField;

    protected GCloudPricingCalcPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    protected GCloudPricingCalcPage(WebDriver driver, String virtualMachineClass, String instanceType, String location, String localSSD, String commitmentTerm) {
        super(driver);
        this.virtualMachineClass = virtualMachineClass;
        this.instanceType = instanceType;
        this.location = location;
        this.localSSD = localSSD;
        this.commitmentTerm = commitmentTerm;

        PageFactory.initElements(driver, this);
    }

    public EmailGeneratorPage sendEmailEstimate() {

        driver.findElement(By.xpath("//button[@aria-label='Email Estimate']")).click();

        String originalWindow = driver.getWindowHandle();

        EmailGeneratorPage emailPage = new EmailGeneratorPage(driver);
        String email = emailPage.generateEmail();

        driver.switchTo().window(originalWindow);
        selectFrame();

        WebElement emailInput = waitForElementBecomesVisibleByLocator(By.xpath("//input[@type='email']"), Duration.ofSeconds(10));
        emailInput.sendKeys(email);
        driver.findElement(By.xpath("//button[@aria-label='Send Email']")).click();

        MyLogger.info("Estimated costs were sent to email");
        return emailPage;
    }

    public String getTotalCostsAfterEstimating() {
        MyLogger.info("Getting total costs after Estimating");
        return driver.findElement(By.xpath("//b[contains(text(),'Total Estimated Cost:')]"))
                .getText()
                .replace("Total Estimated Cost: ", "")
                .replace(" per 1 month", "");
    }

    public GCloudPricingCalcPage fillingComputeEngineForm(String[] arrayDataProvider) {

        selectFrame();

        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .attributeContains(computeEngineCategoryTab, "class", "md-active"));

        inputNumberOfInstances(arrayDataProvider[0]);
        inputWhatAreTheseInstanceFor(arrayDataProvider[1]);

        selectOperatingSystem(arrayDataProvider[2]);
        selectVMClass(arrayDataProvider[3]);
        selectInstanceSeries(arrayDataProvider[4]);
        selectMachineType(arrayDataProvider[5]);
        selectGPUType(arrayDataProvider[6]);
        selectNumberOfGPU(arrayDataProvider[7]);
        selectSsd(arrayDataProvider[8]);
        selectDatacenterLocation(arrayDataProvider[9]);
        selectCommittedUsage(arrayDataProvider[10]);

        waitForElementBecomesClickable(By.xpath("//button[@aria-label='Add to Estimate']")).click();
        waitForElementBecomesClickable(By.xpath("//button[@aria-label='Email Estimate']"));

        MyLogger.info("Compute Engine Form was filled with all provided values");
        return new GCloudPricingCalcPage(driver, arrayDataProvider[3], arrayDataProvider[5], arrayDataProvider[9], arrayDataProvider[8], arrayDataProvider[10]);
    }

    public String getSavedVMCLassText() {
        return getSavedValueByLabel("VM class");
    }

    public String getSavedInstanceTypeText() {
        return getSavedValueByLabel("Instance type")
                .replace("Committed Use Discount applied", "")
                .replace("\n", "");
    }

    public String getSavedLocationText() {
        return getSavedValueByLabel("Region");
    }

    public String getSavedLocalSSDText() {
        return getSavedValueByLabel("Local SSD")
                .replace("Committed Use Discount applied", "")
                .replace("i", "")
                .replace("\n", "");
    }

    public String getSavedCommitmentTermText() {
        return getSavedValueByLabel("Commitment term");
    }

    public String getSavedValueByLabel(String label) {
        By locator = By.xpath(String.format("//*[contains(text(),'%s: ')]", label));
        return driver.findElement(locator).getText().replace(label + ": ", "");
    }

    private void inputNumberOfInstances(String numberOfInstances) {
        numberOfInstancesField.sendKeys(numberOfInstances);
    }

    private void inputWhatAreTheseInstanceFor(String whatAreTheseInstanceFor) {
        whatAreTheseInstanceForField.sendKeys(whatAreTheseInstanceFor);
    }

    private void selectOperatingSystem(String operatingSystem) {
        operatingSystemField.click();
        selectOptionByText(operatingSystem);
    }

    private void selectVMClass(String virtualMachineClass) {
        virtualMachineClassField.click();
        selectOptionByText(virtualMachineClass);
    }

    private void selectInstanceSeries(String instanceSeries) {
        instanceSeriesField.click();
        selectOptionByText(instanceSeries);
    }

    private void selectMachineType(String machineType) {
        instanceTypeField.click();
        selectOptionByText(machineType);
    }

    private void selectGPUType(String gpuType) {
        addGpuCheckbox.click();

        waitForElementBecomesClickable(gpuTypeField);
        gpuTypeField.click();

        selectOptionByText(gpuType);
    }

    private void selectNumberOfGPU(String numberOfGpuToBeAdded) {
        numberOfGpuField.click();
        selectOptionByText(numberOfGpuToBeAdded);
    }

    private void selectSsd(String localSsd) {
        localSsdField.click();
        selectOptionByText(localSsd);
    }

    private void selectDatacenterLocation(String datacenterLocation) {
        datacenterLocationField.click();
        selectOptionByText(datacenterLocation);
    }

    private void selectCommittedUsage(String committedUsage) {
        committedUsageField.click();
        selectOptionByText(committedUsage);
    }

    private void selectOptionByText(String textToBeSelected) {
        By locator =
                By.xpath(
                        String.format(
                                "//div[contains(@class,'md-clickable')]/descendant::div[contains(text(),'%s')]",
                                textToBeSelected));

        waitForElementBecomesClickable(locator).click();
    }

    private void selectFrame() {
        driver.switchTo()
                .frame(0)
                .switchTo()
                .frame(driver.findElement(By.xpath("//iframe[@id='myFrame']")));
    }

}
