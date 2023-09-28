package com.czq.reggie.common;

import lombok.Data;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用结果类，服务端响应的所以结果最终都会包装成各种类型返回给前端页面
 * @Data注解没写，会显示接口406错误
 * @param <T>
 */

@Data
public class Result<T>{

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> Result<T> success(T object) {
        Result<T> res = new Result<T>();
        res.data = object;
        res.code = 1;
        return res;
    }


    public static <T> Result<T> error(String msg) {
        Result res = new Result();
        res.msg = msg;
        res.code = 0;
        return res;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
