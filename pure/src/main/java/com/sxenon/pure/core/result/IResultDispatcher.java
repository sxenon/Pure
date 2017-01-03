package com.sxenon.pure.core.result;

/**
 * Common response handler for common request
 * For Map,use its entrySet or just treat as single.
 * Please use  {@link BaseResultDispatcher}
 * Created by Sui on 2016/12/13.
 */

public interface IResultDispatcher<R> extends ISubmitResultHandler<R>, IFetchListResultHandler<R>, IFetchSingleResultHandler<R>,IFetchSetResultHandler<R> {
}
