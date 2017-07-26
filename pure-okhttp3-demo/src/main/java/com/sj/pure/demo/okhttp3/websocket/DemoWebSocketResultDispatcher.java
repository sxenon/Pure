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

import android.util.Log;

import com.sj.pure.okhttp3.BaseWebSocketResultDispatcher;
import com.sxenon.pure.core.result.IResultHandler;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Demo for specific app
 * Created by Sui on 2016/12/13.
 */

public abstract class DemoWebSocketResultDispatcher<R> extends BaseWebSocketResultDispatcher<R> {
    private static final String TAG = "DemoDispatcher";

    public DemoWebSocketResultDispatcher(IResultHandler resultHandler) {
        super(resultHandler);
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.d(TAG,"onOpen");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d(TAG,"onMessage");
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.d(TAG,"onMessage");
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.d(TAG,"onClosing");
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        Log.d(TAG,"onClosed");
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.d(TAG,"onFailure");
    }
}
