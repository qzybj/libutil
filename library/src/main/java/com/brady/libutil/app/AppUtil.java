package com.brady.libutil.app;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;

import com.brady.libutil.log.CLog;
import com.brady.libutil.UtilsManager;

import java.io.File;


/** App相关工具类,包*/
public class AppUtil {

    private static PackageInfo mPackageInfo;

    /**
     * 获取安装包信息
     * @return
     */
    public static PackageInfo getPackageInfo(){
        if(mPackageInfo==null){
            try {
                mPackageInfo =  UtilsManager.instance().getPackageManager().
                        getPackageInfo(getPackageName(), 0);
                return mPackageInfo;
            } catch (NameNotFoundException e) {
                CLog.e("获取软件的版本名称："+e.getMessage());
            }
        }
        return mPackageInfo;
    }

    /**
     * 获取包名
     * @return
     */
    public static String getPackageName(){
        return UtilsManager.instance().getPackageName();
    }
    /**
     * 获取当前应用版本号名称
     * @return 版本名称
     */
	public static String getVersionName() {
		String versionName = "";
		try {
			versionName = getPackageInfo().versionName;
		} catch (Exception e) {
			 CLog.e("获取软件的版本名称："+e.getMessage());
		}
		return versionName;
	}
	
	
	 /**
     * 获取当前应用版本号code值
     * @return 版本名称
     */
	public static int getVersionCode() {
		try {
			return getPackageInfo().versionCode;
		} catch (Exception e) {
			CLog.e("获取软件的版本号："+e.getMessage());
		}
		return -1;
	}
	
    
    /**
     * 获取应用名称
     * @return 应用名称
     */
    public  static String getAppName() {
    	String applicationName = "";
		try {
			PackageManager pm = UtilsManager.instance().getPackageManager();
			PackageInfo pi = getPackageInfo();
			applicationName = pi.applicationInfo.loadLabel(pm).toString();
		} catch (Exception e) {
			CLog.e("获取应用名称: "+e.getMessage());
		}
		return applicationName;
    }

    /**
     * 获取应用缓存路径
     * @return
     */
	public static File getAppCacheDir() {
		if (Build.VERSION.SDK_INT > 7) {
			File dir = UtilsManager.instance().getExternalCacheDir();
			return dir;
		} else {
			File esd = Environment.getExternalStorageDirectory();
			if (esd != null) {
				return new File(esd, "/Android/data/" + getPackageName()+ "/cache");
			} else {
				return null;
			}
		}
	}

    /**
     * 
     * 判断桌面是否已添加快捷方式
     * @return
     */
    public static boolean isShortcutExist() {
        boolean result = false;
        String title = null;
        try {
            PackageManager pm = UtilsManager.instance().getPackageManager();
            title = pm.getApplicationLabel(
                    pm.getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
            CLog.e(e);
        }

        final String uriStr;
        if (Build.VERSION.SDK_INT < 8) {
            uriStr = "content://com.android.launcher.settings/favorites?notify=true";
        } else {
            uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
        }
        final Uri CONTENT_URI = Uri.parse(uriStr);
        final Cursor c = UtilsManager.instance().getContentResolver().
                query(CONTENT_URI, null,"title=?", new String[] { title }, null);
        if (c != null && c.getCount() > 0) {
            result = true;
        }
        return result;
    }

    /**
     *
     * 为当前应用添加桌面快捷方式
     * @param iconResId 图片资源id
     */
    public static void addShortcut(int iconResId) {
        if (iconResId>0){
            try {
                Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
                Intent shortcutIntent =
                        UtilsManager.instance().getPackageManager().
                                getLaunchIntentForPackage(getPackageName());
                shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
                String title = null;
                try {
                    PackageManager pm = UtilsManager.instance().getPackageManager();
                    title = pm.getApplicationLabel(
                            pm.getApplicationInfo(getPackageName(),
                                    PackageManager.GET_META_DATA)).toString();
                } catch (Exception e) {
                    CLog.e(e);
                }
                // 快捷方式名称
                shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
                // 不允许重复创建（不一定有效）
                shortcut.putExtra("duplicate", false);
                // 快捷方式的图标
                Parcelable iconResource = Intent.ShortcutIconResource.
                        fromContext(UtilsManager.instance(), iconResId);
                shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
                UtilsManager.instance().sendBroadcast(shortcut);
            } catch (Exception e) {
                CLog.e(e);
            }
        }
    }
    
    /**
     * 删除当前应用的桌面快捷方式
     */
    public static void delDesktopShortcut() {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        // 获取当前应用名称
        String title = null;
        try {
            PackageManager pm = UtilsManager.instance().getPackageManager();
            title = pm.getApplicationLabel(
                    pm.getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
            CLog.e(e);
        }
        // 快捷方式名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        Intent shortcutIntent = UtilsManager.instance().getPackageManager().
                getLaunchIntentForPackage(getPackageName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        UtilsManager.instance().sendBroadcast(shortcut);
    }
}