package com.sxenon.pure.core.protocol.http;

import com.sxenon.pure.core.result.IResultDispatcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Common interface for any http library`s wrapper
 * Created by Sui on 2016/12/12.
 */

public interface IHttpClient<RH extends IResultDispatcher> {
    String MEDIA_TYPE_MARKDOWN = "text/x-markdown; charset=utf-8";
    String MEDIA_TYPE_PNG = "image/png";

    IHttpClient buildGetRequest(String url, Object tag);

    IHttpClient buildPostStringRequest(String url, String postBody, Object tag);

    IHttpClient buildPostStreamingRequest(String url, InputStream in, Object tag);

    IHttpClient buildPostFileRequest(String url, File file, Object tag);

    IHttpClient buildPostFormRequest(String url, Map<String, String> form, Object tag);

    IHttpClient buildPostMultiPartRequest(String url, Object tag);

    void cancel(Object tag);

    void execute(RH responseHandler) throws IOException;

    void enqueue(RH responseHandler);
}
