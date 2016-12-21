package com.sxenon.pure.view.submitter;

import com.sxenon.pure.core.IViewComponentGroup;

/**
 * Submitter
 * Created by Sui on 2016/12/12.
 */

public interface ISingleDataSubmitter<T> extends IViewComponentGroup {
    T getDataForSubmit();
}
