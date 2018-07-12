/*
 * Copyright (c) 2018  sxenon
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

package com.sj.pure.demo.pull.swipe;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.sxenon.pure.core.viewmodule.filler.list.BaseListViewModule;
import com.sxenon.pure.core.viewmodule.filler.list.strategy.RefreshAndMoreListStrategy;

import java.util.List;

/**
 * Created by suijun on 23/02/2018.
 */

public class SwipeWithRecyclerViewViewModule<R> extends BaseListViewModule<R, SwipeWithRecyclerViewPullLayout> {

    /**
     * Constructor
     *
     * @param context            上下文
     * @param pullLayout         刷新容器
     * @param fillPageStrategy   分页数据填充策略
     * @param dataSizeInFullPage 完整页数据个数
     */
    public SwipeWithRecyclerViewViewModule(Context context, final SwipeWithRecyclerViewPullLayout pullLayout, RefreshAndMoreListStrategy<R> fillPageStrategy, int dataSizeInFullPage) {
        super(context, pullLayout, fillPageStrategy, dataSizeInFullPage);
        fillPageStrategy.setFillEventListener(new RefreshAndMoreListStrategy.SimpleOnFillEventListener<R>() {

            @Override
            public void onPartialMoreDataFetched(List<R> data) {
                pullLayout.getLoadMore().onAllLoaded();
            }

            @Override
            public void onNoMoreData() {
                pullLayout.getLoadMore().onAllLoaded();
            }
        });
    }

    public void setDelegate(final SwipeRefreshLayout.OnRefreshListener onPullDownListener, final EndlessRecyclerOnScrollListener onPullUpListener){
        getPullLayout().setDelegate(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onBeginPullingDown();
                onPullUpListener.resetState();
                onPullDownListener.onRefresh();
            }
        }, new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(!getPullLayout().getLoadMore().hasLoadedAll()){
                    onBeginPullingUp();
                    getPullLayout().getLoadMore().beginLoadingMore();
                    onPullUpListener.onLoadMore(page, totalItemsCount, view);
                }
            }
        });
    }

    @Override
    public void onEmpty() {
        super.onEmpty();
        getPullLayout().getLoadMore().onAllLoaded();
    }
}
