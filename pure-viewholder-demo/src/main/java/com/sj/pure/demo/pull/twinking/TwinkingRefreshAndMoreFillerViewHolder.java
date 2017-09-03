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

package com.sj.pure.demo.pull.twinking;

import android.content.Context;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.sxenon.pure.core.viewholder.filler.list.BaseListFillerViewHolder;
import com.sxenon.pure.core.viewholder.filler.list.IFillPageStrategyForList;

/**
 * Wrap with TwinklingPullLayout
 * Created by Sui on 2016/12/27.
 */

public class TwinkingRefreshAndMoreFillerViewHolder<R> extends BaseListFillerViewHolder<R, TwinklingPullLayout,IFillPageStrategyForList<R>> {
    public TwinkingRefreshAndMoreFillerViewHolder(Context context, TwinklingRefreshLayout refreshLayout,IFillPageStrategyForList<R> fillPageStrategy,int dataSizeForFullPage) {
        super(context, new TwinklingPullLayout(refreshLayout), fillPageStrategy,dataSizeForFullPage);
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
                onBeginPullingDown();
                refreshListener.onRefresh(refreshLayout);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                onBeginPullingUp();
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
