package com.sxenon.pure.component.navigation;


import com.sxenon.pure.component.IViewComponentGroup;

/**
 * Tab
 * Created by Sui on 2016/11/8.
 */

public interface ITabLayout extends IViewComponentGroup {
    void setTabDelegate(TabDelegate tabDelegate);

    int getCurrentPage();

    int getPageCount();

    interface TabDelegate {
        void onTabSelected(int position);

        void onTabReSelected(int position);
    }
}
