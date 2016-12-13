package com.sxenon.pure.protocol.http;

import com.sxenon.pure.core.ICallBackHandler;
import com.sxenon.pure.core.IResponseHandler;

/**
 * Common interface for any http library`s wrapper
 * Created by Sui on 2016/12/12.
 */

public interface IHttpClient {
    void asyncGet(String url, ICallBackHandler callBackHandler);
    void syncGet(String url, IResponseHandler responseHandler);
}
