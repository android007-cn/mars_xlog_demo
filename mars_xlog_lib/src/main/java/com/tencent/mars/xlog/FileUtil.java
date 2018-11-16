package com.tencent.mars.xlog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    public static boolean deleteDir(String path) {
        return new File(path).delete();
    }

    public static boolean copyDir(final File srcDir,
                                   final File destDir) {
        if (srcDir == null || destDir == null) {
            return false;
        }
        // destDir's path locate in srcDir's path then return false
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) {
            return false;
        }
        if (!srcDir.exists() || !srcDir.isDirectory()) {
            return false;
        }
        if (!createOrExistsDir(destDir)) {
            return false;
        }
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File destFile = new File(destPath + file.getName());
            if (file.isFile()) {
                if (!copyFile(file, destFile)) {
                    return false;
                }
            } else if (file.isDirectory()) {
                if (!copyDir(file, destFile)) {
                    return false;
                }
            }
        }
        return true;

    }

    private static boolean createOrExistsDir(final File file) {
        return file.exists() ? file.isDirectory() : file.mkdirs();
    }

    private static boolean copyFile(final File srcFile, final File destFile) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(srcFile);
            os = new BufferedOutputStream(new FileOutputStream(destFile));
            byte[] data = new byte[8192];
            int len;
            while ((len = is.read(data, 0, 8192)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
