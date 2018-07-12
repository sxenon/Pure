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

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewholder.filler.IFillerViewHolder;
import com.sxenon.pure.core.viewholder.filler.list.BaseListStrategy;
import com.sxenon.pure.core.viewholder.filler.list.IAdapterStrategy;

import java.util.List;

/**
 * RefreshAndMoreListStrategy
 * Created by Sui on 2017/8/6.
 */

public class RefreshAndMoreListStrategy<R> extends BaseListStrategy<R> {

    private OnFillEventListener mOnFillEventListener;

    public RefreshAndMoreListStrategy() {
        super();
    }

    public RefreshAndMoreListStrategy(IAdapterStrategy<R> fillAdapterStrategy) {
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

    private void onInitialize() {
        if (mOnFillEventListener !=null){
            mOnFillEventListener.onInitialize();
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
    public void processPartialList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        if (pageInfo.tempPage == 0) {
            onInitDataFetched(adapter, data);
        } else {
            onPartialMoreDataFetched(adapter,data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void processFullList(IFillerViewHolder fillerViewHolder, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        if (pageInfo.tempPage == 0) {
            onInitDataFetched(adapter, data);
        } else {
            onFullMoreDataFetched(adapter, data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void processEmptyList(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            fillerViewHolder.onEmpty();
        } else {
            onNoMoreData();
        }
        pageInfo.tempPage = pageInfo.currentPage;
    }

    @Override
    public void onException(IFillerViewHolder fillerViewHolder, ApiException exception, IPureAdapter<R> adapter, PageInfo pageInfo) {
        adapter.clearAllItems();
        pageInfo.currentPage = pageInfo.tempPage = -1;
    }

    public void setFillEventListener(OnFillEventListener onFillEventListener) {
        this.mOnFillEventListener = onFillEventListener;
    }

    public interface OnFillEventListener<R>{
        void onFullMoreDataFetched(List<R> data);
        void onPartialMoreDataFetched(List<R> data);
        void onInitDataFetched(List<R> data);
        void onNoMoreData();
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
        public void onInitDataFetched(List<R> data) {

        }

        @Override
        public void onNoMoreData() {

        }

        @Override
        public void onInitialize() {

        }
    }
}
