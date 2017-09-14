package com.brady.libutil.io;

import android.content.Context;
import android.os.Environment;

import com.brady.libutil.UtilsManager;
import com.brady.libutil.data.StringUtil;
import com.brady.libutil.log.CLog;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * File操作类
 */
public class FileUtil {

    /**回车换行*/
    public final static String NEW_LINE_SYMBOL = "\r\n";

    /**
     * 获取程序外部的缓存目录
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    /**
     * 获取应用缓存路径
     * @return
     */
    public static File getAppCacheFilesDir() {
        return UtilsManager.instance().getExternalCacheDir();
    }

    /**
     * 获取可以使用的缓存目录,如果有内存卡，优先使用内存卡，否则使用手机自带存储空间。
     * @param context
     * @param uniqueName 目录名称
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ?
                getExternalCacheDir(context).getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 存储String为文件
     * @param filePath
     * @param data
     * @param append  true时，并且文件存在，是追加内容到文件
     */
    @Deprecated
    public static void writeFile(String filePath, String data, boolean append){
        write(filePath,data,append);
    }

    /**
     * 存储String为文件
     * @param filePath 文件路径
     * @param data 文件内容
     * @param append  true时，并且文件存在，是追加内容到文件
     */
    public static boolean write(String filePath, String data, boolean append) {
        boolean saveFlag = false;
        File targetFile = new File(filePath);
        File dir = targetFile;
        if(!targetFile.isDirectory()) {
            dir = targetFile.getParentFile();
        }
        boolean isSuccess = dir.exists()||dir.mkdirs();

        if(isSuccess){
            OutputStreamWriter output = null;
            BufferedReader buffer = null;
            try {
                output = new OutputStreamWriter(new FileOutputStream(targetFile, append),"utf-8");
                buffer = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes())));

                String line;
                while((line = buffer.readLine()) != null) {
                    output.write(line);
                    //output.write(NEW_LINE_SYMBOL);
                }
                output.flush();
                saveFlag = true;
                CLog.e(FileUtil.class.getSimpleName(),"File create success!");
            } catch (Exception e) {
                CLog.e(e);
                saveFlag = false;
            } finally {
                if(output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        CLog.e(e);
                        saveFlag = false;
                    }
                }
                if(buffer != null) {
                    try {
                        buffer.close();
                    } catch (IOException e) {
                        CLog.e(e);
                        saveFlag = false;
                    }
                }
            }
        } else {
            CLog.e(FileUtil.class.getSimpleName(),"File create fail!");
        }
        return saveFlag;
    }

    /**
     * 通过递归得到某一路径下所有的目录及其文件
     * @param folderFilePath
     */
    public static ArrayList<String> getFiles(String folderFilePath){
        ArrayList<String> fileList = new ArrayList<String>();
        if(StringUtil.isNotEmpty(folderFilePath)) {
            File folderFile = new File(folderFilePath);
            if(folderFile.exists()&&folderFile.isDirectory()){
                File[] files = folderFile.listFiles();
                if(files!=null&&files.length>0){
                    for(File file:files){
                        if(file.isFile()){
                            fileList.add(file.getAbsolutePath());
                        }
                    }
                }
            }
        }
        return fileList;
    }
    /**
     * 删除文件
     * @param dir
     * @return
     */
    public static boolean delete(File dir) {
        boolean result = false;
        if (dir == null) {
            CLog.e("deleteDir null");
            return result;
        }
        try {
            if (dir.isDirectory()) {
                String[] children = dir.list();
                if (children != null) {
                    for (int i=0; i<children.length; i++) {
                        boolean success = delete(new File(dir, children[i]));
                        if (!success) {
                            return false;
                        }
                    }
                }
                result = dir.delete();
            } else {
                result = dir.delete();
            }
        } catch (Exception e) {
            CLog.e(e);
        }
        return result;
    }

    /**
     * 复制文件
     * @param fromFile
     * @param toFile
     */
    public static void copy(File fromFile, File toFile) {
        FileInputStream fisfrom = null;
        if (fromFile != null && fromFile.exists()&&fromFile.isFile()) {
            try {
                copy(new FileInputStream(fromFile), toFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            CLog.e("err", "File not found!");
        }
    }

    /**
     * 复制文件
     * @param fromIns
     * @param toFile
     */
    public static void copy(InputStream fromIns, File toFile) {
        FileOutputStream fosto = null;
        try {
            if (toFile.exists()) {
                toFile.delete();
            }else{
                toFile.getParentFile().mkdirs();
            }
            fosto = new FileOutputStream(toFile);
            byte buffer[] = new byte[1024];
            int read = 0;
            while ((read = fromIns.read(buffer)) > 0) {
                fosto.write(buffer, 0, read); // 将内容写到新文件当中
            }
            fosto.flush();
        } catch (Exception e) {
            CLog.e(e);
        }finally {
            try {
                if (fromIns != null) {
                    fromIns.close();
                }
            } catch (Exception e) {
            }
            try {
                if (fosto != null) {
                    fosto.close();
                }
            } catch (Exception e) {
            }
        }
    }



    /**
     * 获取文件夹大小
     * @param folderFilePath
     * @return long   返回的是字节长度，1M=1024k=1048576字节
     */
    public static long getFileSize(String folderFilePath) {
        if (StringUtil.isNotEmpty(folderFilePath)) {
            return getFileSize(new File(folderFilePath));
        }
        return 0;
    }

    /**
     * 获取文件夹大小
     * @param file
     * @return long   返回的是字节长度，1M=1024k=1048576字节
     */
    public static long getFileSize(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                return getDirectorySize(file);
            } else {
                return getSize(file);
            }
        }
        return 0;
    }

    /**
     * 获取指定文件夹
     * @param f
     * @return
     * @throws Exception
     *
     */
    private static long getDirectorySize(File f){
        long size = 0;
        File fList[] = f.listFiles();
        for (int i = 0; i < fList.length; i++) {
            if (fList[i].isDirectory()) {
                size = size + getDirectorySize(fList[i]);
            } else {
                size = size + getSize(fList[i]);
            }
        }
        return size;
    }


    /**
     * 获取指定文件大小
     * @param file
     * @return
     * @throws Exception 　　
     */
    private static long getSize(File file) {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis;
            try{
                fis = new FileInputStream(file);
                size = fis.available();
            }catch (Exception e){
            }
        }
        return size;
    }

    /**
     * 转换文件字节为mb
     * @param fileSize 文件字节
     * @return
     */
    public static int fileSize2MB(long fileSize){
        int sizeMB = 0;
        if(fileSize>0){
            try{
                sizeMB = Long.valueOf(fileSize/1048576).intValue();
            }catch (Exception e){

            }
        }
        //返回的是字节长度，1M=1024k=1048576字节 也就是if(fileSize<5*1048576)就好了
        return sizeMB;
    }

    /**
     * 把字节数B转化为KB、MB、GB的方法
     * @param size 文件字节
     * @return
     */
    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }
}
