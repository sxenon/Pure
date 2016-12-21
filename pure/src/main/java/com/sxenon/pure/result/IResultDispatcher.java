package com.sxenon.pure.result;

import com.sxenon.pure.component.filler.IFetchListResultHandler;
import com.sxenon.pure.component.filler.IFetchSingleResultHandler;
import com.sxenon.pure.component.submitter.ISubmitResultHandler;

/**
 * Common response handler for common request
 * Please use  {@link BaseResultDispatcher}
 * Created by Sui on 2016/12/13.
 */

public interface IResultDispatcher<R> extends ISubmitResultHandler<R>, IFetchListResultHandler<R>, IFetchSingleResultHandler<R> {
}
