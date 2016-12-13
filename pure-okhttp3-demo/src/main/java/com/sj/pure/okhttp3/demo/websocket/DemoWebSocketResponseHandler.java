package com.sj.pure.okhttp3.demo.websocket;

import com.sxenon.pure.core.IResponseHandler;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Created by Sui on 2016/12/13.
 */

public class DemoWebSocketResponseHandler implements IResponseHandler {
    public void handleResopnse(Response response){

    }

    public void handleWebSocket(WebSocket webSocket){

    }

    public void handleCloseInfo(int code, String reason){

    }

    public void handleException(Throwable t){

    }

    public void handleMessage(String data){

    }

    public void handleMessage(ByteString data){

    }
}
