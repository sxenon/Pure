package com.sxenon.pure.core;

/**
 * Include all kind of exception
 * Created by Sui on 2016/11/20.
 */

public class ApiException {
    private String exceptionCode;
    private String exceptionMsg;

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public ApiException(String exceptionCode, String exceptionMsg) {
        this.exceptionCode = exceptionCode;
        this.exceptionMsg = exceptionMsg;
    }
}
