package com.sj.pure.retrofit2.demo;

import com.sj.pure.retrofit2.RetrofitDecorator;
import com.sj.pure.retrofit2.RetrofitUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DemoRetrofitDecorator
 * Created by Sui on 2016/12/15.
 */

public class DemoRetrofitDecorator implements RetrofitDecorator {
    @Override
    public Retrofit update(Retrofit originalRetrofit) {
        return update(originalRetrofit,null);
    }

    @Override
    public Retrofit update(Retrofit originalRetrofit, OkHttpClient newClient) {
        return RetrofitUtils.newRetrofitBuilder(originalRetrofit, newClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
