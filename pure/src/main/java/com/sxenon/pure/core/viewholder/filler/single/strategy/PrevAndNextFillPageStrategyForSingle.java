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

package com.sxenon.pure.core.viewholder.filler.single.strategy;

import com.sxenon.pure.core.result.filler.ISingleResultFiller;
import com.sxenon.pure.core.viewholder.filler.IFillerViewHolder;
import com.sxenon.pure.core.viewholder.filler.single.BaseFillPageStrategyForSingle;

/**
 * PrevAndNext implement for IFillPageStrategy
 * Created by Sui on 2017/8/6.
 */

public class PrevAndNextFillPageStrategyForSingle<R> extends BaseFillPageStrategyForSingle<R> {
    private final int mInitPage;
    private FillEventListener mFillEventListener;

    public PrevAndNextFillPageStrategyForSingle(int initPage) {
        super();
        mInitPage = initPage;
    }

    private void onInitialize() {
        if (mFillEventListener!=null){
            mFillEventListener.onInitialize();
        }
    }

    private void onNoPrevData(){
        if (mFillEventListener!=null){
            mFillEventListener.onNoPrevData();
        }
    }

    private void onNoNextData() {
        if (mFillEventListener!=null){
            mFillEventListener.onNoNextData();
        }
    }

    private void onNextDataFetched(ISingleResultFiller<R> singleResultFiller,R data){
        singleResultFiller.onSingleDataFetched(data);
        if (mFillEventListener!=null){
            //noinspection unchecked
            mFillEventListener.onNextDataFetched(data);
        }
    }

    private void onPrevDataFetched(ISingleResultFiller<R> singleResultFiller,R data){
        singleResultFiller.onSingleDataFetched(data);
        if (mFillEventListener!=null){
            //noinspection unchecked
            mFillEventListener.onPrevDataFetched(data);
        }
    }

    @Override
    public void processSingle(IFillerViewHolder fillerViewHolder, R data, ISingleResultFiller<R> singleResultFiller, PageInfo pageInfo) {
        if (pageInfo.currentPage<pageInfo.tempPage){
            onNextDataFetched(singleResultFiller, data);
        }else {
            onPrevDataFetched(singleResultFiller, data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void processEmptySingle(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            fillerViewHolder.onEmpty();
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

    public void setFillEventListener(FillEventListener fillEventListener) {
        this.mFillEventListener = fillEventListener;
    }

    public interface FillEventListener<R>{
        void onNextDataFetched(R data);
        void onPrevDataFetched(R data);
        void onNoPrevData();
        void onNoNextData();
        void onInitialize();
    }

    public static class SimpleFillEventListener<R> implements FillEventListener<R>{

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
