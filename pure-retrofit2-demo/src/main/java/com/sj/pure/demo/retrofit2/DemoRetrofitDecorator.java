/*
 * Copyright (c) 2017 sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sj.pure.demo.retrofit2;

import com.sj.pure.retrofit2.RetrofitDecorator;
import com.sj.pure.retrofit2.RetrofitUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Demo for RetrofitDecorator
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
