package com.rabbit.studyweb.result;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果，服务端响应的数据最终都会封装成此对象
 * @param <T>
 */
@Data
public class R<T>  implements Serializable {

    private Integer flag; //编码：200成功，400和其它数字为失败

    private String msg; //错误信息

    private T data; //数据
    private T data2; //数据2

    public R(){}

    public static<T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.flag = 200;
        return r;
    }

    public static<T> R<T> success(T object,String msg) {
        R<T> r = new R<T>();
        r.data = object;
        r.flag = 200;
        r.msg=msg;
        return r;
    }

    public static <T> R<T> success(T arg1,T arg2) {
        R<T> r = new R<T>();
        r.data = arg1;
        r.data2 = arg2;
        r.flag = 200;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = new R<T>();
        r.msg = msg;
        r.flag = 400;
        return r;
    }

}
