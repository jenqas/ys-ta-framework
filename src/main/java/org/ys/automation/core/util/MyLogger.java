package org.ys.automation.core.util;

import com.epam.reportportal.message.ReportPortalMessage;
import com.google.common.io.BaseEncoding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class MyLogger {

    public static Logger logger = LogManager.getLogger(MyLogger.class);

    public static void error(String message){
        logger.error(message);
    }

    public static void error(String message, Throwable throwable){
        logger.error(message, throwable);
    }

    public static void info(String message){
        logger.info(message);
    }

    public static void info(ReportPortalMessage message){
        logger.info(message);
    }

    public static void info(String message, String testName){
        logger.info(message, testName);
    }

    public static void debug(String message){
        logger.debug(message);
    }

    public static void warn(String message){
        logger.warn(message);
    }

    public static void warn(String message, String s) {
        logger.warn(message, s);
    }

    public static void trace(String message){
        logger.trace(message);
    }

    public static void log(String message){
        logger.info(message);
    }

    public static void fatal(String message){
        logger.fatal(message);
    }

    public static void info(String s, String encode, String screenshot_of_failed_step) {
        logger.info(s, encode, screenshot_of_failed_step);
    }



    public static void log(File file, String message) {
        logger.info("RP_MESSAGE#FILE#{}#{}", file.getAbsolutePath(), message);
    }

    public static void log(byte[] bytes, String message) {
        logger.info("RP_MESSAGE#BASE64#{}#{}", BaseEncoding.base64().encode(bytes), message);
    }

    public static void logBase64(String base64, String message) {
        logger.info("RP_MESSAGE#BASE64#{}#{}", base64, message);
    }

}
