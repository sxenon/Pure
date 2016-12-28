package com.sj.pure.demo.pull.bga;

import com.sxenon.pure.core.component.filler.IPullLayout;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Sui on 2016/12/27.
 */

public class BGAPullLayout implements IPullLayout {
    private BGARefreshLayout mRealLayout;

    public BGAPullLayout(BGARefreshLayout realLayout) {
        mRealLayout = realLayout;
        defaultInit(realLayout);
    }

    @Override
    public void beginRefreshing() {
        mRealLayout.beginRefreshing();
    }

    @Override
    public void beginLoadingMore() {
        mRealLayout.beginLoadingMore();
    }

    @Override
    public void endLoadingMore() {
        mRealLayout.endLoadingMore();
    }

    @Override
    public void endRefreshing() {
        mRealLayout.endRefreshing();
    }

    @Override
    public void setVisibility(int visibility) {
        mRealLayout.setVisibility(visibility);
    }

    void setDelegate(BGARefreshLayout.BGARefreshLayoutDelegate delegate) {
        mRealLayout.setDelegate(delegate);
    }

    /**
     * 项目级别通用的初始化工作
     */
    protected void defaultInit(BGARefreshLayout realLayout) {

    }

}
