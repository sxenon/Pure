package com.sxenon.pure.filler;

import com.sxenon.pure.core.IResultHandler;

/**
 * Result for fetching single data
 * Created by Sui on 2016/12/8.
 */

public interface IFetchSingleResultHandler<R> extends IResultHandler {
    void onSingleDataFetched(R data);
}
