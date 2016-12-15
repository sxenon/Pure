package com.sj.pure.retrofit2;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Sui on 2016/12/15.
 */

public interface RetrofitDecorator {
    Retrofit update(Retrofit originalRetrofit);
    Retrofit update(Retrofit originalRetrofit, OkHttpClient newClient);
}
