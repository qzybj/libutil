package com.brady.libutil.io;

import android.content.Context;
import android.os.Environment;

import com.brady.libutil.CLog;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * File操作类
 */
public class FileUtil {

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
     * 将信息写到固定文件
     * @param filePath
     * @param data
     * @param append  true时，并且文件存在，是追加内容到文件
     */
    public static void writeFile(String filePath, String data, boolean append){
        File targetFile = new File(filePath);
        File dir = targetFile ;
        if( !targetFile.isDirectory() ){
            dir = targetFile.getParentFile();
        }
        if(!dir.exists()){
            dir.mkdirs();
        }
        OutputStreamWriter output = null ;
        BufferedReader buffer = null ;
        String line;
        try{
            output = new OutputStreamWriter(new FileOutputStream(targetFile,append));
            buffer = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes())));
            while ((line = buffer.readLine()) != null) {
                output.write(line);
            }
            output.flush();
        }catch(Exception e){
            CLog.e(e);
        }finally{
            if( output != null ){
                try {
                    output.close();
                } catch (IOException e) {
                    CLog.e(e);
                }
            }
            if( buffer != null ){
                try {
                    buffer.close();
                } catch (IOException e) {
                    CLog.e(e);
                }
            }
        }
    }
}
