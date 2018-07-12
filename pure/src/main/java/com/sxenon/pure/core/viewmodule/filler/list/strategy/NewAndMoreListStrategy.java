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

package com.sxenon.pure.core.viewmodule.filler.list.strategy;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewmodule.filler.IFillerViewModule;
import com.sxenon.pure.core.viewmodule.filler.list.BaseListStrategy;
import com.sxenon.pure.core.viewmodule.filler.list.IAdapterStrategy;

import java.util.List;

/**
 * NewAndMore implement for IFillStrategy
 * Created by Sui on 2017/8/6.
 */

public class NewAndMoreListStrategy<R> extends BaseListStrategy<R> {
    private OnFillEventListener mOnFillEventListener;

    public NewAndMoreListStrategy() {
        super();
    }

    public NewAndMoreListStrategy(IAdapterStrategy<R> fillAdapterStrategy) {
        super(fillAdapterStrategy);
    }

    private void onFullMoreDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onMoreDataFetched(adapter, data);
        if (mOnFillEventListener !=null){
            //noinspection unchecked
            mOnFillEventListener.onFullMoreDataFetched(data);
        }
    }

    private void onPartialMoreDataFetched(IPureAdapter<R> adapter, List<R> data){
        getListDataFillStrategy().onMoreDataFetched(adapter, data);
        if (mOnFillEventListener !=null){
            //noinspection unchecked
            mOnFillEventListener.onPartialMoreDataFetched(data);
        }
    }

    private void onNewDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onNewDataFetched(adapter, data);
        if (mOnFillEventListener !=null){
            //noinspection unchecked
            mOnFillEventListener.onNewDataFetched(data);
        }
    }

    private void onInitDataFetched(IPureAdapter<R> adapter, List<R> data) {
        getListDataFillStrategy().onInitDataFetched(adapter, data);
        if (mOnFillEventListener !=null){
            //noinspection unchecked
            mOnFillEventListener.onInitDataFetched(data);
        }
    }

    private void onNoMoreData() {
        if (mOnFillEventListener !=null){
            mOnFillEventListener.onNoMoreData();
        }
    }

    private void onNoNewData() {
        if (mOnFillEventListener !=null){
            mOnFillEventListener.onNoNewData();
        }
    }

    private void onInitialize() {
        if (mOnFillEventListener !=null){
            mOnFillEventListener.onInitialize();
        }
    }

    @Override
    public void processPartialList(IFillerViewModule fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
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
    public void processFullList(IFillerViewModule fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
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
    public void processEmptyList(IFillerViewModule fillerViewHolder, PageInfo pageInfo) {
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

    @Override
    public void onException(IFillerViewModule fillerViewHolder, ApiException exception, IPureAdapter<R> adapter, PageInfo pageInfo) {
        adapter.clearAllItems();
        pageInfo.currentPage = pageInfo.tempPage = -1;
    }

    public void setFillEventListener(OnFillEventListener onFillEventListener) {
        this.mOnFillEventListener = onFillEventListener;
    }

    public interface OnFillEventListener<R> {
        void onFullMoreDataFetched(List<R> data);
        void onPartialMoreDataFetched(List<R> data);
        void onNewDataFetched(List<R> data);
        void onInitDataFetched(List<R> data);
        void onNoMoreData();
        void onNoNewData();
        void onInitialize();
    }

    public static class SimpleOnFillEventListener<R> implements OnFillEventListener<R> {

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
