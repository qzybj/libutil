package com.brady.libutil.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.brady.libutil.CLog;
import com.brady.libutil.data.StringUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Gson 工具类
 */
public class GsonUtil {
	private static Gson gson;
	static {
		 gson = new GsonBuilder()
		 .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式  
		 //.excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性  
		 //.serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")//时间转化为特定格式    
		 // .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.  
		 //.setPrettyPrinting() //对json结果格式化. 该方法不能够放开，因为BI统计不允许JSON 数据格式化
		 .create();	
	}

	public static String toJson(Object object){
		return gson.toJson(object);
	}

	public static <T> T toObject(String json, Class<T> type){
		if(StringUtil.isNotEmpty(json)&&type!=null){
			try {
				return gson.fromJson(json, TypeToken.get(type).getType());
			} catch (Exception e) {
				CLog.e(e);
			}
		}
		return null;
	}

	/**
	 * Simple:GsonUtil.json2List(jsonStr,new TypeToken<ArrayList<String>>(){}.getType());
	 * @param json
	 * @param type
	 * @param <T>
     * @return
     */
	public static <T> ArrayList<T> json2List(String json, Type type){
		if(StringUtil.isNotEmpty(json)&&type!=null){
			try {
				return gson.fromJson(json, type);
			} catch (Exception e) {
				CLog.e(e);
			}
		}
		return null;
	}

	public <T> ArrayList<T> json2List(String json, Class<T> cls) {
		if(StringUtil.isNotEmpty(json)&&cls!=null){
			ArrayList<T> mList = new ArrayList<T>();
			JsonArray array = new JsonParser().parse(json).getAsJsonArray();
			for(JsonElement elem : array){
				mList.add(gson.fromJson(elem, cls));
			}
			return mList;
		}
		return null;
	}

	public static ParameterizedType getType(final Class raw, final Type... args) {
		return new ParameterizedType() {
			public Type getRawType() {
				return raw;
			}

			public Type[] getActualTypeArguments() {
				return args;
			}

			public Type getOwnerType() {
				return null;
			}
		};
	}
}
