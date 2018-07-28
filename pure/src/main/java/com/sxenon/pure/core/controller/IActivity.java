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

package com.sxenon.pure.core.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sxenon.pure.core.controller.impl.AbsControllerVisitorAsPresenter;

/**
 * Interface for Activity
 * Created by Sui on 2017/1/2.
 */

public interface IActivity<P extends AbsControllerVisitorAsPresenter> extends IController<P> {
    void initView(@Nullable Bundle savedInstanceState);

    /**
     * {@link com.sxenon.pure.core.global.ActivityHistoryManager#finishBackgroundActivitiesIfNeed(int)}
     *
     * @param finishForWhat finish background activities for what
     * @return should keep or not
     */
    boolean shouldKeepWhenInBackground(int finishForWhat);

    void finish();

    boolean isFinishing();
}
