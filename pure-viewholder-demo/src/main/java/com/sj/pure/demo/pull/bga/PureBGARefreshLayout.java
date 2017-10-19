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
import android.util.AttributeSet;
import android.util.Log;

import com.sxenon.pure.core.viewholder.filler.IPullLayout;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Adapter from https://github.com/bingoogolapple/BGARefreshLayout-Android to IPullLayout
 * 类适配器
 * Created by Sui on 2016/12/27.
 */

public class PureBGARefreshLayout extends BGARefreshLayout implements IPullLayout {
    private static final String TAG = "PureBGARefreshLayout";

    public PureBGARefreshLayout(Context context) {
        super(context);
        init();
    }

    public PureBGARefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void beginPullingDown() {
        post(new Runnable() {
            @Override
            public void run() {
                beginRefreshing();
            }
        });
    }

    @Override
    public void beginPullingUp() {
        post(new Runnable() {
            @Override
            public void run() {
                beginLoadingMore();
            }
        });
    }

    @Override
    public void endPullingUp() {
        post(new Runnable() {
            @Override
            public void run() {
                endLoadingMore();
            }
        });
    }

    @Override
    public void endPullingDown() {
        post(new Runnable() {
            @Override
            public void run() {
                endRefreshing();
            }
        });
    }

    /**
     * 项目级别通用的初始化工作
     */
    private void init() {
        Log.i(TAG, "defaultInit");
    }

}
