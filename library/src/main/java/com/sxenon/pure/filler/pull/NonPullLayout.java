package com.sxenon.pure.filler.pull;

/**
 * Not support pull up or down
 * Created by Sui on 2016/12/11.
 */

public class NonPullLayout implements IPullLayout {

    @Override
    public void beginRefreshing() {

    }

    @Override
    public void beginLoadingMore() {

    }

    @Override
    public void setVisibility(int visibility) {

    }

    @Override
    public void setDelegate(RefreshDelegate delegate) {

    }
}
