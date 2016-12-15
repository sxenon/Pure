package com.sj.pure.okhttp3.decorator;

import okhttp3.OkHttpClient;

/**
 * OkHttpClientDecorator
 * Created by Sui on 2016/12/15.
 */

public interface OkHttpClientDecorator {
    OkHttpClient update(OkHttpClient originalHttpClient);
}
