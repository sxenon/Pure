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

package com.sxenon.pure.core.request.select.single;

import com.sxenon.pure.core.request.select.SelectStrategy;

import java.util.List;

/**
 * Single strategy implement for SelectStrategy
 * Created by Sui on 2017/1/12.
 */

public class SingleSelectStrategy implements SelectStrategy {
    private int lastSelectedPosition = -1;

    @Override
    public void onOptionSelected(List<Boolean> selectedFlags, int position) {
        if (lastSelectedPosition == position) {
            return;
        }
        if (lastSelectedPosition >= 0) {
            selectedFlags.set(lastSelectedPosition, false);
        }
        selectedFlags.set(position, true);
        lastSelectedPosition = position;
    }

    @Override
    public void onOptionUnSelected(List<Boolean> selectedFlags, int position) {
        selectedFlags.set(position, false);
    }
}
