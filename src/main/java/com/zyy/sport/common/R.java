package com.zyy.sport.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T data;
    private String msg;
    private Integer code;

    public R(String msg, Integer code) {
        this(null, msg, code);
    }

    public static <T> R<T> okOf(String message) {
        return new R<>(message,  200);
    }

    public static <T> R<T> okOf(T data) {
        return new R<>(data, "ok",  200);
    }

    public static <T> R<T> okOf(T data, String message) {
        return new R<>(data, message,  200);
    }

    public static <T> R<T> errorOf(T data, String errorMessage) {
        return new R<>(data, errorMessage,  400);
    }

    public static <T> R<T> errorOf(String errorMessage) {
        return new R<>(errorMessage,  400);
    }
}