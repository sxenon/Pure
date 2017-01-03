package com.sxenon.pure.core.result;

import java.util.Set;

/**
 * Created by Sui on 2017/1/3.
 */

public interface IFetchSetResultHandler<R> extends IResultHandler {
    void onSetDataFetched(Set<R> data);
}
