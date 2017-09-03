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
 * NewAndMore implement for IFillPageStrategy
 * Created by Sui on 2017/8/6.
 */

public class NewAndMoreFillPageStrategy<R> extends BaseFillPageStrategyForList<R> {
    private FillEventListener mFillEventListener;

    public NewAndMoreFillPageStrategy() {
        super();
    }

    public NewAndMoreFillPageStrategy(IFillAdapterStrategy<R> fillAdapterStrategy) {
        super(fillAdapterStrategy);
    }

    private void onFullMoreDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onMoreDataFetched(adapter, data);
        if (mFillEventListener!=null){
            //noinspection unchecked
            mFillEventListener.onFullMoreDataFetched(data);
        }
    }

    private void onPartialMoreDataFetched(IPureAdapter<R> adapter, List<R> data){
        getListDataFillStrategy().onMoreDataFetched(adapter, data);
        if (mFillEventListener!=null){
            //noinspection unchecked
            mFillEventListener.onPartialMoreDataFetched(data);
        }
    }

    private void onNewDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onNewDataFetched(adapter, data);
        if (mFillEventListener!=null){
            //noinspection unchecked
            mFillEventListener.onNewDataFetched(data);
        }
    }

    private void onInitDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onInitDataFetched(adapter, data);
        if (mFillEventListener!=null){
            //noinspection unchecked
            mFillEventListener.onInitDataFetched(data);
        }
    }

    private void onNoMoreData() {
        if (mFillEventListener!=null){
            mFillEventListener.onNoMoreData();
        }
    }

    private void onNoNewData() {
        if (mFillEventListener!=null){
            mFillEventListener.onNoNewData();
        }
    }

    private void onInitialize() {
        if (mFillEventListener!=null){
            mFillEventListener.onInitialize();
        }
    }

    @Override
    public void processPartialList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            onInitDataFetched(adapter, data);
        } else if (pageInfo.tempPage == pageInfo.currentPage) {//refresh
            onNewDataFetched(adapter, data);
        } else {
            onPartialMoreDataFetched(adapter,data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void processFullList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            onInitDataFetched(adapter, data);
        } else if (pageInfo.tempPage == pageInfo.currentPage) {//refresh
            onNewDataFetched(adapter, data);
        } else {
            onFullMoreDataFetched(adapter, data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void processEmptyList(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            fillerViewHolder.onEmpty();
        } else if (pageInfo.tempPage == pageInfo.currentPage) {//refreshForAdd
            onNoNewData();
        } else {
            onNoMoreData();
        }
        pageInfo.tempPage = pageInfo.currentPage;
    }

    @Override
    public void onPullDown(PageInfo pageInfo) {
        if (pageInfo.currentPage == -1){
            onInitialize();
        }
        pageInfo.tempPage = pageInfo.currentPage;
    }

    @Override
    public void onPullUp(PageInfo pageInfo) {
        pageInfo.tempPage = pageInfo.currentPage + 1;
    }

    public void setFillEventListener(FillEventListener fillEventListener) {
        this.mFillEventListener = fillEventListener;
    }

    public interface FillEventListener<R> {
        void onFullMoreDataFetched(List<R> data);
        void onPartialMoreDataFetched(List<R> data);
        void onNewDataFetched(List<R> data);
        void onInitDataFetched(List<R> data);
        void onNoMoreData();
        void onNoNewData();
        void onInitialize();
    }

    public static class SimpleFillEventListener<R> implements FillEventListener<R>{

        @Override
        public void onFullMoreDataFetched(List<R> data) {

        }

        @Override
        public void onPartialMoreDataFetched(List<R> data) {

        }

        @Override
        public void onNewDataFetched(List<R> data) {

        }

        @Override
        public void onInitDataFetched(List<R> data) {

        }

        @Override
        public void onNoMoreData() {

        }

        @Override
        public void onNoNewData() {

        }

        @Override
        public void onInitialize() {

        }
    }

}
