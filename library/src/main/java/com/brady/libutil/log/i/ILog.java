package com.brady.libutil.log.i;

/**
 *
 * @author Cuckoo
 * @date 2017-05-11
 * @description
 *      日志接口类
 */

public interface ILog {

    /**打印的Tag*/
    String getTag();

    /**是否开启*/
    boolean switchOn(boolean isOn);

    /**是否显示调用日志的类以及方法*/
    boolean isShowMethod();

    /**error*/
    void e(String tag, String msg);

    /**warn*/
    void w(String tag, String msg);

    /**info*/
    void i(String tag, String msg);

    /**debug*/
    void d(String tag, String msg);
}
