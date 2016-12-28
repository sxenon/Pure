package com.sj.pure.demo.pull.twinking;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.sxenon.pure.core.component.filler.IPullLayout;

/**
 * Adapter for https://github.com/lcodecorex/TwinklingRefreshLayout
 * Created by Sui on 2016/12/27.
 */

public class TwinklingPullLayout implements IPullLayout {
    private TwinklingRefreshLayout mRealLayout;

    public TwinklingPullLayout(TwinklingRefreshLayout realLayout) {
        mRealLayout = realLayout;
        defaultInit(realLayout);
    }

    @Override
    public void beginRefreshing() {
        mRealLayout.startRefresh();
    }

    @Override
    public void beginLoadingMore() {
        mRealLayout.startLoadMore();
    }

    @Override
    public void endLoadingMore() {
        mRealLayout.finishLoadmore();
    }

    @Override
    public void endRefreshing() {
        mRealLayout.finishLoadmore();
    }

    @Override
    public void setVisibility(int visibility) {
        mRealLayout.setVisibility(visibility);
    }

    void setOnRefreshListener(final RefreshListenerAdapter refreshListener) {
        mRealLayout.setOnRefreshListener(refreshListener);
    }

    /**
     * 项目级别通用的初始化工作
     */
    protected void defaultInit(TwinklingRefreshLayout realLayout){

    }
}
