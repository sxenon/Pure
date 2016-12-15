package com.sj.pure.retrofit2;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Sui on 2016/12/15.
 */

public class Utils {
    public static Retrofit.Builder newRetrofitBuilder(Retrofit retrofit) {
        return newRetrofitBuilder(retrofit, null);
    }

    public static Retrofit.Builder newRetrofitBuilder(Retrofit retrofit, OkHttpClient newClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(retrofit.baseUrl());
        builder.callFactory(newClient != null ? newClient : retrofit.callFactory());
        builder.callbackExecutor(retrofit.callbackExecutor());
        for (Converter.Factory factory : retrofit.converterFactories()) {
            builder.addConverterFactory(factory);
        }
        for (CallAdapter.Factory factory : retrofit.callAdapterFactories()) {
            builder.addCallAdapterFactory(factory);
        }
        return builder;
    }
}
