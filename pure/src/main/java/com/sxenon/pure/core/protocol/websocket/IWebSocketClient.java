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

package com.sxenon.pure.core.protocol.websocket;

import com.sxenon.pure.core.result.IResultDispatcher;

/**
 * Common interface for any webSocket library`s wrapper
 * Created by Sui on 2016/12/13.
 */

public interface IWebSocketClient<RH extends IResultDispatcher> {
    enum ReadyState {
        CONNECTING,
        OPEN,
        CLOSING,
        CLOSED
    }

    /**
     * Status code as defined by <a
     * href="http://tools.ietf.org/html/rfc6455#section-7.4">Section 7.4 of RFC 6455</a>
     */
    int CLOSE_NORMAL = 1000;

    void connect(String url, RH IResponseHandler);

    void reconnect();

    boolean closeBySelf();

    void cancel();

    boolean send(Object data);


}
