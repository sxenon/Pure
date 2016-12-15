package com.sj.pure.retrofit2.demo;

import com.sj.pure.okhttp3.decorator.OkHttpClientDecorator;
import com.sj.pure.retrofit2.PureServiceGenerator;
import com.sj.pure.retrofit2.RetrofitDecorator;
import com.sxenon.pure.util.Preconditions;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * DemoServiceGenerator
 * Created by Sui on 2016/12/15.
 */

public class DemoServiceGenerator extends PureServiceGenerator<DemoRetrofitResponseHandler> {
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
}
