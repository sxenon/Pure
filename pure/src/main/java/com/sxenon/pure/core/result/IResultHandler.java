package com.sxenon.pure.core.result;

import com.sxenon.pure.core.ApiException;

/**
 * Common result for viewComponentGroup to handle
 * Created by Sui on 2016/11/20.
 */

public interface IResultHandler {
    void onCancel();

    void onException(ApiException exception);
}
