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

package com.sxenon.pure.core.component;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sxenon.pure.core.component.impl.PureComponentVisitorAsPresenter;

/**
 * Interface for Fragment
 * Created by Sui on 2017/1/2.
 */

public interface IFragment<P extends PureComponentVisitorAsPresenter> extends IComponent<P> {
    void initView(View view, @Nullable Bundle savedInstanceState);
}
