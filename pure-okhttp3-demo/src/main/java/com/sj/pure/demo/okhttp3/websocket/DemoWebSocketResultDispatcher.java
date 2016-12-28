package com.sj.pure.demo.okhttp3.websocket;

import android.util.Log;

import com.sj.pure.okhttp3.BaseOkHttpResultDispatcher;
import com.sxenon.pure.core.result.IResultHandler;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Created by Sui on 2016/12/13.
 */

public abstract class DemoWebSocketResultDispatcher extends BaseOkHttpResultDispatcher {
    private static final String TAG = "Demo";

    public DemoWebSocketResultDispatcher(IResultHandler resultHandler, Type type) {
        super(resultHandler, type);
    }

    public void handleResponse(Response response){
        try {
            Log.i(TAG,response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
