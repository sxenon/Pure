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
 * RefreshFillPageStrategy
 * Created by Sui on 2017/8/6.
 */

public class RefreshFillPageStrategy<R> extends BaseFillPageStrategyForSingle<R> {
    private FillEventListener mFillEventListener;

    private void onInitialize() {
        if (mFillEventListener!=null){
            mFillEventListener.onInitialize();
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
    public void processSingle(IFillerViewHolder fillerViewHolder, R data, ISingleResultFiller<R> singleResultFiller, PageInfo pageInfo) {
        singleResultFiller.onSingleDataFetched(data);
        pageInfo.tempPage = pageInfo.currentPage = 0;
        if (mFillEventListener!=null){
            //noinspection unchecked
            mFillEventListener.onInitDataFetched(data);
        }
    }

    @Override
    public void processEmptySingle(IFillerViewHolder fillerViewHolder, PageInfo pageInfo) {
        fillerViewHolder.onEmpty();
        pageInfo.tempPage = pageInfo.currentPage =-1;
    }

    public void setFillEventListener(FillEventListener fillEventListener) {
        this.mFillEventListener = fillEventListener;
    }

    public interface FillEventListener<R> {
        void onInitialize();
        void onInitDataFetched(R data);
    }
}
