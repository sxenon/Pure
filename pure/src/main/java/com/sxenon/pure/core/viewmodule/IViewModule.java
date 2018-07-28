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

package com.sxenon.pure.core.viewmodule;

import android.content.Context;

/**
 * Does not equal to ViewGroup,nothing about UI
 * <p>
 * Created by Sui on 2016/11/20.
 */

public interface IViewModule {
    /**
     * 理论上应该持有对IController的引用，但出于以下原因，不强制持有，需要时自行添加
     * 1、降低ViewHolder 模块的使用门槛，使之能够独立于MVP使用
     * 2、一些简单的页面不需要IController的功能
     */
    Context getContext();

    //IController getController();
}
