/*
 * Copyright (c) 2017  sxenon
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

import android.util.Log;

import com.sj.pure.demo.bean.RequestBean;
import com.sj.pure.demo.bean.ResponseBean;
import com.sxenon.pure.core.component.filler.implement.RefreshAndMoreFillPageStrategy;
import com.sxenon.pure.core.component.submitter.ISingleDataSubmitter;
import com.sxenon.pure.core.mvp.IView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Demo for real BgaFillerGroup
 * Created by Sui on 2017/8/14.
 */

public class DemoBgaFillerGroup extends BGAFillerGroup<ResponseBean> implements ISingleDataSubmitter<RequestBean> {
    private final IView mContainer;

    public DemoBgaFillerGroup(IView container,BGARefreshLayout refreshLayout,BGARecyclerViewAdapter<ResponseBean> adapter){
        super(container.getContext(), refreshLayout, adapter, new RefreshAndMoreFillPageStrategy<ResponseBean>());
        mContainer =container;
    }

    @Override
    public void onEmpty() {
        super.onEmpty();
        doSomething();
    }

    private void doSomething(){
        Log.i("Demo",mContainer.toString());
    }

    @Override
    public RequestBean getDataForSubmit() {
        return new RequestBean();
    }
}
