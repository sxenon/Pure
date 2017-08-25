/*
 * Copyright (c) 2017  sxenon
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

package com.sj.pure.okhttp3;

import com.sxenon.pure.core.result.BaseResultDispatcher;
import com.sxenon.pure.core.result.handler.IResultHandler;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * ResultDispatcher for webSocket
 * Created by Sui on 2017/7/26.
 */

public abstract class BaseWebSocketResultDispatcher<R> extends BaseResultDispatcher<R> {

    public BaseWebSocketResultDispatcher(IResultHandler resultHandler) {
        super(resultHandler);
    }

    public abstract void onOpen(WebSocket webSocket, Response response);

    public abstract void onMessage(WebSocket webSocket, String text);

    public abstract void onMessage(WebSocket webSocket, ByteString bytes);

    public abstract void onClosing(WebSocket webSocket, int code, String reason);

    public abstract void onClosed(WebSocket webSocket, int code, String reason);

    public abstract void onFailure(WebSocket webSocket, Throwable t, Response response);
}
