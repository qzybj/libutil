package com.qzybj.libraryutil.reflect;


import com.qzybj.libraryutil.CLog;
import com.qzybj.libraryutil.data.MapUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;


/** 通过Java反射机制，动态给对象属性赋值，并获取属性值*/
public class ClassReflectUtil {

    /**
     * 取Bean的属性和值对应关系的MAP
     * @param bean
     * @return Map
     */
    public static Map<String, String> getFieldValueMap(Object bean) {
        Class<?> cls = bean.getClass();
        Map<String, String> valueMap = new HashMap<String, String>();
        // 取出bean里的所有方法
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            try {
                String fieldType = field.getType().getSimpleName();
                String fieldGetName = parGetName(field.getName());
                if (!checkGetMet(methods, fieldGetName)) {
                    continue;
                }
                Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});
                Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});
                String result = null;
                if ("Date".equals(fieldType)) {
                    result = fmtDate((Date) fieldVal);
                } else {
                    if (null != fieldVal) {
                        result = String.valueOf(fieldVal);
                    }
                }
                valueMap.put(field.getName(), result);
            } catch (Exception e) {
                continue;
            }
        }
        return valueMap;

    }

    /**
     * set属性的值到Bean
     * @param bean
     * @param valMap
     */
    public static void setFieldValue(Object bean, Map<String, String> valMap) {
        Class<?> cls = bean.getClass();
        //取出bean里的所有方法
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            try {
                String fieldSetName = parSetName(field.getName());
                if (!checkSetMet(methods, fieldSetName)) {
                    continue;
                }
                Method fieldSetMet = cls.getMethod(fieldSetName, field.getType());
                String value = valMap.get(field.getName());
                if (null != value && !"".equals(value)) {
                    String fieldType = field.getType().getSimpleName();
                    if ("String".equals(fieldType)) {
                        fieldSetMet.invoke(bean, value);
                    } else if ("Date".equals(fieldType)) {
                        Date temp = parseDate(value);
                        fieldSetMet.invoke(bean, temp);
                    } else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
                        Integer intval = Integer.parseInt(value);
                        fieldSetMet.invoke(bean, intval);
                    } else if ("Long".equalsIgnoreCase(fieldType)) {
                        Long temp = Long.parseLong(value);
                        fieldSetMet.invoke(bean, temp);
                    } else if ("Double".equalsIgnoreCase(fieldType)) {
                        Double temp = Double.parseDouble(value);
                        fieldSetMet.invoke(bean, temp);
                    } else if ("Boolean".equalsIgnoreCase(fieldType)) {
                        Boolean temp = Boolean.parseBoolean(value);
                        fieldSetMet.invoke(bean, temp);
                    } else {
                        System.out.println("not supper type" + fieldType);
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
    }

    /**
     * 格式化string为Date
     * @param datestr
     * @return date
     */
    private static Date parseDate(String datestr) {
        if (null == datestr || "".equals(datestr)) {
            return null;
        }
        try {
            String fmtstr = null;
            if (datestr.indexOf(':') > 0) {
                fmtstr = "yyyy-MM-dd HH:mm:ss";
            } else {
                fmtstr = "yyyy-MM-dd";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr, Locale.UK);
            return sdf.parse(datestr);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 日期转化为String
     * @param date
     * @return date string
     */
    private static String fmtDate(Date date) {
        if (null == date) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断是否存在某属性的 set方法
     * @param methods
     * @param fieldSetMet
     * @return boolean
     */
    private static boolean checkSetMet(Method[] methods, String fieldSetMet) {
        for (Method met : methods) {
            if (fieldSetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 判断是否存在某属性的 get方法
     * @param methods
     * @param fieldGetMet
     * @return boolean
     */
    private static boolean checkGetMet(Method[] methods, String fieldGetMet) {
        for (Method met : methods) {
            if (fieldGetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拼接某属性的 get方法
     * @param fieldName
     * @return String
     */
    private static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "get" + fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
    }
    /**
     * 拼接在某属性的 set方法
     * @param fieldName
     * @return String
     */
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "set" + fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
    }

    /**
     * 根据传入的参数，进行类的实例化,并赋值
     * @param cls
     * @param map
     * @return
     */
    public static <T,S> S getClassInstance4Value(Class<T> cls, WeakHashMap<Class<?>, Object> map) {
        try{
            Object instance = getClassInstance(cls);
            if(MapUtil.isNotEmpty(map)){
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object object = map.get(field.getType());
                    if(object!=null){
                        field.set(instance, object);
                    }else {
                        CLog.d("not supper type" + field.getType().getSimpleName());
                    }
                }
            }
            return (S)instance;
        }catch(Exception e){
            CLog.e(e);
        }
        return null;
    }

    /**
     * 根据传入的参数，进行类的实例化,并赋值
     * @param cls
     * @return
     */
    public static <T,S> S getClassInstance(Class<T> cls) {
        try{
            Constructor<?>[] constructors = cls.getDeclaredConstructors();
            Constructor<?> constructor = null;
            for (int i = 0; i < constructors.length; i++) {
                constructor = constructors[i];
                if (constructor.getGenericParameterTypes().length == 0)
                    break;
            }
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();//实例化对象
            return (S)instance;
        }catch(Exception e){
            CLog.e(e);
        }
        return null;
    }

}