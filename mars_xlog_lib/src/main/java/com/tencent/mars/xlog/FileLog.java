package com.tencent.mars.xlog;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.Process;

public class FileLog {
    private static final String TAG = FileLog.class.getSimpleName();

    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARNING = 3;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_FATAL = 5;
    public static final int LEVEL_NONE = 6;
    private static LogImp logImp;
    // defaults to LEVEL_NONE
    private static int level = LEVEL_NONE;
    public static Context toastSupportContext = null;

    public static void init(Context context, String pubKey) {
        String appDataPath = getInternalAppDataPath(context);
        String cacheDir = appDataPath + "/mars/cachelog";
        String logDir = appDataPath + "/mars/log";
        String namePrefix = "FileLog";
        logImp = new Xlog();
        Xlog.open(cacheDir, logDir, namePrefix, pubKey);
    }

    public static void init(String cacheDir, String logDir, String namePrefix, String pubKey) {
        logImp = new Xlog();
        Xlog.open(cacheDir, logDir, namePrefix, pubKey);
    }

    public static String getInternalAppDataPath(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return context.getApplicationInfo().dataDir;
        }
        return context.getDataDir().getAbsolutePath();
    }

    public static void appenderClose() {
        if (logImp != null) {
            logImp.appenderClose();
        }
    }

    public static void appenderFlush(boolean isSync) {
        if (logImp != null) {
            logImp.appenderFlush(isSync);
        }
    }

    public static int getLogLevel() {
        if (logImp != null) {
            return logImp.getLogLevel();
        }
        return LEVEL_NONE;
    }

    public static void setLevel(final int level, final boolean jni) {
        FileLog.level = level;
        android.util.Log.w(TAG, "new log level: " + level);

        if (jni) {
            Xlog.setLogLevel(level);
        }
    }

    /**
     * use f(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void f(final String tag, final String msg) {
        f(tag, msg, (Object[]) null);
    }

    /**
     * use e(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void e(final String tag, final String msg) {
        e(tag, msg, (Object[]) null);
    }

    /**
     * use w(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void w(final String tag, final String msg) {
        w(tag, msg, (Object[]) null);
    }

    /**
     * use i(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void i(final String tag, final String msg) {
        i(tag, msg, (Object[]) null);
    }

    /**
     * use d(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void d(final String tag, final String msg) {
        d(tag, msg, (Object[]) null);
    }

    /**
     * use v(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void v(final String tag, final String msg) {
        v(tag, msg, (Object[]) null);
    }

    public static void f(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            final String log = obj == null ? format : String.format(format, obj);
            logImp.logF(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }
    }

    public static void e(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            logImp.logE(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }
    }

    public static void w(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            logImp.logW(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }
    }

    public static void i(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            logImp.logI(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }
    }

    public static void d(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            logImp.logD(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }
    }

    public static void v(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            logImp.logV(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }
    }

    public static void printErrStackTrace(String tag, Throwable tr, final String format, final Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            log += "  " + android.util.Log.getStackTraceString(tr);
            logImp.logE(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }
    }

    private static final String SYS_INFO;

    static {
        final StringBuilder sb = new StringBuilder();
        try {
            sb.append("VERSION.RELEASE:[" + android.os.Build.VERSION.RELEASE);
            sb.append("] VERSION.CODENAME:[" + android.os.Build.VERSION.CODENAME);
            sb.append("] VERSION.INCREMENTAL:[" + android.os.Build.VERSION.INCREMENTAL);
            sb.append("] BOARD:[" + android.os.Build.BOARD);
            sb.append("] DEVICE:[" + android.os.Build.DEVICE);
            sb.append("] DISPLAY:[" + android.os.Build.DISPLAY);
            sb.append("] FINGERPRINT:[" + android.os.Build.FINGERPRINT);
            sb.append("] HOST:[" + android.os.Build.HOST);
            sb.append("] MANUFACTURER:[" + android.os.Build.MANUFACTURER);
            sb.append("] MODEL:[" + android.os.Build.MODEL);
            sb.append("] PRODUCT:[" + android.os.Build.PRODUCT);
            sb.append("] TAGS:[" + android.os.Build.TAGS);
            sb.append("] TYPE:[" + android.os.Build.TYPE);
            sb.append("] USER:[" + android.os.Build.USER + "]");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        SYS_INFO = sb.toString();
    }

    public static String getSysInfo() {
        return SYS_INFO;
    }

    public interface LogImp {

        void logV(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        void logI(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        void logD(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        void logW(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        void logE(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        void logF(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        int getLogLevel();

        void appenderClose();

        void appenderFlush(boolean isSync);
    }
}
