package com.sxenon.pure.core;

/**
 * 通用请求结果
 * Created by Sui on 2016/11/20.
 */

public interface IResult {
    void onCancel();

    void onException(ApiException exception);
}
