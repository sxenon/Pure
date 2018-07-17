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

package com.sxenon.pure.core.viewmodule.pull.single.dummy;

import com.sxenon.pure.core.response.ISingleResponseHandler;
import com.sxenon.pure.core.viewmodule.pull.IPullViewModule;
import com.sxenon.pure.core.viewmodule.pull.single.BaseSingleStrategy;

/**
 * DummySingleStrategy
 * Created by Sui on 2017/9/3.
 */

public class DummySingleStrategy<R> extends BaseSingleStrategy<R> {

    @Override
    public void onPullDown(PageInfo pageInfo) {

    }

    @Override
    public void onPullUp(PageInfo pageInfo) {

    }

    @Override
    public void onSingle(IPullViewModule pullViewHolder, R data, ISingleResponseHandler<R> singleResultHandler, PageInfo pageInfo) {
        singleResultHandler.onSingleResponse(data);
    }

    @Override
    public void onEmpty(IPullViewModule pullViewModule, PageInfo pageInfo) {
        pullViewModule.onEmpty();
    }
}
