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

package com.sxenon.pure.core.adapter.abs.select;

import com.sxenon.pure.core.adapter.abs.PureAbsListAdapter;
import com.sxenon.pure.core.request.select.adapter.abs.ISelectInAbsListAdapterStrategy;

import java.util.List;

/**
 * ISelectInPureAbsListAdapterStrategy
 * Created by Sui on 2017/8/29.
 */

public interface ISelectInPureAbsListAdapterStrategy<T> extends ISelectInAbsListAdapterStrategy {
    void onOptionAppended(List<Boolean> selectedFlags, T data, PureAbsListAdapter<T> adapter);

    void onOptionRemoved(List<Boolean> selectedFlags, int position, PureAbsListAdapter<T> adapter);

    void onSelectedOptionsRemoved(List<Boolean> selectedFlags,PureAbsListAdapter<T> adapter);
}
