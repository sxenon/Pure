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

package com.sxenon.pure.core.request.select.base;

import java.util.Collections;
import java.util.List;

/**
 * BaseSelectStrategy
 * Created by Sui on 2017/8/29.
 */

public abstract class BaseSelectStrategy implements ISelectStrategy {
    @Override
    public void onAllOptionsReversed(List<Boolean> selectedFlags) {
        int size = selectedFlags.size();
        for (int position = 0; position < size; position++) {
            selectedFlags.set(position, !selectedFlags.get(position));
        }
    }

    @Override
    public void onAllOptionsSelected(List<Boolean> selectedFlags) {
        Collections.fill(selectedFlags, true);
    }

    @Override
    public void onAllOptionsUnSelected(List<Boolean> selectedFlags) {
        Collections.fill(selectedFlags, false);
    }

    @Override
    public void onOptionAppended(List<Boolean> selectedFlags) {
        selectedFlags.add(false);
    }

    @Override
    public void onOptionRemoved(List<Boolean> selectedFlags, int position) {
        selectedFlags.remove(position);
    }
}
