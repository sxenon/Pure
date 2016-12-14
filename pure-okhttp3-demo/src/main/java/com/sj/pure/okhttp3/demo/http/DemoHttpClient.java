package com.sj.pure.okhttp3.demo.http;

import com.sj.pure.okhttp3.PureHttpClient;
import com.sxenon.pure.util.Preconditions;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Demo for specific app
 * Created by Sui on 2016/12/13.
 */

public class DemoHttpClient extends PureHttpClient<DemoHttpResponseHandler> {
    private static DemoHttpClient mInstance;

    public static void initClient(OkHttpClient client) {
        mInstance = new DemoHttpClient(client);
    }

    private DemoHttpClient(OkHttpClient client) {
        super(client);
    }

    @Override
    protected void preParseFailure(Call call, IOException e, DemoHttpResponseHandler responseHandler) {
        //Just a demo
        responseHandler.handleCall(call);
        responseHandler.handleException(e);
    }

    @Override
    protected void preParseResponse(Call call, Response response, DemoHttpResponseHandler responseHandler) {
        //Just a demo
        responseHandler.handleResponse(response);
        responseHandler.handleCall(call);
    }

    public static DemoHttpClient getInstance() {
        return Preconditions.checkNotNull(mInstance, "Call init first!");
    }


}
