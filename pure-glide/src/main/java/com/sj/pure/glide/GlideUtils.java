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

package com.sj.pure.glide;

import android.app.Activity;
import android.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.sxenon.pure.core.router.IRouter;

/**
 * Helper for Glide with IRouter
 * Created by Sui on 2016/12/19.
 */

public class GlideUtils {
    public static RequestManager with(IRouter router) {
        switch (router.getRouterType()) {
            case ACTIVITY:
            case COMPACT_ACTIVITY:
                return Glide.with((Activity) router);
            case FRAGMENT:
                return Glide.with((Fragment) router);
            case SUPPORT_FRAGMENT:
                return Glide.with((android.support.v4.app.Fragment) router);
            default:
                //Won`t happen
                throw new IllegalArgumentException("Glide.with don`t support:" + router.getClass());
        }
    }
}
