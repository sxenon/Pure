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

package com.sxenon.pure.core.mvp.root;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.ILifecycle;
import com.sxenon.pure.core.mvp.sub.BaseSubPresenter;
import com.sxenon.pure.core.mvp.sub.BaseSubViewModule;

import java.util.List;

/**
 * Include several {@link BaseSubPresenter}，with its related {@link BaseSubViewModule}
 * Don`t use it directly,use PureRootPresenter instead.
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseRootPresenter<VM extends BaseRootViewModule> extends BasePresenter<VM> implements ILifecycle {
    private RootPresenterEvent currentEvent;

    public BaseRootPresenter(VM viewModule) {
        super(viewModule);
    }

    @Override
    public void onCreate(List<Event> savedEventList) {
        currentEvent = RootPresenterEvent.CREATE;
        RxBus.get().register(this);
    }

    @Override
    public void onResume() {
        currentEvent = RootPresenterEvent.RESUME;
    }

    @Override
    public void onPause() {
        currentEvent = RootPresenterEvent.PAUSE;
    }

    @Override
    public void onStop() {
        currentEvent = RootPresenterEvent.STOP;
    }

    @Override
    public void onDestroy() {
        currentEvent = RootPresenterEvent.DESTROY;
        RxBus.get().unregister(this);
    }

    public RootPresenterEvent getCurrentEvent() {
        return currentEvent;
    }

    public abstract List<Event> getEventForSave();

}
