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

package com.sxenon.pure.core.component.submitter.select;

import com.sxenon.pure.core.component.submitter.IListDataSubmitter;

import java.util.List;

/**
 * For adapter with select,delete,insert
 * Created by Sui on 2016/11/18.
 */

public interface ISelectGroup<T> extends IListDataSubmitter<T> {
    int SELECT_MODE_SINGLE=0;
    int SELECT_MODE_MULTI=1;

    void addOption(T data);
    void insertFirstOption(T data);
    void removeOption(T data);
    void removeOption(int position);
    void setOptions(List<T> data);
    void onOptionSelected(int position);
    void onOptionUnSelected(int position);
}
