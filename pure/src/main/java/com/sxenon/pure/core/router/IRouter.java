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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.IViewModule;

import java.util.List;

/**
 * Treat Activity & Fragment as router
 * Created by Sui on 2016/11/20.
 */

public interface IRouter<P extends PureRouterPresenter> extends IViewModule<P>{

    void startActivity(Intent intent);

    void startActivity(Intent intent, Bundle options);

    void startActivityForResult(Intent intent, int requestCode);

    void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options);

    Activity getActivityCompact();

    void requestPermissionsCompact(@NonNull String[] permissions, int requestCode);

    boolean shouldShowRequestPermissionRationale(String permission);

    void saveEventList(List<Event> eventList);

    P bindPresenter();

    RouterType getRouterType();

    void clearFragmentBackStackImmediate();

    enum RouterType {
        ACTIVITY,
        COMPACT_ACTIVITY,
        SUPPORT_FRAGMENT,
        FRAGMENT,
    }
}
