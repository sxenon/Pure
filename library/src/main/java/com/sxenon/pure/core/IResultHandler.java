package com.sxenon.pure.core;

/**
 * Common result for common request
 * Created by Sui on 2016/11/20.
 */

public interface IResultHandler {
    void onCancel();

    void onException(ApiException exception);
}
