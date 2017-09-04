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
    private OnFillEventListener mOnFillEventListener;

    public PrevAndNextFillPageStrategyForList(int initPage) {
        super();
        mInitPage = initPage;
    }

    public PrevAndNextFillPageStrategyForList(IFillAdapterStrategy<R> fillAdapterStrategy, int initPage) {
        super(fillAdapterStrategy);
        mInitPage = initPage;
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

    private void onFullNextDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onInitDataFetched(adapter, data);
        if (mOnFillEventListener !=null){
            //noinspection unchecked
            mOnFillEventListener.onFullNextDataFetched(data);
        }
    }

    private void onPartialNextDataFetched(IPureAdapter<R> adapter, List<R> data){
        getListDataFillStrategy().onInitDataFetched(adapter, data);
        if (mOnFillEventListener !=null){
            //noinspection unchecked
            mOnFillEventListener.onPrevDataFetched(data);
        }
    }

    private void onPrevDataFetched(IPureAdapter<R> adapter, List<R> data){
        getListDataFillStrategy().onInitDataFetched(adapter, data);
        if (mOnFillEventListener !=null){
            //noinspection unchecked
            mOnFillEventListener.onPartialNextDataFetched(data);
        }
    }

    private void onInitialize() {
        if (mOnFillEventListener !=null){
            mOnFillEventListener.onInitialize();
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

    public void setFillEventListener(OnFillEventListener onFillEventListener) {
        this.mOnFillEventListener = onFillEventListener;
    }

    public interface OnFillEventListener<R>{
        void onFullNextDataFetched(List<R> data);
        void onPartialNextDataFetched(List<R> data);
        void onPrevDataFetched(List<R> data);
        void onNoPrevData();
        void onNoNextData();
        void onInitialize();
    }

    public static class SimpleOnFillEventListener<R> implements OnFillEventListener<R> {

        @Override
        public void onFullNextDataFetched(List<R> data) {

        }

        @Override
        public void onPartialNextDataFetched(List<R> data) {

        }

        @Override
        public void onPrevDataFetched(List<R> data) {

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
