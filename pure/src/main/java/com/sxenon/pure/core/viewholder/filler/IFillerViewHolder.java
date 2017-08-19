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

package com.sxenon.pure.core.viewholder.filler;

import com.sxenon.pure.core.adapter.IPureAdapter;
import com.sxenon.pure.core.viewholder.IViewHolder;
import com.sxenon.pure.core.result.IFetchListResultHandler;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

/**
 * Interface for FillerViewHolder
 * Created by Sui on 2017/8/4.
 */

public interface IFillerViewHolder<R> extends IFetchSingleResultHandler<R>, IFetchListResultHandler<R>, IViewHolder {
    void onEmpty();
    void setAdapter(IPureAdapter<R> adapter);
    void setFetchSingleResultHandler(IFetchSingleResultHandler<R> fetchSingleResultHandler);
}
