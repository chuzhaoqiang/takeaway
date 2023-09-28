package com.czq.reggie.common;

/**
 * 基于ThreadLocal 封装工具类，用户保存和获取当前登录的用户id
 * ThreadLocal以线程为 作用域，保存每个线程中的数据副本
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();


    /**
     * 设置当前用户id值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取当前用户id值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}

