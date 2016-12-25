package com.sxenon.pure.core.result;

import com.sxenon.pure.core.component.filler.IFetchListResultHandler;
import com.sxenon.pure.core.component.filler.IFetchSingleResultHandler;
import com.sxenon.pure.core.component.submitter.ISubmitResultHandler;

/**
 * Common response handler for common request
 * Please use  {@link BaseResultDispatcher}
 * Created by Sui on 2016/12/13.
 */

public interface IResultDispatcher<R> extends ISubmitResultHandler<R>, IFetchListResultHandler<R>, IFetchSingleResultHandler<R> {
}
