package com.sxenon.pure.submitter;

import com.sxenon.pure.core.IViewComponentGroup;

/**
 * Submitter
 * Created by Sui on 2016/12/12.
 */

public interface ISubmitter<T> extends IViewComponentGroup {
    T getDataForSubmit();
}
