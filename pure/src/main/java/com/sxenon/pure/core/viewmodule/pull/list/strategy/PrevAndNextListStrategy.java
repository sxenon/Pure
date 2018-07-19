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

package com.sxenon.pure.core.viewmodule.pull.list.strategy;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewmodule.pull.IPullViewModule;
import com.sxenon.pure.core.viewmodule.pull.list.strategy.adapter.IAdapterStrategy;

import java.util.List;

/**
 * PrevAndNext implement for IPullStrategy
 * Created by Sui on 2017/8/6.
 */

public class PrevAndNextListStrategy<R> extends BaseListStrategy<R> {
    private final int mInitPage;
    private EventListener mOnPullEventListener;

    public PrevAndNextListStrategy(int initPage) {
        super();
        mInitPage = initPage;
    }

    public PrevAndNextListStrategy(IAdapterStrategy<R> adapterStrategy, int initPage) {
        super(adapterStrategy);
        mInitPage = initPage;
    }


    private void onNoPrevData(){
        if ( mOnPullEventListener !=null){
            mOnPullEventListener.onNoPrevData();
        }
    }

    private void onNoNextData() {
        if ( mOnPullEventListener !=null){
            mOnPullEventListener.onNoNextData();
        }
    }

    private void onFullNextData(IPureAdapter<R> adapter, List<R> data) {
        getAdapterStrategy().onInitData(adapter, data);
        if ( mOnPullEventListener !=null){
            //noinspection unchecked
            mOnPullEventListener.onFullNextData(data);
        }
    }

    private void onPartialNextData(IPureAdapter<R> adapter, List<R> data){
        getAdapterStrategy().onInitData(adapter, data);
        if ( mOnPullEventListener !=null){
            //noinspection unchecked
            mOnPullEventListener.onPartialNextData(data);
        }
    }

    private void onPrevData(IPureAdapter<R> adapter, List<R> data){
        getAdapterStrategy().onInitData(adapter, data);
        if ( mOnPullEventListener !=null){
            //noinspection unchecked
            mOnPullEventListener.onPrevData(data);
        }
    }

    private void onInitialize() {
        if ( mOnPullEventListener !=null){
            mOnPullEventListener.onInitialize();
        }
    }

    @Override
    public void onPartialList(IPullViewModule pullViewModule, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        onPartialNextData(adapter,data);
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void onFullList(IPullViewModule pullViewModule, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        if (pageInfo.currentPage<pageInfo.tempPage){
            onFullNextData(adapter, data);
        }else {
            onPrevData(adapter, data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void onEmptyList(IPullViewModule pullViewModule, PageInfo pageInfo) {
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

    @Override
    public void onException(IPullViewModule pullViewModule, ApiException exception, IPureAdapter<R> adapter, PageInfo pageInfo) {
        adapter.clearAllItems();
        pageInfo.currentPage = pageInfo.tempPage = -1;
    }

    public void setEventListener(EventListener eventListener) {
        this.mOnPullEventListener = eventListener;
    }

    public interface EventListener<R>{
        void onFullNextData(List<R> data);
        void onPartialNextData(List<R> data);
        void onPrevData(List<R> data);
        void onNoPrevData();
        void onNoNextData();
        void onInitialize();
    }

    public static class SimpleEventListener<R> implements EventListener<R> {

        @Override
        public void onFullNextData(List<R> data) {

        }

        @Override
        public void onPartialNextData(List<R> data) {

        }

        @Override
        public void onPrevData(List<R> data) {

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
