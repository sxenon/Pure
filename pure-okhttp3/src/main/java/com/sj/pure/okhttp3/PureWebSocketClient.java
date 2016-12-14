package com.sj.pure.okhttp3;

import com.sxenon.pure.core.IResponseHandler;
import com.sxenon.pure.protocol.websocket.IWebSocketClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * PureWebSocketClient
 * Created by Sui on 2016/12/13.
 */

public abstract class PureWebSocketClient<RH extends IResponseHandler> implements IWebSocketClient<RH> {
    private OkHttpClient mClient;
    private Request.Builder mRequestBuilder;
    private WebSocket mWebSocket;
    private IWebSocketClient.ReadyState mReadyState = ReadyState.CLOSED;
    private PureWebSocketListener mWebSocketListener;

    public PureWebSocketClient(OkHttpClient client) {
        mClient = client;
    }

    public PureWebSocketClient resetRequestBuilder(Request.Builder builder) {
        mRequestBuilder = builder;
        return this;
    }

    @Override
    public void connect(String url, final RH responseHandler) {
        mWebSocketListener = new PureWebSocketListener(responseHandler);
        mWebSocket = mClient.newWebSocket(mRequestBuilder.url(url).build(), mWebSocketListener);
    }

    @Override
    public boolean closeBySelf() {
        return mWebSocket.close(IWebSocketClient.CLOSE_NORMAL, "A normal closure");
    }

    @Override
    public void cancel() {
        mWebSocket.cancel();
    }

    @Override
    public void reconnect() {
        mWebSocket = mClient.newWebSocket(mWebSocket.request(), mWebSocketListener);
    }

    @Override
    public boolean send(Object data) {
        if (data instanceof String) {
            return mWebSocket.send((String) data);
        } else if (data instanceof ByteString) {
            return mWebSocket.send((ByteString) data);
        } else {
            throw new IllegalArgumentException("OkHttpWebSocketClient can only send String or ByteString");
        }
    }

    public ReadyState getReadyState() {
        return mReadyState;
    }

    private class PureWebSocketListener extends WebSocketListener {
        private RH responseHandler;

        PureWebSocketListener(RH responseHandler) {
            this.responseHandler = responseHandler;
        }

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            mReadyState = ReadyState.OPEN;
            preParseOnOpen(webSocket, response, responseHandler);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            preParseOnMessage(webSocket, text, responseHandler);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            preParseOnMessage(webSocket, bytes, responseHandler);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            mReadyState = ReadyState.CLOSING;
            preParseOnClosing(webSocket, code, reason, responseHandler);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            mReadyState = ReadyState.CLOSED;
            preParseOnClosed(webSocket, code, reason, responseHandler);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            mReadyState = ReadyState.CLOSED;
            preParseOnFailure(webSocket, t, response, responseHandler);
        }
    }

    protected abstract void preParseOnOpen(WebSocket webSocket, Response response, RH responseHandler);

    protected abstract void preParseOnMessage(WebSocket webSocket, String text, RH responseHandler);

    protected abstract void preParseOnMessage(WebSocket webSocket, ByteString bytes, RH responseHandler);

    protected abstract void preParseOnClosing(WebSocket webSocket, int code, String reason, RH responseHandler);

    protected abstract void preParseOnClosed(WebSocket webSocket, int code, String reason, RH responseHandler);

    protected abstract void preParseOnFailure(WebSocket webSocket, Throwable t, Response response, RH responseHandler);
}