package com.qzybj.libraryutil.h5;

import android.net.Uri;


import com.qzybj.libraryutil.CLog;
import com.qzybj.libraryutil.data.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**URL辅助工具类*/
public class WebUrlUtil {

	public static final String TAG =WebUrlUtil.class.getName();
	
	/**
	 * 获取URL中指定key的参数 
	 * @param webUrl
	 * @return
	 */
	public static String getParam(String webUrl, String key){
		try {
			if (StringUtil.isNotEmpty(webUrl)) {
				Uri uri = Uri.parse(StringUtil.format(webUrl));
				if (uri!=null) {
					return uri.getQueryParameter(key);
				}
			}
		} catch (Exception e) {
			CLog.e(TAG, e.getMessage());
		}
		return "";
	}

	/**
	 * 解析URL中的参数,并以map返回
	 * @param webUrl
	 * @return
	 */
	public static HashMap<String, String> getParams2Map(String webUrl){
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (StringUtil.isNotEmpty(webUrl)) {
				Uri uri = Uri.parse(StringUtil.format(webUrl));
				if (uri!=null) {
					Set<String> names = uri.getQueryParameterNames();
					if (names!=null&&names.size()>0) {
						Iterator<String> it = names.iterator();
						while (it.hasNext()){
							String key = it.next();
							if (StringUtil.isNotEmpty(key)) {
								map.put(key,uri.getQueryParameter(key));
							}
						}
					}
					return map;
				}
			}
		} catch (Exception e) {
			CLog.e(TAG, e.getMessage());
		}
		return map ;
	}
	public static URI convert2URI(String webUrl){
		try {
			URI uri = new URI(StringUtil.format(webUrl));
			return uri;
		} catch (URISyntaxException e) {
			CLog.e(e);
		}
		return null;
	}

	public static String getScheme(String webUrl){
		if(StringUtil.isNotEmpty(webUrl)){
			URI uri = convert2URI(webUrl);
			if(uri!=null&& StringUtil.isNotEmpty(uri.getScheme())){
				return uri.getScheme();
			}
		}
		return "";
	}
	public static String getCallCommand(String webUrl){
		if(StringUtil.isNotEmpty(webUrl)){
			URI uri = convert2URI(webUrl);
			if(uri!=null&& StringUtil.isNotEmpty(uri.getHost())){
				return uri.getHost();
			}
		}
		return "";
	}
	public static String getJsonValue(String jsonStr, String key){
		if(StringUtil.isNotEmpty(jsonStr)&& StringUtil.isNotEmpty(key)){
			JSONObject json = StringUtil.str2JsonObj(jsonStr);
			if (json!=null&&json.has(key)) {
				try {
					String keyValue = json.getString(key);
					return keyValue;
				} catch (JSONException e) {
				}
			}
		}
		return "";
	}
}