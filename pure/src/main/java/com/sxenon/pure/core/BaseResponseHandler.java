package com.sxenon.pure.core;

/**
 * BaseResponseHandler
 * Created by Sui on 2016/12/15.
 */

public class BaseResponseHandler implements IResponseHandler {
    private IResultHandler mResultHandler;

    public BaseResponseHandler(IResultHandler resultHandler) {
        mResultHandler=resultHandler;
    }

    @Override
    public IResultHandler getResultHandler() {
        return mResultHandler;
    }
}
