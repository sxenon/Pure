package com.sxenon.pure.submitter;

import com.sxenon.pure.core.IResultHandler;

/**
 * ResultHandler for submitting
 * Created by Sui on 2016/12/12.
 */

public interface ISubmitResultHandler<R> extends IResultHandler {
    void onSuccess(R result);
    void onFail(R result);
}
