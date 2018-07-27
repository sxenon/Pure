/*
 * Copyright (c) 2018  sxenon
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

import android.content.Context;
import android.net.Uri;

/**
 *
 * 参考ARouter
 */
public interface IRouter {
    /**
     * Build the roadmap, draw a postcard.
     *
     * @param path Where you go.
     */
    IPostcard build(String path);

    /**
     * Build the roadmap, draw a postcard.
     *
     * @param url the path
     */
    IPostcard build(Uri url);

    /**
     * Launch the navigation by type
     *
     * @param service interface of service
     * @param <T>     return type
     * @return instance of service
     */
    <T> T navigation(Class<? extends T> service);

    /**
     * Launch the navigation.
     *
     * @param mContext    .
     * @param postcard    .
     * @param requestCode Set for startActivityForResult
     * @param callback    cb
     */
    Object navigation(Context mContext, IPostcard postcard, int requestCode, INavigationCallback callback);
}
