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

package com.sxenon.pure.core.viewmodule.pull.single;

import com.sxenon.pure.core.ApiException;
import com.sxenon.pure.core.viewmodule.pull.IPullStrategy;
import com.sxenon.pure.core.viewmodule.pull.IPullViewModule;

/**
 * ISingleStrategy
 * Created by Sui on 2017/9/3.
 */

public interface ISingleStrategy<R> extends IPullStrategy {
    void onSingle(IPullViewModule pullViewHolder, R data, PageInfo pageInfo);

    void onEmpty(IPullViewModule pullViewModule, PageInfo pageInfo);

    void onException(IPullViewModule pullViewModule, ApiException exception, PageInfo pageInfo);

}
