package com.sj.pure.demo.pull.bga;

import android.content.Context;

import com.sxenon.pure.core.component.adapter.IPureAdapter;
import com.sxenon.pure.core.component.filler.FillerGroup;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Adapter for https://github.com/bingoogolapple/BGARefreshLayout-Android
 * Created by Sui on 2016/12/27.
 */

public class BGAFillerGroup<R> extends FillerGroup<R,BGAPullLayout> {
    private CustomLister<R> mCustomLister;

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
                if (mCustomLister!=null){
                    mCustomLister.onBeginRefreshingCustom(refreshLayout);
                }
                delegate.onBGARefreshLayoutBeginRefreshing(refreshLayout);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                onBeginLoadingMore();
                if (mCustomLister!=null){
                    mCustomLister.onBeginLoadingMoreCustom(refreshLayout);
                }
                return delegate.onBGARefreshLayoutBeginLoadingMore(refreshLayout);
            }
        });
    }

    @Override
    protected void onMoreDataFetched(List<R> data) {
        super.onMoreDataFetched(data);
        if (mCustomLister!=null){
            mCustomLister.onMoreDataFetchedCustom(data);
        }
    }

    @Override
    protected void onNewDataFetched(List<R> data) {
        super.onNewDataFetched(data);
        if (mCustomLister!=null){
            mCustomLister.onNewDataFetchedCustom(data);
        }
    }

    @Override
    protected void onNoMoreData() {
        super.onNoMoreData();
        bgaCommonOnNoMoreData();
        if (mCustomLister!=null){
            mCustomLister.onNoMoreDataCustom();
        }
    }

    @Override
    protected void onNoNewData() {
        super.onNoNewData();
        bgaCommonOnNoNewData();
        if (mCustomLister!=null){
            mCustomLister.onNoNewDataCustom();
        }
    }

    public void setCustomLister(CustomLister<R> customLister){
        mCustomLister=customLister;
    }

    private void bgaCommonOnNoNewData(){
        //For project level
    }

    private void bgaCommonOnNoMoreData(){
        //For project level
    }

    public interface CustomLister<R>{
        void onBeginRefreshingCustom(BGARefreshLayout refreshLayout);
        void onBeginLoadingMoreCustom(BGARefreshLayout refreshLayout);
        void onMoreDataFetchedCustom(List<R> data);
        void onNewDataFetchedCustom(List<R> data);
        void onNoMoreDataCustom();
        void onNoNewDataCustom();
    }
}
