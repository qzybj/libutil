package com.brady.libutil.log;

import android.text.TextUtils;

import com.brady.libutil.data.StringUtil;
import com.brady.libutil.log.i.ILog;
import com.brady.libutil.log.i.impl.DefILogImpl;

/**
 * @author Cuckoo
 * @date 2017-05-11
 * @descitpion
 *      日志工具类, 统一输出日志
 */

public class CLog {
    private static ILog log = null ;
    private static boolean isOn = false ;

    /**
     * 日志类型
     */
    enum LogType{
        D,I,W,E
    }

    /**
     * 设置log实现类
     * @param logImpl
     */
    public static void setLogImpl(ILog logImpl){
        log = logImpl;
    }

    public static void e(Exception e) {
        e(null, e);
    }

    public static void e(String tag, Exception e) {
        log(LogType.E,tag,e.getLocalizedMessage(),getMethodInfo());
    }
    public static void e(String msg) {
        log(LogType.E,null,msg,getMethodInfo());
    }

    public static void e(String tag, String msg) {
        log(LogType.E,tag,msg,getMethodInfo());
    }

    public static  void e(String tag, String msg, Throwable e) {
        if(isOn()){
            if(e != null && !TextUtils.isEmpty(e.getMessage())){
                msg = StringUtil.format(msg) + e.getMessage();
            }
            log(LogType.E,tag,msg,getMethodInfo());
        }
    }

    public static  void w(String msg) {
        log(LogType.W,null,msg,getMethodInfo());
    }

    public static  void w(String tag, String msg) {
        log(LogType.W,tag,msg,getMethodInfo());
    }

    
    public static  void i(String msg) {
        log(LogType.I,null,msg,getMethodInfo());
    }

    
    public static  void i(String tag, String msg) {
        log(LogType.I,tag,msg,getMethodInfo());
    }

    public static  void d(String msg) {
        log(LogType.D,null,msg,getMethodInfo());
    }

    public static  void d(String tag, String msg) {
        log(LogType.D,tag,msg,getMethodInfo());
    }

    /**
     * 打印日志
     * @param logType
     *  日志类型
     * @param tag
     *  日志tag
     * @param msg
     * @param methodInfo
     *  方法信息， 如果有值就打印
     */
    private static  void log(LogType logType, String tag, String msg, String methodInfo) {
        if( isOn() ){
            if(!TextUtils.isEmpty(methodInfo)){
                msg = methodInfo+ msg;
            }
            if(log==null){
                log =  new DefILogImpl();
            }
            if(TextUtils.isEmpty(tag)){
                tag = log.getTag();
            }
            if(LogType.D == logType){
                log.d(tag,msg);
            }else if(LogType.I == logType){
                log.i(tag,msg);
            }else if(LogType.W == logType){
                log.w(tag,msg);
            }else if(LogType.E == logType){
                log.e(tag,msg);
            }
        }
    }

    /**
     * 获取方法信息,当允许打日志并且允许显示调用者信息时 返回值
     * @return
     */
    private static String getMethodInfo(){
        if(isOn() &&
                isShowMethod()){
            StackTraceElement element = getPreviousCallerInfo(2);
            if( element != null ){
                return element.toString()+"\n";
            }
        }
        return null ;
    }

    /**
     * 是否开启日志
     * @return
     */
    private static boolean isOn(){
        return isOn;
    }
    private static void switchOn(boolean isOnFlag){
        isOn = isOnFlag;
    }

    /**
     * 是否显示调用日志的类以及方法
     * @return
     */
    private static boolean isShowMethod(){
        if( log != null ){
            return log.isShowMethod();
        }
        return false;
    }

    /**
     * 获取调用当前方法前的第二个方法信息
     * @param methodLayer
     *  查看调用当前方法之前几层方法。 如{@link CLog#d(String, String)}调用改方法，需要查看改方法的信息，其值为1
     * @return
     */
    private static StackTraceElement getPreviousCallerInfo(int methodLayer){
        StackTraceElement[] elements  = Thread.currentThread().getStackTrace();
        /**
         * 前三层的方法分别为：
         *  0 = {StackTraceElement@3668} "dalvik.system.VMStack.getThreadStackTrace(Native Method)"
         *  1 = {StackTraceElement@3669} "java.lang.Thread.getStackTrace(Thread.java:580)"
         *  2 = {StackTraceElement@3670} "com.cutil.OUtil.getPreviousCallerInfo(OUtil.java:22)"
         */

        int layer = 3 + methodLayer;
        if(elements!= null && elements.length > layer){
            //根据传入的参数以及方法名 获取对应的方法
            return elements[layer];
        }
        return null ;
    }
}