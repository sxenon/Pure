package com.sxenon.pure.view.navigation.search;


import com.sxenon.pure.core.IViewComponentGroup;

import rx.functions.Action0;
import rx.functions.Func1;

/**
 * SearchView
 * Created by Sui on 2016/11/8.
 */

public interface ISearchView extends IViewComponentGroup {
    void setCancelAction(Action0 onCancel);

    void setSearchAction(Func1<CharSequence, Boolean> searchAction);

    void resetKeyword(CharSequence keyword);
}
