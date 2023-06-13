package org.ys.automation.core.util;

import com.epam.reportportal.message.ReportPortalMessage;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.ys.automation.core.driver.DriverSingleton;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.sql.Driver;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        MyLogger.info("{} started", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        MyLogger.info("{} passed", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        MyLogger.info("{} failed", result.getName());
        try {
            saveScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveScreenshot() throws IOException {
        File screenCapture = ((TakesScreenshot)DriverSingleton
                .getWebDriverInstance())
                .getScreenshotAs(OutputType.FILE);
        String screenshotFilePath = "target/screenshots/" + getCurrentTimeAsString() + ".png";
        try {
            FileUtils.copyFile(screenCapture, new File(screenshotFilePath));
            MyLogger.info("Screenshot Path: " + screenshotFilePath);

//            MyLogger.info(
//                    "RP_MESSAGE#BASE64#{}#{}",
//                    BaseEncoding.base64().encode(Resources.asByteSource(Resources.getResource(screenshotFilePath)).read()),
//                    "Screenshot of failed step");

        } catch (IOException e) {
            MyLogger.error("Failed to save screenshot: " + e.getLocalizedMessage());
        }

//        File file = File.createTempFile("rp-test", ".png");
//        Resources.asByteSource(Resources.getResource(screenshotFilePath)).copyTo(Files.asByteSink(file));
//        MyLogger.log(file, "I'm logging png");

    }

    private String getCurrentTimeAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss");
        return ZonedDateTime.now().format(formatter);
    }

}
