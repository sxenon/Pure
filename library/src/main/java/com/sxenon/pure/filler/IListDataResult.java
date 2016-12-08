package com.sxenon.pure.filler;

import com.sxenon.pure.core.IResult;

import java.util.List;

/**
 * Result for list data
 * Created by Sui on 2016/12/8.
 */

public interface IListDataResult<T> extends IResult {
    void onListDataFetched(List<T> data);
}
