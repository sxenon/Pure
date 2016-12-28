package com.sj.pure.demo.pull.bga;

import android.content.Context;

import com.sxenon.pure.core.component.adapter.IPureAdapter;
import com.sxenon.pure.core.component.filler.FillerGroup;
import com.sxenon.pure.core.component.filler.IFetchSingleResultHandler;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Adapter for https://github.com/bingoogolapple/BGARefreshLayout-Android
 * Created by Sui on 2016/12/27.
 */

public class BGAFillerGroup<R> extends FillerGroup<R,BGAPullLayout> {
    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, IFetchSingleResultHandler<R> singleDataResult) {
        super(context, new BGAPullLayout(refreshLayout), singleDataResult);
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, IPureAdapter<R> adapter) {
        super(context, new BGAPullLayout(refreshLayout), adapter);
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, IPureAdapter<R> adapter, boolean isFreshForAdd) {
        super(context, new BGAPullLayout(refreshLayout), adapter, isFreshForAdd);
    }

    /**
     * Don`t call {@link BGARefreshLayout#setDelegate(BGARefreshLayout.BGARefreshLayoutDelegate)} directly!
     * @param delegate 刷新控件监听器
     */
    public void setDelegate(final BGARefreshLayout.BGARefreshLayoutDelegate delegate){
        getPullLayout().setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                onBeginRefreshing();
                delegate.onBGARefreshLayoutBeginRefreshing(refreshLayout);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                onBeginLoadingMore();
                return delegate.onBGARefreshLayoutBeginLoadingMore(refreshLayout);
            }
        });
    }
}
