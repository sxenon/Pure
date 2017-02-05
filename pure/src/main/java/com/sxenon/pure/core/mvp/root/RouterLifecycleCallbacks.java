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

package com.sxenon.pure.core.mvp.root;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.ILifecycle;
import com.sxenon.pure.core.router.IRouterVisitor;

import java.util.List;

/**
 * Inspired by Application.ActivityLifecycleCallbacks
 * Created by Sui on 2017/1/15.
 */

public interface RouterLifecycleCallbacks {
    void onRouterCreated(IRouterVisitor routerVisitor, List<Event> savedEventList);

    void onRouterResumed(IRouterVisitor routerVisitor);

    void onRouterPaused(IRouterVisitor routerVisitor);

    void onRouterStopped(IRouterVisitor routerVisitor);

    void onRouterDestroyed(IRouterVisitor routerVisitor);
}
