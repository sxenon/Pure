package com.sxenon.pure.core.component.submitter;

import com.sxenon.pure.core.component.IViewComponentGroup;

import java.util.List;

/**
 * Created by Sui on 2016/11/18.
 */

public interface IListDataSubmitter<T> extends IViewComponentGroup {
    List<T> getDataForSubmit();
}
