package me.desu.chromecookieextractor.utils;

import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class Utils {

    public static String localState() {
        String path = System.getProperty("user.home") + "/AppData/Local/Google/Chrome/User Data/Local State";
        File localStatePath = new File(path);
        return localStatePath.exists() && localStatePath.isFile()
                ? localStatePath.getAbsolutePath()
                : "Error, Chrome LocalState not fount";
    }

    public static String cookiePath() {
        String path = System.getProperty("user.home") + "/AppData/Local/Google/Chrome/User Data/Default/Network/Cookies";
        File cookie = new File(path);
        return cookie.exists() && cookie.isFile() ? cookie.getAbsolutePath() : "Error, Chrome cookies not found";
    }
}
