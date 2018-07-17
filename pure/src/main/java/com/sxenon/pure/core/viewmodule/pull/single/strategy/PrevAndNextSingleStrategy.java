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
 * PrevAndNext implement for IPullStrategy
 * Created by Sui on 2017/8/6.
 */

public class PrevAndNextSingleStrategy<R> extends BaseSingleStrategy<R> {
    private final int mInitPage;
    private EventListener mEventListener;

    public PrevAndNextSingleStrategy(int initPage) {
        super();
        mInitPage = initPage;
    }

    private void onInitialize() {
        if ( mEventListener !=null){
            mEventListener.onInitialize();
        }
    }

    private void onNoPrevData(){
        if ( mEventListener !=null){
            mEventListener.onNoPrevData();
        }
    }

    private void onNoNextData() {
        if ( mEventListener !=null){
            mEventListener.onNoNextData();
        }
    }

    private void onNextDataFetched(ISingleResultHandler<R> singleResultHandler, R data){
        singleResultHandler.onSingleData(data);
        if ( mEventListener !=null){
            //noinspection unchecked
            mEventListener.onNextDataFetched(data);
        }
    }

    private void onPrevDataFetched(ISingleResultHandler<R> singleResultHandler, R data){
        singleResultHandler.onSingleData(data);
        if ( mEventListener !=null){
            //noinspection unchecked
            mEventListener.onPrevDataFetched(data);
        }
    }

    @Override
    public void processSingle(IPullViewModule pullViewHolder, R data, ISingleResultHandler<R> singleResultHandler, PageInfo pageInfo) {
        if (pageInfo.currentPage<pageInfo.tempPage){
            onNextDataFetched(singleResultHandler, data);
        }else {
            onPrevDataFetched(singleResultHandler, data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void processEmptySingle(IPullViewModule pullViewModule, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            pullViewModule.onEmpty();
        } else {
            onNoNextData();
        }
        pageInfo.tempPage = pageInfo.currentPage;
    }

    @Override
    public void onPullDown(PageInfo pageInfo) {
        if (pageInfo.currentPage==-1){
            onInitialize();
            pageInfo.tempPage=mInitPage;
        }else if (pageInfo.currentPage==0){
            onNoPrevData();
        } else{
            pageInfo.tempPage = pageInfo.currentPage - 1;
        }
    }

    @Override
    public void onPullUp(PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage + 1;
    }

    public void setFillEventListener(EventListener eventListener) {
        this.mEventListener = eventListener;
    }

    public interface EventListener<R>{
        void onNextDataFetched(R data);
        void onPrevDataFetched(R data);
        void onNoPrevData();
        void onNoNextData();
        void onInitialize();
    }

    public static class SimpleEventListener<R> implements EventListener<R> {

        @Override
        public void onNextDataFetched(R data) {

        }

        @Override
        public void onPrevDataFetched(R data) {

        }

        @Override
        public void onNoPrevData() {

        }

        @Override
        public void onNoNextData() {

        }

        @Override
        public void onInitialize() {

        }
    }
}
