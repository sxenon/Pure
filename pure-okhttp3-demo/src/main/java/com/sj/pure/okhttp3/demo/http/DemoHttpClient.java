package com.sj.pure.okhttp3.demo.http;

import com.sj.pure.okhttp3.PureHttpClient;
import com.sj.pure.okhttp3.decorator.OkHttpClientDecorator;
import com.sxenon.pure.util.Preconditions;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Demo for specific app
 * Created by Sui on 2016/12/13.
 */

public class DemoHttpClient extends PureHttpClient<DemoHttpResultDispatcher> {
    private static DemoHttpClient mBaseInstance;
    private static OkHttpClient innerHttpClient;

    public static void initClient(OkHttpClient client) {
        innerHttpClient=client;
        mBaseInstance = new DemoHttpClient(client);
    }

    public DemoHttpClient update(OkHttpClientDecorator clientDecorator){
        return new DemoHttpClient(clientDecorator.update(innerHttpClient));
    }

    private DemoHttpClient(OkHttpClient client) {
        super(client);
    }

    @Override
    protected void preParseFailure(Call call, IOException e, DemoHttpResultDispatcher resultDispatcher) {
        //Just a demo
        resultDispatcher.handleCall(call);
        resultDispatcher.handleException(e);
    }

    @Override
    protected void preParseResponse(Call call, Response response, DemoHttpResultDispatcher resultDispatcher) {
        //Just a demo
        resultDispatcher.handleResponse(response);
        resultDispatcher.handleCall(call);
    }

    public static DemoHttpClient getBaseInstance() {
        return Preconditions.checkNotNull(mBaseInstance, "Call init first!");
    }


}
