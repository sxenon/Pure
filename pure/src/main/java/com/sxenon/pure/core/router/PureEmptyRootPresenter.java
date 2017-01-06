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

package com.sxenon.pure.core.router;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;

import java.util.List;

/**
 * PureEmptyRootPresenter
 * Created by Sui on 2016/12/15.
 */

public class PureEmptyRootPresenter extends PureRootPresenter<BaseRootViewModule> {
    public PureEmptyRootPresenter(BaseRootViewModule viewModule) {
        super(viewModule);
    }

    @Override
    public List<Event> getEventForSave() {
        return null;
    }
}
