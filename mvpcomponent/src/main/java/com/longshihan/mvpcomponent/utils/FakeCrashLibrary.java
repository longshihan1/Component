package com.longshihan.mvpcomponent.utils;

/**
 * Created by LONGHE001.
 *
 * @time 2018/12/21 0021
 * @des
 * @function
 */

public class FakeCrashLibrary {
    public static void log(int priority, String tag, String message) {
        // TODO add log entry to circular buffer.
    }

    public static void logWarning(Throwable t) {
        // TODO report non-fatal warning.
    }

    public static void logError(Throwable t) {
        // TODO report non-fatal error.
    }

    private FakeCrashLibrary() {
        throw new AssertionError("No instances.");
    }
}
