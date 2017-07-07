package com.brady.libutil.view;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;

import com.brady.libutil.CLog;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 	WebView的工具类
 */
public class WebViewUtil {
	public final String TAG = getClass().getSimpleName();
	private final static String ENCODE_TYPE = "UTF_8";//"gb2312"
	private final static String MIME_TYPE  = "text/html; charset=UTF-8";

	/** WebView的初始化 */
	public  static void initWebView(WebView w) {
		if(w!=null){
			WebSettings ws = w.getSettings();
			ws.setJavaScriptEnabled(true);
			//webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
			ws.setRenderPriority(RenderPriority.HIGH);// 提高渲染的优先级
			ws.setDomStorageEnabled(true);// 设置可以使用localStorage
			//webSettings.setBlockNetworkImage(true);//把图片加载放在最后来加载渲染
			ws.setLoadWithOverviewMode(true);

			ws.setAllowFileAccess(true);// 设置允许访问文件数据
			ws.setBuiltInZoomControls(false);// 设置是否启用内置的缩放控件
			ws.setSupportZoom(false); // 设置是否支持缩放
			ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//不设置网络缓存
			ws.setDatabaseEnabled(true);

			//H5页面视频播放相关
		//		webSettings.setPluginState(WebSettings.PluginState.ON);
		//		webSettings.setLoadWithOverviewMode(true);
		//		webSettings.setUseWideViewPort(true);//设置适应屏幕

			w.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 去掉滚动条占位
			w.requestFocus();// 支持网页内部操作，比如点击按钮
		}
	}

	/**
	 *
	 * @param webView
	 */
	public static void clearCache(WebView webView) {
		if (webView!=null) {
			webView.clearHistory();
			webView.clearCache(true);
			webView.clearFormData();
		}
	}

	/**
	 * 释放webview BasicWebviewActivity.destroyWebView()<BR>
	 */
	public static void destroyWebView(WebView webView) {
		if (webView != null) {
			webView.stopLoading();
			//root.removeView(mWebView);//error nullpointerexception
			webView.removeAllViews();
			webView.destroy();
			webView = null;
		}
	}

	/**
	 * 删除所有cookie
	 * @param context
	 */
	public void deleteAllCookies(Context context) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		//cookieManager.removeSessionCookie();//移除用户信息
		//cookieManager.removeExpiredCookie();//移除生命周期信息
		CookieSyncManager.getInstance().sync();
	}
	/**
	 * 
	 * 添加指定cookie值
	 * @param context
	 * @param url
	 * @param cookies
	 */
	public void setCookies(Context context, String url, String cookies) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		cookieManager.setCookie(url, cookies);//指定要修改的cookies
		CookieSyncManager.getInstance().sync();
	}
	

	/**
	 * 设置COOKIE
	 */
	private static void setLocalCookie(CookieManager cookieManager, String url, String key, String sessionid) {
		String temp = String.valueOf(System.currentTimeMillis());
		String start = temp.substring(temp.length() - 5, temp.length());
		String end = temp.substring(temp.length() - 5, temp.length());

		String val = Base64.encodeToString(
				Base64.encodeToString((start + sessionid + end).getBytes(),
						Base64.DEFAULT).getBytes(), Base64.DEFAULT);
		cookieManager.setCookie(url, (key+"=" + val));
	}
	

	/**
	 * HTML 编码确保文本能在浏览器中正确显示，而不是被浏览器解释为 HTML。 
	 * 例如，如果文本字符串包含小于号 (<) 或大于号 (>)，则浏览器会把这些字符解释为 HTML 标记的起始或结束括号。 
	 * 当字符为 HTML 编码时，它们将转换为字符串 &lt; 和 &gt;，以便浏览器能够正确显示小于号和大于号。
	 * @param html
	 * @return
	 */
	public static String htmlEncodeText(String html){
		return TextUtils.htmlEncode(html);
	}
	
	/**
	 * 处理html文本内容转换为webview可以显示的内容
	 * @param html
	 * @return
	 */
	public static String processHtmlContent(String html){
		html = Html.fromHtml(html).toString();
		//html=String.format(html);
		return html;
	}

	/**
	 * 判断url中是否包含"bmp","gif","jpg","png"等图片关键字
	 * @param url
	 * @return
	 */
	public static boolean isImgUrl(String url){
		String lowerCaseUrl = url.toLowerCase(Locale.getDefault());
		if (lowerCaseUrl.indexOf("http")>-1) {
			return false;
		}
		String key[] = {"bmp","gif","jpg","png"};
		for (int i = 0; i < key.length; i++) {
			int flag= lowerCaseUrl.indexOf(key[i]);
			if (flag==-1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * 得到网页中图片的地址list集合
	 * @param htmlStr
	 * @return
	 */
	public static ArrayList<String> fetchURL(String htmlStr) {
		ArrayList<String> pics = new ArrayList<String>();
		CLog.log(htmlStr);
		String regEx_img = "<img.*?src=\"http://(.*?).jpg\""; // 图片链接地址
		Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		Matcher m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			String src = m_image.group(1);
			if (src.length() < 100) {
				pics.add("http://" + src + ".jpg");
			}
		}
		return pics;
	}
}