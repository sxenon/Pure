package com.sj.pure.okhttp3;

import com.sxenon.pure.core.IResponseHandler;

import okhttp3.Response;

/**
 * Common responseHandler for okhttp3
 * Created by Sui on 2016/12/13.
 */

public interface IOkHttpResponseHandler extends IResponseHandler {
    void handleResponse(Response response);
}
