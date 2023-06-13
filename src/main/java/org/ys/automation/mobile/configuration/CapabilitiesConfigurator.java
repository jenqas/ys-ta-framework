package org.ys.automation.mobile.configuration;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.*;
import static io.appium.java_client.remote.MobileCapabilityType.*;

public class CapabilitiesConfigurator {

    private CapabilitiesConfigurator() {
    }

    public static DesiredCapabilities getLocalCapabilities(){

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(UDID, ConfigurationReader.get().getUdId());
        desiredCapabilities.setCapability(AVD, ConfigurationReader.get().getDeviceName());
        desiredCapabilities.setCapability(APP_PACKAGE, ConfigurationReader.get().getAppPackage());
        desiredCapabilities.setCapability(APP_ACTIVITY, ConfigurationReader.get().getAppActivity());
        desiredCapabilities.setCapability(APP, new File(ConfigurationReader.get().getAppPath()).getAbsolutePath());
        desiredCapabilities.setCapability(CHROMEDRIVER_EXECUTABLE,new File(ConfigurationReader.get().getAppiumExecutable()).getAbsolutePath());

        return desiredCapabilities;
    }
}
