package com.tencent.mars.xlog;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLog {
    private static final String TAG = FileLog.class.getSimpleName();
    private static LogImp logImp;
    private static final String CACHE_DIR_SUFFIX = "/xlog/cache_log";
    private static final String LOG_DIR_SUFFIX = "/xlog/log";
    private static final String LOG_COPY_DIR_SUFFIX = "/xlog/log_copy";
    private static final String LOG_FILE_PREFIX = "FileLog";
    private static final String ZIP_LOG_FILE_PREFIX = "xlog_";
    private static final String ZIP_LOG_FILE_SUFFIX = ".zip";
    private static String appDataPath;
    private static String cacheDir;
    private static String logDir;
    private static String logCopyDir;

    public static void init(Context context, String pubKey) {
        if (context != null) {
            appDataPath = getInternalAppDataPath(context);
            cacheDir = appDataPath + CACHE_DIR_SUFFIX;
            logDir = appDataPath + LOG_DIR_SUFFIX;
            logCopyDir = appDataPath + LOG_COPY_DIR_SUFFIX;
            FileUtil.createOrExistsDir(new File(cacheDir));
            FileUtil.createOrExistsDir(new File(logDir));
            FileUtil.createOrExistsDir(new File(logCopyDir));
            logImp = new Xlog();
            Xlog.open(cacheDir, logDir, LOG_FILE_PREFIX, pubKey);
        }
    }

    public static void init(String cacheDir, String logDir, String namePrefix, String pubKey) {
        logImp = new Xlog();
        Xlog.open(cacheDir, logDir, namePrefix, pubKey);
    }

    private static String getInternalAppDataPath(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return context.getApplicationInfo().dataDir;
        }
        return context.getDataDir().getAbsolutePath();
    }

    /**
     * 将缓存内容写入最终文件，关闭文件
     */
    public static void appenderClose() {
        if (logImp != null) {
            logImp.appenderClose();
        }
    }

    /**
     * 获取日志文件。
     * 先将缓存写入文件，然后清空logCopy目录；
     * 再将日志从log目录拷贝到logCopy目录，返回目录下文件列表给调用者。
     */
    public static String[] retrieveLogFiles() {
        appenderFlush(true);
        File logCopyDirFile = new File(logCopyDir);
        File logDirFile = new File(logDir);
        FileUtil.deleteFileOrDir(logCopyDirFile);
        FileUtil.copyDir(logDirFile, logCopyDirFile);
        FileUtil.deleteFileOrDir(logDirFile);
        File[] files = logCopyDirFile.listFiles();
        String[] filePathArr = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            filePathArr[i] = files[i].getAbsolutePath();
        }
        return filePathArr;
    }

    /**
     * 获取日志文件；
     * 先将缓存写入文件，然后将log目录下文件打包到压缩包中，清空log目录下文件；
     * 将压缩包的路径返回给调用者。
     */
    public static String retrieveLogFilesAsZip() {
        appenderFlush(true);
        File logCopyDirFile = new File(logCopyDir);
        File logDirFile = new File(logDir);
        FileUtil.delFilesInDir(logCopyDirFile);

        String zipFilePath = getZipLogFilePathInLogCopyDir();
        try {
            FileUtil.generateZip(logDirFile.listFiles(), zipFilePath);
            FileUtil.delFilesInDir(logDirFile);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return zipFilePath;
    }

    private static String getZipLogFilePathInLogCopyDir() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = sdf.format(now);
        String zipFileName = ZIP_LOG_FILE_PREFIX + currentTime + ZIP_LOG_FILE_SUFFIX;
        return logCopyDir + "/" + zipFileName;
    }

    /**
     * 将缓存写如到文件中
     *
     * @param isSync 是否同步写
     */
    public static void appenderFlush(boolean isSync) {
        if (logImp != null) {
            logImp.appenderFlush(isSync);
        }
    }

    public static void setLevel(final int level) {
        Log.w(TAG, "new log level: " + level);
        Xlog.setLogLevel(level);
    }

    /**
     * use f(tag, format, obj) instead
     */
    public static void f(final String tag, final String msg) {
        f(tag, msg, (Object[]) null);
    }

    /**
     * use e(tag, format, obj) instead
     */
    public static void e(final String tag, final String msg) {
        e(tag, msg, (Object[]) null);
    }

    /**
     * use w(tag, format, obj) instead
     */
    public static void w(final String tag, final String msg) {
        w(tag, msg, (Object[]) null);
    }

    /**
     * use i(tag, format, obj) instead
     */
    public static void i(final String tag, final String msg) {
        i(tag, msg, (Object[]) null);
    }

    /**
     * use d(tag, format, obj) instead
     */
    public static void d(final String tag, final String msg) {
        d(tag, msg, (Object[]) null);
    }

    /**
     * use v(tag, format, obj) instead
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
            log += "  " + Log.getStackTraceString(tr);
            logImp.logE(tag, "", "", 0, Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), log);
        }
    }

    private static final String SYS_INFO;

    static {
        final StringBuilder sb = new StringBuilder();
        try {
            sb.append("VERSION.RELEASE:[").append(Build.VERSION.RELEASE);
            sb.append("] VERSION.CODENAME:[").append(Build.VERSION.CODENAME);
            sb.append("] VERSION.INCREMENTAL:[").append(Build.VERSION.INCREMENTAL);
            sb.append("] BOARD:[").append(Build.BOARD);
            sb.append("] DEVICE:[").append(Build.DEVICE);
            sb.append("] DISPLAY:[").append(Build.DISPLAY);
            sb.append("] FINGERPRINT:[").append(Build.FINGERPRINT);
            sb.append("] HOST:[").append(Build.HOST);
            sb.append("] MANUFACTURER:[").append(Build.MANUFACTURER);
            sb.append("] MODEL:[").append(Build.MODEL);
            sb.append("] PRODUCT:[").append(Build.PRODUCT);
            sb.append("] TAGS:[").append(Build.TAGS);
            sb.append("] TYPE:[").append(Build.TYPE);
            sb.append("] USER:[").append(Build.USER).append("]");
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
