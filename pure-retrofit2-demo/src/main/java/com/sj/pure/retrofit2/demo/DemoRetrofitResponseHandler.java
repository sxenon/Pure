package com.sj.pure.retrofit2.demo;

import android.util.Log;

import com.sxenon.pure.core.IResponseHandler;

import retrofit2.Call;
import retrofit2.Response;

/**
 * DemoRetrofitResponseHandler
 * Created by Sui on 2016/12/15.
 */

public abstract class DemoRetrofitResponseHandler<T> implements IResponseHandler {
    private static final String TAG = "Demo";

    public void handleCall(Call<T> call) {
        Log.i(TAG, call.toString());
    }

    public void handleResponse(Response<T> response) {
        Log.i(TAG, response.body().toString());
    }

    public void handleException(Throwable t) {
        Log.getStackTraceString(t);
    }
}
