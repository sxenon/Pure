/*
 * Copyright (c) 2017 sxenon
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

package com.sxenon.pure.core.mvp.sub;

import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;

/**
 * Optional,for those complex {@link BaseRootPresenter}s
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseSubPresenter<VM extends BaseSubViewModule> extends BasePresenter<VM> {
    private final BaseRootPresenter mRootPresenter;

    public BaseSubPresenter(VM viewModule, BaseRootPresenter rootPresenter) {
        super(viewModule);
        mRootPresenter = rootPresenter;
    }

    public BaseRootPresenter getRootPresenter() {
        return mRootPresenter;
    }
}
