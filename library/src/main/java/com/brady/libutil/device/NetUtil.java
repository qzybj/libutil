package com.brady.libutil.device;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.brady.libutil.log.CLog;


/**
 * 网络相关工具类
 */
public class NetUtil {

	public static final int NETWORK_TYPE_BASE = 0x010100;
	/**未知类型网络*/
    public static final int NETWORK_TYPE_UNKNOWN = NETWORK_TYPE_BASE+1;
    /**WIFI网络开启*/
    public static final int NETWORK_TYPE_WIFI = NETWORK_TYPE_BASE+2;
    /**mobile网络开启：2G、2.5G、3G、3.5G等*/
    public static final int NETWORK_TYPE_MOBILE = NETWORK_TYPE_BASE+3;
    /** wifi和mobile网络都已开启*/
    public static final int NETWORK_TYPE_ALL = NETWORK_TYPE_BASE+4;
    
    /**网络类型 - 当前网络为3G网络*/
    public static final int NETWORK_TYPE_MOBILE_3G = NETWORK_TYPE_BASE+10;
    /**网络类型 - 当前网络为4G网络*/
    public static final int NETWORK_TYPE_MOBILE_4G = NETWORK_TYPE_BASE+11;
    /**网络类型 - 当前网络为2G或者其他网络*/
    public static final int NETWORK_TYPE_MOBILE_OTHER = NETWORK_TYPE_BASE+12;
	/**网络类型 - 无连接*/
	public static final int NETWORK_TYPE_NO_CONNECTION = -1231545315;
    

	/**
	 * 获取当然有效的网络类型，该函数只区分WIFI和MOBILE。详细区分
	 * wifi、2g、3g、4g请查看函数：<BR>
	 * @return int 网络类型
	 */
	public static int getNetConnectType(Context con) {
		int res = NETWORK_TYPE_UNKNOWN;
        final ConnectivityManager connMgr =
				(ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (((wifi != null) && wifi.isAvailable() && wifi.isConnectedOrConnecting())
                && ((mobile != null) && mobile.isAvailable() && mobile.isConnectedOrConnecting())) {
        	res = NETWORK_TYPE_ALL;
        } else if ((wifi != null) && wifi.isAvailable() && wifi.isConnectedOrConnecting()) {
        	res = NETWORK_TYPE_WIFI;
        } else if ((mobile != null) && mobile.isAvailable() && mobile.isConnectedOrConnecting()) {
        	res = NETWORK_TYPE_MOBILE;
        } else {
        	res = NETWORK_TYPE_UNKNOWN;
        }
        return res;
    }
	
	
	/**
	 * 获取当前有效网络类型，能够详细区分WIFI、2G、3G等网络类型。如果想只区分
	 * WIFI和MOBILE，请查看函数：
	 * @return
	 */
	public static int getNetConnectSubType(Context con) {
		int type = NETWORK_TYPE_UNKNOWN;
		int subtype = NETWORK_TYPE_UNKNOWN;
		ConnectivityManager connManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
		if ((activeNetInfo != null) && activeNetInfo.isConnectedOrConnecting()) {
			type = activeNetInfo.getType();
			CLog.d("getNetConnectSubType: activeNetInfo.getType() = "+type);
			if (type == ConnectivityManager.TYPE_WIFI) {
				type = NETWORK_TYPE_WIFI;
				subtype = type;
				CLog.d("getNetConnectSubType: subtype = "+"NETWORK_TYPE_WIFI");
			} else if (type == ConnectivityManager.TYPE_MOBILE) {
				switch (activeNetInfo.getSubtype()) {
				case TelephonyManager.NETWORK_TYPE_1xRTT:// ~ 50-100 kbps
				case TelephonyManager.NETWORK_TYPE_CDMA:// ~ 14-64 kbps IS95A or IS95B
				case TelephonyManager.NETWORK_TYPE_EDGE:// ~ 50-100 kbps
				case TelephonyManager.NETWORK_TYPE_GPRS: // ~ 100 kbps
					subtype = NETWORK_TYPE_MOBILE_OTHER;
					CLog.d("getNetConnectSubType: subtype = "+"NETWORK_TYPE_MOBILE_OTHER");
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_0:// ~ 400-1000 kbps
				case TelephonyManager.NETWORK_TYPE_EVDO_A:// ~ 600-1400 kbps
//				case TelephonyManager.NETWORK_TYPE_HSDPA: // ~ 2-14 Mbps
//				case TelephonyManager.NETWORK_TYPE_HSPA: // ~ 700-1700 kbps
//				case TelephonyManager.NETWORK_TYPE_HSUPA:// ~ 1-23 Mbps
				case TelephonyManager.NETWORK_TYPE_UMTS:// ~ 400-7000 kbps
					subtype = NETWORK_TYPE_MOBILE_3G;
					CLog.d("getNetConnectSubType: subtype = "+"NETWORK_TYPE_MOBILE_3G");
					 break;
				// NOT AVAILABLE YET IN API LEVEL 7
				// case Connectivity.NETWORK_TYPE_EHRPD:// ~ 1-2 Mbps
				// case Connectivity.NETWORK_TYPE_EVDO_B:// ~ 5 Mbps
				// case Connectivity.NETWORK_TYPE_HSPAP:// ~ 10-20 Mbps
				// case Connectivity.NETWORK_TYPE_LTE:// ~ 10+ Mbps
				//	 subtype = NETWORK_TYPE_MOBILE_4G;
				//break;
			    // Unknown
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				default:
					subtype = NETWORK_TYPE_UNKNOWN;
					CLog.d("getNetConnectSubType: subtype = "+"NETWORK_TYPE_UNKNOWN");
                  break;
				}
			}
		}
		CLog.d("getNetConnectSubType:----- end ------");
		return subtype;
	}

	/**
	 * 判断终端网络是否有效
	 * @return boolean TRUE:代表网络有效
	 */
	public static boolean isNetConnected(Context con) {
		return getNetConnectType(con)!=NETWORK_TYPE_UNKNOWN;
	}

	/**
	 * 判断当前网络的类型是否是移动网络
	 *
	 * @return 当前网络的类型是否是移动网络。false：当前没有网络连接或者网络类型不是移动网络
	 */
	public static boolean isMobileByType(Context con) {
		return getCurrentNetworkType(con) == ConnectivityManager.TYPE_MOBILE;
	}


	/**
	 * 获取当前网络的类型
	 *
	 * @return 当前网络的类型。具体类型可参照ConnectivityManager中的TYPE_BLUETOOTH、TYPE_MOBILE、TYPE_WIFI等字段。当前没有网络连接时返回NetworkUtils.NETWORK_TYPE_NO_CONNECTION
	 */
	public static int getCurrentNetworkType(Context con) {
		NetworkInfo networkInfo
				= ((ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		return networkInfo != null? networkInfo.getType(): NETWORK_TYPE_NO_CONNECTION;
	}

	/**
	 * 获取当前网络的状态
	 *
	 * @return 当前网络的状态。具体类型可参照NetworkInfo.State.CONNECTED、NetworkInfo.State.CONNECTED.DISCONNECTED等字段。当前没有网络连接时返回null
	 */
	public static NetworkInfo.State getCurrentNetworkState(Context con) {
		NetworkInfo networkInfo =
				((ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		return networkInfo != null ? networkInfo.getState() : null;
	}

	/**
	 * 判断是否连接WIFI
	 * @return  boolean
	 */
	public static boolean isWifiConnected(Context con) {
		ConnectivityManager connectivityManager =
				(ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo =
				connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetworkInfo.isConnected()) {
			return true;
		}
		return false;
	}
}