package com.sj.pure.okhttp3;

import com.sxenon.pure.result.BaseResultDispatcher;
import com.sxenon.pure.result.IResultHandler;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * BaseOkHttpResultDispatcher
 * Created by Sui on 2016/12/15.
 */

public abstract class BaseOkHttpResultDispatcher extends BaseResultDispatcher {
    private final Type mTypeForConvert;

    public BaseOkHttpResultDispatcher(IResultHandler resultHandler, Type type) {
        super(resultHandler);
        mTypeForConvert=type;
    }

    protected Type getTypeForConvert(){
        return mTypeForConvert;
    }

    protected abstract <T> T convert(ResponseBody responseBody,Type type);
}
