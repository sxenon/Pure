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

public class DemoWebSocketClient extends PureWebSocketClient<DemoWebSocketResultDispatcher> {
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
    protected void preParseOnOpen(WebSocket webSocket, Response response, DemoWebSocketResultDispatcher resultDispatcher) {
        resultDispatcher.handleWebSocket(webSocket);
        resultDispatcher.handleResponse(response);
    }

    @Override
    protected void preParseOnMessage(WebSocket webSocket, String text, DemoWebSocketResultDispatcher resultDispatcher) {
        resultDispatcher.handleWebSocket(webSocket);
        resultDispatcher.handleMessage(text);
    }

    @Override
    protected void preParseOnMessage(WebSocket webSocket, ByteString bytes, DemoWebSocketResultDispatcher resultDispatcher) {
        resultDispatcher.handleWebSocket(webSocket);
        resultDispatcher.handleMessage(bytes);
    }

    @Override
    protected void preParseOnClosing(WebSocket webSocket, int code, String reason, DemoWebSocketResultDispatcher resultDispatcher) {
        resultDispatcher.handleWebSocket(webSocket);
        resultDispatcher.handleCloseInfo(code, reason);
    }

    @Override
    protected void preParseOnClosed(WebSocket webSocket, int code, String reason, DemoWebSocketResultDispatcher resultDispatcher) {
        resultDispatcher.handleWebSocket(webSocket);
        resultDispatcher.handleCloseInfo(code, reason);
    }

    @Override
    protected void preParseOnFailure(WebSocket webSocket, Throwable t, Response response, DemoWebSocketResultDispatcher resultDispatcher) {
        resultDispatcher.handleWebSocket(webSocket);
        resultDispatcher.handleException(t);
        resultDispatcher.handleResponse(response);
    }
}
