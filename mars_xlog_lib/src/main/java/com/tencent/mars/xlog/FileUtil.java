package com.tencent.mars.xlog;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class FileUtil {
    private static String TAG = "FileUtil";

    public static void deleteFileOrDir(File file) {
        if (file.isDirectory()) {
            File[] fileArr = file.listFiles();
            for (File fileInArr : fileArr) {
                deleteFileOrDir(fileInArr);
            }
        } else {
            file.delete();
        }
    }

    /**
     * 删除目录下所有文件和目录
     */
    public static void delFilesInDir(File dirFile) {
        if (dirFile.isDirectory()) {
            File[] fileArr = dirFile.listFiles();
            for (File fileInArr : fileArr) {
                deleteFileOrDir(fileInArr);
            }
        }
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

    public static boolean createOrExistsDir(final File file) {
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

    /**
     * 打包成zip包
     */
    public static void generateZip(File[] files, String outputFilePath) throws Exception {
        ZipOutputStream out = null;
        try {
            byte[] buffer = new byte[1024];
            //生成的ZIP文件名为Demo.zip
            out = new ZipOutputStream(new FileOutputStream(outputFilePath));
            //需要同时下载的两个文件result.txt ，source.txt
            for (File file : files) {
                FileInputStream fis = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(file.getName()));
                int len;
                //读入需要下载的文件的内容，打包到zip文件
                while ((len = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.closeEntry();
                fis.close();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
