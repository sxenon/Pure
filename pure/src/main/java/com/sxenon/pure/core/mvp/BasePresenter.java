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

package com.sxenon.pure.core.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.router.IRouter;

/**
 * Base implement for IPresenter
 * Created by Sui on 2017/1/5.
 */

public abstract class BasePresenter<V extends IView> implements IPresenter<V> {
    private final V mView;
    private final Context mContext;
    private final IRouter mRouter;

    public BasePresenter(V view) {
        mView = view;
        mContext = mView.getContext();
        mRouter = mView.getRouter();
    }

    @Override
    public V getView() {
        return mView;
    }

    @NonNull
    @Override
    public Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public IRouter getRouter() {
        return mRouter;
    }
}
