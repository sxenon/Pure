package com.sj.pure.okhttp3.demo;

import com.sj.pure.okhttp3.PureOkHttpClient;
import com.sxenon.pure.util.Preconditions;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Demo for specific app
 * Created by Sui on 2016/12/13.
 */

public class DemoOkHttpClient extends PureOkHttpClient<DemoResponseHandler> {
    private volatile static DemoOkHttpClient mInstance;

    public static void initClient(OkHttpClient client) {
        mInstance = new DemoOkHttpClient(client);
    }

    private DemoOkHttpClient(OkHttpClient client) {
        super(client);
    }

    @Override
    protected void preParseFailure(Call call, IOException e, DemoResponseHandler responseHandler) {
        //Just a demo
        responseHandler.handleCall(call);
        responseHandler.handleException(e);
    }

    @Override
    protected void preParseResponse(Call call, Response response, DemoResponseHandler responseHandler) {
        //Just a demo
        responseHandler.handleResponse(response);
        responseHandler.handleCall(call);
    }

    public static DemoOkHttpClient getInstance() {
        return Preconditions.checkNotNull(mInstance, "Call init first!");
    }


}
