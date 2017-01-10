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

package com.sj.pure.retrofit2;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Utils for Retrofit
 * Created by Sui on 2016/12/15.
 */

public class RetrofitUtils {
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
