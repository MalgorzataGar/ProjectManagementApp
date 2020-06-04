package com.example.projectmanagementapp.extensions;

import android.util.Log;

public class Clog {
    public static void log(final String tag, final String msg) {
        final StackTraceElement stackTrace = new Exception().getStackTrace()[1];

        String fileName = stackTrace.getFileName();
        if (fileName == null) fileName="";

        final String info = stackTrace.getMethodName() + " (" + fileName + ":"
                + stackTrace.getLineNumber() + ")";

        Log.i(tag, info + ": " + msg);
    }
    public static void log(final String msg) {
        final String default_tag = "CUSTOM";
        log(default_tag, msg);
    }
}
