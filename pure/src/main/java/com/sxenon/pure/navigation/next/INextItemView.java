package com.sxenon.pure.navigation.next;


import com.sxenon.pure.core.IViewComponentGroup;

/**
 * 仿IOS设置项
 * Created by Sui on 2016/11/9.
 */

public interface INextItemView extends IViewComponentGroup {
    void setNextDelegate(NextDelegate nextDelegate);

    interface NextDelegate {
        void onNext();
    }
}
