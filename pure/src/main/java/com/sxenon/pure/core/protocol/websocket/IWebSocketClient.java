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
