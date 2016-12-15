package com.sj.pure.okhttp3.demo.websocket;

import com.sj.pure.okhttp3.PureWebSocketClient;
import com.sj.pure.okhttp3.decorator.OkHttpClientDecorator;
import com.sxenon.pure.util.Preconditions;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Created by Sui on 2016/12/13.
 */

public class DemoWebSocketClient extends PureWebSocketClient<DemoWebSocketResponseHandler> {
    private static DemoWebSocketClient mBaseInstance;
    private static OkHttpClient innerHttpClient;

    public static void initClient(OkHttpClient originalHttpClient) {
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


    @Override
    protected void preParseOnOpen(WebSocket webSocket, Response response, DemoWebSocketResponseHandler responseHandler) {
        responseHandler.handleWebSocket(webSocket);
        responseHandler.handleResponse(response);
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
        responseHandler.handleResponse(response);
    }
}
