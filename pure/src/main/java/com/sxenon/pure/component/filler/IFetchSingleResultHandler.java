package com.sxenon.pure.component.filler;

import com.sxenon.pure.result.IResultHandler;

/**
 * Result for fetching single data
 * Created by Sui on 2016/12/8.
 */

public interface IFetchSingleResultHandler<R> extends IResultHandler {
    void onSingleDataFetched(R data);
}
