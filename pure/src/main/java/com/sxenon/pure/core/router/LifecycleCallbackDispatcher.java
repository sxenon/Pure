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

package com.sxenon.pure.core.router;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;
import com.sxenon.pure.core.mvp.root.RootPresenterLifecycleCallbacks;

import java.util.ArrayList;
import java.util.List;

/**
 * Inspired by Application.ActivityLifecycleCallbacks
 * Created by Sui on 2017/1/15.
 */

public class LifecycleCallbackDispatcher {
    private final ArrayList<RootPresenterLifecycleCallbacks> mRootPresenterLifecycleCallbacks =
            new ArrayList<>();

    public void registerRootPresenterLifecycleCallbacks(RootPresenterLifecycleCallbacks callback) {
        synchronized (mRootPresenterLifecycleCallbacks) {
            mRootPresenterLifecycleCallbacks.add(callback);
        }
    }

    public void unregisterRootPresenterLifecycleCallbacks(RootPresenterLifecycleCallbacks callback) {
        synchronized (mRootPresenterLifecycleCallbacks) {
            mRootPresenterLifecycleCallbacks.remove(callback);
        }
    }

    /* package */ void dispatchRootPresenterCreated(BaseRootPresenter rootPresenter, List<Event> savedEventList) {
        Object[] callbacks = collectRootPresenterLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((RootPresenterLifecycleCallbacks) callback).onRootPresenterCreated(rootPresenter,
                        savedEventList);
            }
        }
    }

    /* package */ void dispatchRootPresenterResumed(BaseRootPresenter rootPresenter) {
        Object[] callbacks = collectRootPresenterLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((RootPresenterLifecycleCallbacks) callback).onRootPresenterResumed(rootPresenter);
            }
        }
    }

    /* package */ void dispatchRootPresenterPaused(BaseRootPresenter rootPresenter) {
        Object[] callbacks = collectRootPresenterLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((RootPresenterLifecycleCallbacks) callback).onRootPresenterPaused(rootPresenter);
            }
        }
    }

    /* package */ void dispatchRootPresenterStopped(BaseRootPresenter rootPresenter) {
        Object[] callbacks = collectRootPresenterLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((RootPresenterLifecycleCallbacks) callback).onRootPresenterStopped(rootPresenter);
            }
        }
    }

    /* package */ void dispatchRootPresenterDestroyed(BaseRootPresenter rootPresenter) {
        Object[] callbacks = collectRootPresenterLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((RootPresenterLifecycleCallbacks) callback).onRootPresenterDestroyed(rootPresenter);
            }
        }
    }

    private Object[] collectRootPresenterLifecycleCallbacks() {
        Object[] callbacks = null;
        synchronized (mRootPresenterLifecycleCallbacks) {
            if (mRootPresenterLifecycleCallbacks.size() > 0) {
                callbacks = mRootPresenterLifecycleCallbacks.toArray();
            }
        }
        return callbacks;
    }
}
