package com.sj.pure.demo.pull.twinking;

import android.content.Context;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.sxenon.pure.core.component.adapter.IPureAdapter;
import com.sxenon.pure.core.component.filler.FillerGroup;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

/**
 * Created by Sui on 2016/12/27.
 */

public class TwinkingFillerGroup<R> extends FillerGroup<R, TwinklingPullLayout> {
    public TwinkingFillerGroup(Context context, TwinklingRefreshLayout refreshLayout, IFetchSingleResultHandler<R> singleDataResult) {
        super(context, new TwinklingPullLayout(refreshLayout), singleDataResult);
    }

    public TwinkingFillerGroup(Context context, TwinklingRefreshLayout refreshLayout, IPureAdapter<R> adapter) {
        super(context, new TwinklingPullLayout(refreshLayout), adapter);
    }

    public TwinkingFillerGroup(Context context, TwinklingRefreshLayout refreshLayout, IPureAdapter<R> adapter, boolean isFreshForAdd) {
        super(context, new TwinklingPullLayout(refreshLayout), adapter, isFreshForAdd);
    }

    /**
     * Don`t call {@link TwinklingRefreshLayout#setOnRefreshListener(RefreshListenerAdapter)} directly!!!
     *
     * @param refreshListener 刷新控件监听器
     */
    public void setOnRefreshListener(final RefreshListenerAdapter refreshListener) {
        getPullLayout().setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction) {
                refreshListener.onPullingDown(refreshLayout, fraction);
            }

            @Override
            public void onPullingUp(TwinklingRefreshLayout refreshLayout, float fraction) {
                refreshListener.onPullingUp(refreshLayout, fraction);
            }

            @Override
            public void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
                refreshListener.onPullDownReleasing(refreshLayout, fraction);
            }

            @Override
            public void onPullUpReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
                refreshListener.onPullUpReleasing(refreshLayout, fraction);
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                onBeginRefreshing();
                refreshListener.onRefresh(refreshLayout);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                onBeginLoadingMore();
                refreshListener.onLoadMore(refreshLayout);
            }

            @Override
            public void onFinishRefresh() {
                refreshListener.onFinishRefresh();
            }

            @Override
            public void onFinishLoadMore() {
                refreshListener.onFinishLoadMore();
            }

            @Override
            public void onRefreshCanceled() {
                refreshListener.onRefreshCanceled();
            }

            @Override
            public void onLoadmoreCanceled() {
                refreshListener.onLoadmoreCanceled();
            }
        });
    }
}
