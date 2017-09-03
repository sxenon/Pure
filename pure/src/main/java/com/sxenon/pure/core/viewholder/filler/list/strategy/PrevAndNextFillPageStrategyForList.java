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

package com.sxenon.pure.core.viewholder.filler.list.strategy;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewholder.filler.IFillerViewHolder;
import com.sxenon.pure.core.viewholder.filler.list.BaseFillPageStrategyForList;
import com.sxenon.pure.core.viewholder.filler.list.IFillAdapterStrategy;

import java.util.List;

/**
 * PrevAndNext implement for IFillPageStrategy
 * Created by Sui on 2017/8/6.
 */

public class PrevAndNextFillPageStrategyForList<R> extends BaseFillPageStrategyForList<R> {
    private final int mInitPage;
    private FillEventListener mFillEventListener;

    public PrevAndNextFillPageStrategyForList(int initPage) {
        super();
        mInitPage = initPage;
    }

    public PrevAndNextFillPageStrategyForList(IFillAdapterStrategy<R> fillAdapterStrategy, int initPage) {
        super(fillAdapterStrategy);
        mInitPage = initPage;
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

    private void onFullNextDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onInitDataFetched(adapter, data);
        if (mFillEventListener!=null){
            //noinspection unchecked
            mFillEventListener.onFullNextDataFetched(data);
        }
    }

    private void onPartialNextDataFetched(IPureAdapter<R> adapter, List<R> data){
        getListDataFillStrategy().onInitDataFetched(adapter, data);
        if (mFillEventListener!=null){
            //noinspection unchecked
            mFillEventListener.onPrevDataFetched(data);
        }
    }

    private void onPrevDataFetched(IPureAdapter<R> adapter, List<R> data){
        getListDataFillStrategy().onInitDataFetched(adapter, data);
        if (mFillEventListener!=null){
            //noinspection unchecked
            mFillEventListener.onPartialNextDataFetched(data);
        }
    }

    private void onInitialize() {
        if (mFillEventListener!=null){
            mFillEventListener.onInitialize();
        }
    }

    @Override
    public void processPartialList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        onPartialNextDataFetched(adapter,data);
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void processFullList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        if (pageInfo.currentPage<pageInfo.tempPage){
            onFullNextDataFetched(adapter, data);
        }else {
            onPrevDataFetched(adapter, data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void processEmptyList(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
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
        void onFullNextDataFetched(List<R> data);
        void onPartialNextDataFetched(List<R> data);
        void onPrevDataFetched(List<R> data);
        void onNoPrevData();
        void onNoNextData();
        void onInitialize();
    }
}
