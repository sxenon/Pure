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
import com.sxenon.pure.core.mvp.root.RouterLifecycleCallbacks;

import java.util.ArrayList;
import java.util.List;

/**
 * Inspired by Application.ActivityLifecycleCallbacks
 * Created by Sui on 2017/1/15.
 */

public class RouterLifecycleCallbackDispatcher {
    private final ArrayList<RouterLifecycleCallbacks> mRouterLifecycleCallbacks =
            new ArrayList<>();

    public void registerRouterLifecycleCallbacks(RouterLifecycleCallbacks callback) {
        synchronized (mRouterLifecycleCallbacks) {
            mRouterLifecycleCallbacks.add(callback);
        }
    }

    public void unregisterRouterLifecycleCallbacks(RouterLifecycleCallbacks callback) {
        synchronized (mRouterLifecycleCallbacks) {
            mRouterLifecycleCallbacks.remove(callback);
        }
    }

    /* package */ void dispatchRouterCreated(IRouterVisitor routerVisitor, List<Event> savedEventList) {
        Object[] callbacks = collectRouterLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((RouterLifecycleCallbacks) callback).onRouterCreated(routerVisitor,
                        savedEventList);
            }
        }
    }

    /* package */ void dispatchRouterResumed(IRouterVisitor routerVisitor) {
        Object[] callbacks = collectRouterLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((RouterLifecycleCallbacks) callback).onRouterResumed(routerVisitor);
            }
        }
    }

    /* package */ void dispatchRouterPaused(IRouterVisitor routerVisitor) {
        Object[] callbacks = collectRouterLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((RouterLifecycleCallbacks) callback).onRouterPaused(routerVisitor);
            }
        }
    }

    /* package */ void dispatchRouterStopped(IRouterVisitor routerVisitor) {
        Object[] callbacks = collectRouterLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((RouterLifecycleCallbacks) callback).onRouterStopped(routerVisitor);
            }
        }
    }

    /* package */ void dispatchRouterDestroyed(IRouterVisitor routerVisitor) {
        Object[] callbacks = collectRouterLifecycleCallbacks();
        if (callbacks != null) {
            for (Object callback : callbacks) {
                ((RouterLifecycleCallbacks) callback).onRouterDestroyed(routerVisitor);
            }
        }
    }

    private Object[] collectRouterLifecycleCallbacks() {
        Object[] callbacks = null;
        synchronized (mRouterLifecycleCallbacks) {
            if (mRouterLifecycleCallbacks.size() > 0) {
                callbacks = mRouterLifecycleCallbacks.toArray();
            }
        }
        return callbacks;
    }
}
