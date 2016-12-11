package com.sxenon.pure.filler.pull;

/**
 * Layout for pull down or up
 * Created by Sui on 2016/12/11.
 */

public interface IPullLayout {
    void beginRefreshing();
    void beginLoadingMore();
    void setVisibility(int visibility);
    void setDelegate(RefreshDelegate delegate);

    interface RefreshDelegate{
        void onBeginRefreshing();
        void onBeginLoadingMore();
    }
}
