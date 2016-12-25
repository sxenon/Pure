package com.sxenon.pure.core.component.submitter;

import com.sxenon.pure.core.component.IViewComponentGroup;

/**
 * Submitter
 * Created by Sui on 2016/12/12.
 */

public interface ISingleDataSubmitter<T> extends IViewComponentGroup {
    T getDataForSubmit();
}
