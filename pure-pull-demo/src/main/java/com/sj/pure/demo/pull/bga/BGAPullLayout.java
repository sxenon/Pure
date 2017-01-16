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

import com.sxenon.pure.core.component.filler.IPullLayout;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Adapter from https://github.com/bingoogolapple/BGARefreshLayout-Android to IPullLayout
 * Created by Sui on 2016/12/27.
 */

public class BGAPullLayout implements IPullLayout {
    private BGARefreshLayout mRealLayout;

    public BGAPullLayout(BGARefreshLayout realLayout) {
        mRealLayout = realLayout;
        defaultInit(realLayout);
    }

    @Override
    public void beginRefreshing() {
        mRealLayout.beginRefreshing();
    }

    @Override
    public void beginLoadingMore() {
        mRealLayout.beginLoadingMore();
    }

    @Override
    public void endLoadingMore() {
        mRealLayout.endLoadingMore();
    }

    @Override
    public void endRefreshing() {
        mRealLayout.endRefreshing();
    }

    @Override
    public void setVisibility(int visibility) {
        mRealLayout.setVisibility(visibility);
    }

    void setDelegate(BGARefreshLayout.BGARefreshLayoutDelegate delegate) {
        mRealLayout.setDelegate(delegate);
    }

    /**
     * 项目级别通用的初始化工作
     */
    protected void defaultInit(BGARefreshLayout realLayout) {

    }

}
