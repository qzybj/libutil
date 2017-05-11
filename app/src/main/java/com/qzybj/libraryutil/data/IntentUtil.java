package com.qzybj.libraryutil.data;

import android.content.Intent;
import android.os.Bundle;


public class IntentUtil {

	public static boolean isNotEmpty(Intent intent){
		return !isEmpty(intent);
	}
	public static boolean isEmpty(Intent intent){
		if (intent!=null&&intent.getExtras()!=null&&!intent.getExtras().isEmpty()) {
			return false;
		}
		return true;
	}

	public static boolean isNotEmpty(Bundle extras){
		return !isEmpty(extras);
	}
	public static boolean isEmpty(Bundle extras){
		if (extras!=null&&!extras.isEmpty()) {
			return false;
		}
		return true;
	}

	public static Bundle getBundle(Intent intent){
		if(!isEmpty(intent)){
			return intent.getExtras();
		}
		return null;
	}

	public static Intent setIString(Intent intent, String key, String value){
		if(intent==null){
			intent = new Intent();
		}
		intent.putExtras(setBString(intent.getExtras(), key,value));
		return intent;
	}

	public static Bundle setBString(Bundle extras, String key, String value){
		if (extras==null) {
			extras = new Bundle();
		}
		extras.putString(key, value);
		return extras;
	}
	
	public static String getString(Intent intent, String key){
		if(!isEmpty(intent)){
			return getString(intent.getExtras(), key);
		}
		return null;
	}
	public static int getInt(Intent intent, String key, int defValue){
		if(!isEmpty(intent)){
			return getInt(intent.getExtras(), key,defValue);
		}
		return defValue;
	}
	
	public static boolean getBoolean(Intent intent, String key, boolean defValue){
		if(!isEmpty(intent)){
			return getBoolean(intent.getExtras(), key,defValue);
		}
		return defValue;
	}
	
	public static String[] getStringArray(Intent intent, String key){
		if(!isEmpty(intent)){
			return getStringArray(intent.getExtras(), key);
		}
		return null;
	}
	
	public static String getString(Bundle extras, String key){
		if (containsKey(extras,key)) {
			return extras.getString(key);
		}
		return null;
	}
	public static int getInt(Bundle extras, String key, int defValue){
		if (containsKey(extras,key)) {
			return extras.getInt(key,defValue);
		}
		return defValue;
	}
	public static boolean getBoolean(Bundle extras, String key, boolean defValue){
		if (containsKey(extras,key)) {
			return extras.getBoolean(key,defValue);
		}
		return defValue;
	}
	public static String[] getStringArray(Bundle extras, String key){
		if (containsKey(extras,key)) {
			return extras.getStringArray(key);
		}
		return null;
	}
	
	public static boolean containsKey(Bundle extras, String key){
		if (isNotEmpty(extras)&&extras.containsKey(key)) {
			return true;
		}
		return false;
	}
	
	public static void removeString(Intent intent, String key){
		if(!isEmpty(intent)){
			removeString(intent.getExtras(), key);
		}
	}
	public static void removeString(Bundle extras, String key){
		if (containsKey(extras,key)) {
			extras.remove(key);
		}
	}
}
