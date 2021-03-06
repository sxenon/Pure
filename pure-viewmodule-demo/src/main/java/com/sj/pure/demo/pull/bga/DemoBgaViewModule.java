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

import com.sj.pure.demo.bean.ResponseBean;
import com.sxenon.pure.core.controller.IController;
import com.sxenon.pure.core.viewmodule.pull.list.strategy.RefreshAndMoreListStrategy;

/**
 * Demo for real BgaFillerGroup
 * Created by Sui on 2017/8/14.
 */

public class DemoBgaViewModule extends BGAViewModule<ResponseBean> {
    private final IController mContainer;

    public DemoBgaViewModule(IController container, PureBGARefreshLayout refreshLayout, int dataSizeForFullPage){
        super(container.getContext(), refreshLayout, new RefreshAndMoreListStrategy<ResponseBean>(),dataSizeForFullPage);
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
}
