package org.ys.automation.mobile.utils;

import org.ys.automation.mobile.driver.DriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MobileListener implements ITestListener {

    private Logger log = LogManager.getRootLogger();


    @Override
    public void onTestStart(ITestResult result) {
        log.info("{} started", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("{} passed", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.info("{} failed", result.getName());
        saveScreenshot();
    }

    private void saveScreenshot() {
        File screenCapture = ((TakesScreenshot) DriverManager
                .getDriver())
                .getScreenshotAs(OutputType.FILE);
        String saveScreenshotTo = ".//target/screenshots/" + getCurrentTimeAsString() + ".png";

        try {
            FileUtils.copyFile(screenCapture, new File(saveScreenshotTo));
            log.info("Screenshot Path: " + saveScreenshotTo);
        } catch (IOException e) {
            log.error("Failed to save screenshot: " + e.getLocalizedMessage());
        }
    }

    private String getCurrentTimeAsString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "uuuu-MM-dd_HH-mm-ss" );
        return ZonedDateTime.now().format(formatter);
    }

}
