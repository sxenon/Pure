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

package com.sxenon.pure.core.viewholder.filler.single;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.result.filler.ISingleResultFiller;
import com.sxenon.pure.core.viewholder.filler.IFillPageStrategy;
import com.sxenon.pure.core.viewholder.filler.IFillerViewHolder;

/**
 * IFillPageStrategyForSingle
 * Created by Sui on 2017/9/3.
 */

public interface IFillPageStrategyForSingle<R> extends IFillPageStrategy {
    void processSingle(IFillerViewHolder fillerViewHolder, R data, ISingleResultFiller<R> singleResultFiller, PageInfo pageInfo);

    void processEmptySingle(IFillerViewHolder fillerViewHolder, PageInfo pageInfo);

    void onException(IFillerViewHolder fillerViewHolder, ApiException exception, ISingleResultFiller<R> singleResultFiller, PageInfo pageInfo);

}
