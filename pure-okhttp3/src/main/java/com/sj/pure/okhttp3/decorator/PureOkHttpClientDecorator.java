package com.sj.pure.okhttp3.decorator;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * PureOkHttpClientDecorator
 * Created by Sui on 2016/12/15.
 */

public class PureOkHttpClientDecorator implements OkHttpClientDecorator {

    @Override
    public OkHttpClient update(OkHttpClient originalHttpClient) {
        return originalHttpClient.newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
    }
}
