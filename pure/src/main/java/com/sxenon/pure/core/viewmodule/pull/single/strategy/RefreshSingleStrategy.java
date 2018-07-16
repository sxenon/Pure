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

package com.sxenon.pure.core.viewmodule.pull.single.strategy;

import com.sxenon.pure.core.result.handler.ISingleResultHandler;
import com.sxenon.pure.core.viewmodule.pull.IPullViewModule;
import com.sxenon.pure.core.viewmodule.pull.single.BaseSingleStrategy;

/**
 * RefreshSingleStrategy
 * Created by Sui on 2017/8/6.
 */

public class RefreshSingleStrategy<R> extends BaseSingleStrategy<R> {
    private EventListener mEventListener;

    private void onInitialize() {
        if ( mEventListener !=null){
            mEventListener.onInitialize();
        }
    }

    @Override
    public void onPullDown(PageInfo pageInfo) {
        if (pageInfo.currentPage == -1){
            onInitialize();
        }
        pageInfo.tempPage = 0;
    }

    @Override
    public void onPullUp(PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage + 1;
    }

    @Override
    public void processSingle(IPullViewModule pullViewHolder, R data, ISingleResultHandler<R> singleResultHandler, PageInfo pageInfo) {
        singleResultHandler.onSingleData(data);
        pageInfo.tempPage = pageInfo.currentPage = 0;
        if ( mEventListener !=null){
            //noinspection unchecked
            mEventListener.onInitDataFetched(data);
        }
    }

    @Override
    public void processEmptySingle(IPullViewModule pullViewModule, PageInfo pageInfo) {
        pullViewModule.onEmpty();
        pageInfo.tempPage = pageInfo.currentPage =-1;
    }

    public void setFillEventListener(EventListener eventListener) {
        this.mEventListener = eventListener;
    }

    public interface EventListener<R> {
        void onInitialize();
        void onInitDataFetched(R data);
    }

    public static class SimpleEventListener<R> implements EventListener<R> {

        @Override
        public void onInitialize() {

        }

        @Override
        public void onInitDataFetched(R data) {

        }
    }
}
