package com.sxenon.pure.core.result;

import java.util.List;

/**
 * ResultHandler for fetching list data
 * Created by Sui on 2016/12/8.
 */

public interface IFetchListResultHandler<R> extends IResultHandler {
    void onListDataFetched(List<R> data);
}
