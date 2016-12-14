package com.sj.pure.retrofit2;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.sxenon.pure.core.IResponseHandler;
import com.sxenon.pure.util.Preconditions;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Sui on 2016/12/14.
 */

public abstract class PureServiceGenerator<RH extends IResponseHandler> {
    private Retrofit mRetrofit;

    public PureServiceGenerator(Retrofit.Builder builder, String baseUrl, OkHttpClient httpClient) {
        mRetrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(getConverterFactory())
                .client(httpClient.newBuilder()
                        .addNetworkInterceptor(new StethoInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(getLoggingLevel()))
                        .build())
                .build();
    }

    /**
     * {@link Callback#onResponse(Call, Response)}
     * {@link Call#execute()} normal
     */
    protected abstract <T> void preParseResponse(Call<T> call, Response<T> response, RH responseHandler);

    /**
     * {@link Callback#onFailure(Call, Throwable)}
     * {@link Call#execute()} IOException
     */
    protected abstract <T> void preParseFailure(Call<T> call, Throwable t, RH responseHandler);

    protected abstract Converter.Factory getConverterFactory();

    protected HttpLoggingInterceptor.Level getLoggingLevel() {
        return HttpLoggingInterceptor.Level.BASIC;
    }

    public <S> S createService(Class<S> serviceClass) {
        return Preconditions.checkNotNull(mRetrofit, "Please call init first!").create(serviceClass);
    }
}
