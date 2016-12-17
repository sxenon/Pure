package com.sj.pure.okhttp3;

import com.sxenon.pure.protocol.http.IHttpClient;

import okhttp3.MediaType;

/**
 * Created by Sui on 2016/12/17.
 */

public class OkHttpConstants {
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse(IHttpClient.MEDIA_TYPE_MARKDOWN);
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse(IHttpClient.MEDIA_TYPE_PNG);
}
