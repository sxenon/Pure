package com.sj.pure.okhttp3;

import com.sxenon.pure.core.BaseResponseHandler;
import com.sxenon.pure.core.IResultHandler;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * BaseOkHttpResponseHandler
 * Created by Sui on 2016/12/15.
 */

public abstract class BaseOkHttpResponseHandler extends BaseResponseHandler {
    private Type mTypeForConvert;

    public BaseOkHttpResponseHandler(IResultHandler resultHandler, Type type) {
        super(resultHandler);
        mTypeForConvert=type;
    }

    protected Type getTypeForConvert(){
        return mTypeForConvert;
    }

    protected abstract <T> T convert(ResponseBody responseBody,Type type);
}
