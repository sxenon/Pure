package com.sj.pure.retrofit2;

import com.sxenon.pure.result.IResultDispatcher;
import com.sxenon.pure.util.Preconditions;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
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
