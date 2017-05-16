package com.qzybj.libutil.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MapUtil {

	public static final int NONE = -1;

	public static boolean isEmpty(Map<?,?> map){
		if (map!=null&&map.size()>0) {
			return false;
		}
		return true;
	}
	public static boolean isNotEmpty(Map<?,?> map){
		return !isEmpty(map);
	}

	/**
	 * 包含为空判断Map值获取
	 * @param map
	 * @param key
	 * @param <T>
	 * @return
	 */
	public static <T> Object getData(Map<?,?> map, T key){
		if (isNotEmpty(map)) {
			if (map.containsKey(key)) {
				return map.get(key);
			}
		}
		return null;
	}

	/**
	 * 根据value来获取key
	 * @param map
	 * @param value
	 * @return
	 */
	public static Object getKeyByValue(HashMap<?,?> map, Object value){
		if(isNotEmpty(map)&&value!=null){
			Iterator it= map.keySet().iterator();
			while(it.hasNext()){
				Object key=it.next();
				if(map.get(key).equals(value)){
					return key;
				}
			}
		}
		return NONE;
	}

	/**
	 * 获取map中的第一项
	 * @param map
	 * @return
	 */
	public static Map.Entry getFirst(HashMap<?,?> map){
		if(isNotEmpty(map)){
			return map.entrySet().iterator().next();
		}
		return null;
	}

}
