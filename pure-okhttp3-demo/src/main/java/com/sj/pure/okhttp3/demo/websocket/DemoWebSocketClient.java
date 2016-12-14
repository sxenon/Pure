package com.sj.pure.okhttp3.demo.websocket;

import com.sj.pure.okhttp3.PureWebSocketClient;
import com.sxenon.pure.util.Preconditions;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Created by Sui on 2016/12/13.
 */

public class DemoWebSocketClient extends PureWebSocketClient<DemoWebSocketResponseHandler> {
    private static DemoWebSocketClient mInstance;

    public static void init(OkHttpClient client) {
        mInstance = new DemoWebSocketClient(client);
    }

    private DemoWebSocketClient(OkHttpClient client) {
        super(client);
    }

    public static DemoWebSocketClient getInstance() {
        return Preconditions.checkNotNull(mInstance, "Call init first!");
    }


    @Override
    protected void preParseOnOpen(WebSocket webSocket, Response response, DemoWebSocketResponseHandler responseHandler) {
        responseHandler.handleWebSocket(webSocket);
        responseHandler.handleResopnse(response);
    }

    @Override
    protected void preParseOnMessage(WebSocket webSocket, String text, DemoWebSocketResponseHandler responseHandler) {
        responseHandler.handleWebSocket(webSocket);
        responseHandler.handleMessage(text);
    }

    @Override
    protected void preParseOnMessage(WebSocket webSocket, ByteString bytes, DemoWebSocketResponseHandler responseHandler) {
        responseHandler.handleWebSocket(webSocket);
        responseHandler.handleMessage(bytes);
    }

    @Override
    protected void preParseOnClosing(WebSocket webSocket, int code, String reason, DemoWebSocketResponseHandler responseHandler) {
        responseHandler.handleWebSocket(webSocket);
        responseHandler.handleCloseInfo(code, reason);
    }

    @Override
    protected void preParseOnClosed(WebSocket webSocket, int code, String reason, DemoWebSocketResponseHandler responseHandler) {
        responseHandler.handleWebSocket(webSocket);
        responseHandler.handleCloseInfo(code, reason);
    }

    @Override
    protected void preParseOnFailure(WebSocket webSocket, Throwable t, Response response, DemoWebSocketResponseHandler responseHandler) {
        responseHandler.handleWebSocket(webSocket);
        responseHandler.handleException(t);
        responseHandler.handleResopnse(response);
    }
}