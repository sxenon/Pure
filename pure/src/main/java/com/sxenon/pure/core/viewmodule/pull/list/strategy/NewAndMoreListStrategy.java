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
 * NewAndMore implement for IPullStrategy
 * Created by Sui on 2017/8/6.
 */

public class NewAndMoreListStrategy<R> extends BaseListStrategy<R> {
    private EventListener mEventListener;

    public NewAndMoreListStrategy() {
        super();
    }

    public NewAndMoreListStrategy(IAdapterStrategy<R> adapterStrategy) {
        super(adapterStrategy);
    }

    private void onFullMoreData(IPureAdapter<R> adapter, List<R> data) {
        getAdapterStrategy().onMoreData(adapter, data);
        if ( mEventListener !=null){
            //noinspection unchecked
            mEventListener.onFullMoreData(data);
        }
    }

    private void onPartialMoreData(IPureAdapter<R> adapter, List<R> data){
        getAdapterStrategy().onMoreData(adapter, data);
        if ( mEventListener !=null){
            //noinspection unchecked
            mEventListener.onPartialMoreData(data);
        }
    }

    private void onNewData(IPureAdapter<R> adapter, List<R> data) {
        getAdapterStrategy().onNewData(adapter, data);
        if ( mEventListener !=null){
            //noinspection unchecked
            mEventListener.onNewData(data);
        }
    }

    private void onInitData(IPureAdapter<R> adapter, List<R> data) {
        getAdapterStrategy().onInitData(adapter, data);
        if ( mEventListener !=null){
            //noinspection unchecked
            mEventListener.onInitData(data);
        }
    }

    private void onNoMoreData() {
        if ( mEventListener !=null){
            mEventListener.onNoMoreData();
        }
    }

    private void onNoNewData() {
        if ( mEventListener !=null){
            mEventListener.onNoNewData();
        }
    }

    private void onInitialize() {
        if ( mEventListener !=null){
            mEventListener.onInitialize();
        }
    }

    @Override
    public void onPartialList(IPullViewModule pullViewModule, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            onInitData(adapter, data);
        } else if (pageInfo.tempPage == pageInfo.currentPage) {//refresh
            onNewData(adapter, data);
        } else {
            onPartialMoreData(adapter,data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void onFullList(IPullViewModule pullViewModule, List<R> data, IPureAdapter<R> adapter, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            onInitData(adapter, data);
        } else if (pageInfo.tempPage == pageInfo.currentPage) {//refresh
            onNewData(adapter, data);
        } else {
            onFullMoreData(adapter, data);
        }
        pageInfo.currentPage = pageInfo.tempPage;
    }

    @Override
    public void onEmptyList(IPullViewModule pullViewModule, PageInfo pageInfo) {
        if (pageInfo.currentPage == -1) {
            pullViewModule.onEmpty();
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
    public void onException(IPullViewModule pullViewModule, ApiException exception, IPureAdapter<R> adapter, PageInfo pageInfo) {
        adapter.clearAllItems();
        pageInfo.currentPage = pageInfo.tempPage = -1;
    }

    public void setEventListener(EventListener eventListener) {
        this.mEventListener = eventListener;
    }

    public interface EventListener<R> {
        void onFullMoreData(List<R> data);
        void onPartialMoreData(List<R> data);
        void onNewData(List<R> data);
        void onInitData(List<R> data);
        void onNoMoreData();
        void onNoNewData();
        void onInitialize();
    }

    public static class SimpleEventListener<R> implements EventListener<R> {

        @Override
        public void onFullMoreData(List<R> data) {

        }

        @Override
        public void onPartialMoreData(List<R> data) {

        }

        @Override
        public void onNewData(List<R> data) {

        }

        @Override
        public void onInitData(List<R> data) {

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
