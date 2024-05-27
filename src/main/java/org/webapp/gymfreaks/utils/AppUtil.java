package org.webapp.gymfreaks.utils;

public class AppUtil {
    private AppUtil() {
        super();
    }

    public static String getDefaultUserPhotResource() {
        final String LOCATION = "src\\main\\resources\\static\\user_photo\\";
        final String FILENAME = "user_photo_default.jpg";
        return LOCATION + FILENAME;
    }
}
