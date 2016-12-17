package com.sxenon.pure.core;

import com.sxenon.pure.filler.IFetchListResultHandler;
import com.sxenon.pure.filler.IFetchSingleResultHandler;
import com.sxenon.pure.submitter.ISubmitResultHandler;

/**
 * Common response handler for common request
 * Please use  {@link BaseResponseHandler}
 * Created by Sui on 2016/12/13.
 */

public interface IResponseHandler<R> extends ISubmitResultHandler<R>, IFetchListResultHandler<R>, IFetchSingleResultHandler<R> {
}
