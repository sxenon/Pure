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

import android.util.Log;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.sxenon.pure.core.viewholder.filler.IPullLayout;

/**
 * Adapter from https://github.com/lcodecorex/TwinklingRefreshLayout to IPullLayout
 * 对象适配器
 * Created by Sui on 2016/12/27.
 */

public class TwinklingPullLayout implements IPullLayout {
    private final TwinklingRefreshLayout mRealLayout;
    private static final String TAG = "TwinklingPullLayout";

    public TwinklingPullLayout(TwinklingRefreshLayout realLayout) {
        mRealLayout = realLayout;
        defaultInit(realLayout);
    }

    @Override
    public void beginPullingDown() {
        mRealLayout.startRefresh();
    }

    @Override
    public void beginPullingUp() {
        mRealLayout.startLoadMore();
    }

    @Override
    public void endPullingUp() {
        mRealLayout.finishLoadmore();
    }

    @Override
    public void endPullingDown() {
        mRealLayout.finishRefreshing();
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
    private void defaultInit(TwinklingRefreshLayout realLayout) {
        Log.i(TAG, "defaultInit");
    }
}
