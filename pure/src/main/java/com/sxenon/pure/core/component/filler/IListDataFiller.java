package com.sxenon.pure.core.component.filler;

import com.sxenon.pure.core.component.IViewComponentGroup;

import java.util.List;

/**
 * Filler for list data
 * Created by Sui on 2016/12/8.
 */

public interface IListDataFiller<T> extends IViewComponentGroup {
    List<T> getValues();

    void onEmpty();
}
