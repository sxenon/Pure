package com.sxenon.pure.component.filler;

import com.sxenon.pure.component.IViewComponentGroup;

/**
 * Filler for single data
 * Created by Sui on 2016/12/8.
 */

public interface ISingleDataFiller<T> extends IViewComponentGroup {
    T getValue();

    void onEmpty();
}
