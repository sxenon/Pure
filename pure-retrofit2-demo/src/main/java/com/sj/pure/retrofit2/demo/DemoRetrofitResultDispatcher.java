package com.sj.pure.retrofit2.demo;

import android.util.Log;

import com.sxenon.pure.result.BaseResultDispatcher;
import com.sxenon.pure.result.IResultHandler;

import retrofit2.Call;
import retrofit2.Response;

/**
 * DemoRetrofitResultDispatcher
 * Created by Sui on 2016/12/15.
 */

public abstract class DemoRetrofitResultDispatcher<T> extends BaseResultDispatcher {
    private static final String TAG = "Demo";

    public DemoRetrofitResultDispatcher(IResultHandler resultHandler) {
        super(resultHandler);
    }

    public void handleCall(Call<T> call) {
        Log.i(TAG, call.toString());
    }

    public void handleResponse(Response<T> response) {
        Log.i(TAG, response.body()+"");
    }

    public void handleException(Throwable t) {
        Log.getStackTraceString(t);
    }
}
