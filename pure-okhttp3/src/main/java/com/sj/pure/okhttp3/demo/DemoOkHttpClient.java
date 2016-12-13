package com.sj.pure.okhttp3.demo;

import com.sj.pure.okhttp3.PureOkHttpClient;
import com.sxenon.pure.util.Preconditions;

import okhttp3.OkHttpClient;

/**
 * Demo for specific app
 * Created by Sui on 2016/12/13.
 */

public class DemoOkHttpClient extends PureOkHttpClient {
    private volatile static DemoOkHttpClient mInstance;

    public static void initClient(OkHttpClient client) {
        mInstance = new DemoOkHttpClient(client);
    }

    private DemoOkHttpClient(OkHttpClient client) {
        super(client);
    }

    public static DemoOkHttpClient getInstance() {
        return Preconditions.checkNotNull(mInstance, "Call init first!");
    }
}
