package com.sxenon.pure.core;

/**
 * Common response handler for common request
 * Please use with {@link IResultHandler}
 * Created by Sui on 2016/12/13.
 */

public interface IResponseHandler {
    IResultHandler getInnerResultHandler();
}
