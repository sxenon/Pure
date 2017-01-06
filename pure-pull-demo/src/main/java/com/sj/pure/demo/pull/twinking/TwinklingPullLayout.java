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

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.sxenon.pure.core.component.filler.IPullLayout;

/**
 * Adapter for https://github.com/lcodecorex/TwinklingRefreshLayout
 * Created by Sui on 2016/12/27.
 */

public class TwinklingPullLayout implements IPullLayout {
    private TwinklingRefreshLayout mRealLayout;

    public TwinklingPullLayout(TwinklingRefreshLayout realLayout) {
        mRealLayout = realLayout;
        defaultInit(realLayout);
    }

    @Override
    public void beginRefreshing() {
        mRealLayout.startRefresh();
    }

    @Override
    public void beginLoadingMore() {
        mRealLayout.startLoadMore();
    }

    @Override
    public void endLoadingMore() {
        mRealLayout.finishLoadmore();
    }

    @Override
    public void endRefreshing() {
        mRealLayout.finishLoadmore();
    }

    @Override
    public void setVisibility(int visibility) {
        mRealLayout.setVisibility(visibility);
    }

    void setOnRefreshListener(final RefreshListenerAdapter refreshListener) {
        mRealLayout.setOnRefreshListener(refreshListener);
    }

    /**
     * 项目级别通用的初始化工作
     */
    protected void defaultInit(TwinklingRefreshLayout realLayout){

    }
}
