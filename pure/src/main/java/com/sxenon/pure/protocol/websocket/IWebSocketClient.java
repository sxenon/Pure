package com.sxenon.pure.protocol.websocket;

import com.sxenon.pure.core.IResponseHandler;

/**
 * Common interface for any webSocket library`s wrapper
 * Created by Sui on 2016/12/13.
 */

public interface IWebSocketClient<RH extends IResponseHandler> {
    void connect(String url, RH IResponseHandler);

    boolean close(int code, String reason);

    void cancel();

    boolean send(Object data);
}
