/*
 * Copyright (c) 2017 sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sj.pure.demo.okhttp3.websocket;

import com.sj.pure.okhttp3.BaseWebSocketResultDispatcher;
import com.sj.pure.okhttp3.PureWebSocketClient;
import com.sj.pure.okhttp3.decorator.OkHttpClientDecorator;
import com.sxenon.pure.core.util.Preconditions;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Demo for specific app
 * Created by Sui on 2016/12/13.
 */

public class DemoWebSocketClient extends PureWebSocketClient<BaseWebSocketResultDispatcher> {
    private static DemoWebSocketClient mBaseInstance;
    private static OkHttpClient innerHttpClient;

    public static void initClient(OkHttpClient originalHttpClient) {
        innerHttpClient=originalHttpClient;
        mBaseInstance = new DemoWebSocketClient(originalHttpClient);
    }

    public DemoWebSocketClient update(OkHttpClientDecorator clientDecorator) {
        return new DemoWebSocketClient(clientDecorator.update(innerHttpClient));
    }

    private DemoWebSocketClient(OkHttpClient originalHttpClient) {
        super(originalHttpClient);
    }

    public static DemoWebSocketClient getBaseInstance() {
        return Preconditions.checkNotNull(mBaseInstance, "Call init first!");
    }

}
