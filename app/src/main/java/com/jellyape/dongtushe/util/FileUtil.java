package com.jellyape.dongtushe.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;


public class FileUtil {

    /**
     * 判断外部存储是否可用
     */
    public static boolean isExternalStorageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取Sdcard根目录路径
     */
    public static String getSdCardRootPath() {
        if (!isExternalStorageAvailable()) {
            return null;
        }
        File root = Environment.getExternalStorageDirectory();
        return root.getAbsolutePath();
    }

    public static String makeDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                return null;
            }
        }
        return path;
    }

    /**
     * 获取系统默认SD卡的文件保存目录,没有就新建
     */
    public static String getDefaultLocalDir(String subDir) {
        String rootPath = getSdCardRootPath();
        if (rootPath == null) {
            return null;
        }
        String path_dir = rootPath + File.separatorChar + subDir;
        return makeDir(path_dir);
    }


    /**
     * 修改文件名
     *
     * @param oldName
     * @param newName
     * @return 文件修改是否成功
     */
    public static String rename(String path_dir, String oldName, String newName) {
        // String path_dir = FileUtil.getVideoDir(id);
        if (path_dir == null) {
            return null;
        }

        String path_old = path_dir + "/" + oldName;
        String path_new = path_dir + "/" + newName;
        File file_old = new File(path_old);
        File file_new = new File(path_new);
        if (file_old.exists() && !file_new.exists()) {
            file_old.renameTo(file_new);
        }

        return file_new.getAbsolutePath();
    }


    /**
     * 删除文件夹
     */
    public static void deleteDir(File dir) {
        if (dir != null && dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDir(file); // 递归删除子文件夹
                    } else {
                        file.delete(); // 删除子文件
                    }
                }
            }
            dir.delete(); // 最后删掉自己
        }
    }

    /**
     * 删除多个文件
     */
    public static void deleteFiles(File dir, FilenameFilter filter) {
        if (dir.exists()) {
            File[] files = dir.listFiles(filter);
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDir(file); // 递归删除子文件夹
                    } else {
                        file.delete(); // 删除子文件
                    }
                }
            }
        }
    }

    public static boolean moveDir(String dir_from, String dir_to) {

        if (dir_from == null || dir_to == null) {
            return false;
        }

        if (dir_from.equals(dir_to)) {
            return false;
        }

        boolean success = copyDir(dir_from, dir_to);
        if (success) {
            deleteDir(new File(dir_from)); // 复制完成之后，
        } else {
            deleteDir(new File(dir_to));
            return false;
        }

        return true;
    }

    public static boolean copyDir(String dir_from, String dir_to) {

		/*
         * if(dir_from==null || dir_to==null){ return false; }
		 * 
		 * if(dir_from.equals(dir_to)){ return false; }
		 */

        try {
            File dir = new File(dir_from);
            if (!dir.exists()) {
                return true; // 如果dir_from目录不存在，说明不需要移动，所以认为成功。
            }

            // 文件一览
            File[] files = dir.listFiles();
            if (files == null) {
                return false;
            }
            // 目标
            File moveDir = new File(dir_to);
            if (!moveDir.exists()) {
                moveDir.mkdirs();
            }
            // 文件移动
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    boolean success = copyDir(files[i].getPath(), dir_to + "/" + files[i].getName());
                    // 成功，删除原文件
                    if (success) {
                        files[i].delete();
                    }

                }
                File moveFile = new File(moveDir.getPath() + "/" + files[i].getName());
                // 目标文件夹下存在的话，删除
                if (moveFile.exists()) {
                    moveFile.delete();
                }

                copyFile(files[i], moveFile);
                // files[i].renameTo(moveFile);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
        return false;
    }

    /**
     * 获取文件夹大小
     */
    public static long getDirSize(File dir) {
        if (dir == null || !dir.exists()) {
            return 0;
        }

        long size = 0;
        File[] flist = dir.listFiles();
        if (flist != null) {
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getDirSize(flist[i]);
                } else {
                    size = size + flist[i].length();
                }
            }
        }

        return size;
    }

    public static boolean saveImageToLocal(String url_img, File file_img) {

        HttpURLConnection httpConnection = null;
        try {
            URL url = new URL(url_img);
            httpConnection = (HttpURLConnection) url.openConnection();
            BufferedInputStream bis = new BufferedInputStream(httpConnection.getInputStream());
            FileOutputStream fos = new FileOutputStream(file_img);

            int len = 0;
            byte[] buffer = new byte[1024 * 10]; // 文件写缓存
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }

            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return true;
    }


    public static boolean saveStreamToLocal(InputStream stream, File file_img) {

        try {
            BufferedInputStream bis = new BufferedInputStream(stream);
            FileOutputStream fos = new FileOutputStream(file_img);

            int len = 0;
            byte[] buffer = new byte[1024 * 10]; // 文件写缓存
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }

            fos.flush();
            fos.close();

        } catch (IOException e) {
        } catch (IllegalStateException e) {
        } catch (Exception e) {
        } finally {

        }
        return true;
    }

    public static byte[] getBytesFromFile(File file) {
        byte[] ret = null;
        try {
            if (file == null) {
                // log.error("helper:the file is null!");
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
            ret = out.toByteArray();
        } catch (IOException e) {
            // log.error("helper:get bytes from file process error!");
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 保存bitmap为本地文件
     *
     * @param target 本地文件
     * @param format 格式
     */
    public static boolean convertBitmapToFile(Bitmap bitmap, File target, CompressFormat format) {
        OutputStream os = null;
        try {
            target.createNewFile();
            os = new FileOutputStream(target);
            bitmap.compress(format, 100, os);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 加载本地图片
     *
     * @return
     */
    public static Bitmap getLocalBitmap(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            return BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字符串写入本地文件
     *
     * @return
     */
    public static boolean saveStringToLocal(String str, File file_local) {
        try {
            FileOutputStream fos = new FileOutputStream(file_local);
            Writer out = new OutputStreamWriter(fos);
            out.write(str);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读取本地文件到字符流
     */

    public static InputStream getLocalStream(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            return fis;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取本地文件到字符串
     *
     * @return
     */
    public static String getLocalString(File file) {
        try {
            StringBuffer buffer = new StringBuffer();
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            Reader in = new BufferedReader(isr);

            int i;
            while ((i = in.read()) > -1) {
                buffer.append((char) i);
            }
            in.close();

            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
