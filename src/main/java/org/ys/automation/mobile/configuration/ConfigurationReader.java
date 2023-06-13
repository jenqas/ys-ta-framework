package org.ys.automation.mobile.configuration;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private static final Logger log = LogManager.getRootLogger();
    private static final Properties properties = new Properties();
    private static ConfigurationReader instance;

    private ConfigurationReader(){}

    public static ConfigurationReader get() {
        if (instance == null){
            instance = new ConfigurationReader();
            try {
                properties.load(new FileInputStream("src/main/resources/appium.properties"));
            } catch (IOException e) {
                log.error("Properties were loaded for appium");
            }
        }

        return instance;
    }

    public String getEnvironment() {
        return properties.getProperty("env.type");
    }

    public String getAppPath() {
        return properties.getProperty("app.path");
    }

    public String getAppPackage() {
        return properties.getProperty("app.package");
    }

    public String getAppActivity() {
        return properties.getProperty("app.activity");
    }

    public String getPlatformName() {
        return properties.getProperty("platform.name");
    }

    public String getPlatformVersion() {
        return properties.getProperty("platform.version");
    }

    public String getDeviceName() {
        return properties.getProperty("device.name");
    }

    public String getUdId() {
        return properties.getProperty("ud.id");
    }

    public String getAppiumAddress() {
        return properties.getProperty("appium.address");
    }

    public int getAppiumPort() {
        return NumberUtils.toInt(properties.getProperty("appium.port"));
    }

    public String getAppiumPath() {
        return properties.getProperty("appium.path");
    }

    public String getAppiumExecutable() { return properties.getProperty("appium.chrome.executable");  }

//    public String getAutoWebview() {
//        return properties.getProperty("appium.webview");
//    }
}
