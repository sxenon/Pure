package com.sxenon.pure.protocol.http;

import com.sxenon.pure.core.ICallBackHandler;
import com.sxenon.pure.core.IResponseHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Common interface for any http library`s wrapper
 * Created by Sui on 2016/12/12.
 */

public interface IHttpClient<CB extends ICallBackHandler, R extends IResponseHandler> {
    String MEDIA_TYPE_MARKDOWN = "text/x-markdown; charset=utf-8";
    String MEDIA_TYPE_PNG = "image/png";

    void get(String url, Object tag);

    void postString(String url, String postBody, Object tag);

    void postStreaming(String url, InputStream in, Object tag);

    void postFile(String url, File file, Object tag);

    void postForm(String url, Map<String, String> form, Object tag);

    void postMultiPart(String url, Object tag);

    void cancel(Object tag);

    void execute(R responseHandler) throws IOException;

    void enqueue(CB callBackHandler);
}
