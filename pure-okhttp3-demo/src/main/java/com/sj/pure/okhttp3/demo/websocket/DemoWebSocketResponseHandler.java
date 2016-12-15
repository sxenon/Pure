package com.sj.pure.okhttp3.demo.websocket;

import android.util.Log;

import com.sxenon.pure.core.IResponseHandler;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Created by Sui on 2016/12/13.
 */

public class DemoWebSocketResponseHandler implements IResponseHandler {
    private static final String TAG = "Demo";

    public void handleResponse(Response response){
        Log.i(TAG,response.body().toString());
    }

    public void handleWebSocket(WebSocket webSocket){
        Log.i(TAG,webSocket.toString());
    }

    public void handleCloseInfo(int code, String reason){
        Log.i(TAG,code+":"+reason);
    }

    public void handleException(Throwable t){
        Log.getStackTraceString(t);
    }

    public void handleMessage(String data){
        Log.i(TAG,data);
    }

    public void handleMessage(ByteString data){
        Log.i(TAG,data.toString());
    }
}
