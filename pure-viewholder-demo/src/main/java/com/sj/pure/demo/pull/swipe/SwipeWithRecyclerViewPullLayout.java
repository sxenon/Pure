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

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.sxenon.pure.core.viewholder.filler.IPullLayout;

/**
 * Created by suijun on 23/02/2018.
 */

public class SwipeWithRecyclerViewPullLayout implements IPullLayout {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ILoadMore loadMore;

    /**
     *
     * @param swipeRefreshLayout only support refreshing
     * @param recyclerView only support loading more
     * @param loadMore actually,it should be a {@link RecyclerView.Adapter}
     */
    public SwipeWithRecyclerViewPullLayout(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,ILoadMore loadMore) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.recyclerView = recyclerView;
        this.loadMore = loadMore;
    }

    @Override
    public void beginPullingDown() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void beginPullingUp() {
        loadMore.beginLoadingMore();
    }

    @Override
    public void endPullingUp() {
        loadMore.endLoadingMore();
    }

    @Override
    public void endPullingDown() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setVisibility(int visibility) {
        swipeRefreshLayout.setVisibility(visibility);
        recyclerView.setVisibility(visibility);
    }

    void setDelegate(final SwipeRefreshLayout.OnRefreshListener onPullDownListener, EndlessRecyclerOnScrollListener onPullUpListener){
        swipeRefreshLayout.setOnRefreshListener(onPullDownListener);
        recyclerView.addOnScrollListener(onPullUpListener);
    }

    ILoadMore getLoadMore() {
        return loadMore;
    }
}
