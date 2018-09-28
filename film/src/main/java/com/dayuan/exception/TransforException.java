package com.dayuan.exception;

/**
 * 自定义异常  转账异常
 */
public class TransforException extends Exception {

    public TransforException() {
    }

    public TransforException(String message) {
        super(message);
    }
}
