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

import com.sxenon.pure.core.result.handler.IFetchSingleResultHandler;
import com.sxenon.pure.core.viewmodule.pull.IPullViewModule;
import com.sxenon.pure.core.viewmodule.pull.single.BaseSingleStrategy;

/**
 * PrevAndNext implement for IPullStrategy
 * Created by Sui on 2017/8/6.
 */

public class PrevAndNextSingleStrategy<R> extends BaseSingleStrategy<R> {
    private final int mInitPage;
    private OnFillEventListener mOnFillEventListener;

    public PrevAndNextSingleStrategy(int initPage) {
        super();
        mInitPage = initPage;
    }

    private void onInitialize() {
        if (mOnFillEventListener !=null){
            mOnFillEventListener.onInitialize();
        }
    }

    private void onNoPrevData(){
        if (mOnFillEventListener !=null){
            mOnFillEventListener.onNoPrevData();
        }
    }

    private void onNoNextData() {
        if (mOnFillEventListener !=null){
            mOnFillEventListener.onNoNextData();
        }
    }

    private void onNextDataFetched(IFetchSingleResultHandler<R> singleResultFiller,R data){
        singleResultFiller.onSingleDataFetched(data);
        if (mOnFillEventListener !=null){
            //noinspection unchecked
            mOnFillEventListener.onNextDataFetched(data);
        }
    }

    private void onPrevDataFetched(IFetchSingleResultHandler<R> singleResultFiller,R data){
        singleResultFiller.onSingleDataFetched(data);
        if (mOnFillEventListener !=null){
            //noinspection unchecked
            mOnFillEventListener.onPrevDataFetched(data);
        }
    }

    @Override
    public void processSingle(IPullViewModule pullViewHolder, R data, IFetchSingleResultHandler<R> singleResultHandler, PageInfo pageInfo) {
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

    public void setFillEventListener(OnFillEventListener onFillEventListener) {
        this.mOnFillEventListener = onFillEventListener;
    }

    public interface OnFillEventListener<R>{
        void onNextDataFetched(R data);
        void onPrevDataFetched(R data);
        void onNoPrevData();
        void onNoNextData();
        void onInitialize();
    }

    public static class SimpleOnFillEventListener<R> implements OnFillEventListener<R> {

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
