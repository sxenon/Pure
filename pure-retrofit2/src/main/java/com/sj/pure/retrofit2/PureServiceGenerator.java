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

import com.sxenon.pure.core.result.IResultDispatcher;
import com.sxenon.pure.core.util.Preconditions;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * ServiceGenerator for Retrofit
 * Created by Sui on 2016/12/14.
 */

public abstract class PureServiceGenerator<R extends IResultDispatcher> {
    private Retrofit mRetrofit;

    public PureServiceGenerator(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    /**
     * {@link Callback#onResponse(Call, Response)}
     * {@link Call#execute()} normal
     */
    protected abstract <T> void preParseResponse(Call<T> call, Response<T> response, R resultDispatcher);

    /**
     * {@link Callback#onFailure(Call, Throwable)}
     * {@link Call#execute()} IOException
     */
    protected abstract <T> void preParseFailure(Call<T> call, Throwable t, R resultDispatcher);

    protected HttpLoggingInterceptor.Level getLoggingLevel() {
        return HttpLoggingInterceptor.Level.BASIC;
    }

    public <S> S createService(Class<S> serviceClass) {
        return Preconditions.checkNotNull(mRetrofit, "Please call init first!").create(serviceClass);
    }
}
