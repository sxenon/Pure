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
import android.util.AttributeSet;

import com.sxenon.pure.core.viewmodule.pull.IPullLayout;

/**
 * Created by suijun on 23/02/2018.
 */

public class SwipeWithRecyclerViewPullLayout extends SwipeRefreshLayout implements IPullLayout {
    private RecyclerView recyclerView;
    private ILoadMore loadMore;

    public SwipeWithRecyclerViewPullLayout(Context context) {
        super(context);
    }

    public SwipeWithRecyclerViewPullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeWithRecyclerViewPullLayout setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
        return this;
    }

    public SwipeWithRecyclerViewPullLayout setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }

    @Override
    public void beginPullingDown() {
        setRefreshing(true);
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
        setRefreshing(false);
    }

    @Override
    public void setVisibility(int visibility) {
        setVisibility(visibility);
    }

    void setDelegate(final SwipeRefreshLayout.OnRefreshListener onPullDownListener, EndlessRecyclerOnScrollListener onPullUpListener){
        setOnRefreshListener(onPullDownListener);
        recyclerView.addOnScrollListener(onPullUpListener);
    }

    ILoadMore getLoadMore() {
        return loadMore;
    }
}
