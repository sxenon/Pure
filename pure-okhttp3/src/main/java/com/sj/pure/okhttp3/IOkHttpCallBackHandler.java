package com.sj.pure.okhttp3;

import com.sxenon.pure.core.ICallBackHandler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Common callBackHandler for okhttp3
 * Created by Sui on 2016/12/13.
 */

public interface IOkHttpCallBackHandler extends ICallBackHandler {
    void onFailure(Call call, IOException e);
    void onResponse(Call call, Response response) throws IOException;
}
