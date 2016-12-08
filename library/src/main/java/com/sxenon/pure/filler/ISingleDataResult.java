package com.sxenon.pure.filler;

import com.sxenon.pure.core.IResult;

/**
 * Result for single data
 * Created by Sui on 2016/12/8.
 */

public interface ISingleDataResult<T> extends IResult {
    void onSingleDataFetched(T value);
}
