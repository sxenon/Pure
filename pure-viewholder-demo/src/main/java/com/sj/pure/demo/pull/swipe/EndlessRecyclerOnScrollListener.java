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

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by sanvi on 8/7/17.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener{
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private static final int visibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;


    public EndlessRecyclerOnScrollListener(){

    }

    private int getVisibleThreshold(RecyclerView view) {
        RecyclerView.LayoutManager mLayoutManager = view.getLayoutManager();
        if (mLayoutManager instanceof GridLayoutManager ){
            return visibleThreshold * ((GridLayoutManager)mLayoutManager).getSpanCount();
        }
        if (mLayoutManager instanceof StaggeredGridLayoutManager ){
            return visibleThreshold * ((StaggeredGridLayoutManager)mLayoutManager).getSpanCount();
        }
        return visibleThreshold;
    }

    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int last = lastVisibleItemPositions[0];
        for (int value : lastVisibleItemPositions) {
            if (value > last) {
                last = value;
            }
        }
        return last;
    }

    private int getLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager){
        int lastVisibleItemPosition = 0;
        if (layoutManager instanceof StaggeredGridLayoutManager ) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (layoutManager instanceof GridLayoutManager ) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager ) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        return lastVisibleItemPosition;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we setCheck if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {

        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = getLastVisibleItemPosition(layoutManager);

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we setCheck to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we setCheck to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!loading && (lastVisibleItemPosition + getVisibleThreshold(view)) > totalItemCount) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, view);
            loading = true;
        }
    }

    // Call this method whenever performing new searches
    public void resetState() {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
}
