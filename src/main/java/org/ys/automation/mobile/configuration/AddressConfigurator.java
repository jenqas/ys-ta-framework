package org.ys.automation.mobile.configuration;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.ys.automation.core.util.MyLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Optional;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.*;

public class AddressConfigurator {

    private static final String ERROR_LOG_LEVEL = "error";
    private static final String KILL_NODE_COMMAND = "taskkill /F /IM node.exe";
    private static AppiumDriverLocalService appiumDriverLocalService;

    private AddressConfigurator(){
    }

    public static AppiumDriverLocalService getAppiumDriverLocalService(int port){
        if (appiumDriverLocalService==null) startService(port);
        return appiumDriverLocalService;
    }

    public static void startService(int port){
        makePortAvailableIfOccupied(port);
        appiumDriverLocalService = new AppiumServiceBuilder()
                .withIPAddress(ConfigurationReader.get().getAppiumAddress())
                .usingPort(port)
                .withArgument(BASEPATH, "/wd/hub/")
                .withArgument(SESSION_OVERRIDE)
                .withArgument(LOG_LEVEL, ERROR_LOG_LEVEL)
                .build();
        appiumDriverLocalService.start();
        MyLogger.info("Appium server was launched on port: {}", String.valueOf(port));
    }

    public static void stopService() {
        Optional.ofNullable(appiumDriverLocalService).ifPresent(service ->{
            service.stop();
            MyLogger.info("Appium server was stopped");
        });
    }

    private static void makePortAvailableIfOccupied(int port){
        if (!isPortFree(port)){
            try {
                Runtime.getRuntime().exec(KILL_NODE_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
                MyLogger.info("Couldn't execute runtime command, message: {}", e.getMessage());
            }
        }

    }

    private static Boolean isPortFree(int port){
        Boolean isFree = true;

        try (ServerSocket ignored = new ServerSocket(port)){
            MyLogger.info("Specified port is available and can be used {}", String.valueOf(port));
        } catch (Exception e) {
            isFree = false;
            MyLogger.warn("Specified port - {} is occupied by process, process will be terminated", String.valueOf(port));
        }

        return isFree;
    }

}
