package org.ys.automation.mobile.driver;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ys.automation.core.util.MyLogger;
import org.ys.automation.mobile.configuration.AddressConfigurator;
import org.ys.automation.mobile.configuration.CapabilitiesConfigurator;
import org.ys.automation.mobile.configuration.ConfigurationReader;
import org.ys.automation.mobile.configuration.EnvironmentType;

import java.io.IOException;
import java.util.Optional;

import static java.lang.String.format;

public class DriverManager {

    private static final Logger logger = LogManager.getRootLogger();
    private static final EnvironmentType ENVIRONMENT_TYPE = EnvironmentType
            .valueOf(ConfigurationReader.get().getEnvironment()
                    .toUpperCase());
    private static AndroidDriver driver;

    private DriverManager(){
    }

    public static AndroidDriver getDriver(){
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    public static AndroidDriver createDriver() {
        switch (ENVIRONMENT_TYPE) {
            case LOCAL:
                driver = new AndroidDriver(
                        AddressConfigurator.getAppiumDriverLocalService(ConfigurationReader.get().getAppiumPort()),
                        CapabilitiesConfigurator.getLocalCapabilities());
                break;
            default:
                throw new IllegalArgumentException(format("Unexpected environment  type: %s", ENVIRONMENT_TYPE));
        }
        MyLogger.info("Driver is created");
        MyLogger.info("Environment type is {}", String.valueOf(ENVIRONMENT_TYPE));
        return driver;
    }

    public static void closeDriver(){
        Optional.ofNullable(getDriver()).ifPresent(driverInstance-> {
            driverInstance.quit();
            driver = null;
            MyLogger.info("Driver was closed");
        });
    }

    public static void closeAppium() {
        AddressConfigurator.stopService();
    }

    public static void closeEmulator(){
        try {
            Runtime.getRuntime()
                    .exec(format("adb -s %s emu kill", ConfigurationReader.get().getUdId()));
            MyLogger.info("AVD was closed");
        } catch (IOException e) {
            MyLogger.info("AVD was not closed due to message: {}", e.getMessage());
        }
    }
}
