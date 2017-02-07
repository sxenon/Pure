/*
 * Copyright (c) 2017 sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sj.pure.demo.pull.bga;

import android.content.Context;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.component.filler.FillerGroup;
import com.sxenon.pure.core.component.filler.ListDataFillStrategy;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Wrap with BGAPullLayout
 * Created by Sui on 2016/12/27.
 */

public class BGAFillerGroup<R> extends FillerGroup<R, BGAPullLayout> {
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

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, IPureAdapter<R> adapter, ListDataFillStrategy<R> listDataFillStrategy){
        super(context, new BGAPullLayout(refreshLayout), adapter, listDataFillStrategy);
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, IPureAdapter<R> adapter, boolean isFreshForAdd, ListDataFillStrategy<R> listDataFillStrategy){
        super(context, new BGAPullLayout(refreshLayout), adapter, isFreshForAdd,listDataFillStrategy);
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, BGAAdapterViewAdapter<R> adapter) {
        super(context, new BGAPullLayout(refreshLayout), new BGAAdapterViewPureAdapter<>(adapter));
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, BGAAdapterViewAdapter<R> adapter, boolean isFreshForAdd) {
        super(context, new BGAPullLayout(refreshLayout), new BGAAdapterViewPureAdapter<>(adapter), isFreshForAdd);
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, BGAAdapterViewAdapter<R> adapter, ListDataFillStrategy<R> listDataFillStrategy) {
        super(context, new BGAPullLayout(refreshLayout), new BGAAdapterViewPureAdapter<>(adapter), listDataFillStrategy);
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, BGAAdapterViewAdapter<R> adapter, boolean isFreshForAdd, ListDataFillStrategy<R> listDataFillStrategy) {
        super(context, new BGAPullLayout(refreshLayout), new BGAAdapterViewPureAdapter<>(adapter), isFreshForAdd, listDataFillStrategy);
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, BGARecyclerViewAdapter<R> adapter) {
        super(context, new BGAPullLayout(refreshLayout), new BGARecyclerViewPureAdapter<>(adapter));
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, BGARecyclerViewAdapter<R> adapter, boolean isFreshForAdd) {
        super(context, new BGAPullLayout(refreshLayout), new BGARecyclerViewPureAdapter<>(adapter), isFreshForAdd);
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, BGARecyclerViewAdapter<R> adapter, ListDataFillStrategy<R> listDataFillStrategy) {
        super(context, new BGAPullLayout(refreshLayout), new BGARecyclerViewPureAdapter<>(adapter), listDataFillStrategy);
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, BGARecyclerViewAdapter<R> adapter, boolean isFreshForAdd, ListDataFillStrategy<R> listDataFillStrategy) {
        super(context, new BGAPullLayout(refreshLayout), new BGARecyclerViewPureAdapter<>(adapter), isFreshForAdd, listDataFillStrategy);
    }

    /**
     * Don`t call {@link BGARefreshLayout#setDelegate(BGARefreshLayout.BGARefreshLayoutDelegate)} directly!
     */
    public void setDelegate(final BGARefreshLayout.BGARefreshLayoutDelegate delegate) {
        getPullLayout().setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                onBeginPullingDown();
                projectLevelOnBGARefreshLayoutBeginRefreshing(refreshLayout);
                delegate.onBGARefreshLayoutBeginRefreshing(refreshLayout);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                onBeginPullingUp();
                projectLevelOnBGARefreshLayoutBeginLoadingMore(refreshLayout);
                return delegate.onBGARefreshLayoutBeginLoadingMore(refreshLayout);
            }
        });
    }

    @Override
    protected void onMoreDataFetched(List<R> data) {
        super.onMoreDataFetched(data);
        projectLevelOnMoreDataFetched(data);
        if (mCustomLister != null) {
            mCustomLister.onMoreDataFetchedCustom(data);
        }
    }

    @Override
    protected void onNewDataFetched(List<R> data) {
        super.onNewDataFetched(data);
        projectLevelOnNewDataFetched(data);
        if (mCustomLister != null) {
            mCustomLister.onNewDataFetchedCustom(data);
        }
    }

    @Override
    protected void onNoMoreData() {
        super.onNoMoreData();
        projectLevelOnNoMoreData();
        if (mCustomLister != null) {
            mCustomLister.onNoMoreDataCustom();
        }
    }

    @Override
    protected void onNoNewData() {
        super.onNoNewData();
        projectOnNoNewData();
        if (mCustomLister != null) {
            mCustomLister.onNoNewDataCustom();
        }
    }

    public void setCustomLister(CustomLister<R> customLister) {
        mCustomLister = customLister;
    }

    private void projectOnNoNewData() {

    }

    private void projectLevelOnNoMoreData() {

    }

    private void projectLevelOnNewDataFetched(List<R> data) {

    }

    private void projectLevelOnMoreDataFetched(List<R> data) {

    }

    private void projectLevelOnBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    private void projectLevelOnBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

    }


    public interface CustomLister<R> {
        void onMoreDataFetchedCustom(List<R> data);

        void onNewDataFetchedCustom(List<R> data);

        void onNoMoreDataCustom();

        void onNoNewDataCustom();
    }
}
