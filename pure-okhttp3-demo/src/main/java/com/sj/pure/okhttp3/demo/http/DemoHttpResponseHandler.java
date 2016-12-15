package com.sj.pure.okhttp3.demo.http;

import android.util.Log;

import com.sxenon.pure.core.IResponseHandler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Demo for specific app
 * Created by Sui on 2016/12/13.
 */

public abstract class DemoHttpResponseHandler implements IResponseHandler {
    private static final String TAG = "Demo";

    public void handleCall(Call call) {
        Log.i(TAG, call.toString());
    }

    public void handleResponse(Response response) {
        Log.i(TAG, response.body().toString());
    }

    public void handleException(IOException exception) {
        Log.getStackTraceString(exception);
    }

}
