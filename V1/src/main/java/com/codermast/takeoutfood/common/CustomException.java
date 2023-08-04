package com.codermast.takeoutfood.common;

/**
 * @Description: 业务异常
 * @author: Yue
 * @date: 2023/8/3
 */
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
