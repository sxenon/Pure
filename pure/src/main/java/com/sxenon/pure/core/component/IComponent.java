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

package com.sxenon.pure.core.component;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxenon.pure.core.component.impl.PureComponentVisitorAsPresenter;
import com.sxenon.pure.core.mvp.IView;
import com.sxenon.pure.core.viewmodule.IViewModule;

/**
 * Treat Activity & Fragment as router
 * Architecture: IComponent-->{@link IViewModule} with Business logic/ViewGroup-->View/Adapter
 * Created by Sui on 2016/11/20.
 */

public interface IComponent<P extends PureComponentVisitorAsPresenter> extends IView<P> {

    void startActivity(Intent intent);

    void startActivity(Intent intent, Bundle options);

    void startActivityForResult(Intent intent, int requestCode);

    void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options);

    /**
     * @return Return false, if the router is instance of FragmentActivity and its supportFragment start activity,otherwise true.
     */
    boolean startActivityForResultBySelf(int requestCode);

    Activity getActivityCompact();

    void requestPermissionsCompact(@NonNull String[] permissions, int requestCode, Runnable runnable, boolean forceAccepting);

    /**
     * @return Return false if the router is instance of FragmentActivity and its supportFragment request the permission,otherwise true.
     */
    boolean requestPermissionsBySelf(int requestCode);

    boolean shouldShowRequestPermissionRationale(String permission);

    P bindPresenter();

    @Override
    P getPresenter();

    RouterType getRouterType();

    enum RouterType {
        COMPACT_ACTIVITY,
        SUPPORT_FRAGMENT,
    }

    ViewModelProvider getViewModelProvider();

    ViewModelProvider getViewModelProvider(ViewModelProvider.Factory factory);
}
