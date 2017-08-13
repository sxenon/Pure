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

import com.sxenon.pure.core.component.filler.FillPageStrategy;
import com.sxenon.pure.core.component.filler.implement.BaseFillerGroup;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Wrap with BGAPullLayout
 * Created by Sui on 2016/12/27.
 */

public class BGAFillerGroup<R> extends BaseFillerGroup<R, BGAPullLayout> {
    private static final String TAG = "FillerGroup";
    private CustomListener mCustomerListener;

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, IFetchSingleResultHandler<R> singleDataResult, FillPageStrategy<R> fillPageStrategy) {
        super(context, new BGAPullLayout(refreshLayout), singleDataResult, fillPageStrategy);
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, BGARecyclerViewAdapter<R> adapter, FillPageStrategy<R> fillPageStrategy) {
        super(context, new BGAPullLayout(refreshLayout), new BGARecyclerViewPureAdapter<>(adapter), fillPageStrategy);
    }

    public BGAFillerGroup(Context context, BGARefreshLayout refreshLayout, BGAAdapterViewAdapter<R> adapter, FillPageStrategy<R> fillPageStrategy) {
        super(context, new BGAPullLayout(refreshLayout), new BGAAdapterViewPureAdapter<>(adapter), fillPageStrategy);
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
