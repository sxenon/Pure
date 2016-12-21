package com.sxenon.pure.component.submitter;

import com.sxenon.pure.component.IViewComponentGroup;

/**
 * Submitter
 * Created by Sui on 2016/12/12.
 */

public interface ISingleDataSubmitter<T> extends IViewComponentGroup {
    T getDataForSubmit();
}
