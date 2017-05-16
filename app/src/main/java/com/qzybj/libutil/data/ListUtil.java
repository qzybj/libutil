package com.qzybj.libutil.data;


import com.qzybj.libutil.CLog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ListUtil {

	public static boolean isEmpty(List<?> l){
		if (l!=null&&l.size()>0) {
			return false;
		}
		return true;
	}
	public static boolean isNotEmpty(List<?> l){
		return !isEmpty(l);
	}

	public static <T> T getData(List<T> l, int index){
		if (!isEmpty(l)) {
			if (index>=0&&index<l.size()) {
				return l.get(index);
			}
		}
		return null;
	}

	public static <T> ArrayList<T> set2ArrayList(Set<T> set){
		if (set!=null&&set.size()>0) {
			try {
				ArrayList<T> list = new ArrayList<T>(set);
				return list;
			} catch (Exception e) {
				CLog.e(e);
			}
		}
		return null;
	}
	public static boolean isEmptyArray(Object[] o){
		if(o == null ||o.length == 0){
			return true ;
		}
		return false;
	}
	public static boolean isNotEmptyArray(Object[] ol){
		return !isEmptyArray(ol);
	}

	public static boolean isEmptySet(HashSet hs){
		if( hs == null ||
				hs.size() == 0 ){
			return true ;
		}
		return false ;
	}
	public static  <T> ArrayList<T> buildList(T... ol){
		if (ol!=null&&ol.length>0) {
				ArrayList<T> list = new ArrayList<T>();
				for (int i = 0; i <ol.length; i++) {
					list.add(ol[i]);
				}
				return list;
		}
		return null;
	}

	/**
	 * 泛型ArrayList转数组
	 */
	public static <T> T[] toArray(Class<?> cls, ArrayList<T> items) {
		if (items == null || items.size() == 0) {
			return (T[]) Array.newInstance(cls, 0);
		}
		return items.toArray((T[]) Array.newInstance(cls, items.size()));
	}

	/**
	 * 泛型数组转ArrayList
	 */
	public static <T> ArrayList<T> toList(Class<?> cls, T[] array){
		if(isEmptyArray(array)){
			return new ArrayList<T>();
		}
		return new ArrayList<>(Arrays.asList(array));
	}
}
