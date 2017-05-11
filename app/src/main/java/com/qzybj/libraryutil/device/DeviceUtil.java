package com.qzybj.libraryutil.device;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;


import com.qzybj.libraryutil.CLog;
import com.qzybj.libraryutil.UtilsManager;
import com.qzybj.libraryutil.data.StringUtil;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

/** Android系统工具类*/
public class DeviceUtil {
	private static String packageName = null ;
	
	private static String imei = null ;

	/**
	 * 根据/system/bin/或/system/xbin目录下是否存在su文件判断是否已ROOT
	 * @return true：已ROOT
	 */
	public static boolean isRoot() {
		try {
			return new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
		} catch (Exception e) {
			return false;
		}
	}

	public static int getScreenWidth( Context context) {
		DisplayMetrics dm = new DisplayMetrics(  );
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
    public static int getScreenHeight(Context context){
    	DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);

    return dm.heightPixels;
    }
    
    /**
	 * 获取当前手机的密度(float)
	 * 
	 * @param context
	 * @return
	 */
	public static float getDensity(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return dm.density;
	}
	/**
	 * 获取当前手机的密度(int)
	 * 
	 * @param context
	 * @return
	 */
	public static float getDensityDpi(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return dm.densityDpi;
	}
    
    /**
     * 获取系统当前时间，精确到秒
     * @return 返回当前时间字符串格式为 yyyyMMddHHmmss
     */
    public static String getNowTime(){
		Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(data);
    }
    
    /**
     * 获取系统版本
     * DeviceUtil.getSDKVersion()<BR>
      * <P>Author : chenbo </P>  
     * <P>Date : 2012-11-8 </P>
     * @return string
     */
    @SuppressWarnings("deprecation")
	public static String getAndroidSDKVersion(){
         return Build.VERSION.SDK;
    }

    /**
     * 获取数字类型的sdk版本号
     * @return
     */
    public static int getSDKVerionCode(){
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机型号
     * DeviceUtil.getPhoneModel()<BR>
      * <P>Author : chenbo </P>
     * <P>Date : 2012-11-8 </P>
     * @return
     */
    @SuppressWarnings("deprecation")
	public static String getPhoneModel(){
        return Build.VERSION.SDK;
    }
    
    /**
     * 获取手机号
     * DeviceUtil.getPhoneNumber()<BR>
      * <P>Author : chenbo </P>  
     * <P>Date : 2012-11-8 </P>
     * @return
     */
    public static String getPhoneNumber(Context context){
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
    }
    
    /**
     * 读取sim卡序列号  IMSI
     */
    public static String readSimSerialNum(Context context) {
        TelephonyManager telephonyManager = null;
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
        }
        // telephonyManager.getSubscriberId();
        return telephonyManager.getSimSerialNumber();
    }
    /**
     * 读取手机串号  IMEI
     * @return String 手机串号
     */
    public static String getIMEI() {
    	if( StringUtil.isEmpty(imei)){
    		//改成只取一次
    		TelephonyManager telephonyManager = (TelephonyManager) UtilsManager.instance().getSystemService(Context.TELEPHONY_SERVICE);
    		imei = telephonyManager.getDeviceId();
    	}
		return imei ;
    }
    
    /**
     * 获取当前操作系统的语言
     * 
     * @return String 系统语言
     */
    public static String getSysLanguage() {
        return Locale.getDefault().getLanguage();
    }
    
    /**
     * 获取运营商信息
     *
     * @param context 上下文
     * @return String 运营商信息
     */
    public static String getCarrier(Context context) {
		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telManager.getSubscriberId();
        if (imsi != null && !"".equals(imsi)) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
				// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
                return "china_mobile";
            } else if (imsi.startsWith("46001")) {
                return "china_unicom";
            } else if (imsi.startsWith("46003")) {
                return "china_telecom";
            }
        }
        return "";
    }

    /**
     * 得到手机MAC地址
     *
     * @param context
     *            上下文对象
     * @return
     */
    public static String getLocalMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
    }

    /**
     * 得到手机IP地址
     * @param con 上下文对象
     * @return null 为无网络
     */
    public static String getIPAddress(Context con) {
    	int netConnectType = NetUtil.getNetConnectType(con);
    	switch (netConnectType) {
    	case NetUtil.NETWORK_TYPE_UNKNOWN:{//no Net Connected
    		return null;
    	}
    	case NetUtil.NETWORK_TYPE_WIFI:{//wifi
    		return getWIFIIpAddress(con);
    	}
    	default:{//mobile
    		return getGPRSIpAddress();
    	}
    	}
    }

	private static String getWIFIIpAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress =wifiInfo.getIpAddress();
		if(ipAddress==0)return null;
		return (ipAddress & 0xFF ) + "." +((ipAddress >> 8 ) & 0xFF) + "." +((ipAddress >> 16 ) & 0xFF) + "." +( ipAddress >> 24 & 0xFF) ;
	}

	private static String getGPRSIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			CLog.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}

	/**
	 * 检查网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean hasActiveNetwork(Context context) {
		ConnectivityManager mConnectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 检查网络连接，如果无网络可用，就不需要进行连网操作等
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();
		if (info == null/* || !info.isAvailable()*/) {
			return false;
		} else {
			return true;
		}
	}
}