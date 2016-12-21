package com.sxenon.pure.component.navigation;

import android.view.View;

import com.sxenon.pure.component.IViewComponentGroup;


/**
 * TitleView
 * Created by Sui on 2016/11/8.
 */

public interface ITitleView<T> extends IViewComponentGroup {
    void setTitle(T date);

    void setTitleDelegate(TitleDelegate delegate);

    interface TitleDelegate {
        void onClick(View view);
    }
}
