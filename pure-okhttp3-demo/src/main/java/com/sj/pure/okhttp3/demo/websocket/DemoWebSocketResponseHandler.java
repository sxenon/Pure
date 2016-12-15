package com.sj.pure.okhttp3.demo.websocket;

import android.util.Log;

import com.sj.pure.okhttp3.BaseOkHttpResponseHandler;
import com.sxenon.pure.core.IResponseHandler;
import com.sxenon.pure.core.IResultHandler;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Created by Sui on 2016/12/13.
 */

public abstract class DemoWebSocketResponseHandler extends BaseOkHttpResponseHandler {
    private static final String TAG = "Demo";

    public DemoWebSocketResponseHandler(IResultHandler resultHandler, Type type) {
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
