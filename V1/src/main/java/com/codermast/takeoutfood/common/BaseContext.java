package com.codermast.takeoutfood.common;

/**
 * @Description: 基于ThreadLocal封装工具类，保存和获取当前登录用户id
 * @author: Yue
 * @date: 2023/8/3
 */
public class BaseContext {
    // ThreadLocal中只能存储一个值。
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * @Description: 设置当前用户id
     * @param id 用户id
     * @Author: Yue
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * @Description: 获取当前用户id
     * @Author: Yue
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
