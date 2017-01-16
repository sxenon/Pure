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

import com.sj.pure.okhttp3.decorator.OkHttpClientDecorator;
import com.sj.pure.retrofit2.PureServiceGenerator;
import com.sj.pure.retrofit2.RetrofitDecorator;
import com.sxenon.pure.core.util.Preconditions;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Demo for ServiceGenerator
 * Created by Sui on 2016/12/15.
 */

public class DemoServiceGenerator extends PureServiceGenerator<DemoRetrofitResultDispatcher> {
    private static DemoServiceGenerator mBaseInstance;
    private static Retrofit innerRetrofit;

    public static void init(Retrofit retrofit) {
        innerRetrofit = retrofit;
        mBaseInstance = new DemoServiceGenerator(innerRetrofit);
    }

    public DemoServiceGenerator update(RetrofitDecorator retrofitDecorator){
        return update(retrofitDecorator,null);
    }

    public DemoServiceGenerator update(RetrofitDecorator retrofitDecorator, OkHttpClientDecorator clientDecorator) {
        return new DemoServiceGenerator(retrofitDecorator.update(innerRetrofit, clientDecorator != null ? clientDecorator.update((OkHttpClient) innerRetrofit.callFactory()) : null));
    }

    public static DemoServiceGenerator getBaseInstance() {
        return Preconditions.checkNotNull(mBaseInstance, "Please call init first");
    }

    private DemoServiceGenerator(Retrofit retrofit) {
        super(retrofit);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> void preParseResponse(Call<T> call, Response<T> response, DemoRetrofitResultDispatcher resultDispatcher) {
        resultDispatcher.handleCall(call);
        resultDispatcher.handleResponse(response);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> void preParseFailure(Call<T> call, Throwable t, DemoRetrofitResultDispatcher resultDispatcher) {
        resultDispatcher.handleCall(call);
        resultDispatcher.handleException(t);
    }
}
