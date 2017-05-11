package com.qzybj.libraryutil.test;

import com.qzybj.libraryutil.data.ListUtil;
import com.qzybj.libraryutil.data.RandomUtil;
import com.qzybj.libraryutil.reflect.ClassReflectUtil;

import java.util.Date;
import java.util.WeakHashMap;


public abstract class TestDataBuilder {
    protected static Integer[] valueIntArray = new Integer[]{20,36,48,24,199,846};
    protected static String[] valueStringArray = new String[]{ "Abertam", "Abondance", "Ackawi","Acorn", "Adelost", "Afuega'l Pitu", "Airag", "Airedale"};
    protected static Double[] valueDoubleArray = new Double[]{10.2,11.98,188.88,978.13};
    protected static Date[] valueDateArray = new Date[]{new Date()};
    protected static String[] imageUrls = {
            "http://yrs.yintai.com/rs/img/AppCMS/images/1186f052-21cb-4f0c-bd7d-4e379efedf37.png",
            "http://yrs.yintai.com/rs/img/AppCMS/images/4b11918e-0927-4cec-a002-5b59d619109b.png",
            "http://yrs.yintai.com/rs/img/AppCMS/images/93b5703a-2a60-429d-b130-c8865523f053.png",
            "http://yrs.yintai.com/rs/img/AppCMS/images/12333f18-acdf-489d-a831-3421f6721e96.png"};

    /**
     * 根据传入的参数，进行类的实例化,并赋值(只传入了几种通用类型)
     * @param cls
     * @return
     */
    public static <T,S> S getClassInstance(Class<T> cls){
        WeakHashMap<Class<?>, Object> classWeakHashMap = new WeakHashMap<>();
        if(checkRandomValue()){
            classWeakHashMap.put(String.class, RandomUtil.getRandomObj(valueStringArray));
            classWeakHashMap.put(Integer.class, RandomUtil.getRandomObj(valueIntArray));
            classWeakHashMap.put(Double.class, RandomUtil.getRandomObj(valueDoubleArray));
            classWeakHashMap.put(Date.class, RandomUtil.getRandomObj(valueDateArray));
            classWeakHashMap.put(Boolean.class, RandomUtil.getRandomBoolean());
        }
        return ClassReflectUtil.getClassInstance4Value(cls,classWeakHashMap);
    }


    private static boolean checkRandomValue(){
        if(ListUtil.isEmptyArray(valueIntArray)|| ListUtil.isEmptyArray(valueStringArray)||
                ListUtil.isEmptyArray(valueDoubleArray)|| ListUtil.isEmptyArray(valueDateArray)){
            return false;
        }
        return true;
    }

    public String getImageUrl() {
        if(ListUtil.isEmptyArray(imageUrls)){
            return null;
        }
        return imageUrls[RandomUtil.getRandom(imageUrls.length-1)];
    }

    /**
     * 必须在方法中初始化所有用来产生随机数的数组
     */
    protected abstract void init();

}