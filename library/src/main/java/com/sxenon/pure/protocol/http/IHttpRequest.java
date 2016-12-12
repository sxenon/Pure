package com.sxenon.pure.protocol.http;

import com.sxenon.pure.core.IResultHandler;

/**
 * Common interface for any http library`s wrapper
 * Created by Sui on 2016/12/12.
 */

public interface IHttpRequest {
    void asynchronousGet(String url, IResultHandler result);
    void synchronousGet(String url,IResultHandler result);
}
