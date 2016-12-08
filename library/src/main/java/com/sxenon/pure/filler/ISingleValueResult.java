package com.sxenon.pure.filler;

import com.sxenon.pure.core.IResult;

/**
 * Created by Sui on 2016/12/8.
 */

public interface ISingleValueResult<T> extends IResult {
    void onEmpty();

    void onSingleValueFetched(T value);

    T getValue();
}
