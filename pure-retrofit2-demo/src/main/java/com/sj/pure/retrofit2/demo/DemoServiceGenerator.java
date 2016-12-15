package com.sj.pure.retrofit2.demo;

import com.sj.pure.retrofit2.PureServiceGenerator;
import com.sxenon.pure.util.Preconditions;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DemoServiceGenerator
 * Created by Sui on 2016/12/15.
 */

public class DemoServiceGenerator extends PureServiceGenerator<DemoRetrofitResponseHandler> {
    private static DemoServiceGenerator mInstance;

    public static void init(Retrofit.Builder builder, String baseUrl, OkHttpClient httpClient) {
        mInstance = new DemoServiceGenerator(builder, baseUrl, httpClient);
    }

    public static DemoServiceGenerator getInstance() {
        return Preconditions.checkNotNull(mInstance, "Please call init first");
    }

    private DemoServiceGenerator(Retrofit.Builder builder, String baseUrl, OkHttpClient httpClient) {
        super(builder, baseUrl, httpClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> void preParseResponse(Call<T> call, Response<T> response, DemoRetrofitResponseHandler responseHandler) {
        responseHandler.handleCall(call);
        responseHandler.handleResponse(response);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> void preParseFailure(Call<T> call, Throwable t, DemoRetrofitResponseHandler responseHandler) {
        responseHandler.handleCall(call);
        responseHandler.handleException(t);
    }

    @Override
    protected Converter.Factory getConverterFactory() {
        return GsonConverterFactory.create();
    }
}
