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
import android.util.Log;

import com.sxenon.pure.core.viewholder.filler.FillPageStrategy;
import com.sxenon.pure.core.viewholder.filler.implement.BaseFillerViewHolder;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Wrap with BGAPullLayout
 * Created by Sui on 2016/12/27.
 */

public class BGAFillerViewHolder<R> extends BaseFillerViewHolder<R, BGAPullLayout> {
    private static final String TAG = "FillerGroup";
    private CustomListener mCustomerListener;

    public BGAFillerViewHolder(Context context, BGARefreshLayout refreshLayout, FillPageStrategy<R> fillPageStrategy) {
        super(context, new BGAPullLayout(refreshLayout), fillPageStrategy);
    }

    public void setAdapter(BGARecyclerViewAdapter<R> adapter){
        setAdapter( new BGAPureRecyclerViewAdapter<>(adapter));
    }

    public void setAdapter(BGAAdapterViewAdapter<R> adapter){
        setAdapter(new BGAPureAdapterViewAdapter<>(adapter));
    }

    /**
     * Don`t call {@link BGARefreshLayout#setDelegate(BGARefreshLayout.BGARefreshLayoutDelegate)} directly!
     */
    public void setDelegate(final BGARefreshLayout.BGARefreshLayoutDelegate delegate) {
        getPullLayout().setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                onBeginPullingDown();
                delegate.onBGARefreshLayoutBeginRefreshing(refreshLayout);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                onBeginPullingUp();
                return delegate.onBGARefreshLayoutBeginLoadingMore(refreshLayout);
            }
        });
    }

    public void setCustomerListener(CustomListener customerListener) {
        mCustomerListener = customerListener;
    }

    @Override
    public void onEmpty() {
        super.onEmpty();
        commonOnEmpty();
        if (mCustomerListener != null) {
            mCustomerListener.onEmpty();
        }
    }

    @Override
    public void onCancel() {
        super.onCancel();
        commonOnCancel();
        if (mCustomerListener != null) {
            mCustomerListener.onCancel();
        }
    }

    private void commonOnCancel() {
        Log.i(TAG, "commonOnCancel");
    }

    private void commonOnEmpty() {
        Log.i(TAG, "commonOnEmpty");
    }

    public interface CustomListener {
        void onEmpty();

        void onCancel();
    }
}
