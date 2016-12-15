package com.sj.pure.okhttp3.demo.http;

import android.util.Log;

import com.sj.pure.okhttp3.BaseOkHttpResponseHandler;
import com.sxenon.pure.core.IResultHandler;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Demo for specific app
 * Created by Sui on 2016/12/13.
 */

public abstract class DemoHttpResponseHandler extends BaseOkHttpResponseHandler {
    private static final String TAG = "Demo";

    public DemoHttpResponseHandler(IResultHandler resultHandler, Type type) {
        super(resultHandler, type);
    }

    public void handleCall(Call call) {
        Log.i(TAG, call.toString());
    }

    public void handleResponse(Response response)  {

        try {
            Log.i(TAG, response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleException(IOException exception) {
        Log.getStackTraceString(exception);
    }

}
