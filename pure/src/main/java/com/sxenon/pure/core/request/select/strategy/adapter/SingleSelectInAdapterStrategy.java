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

package com.sxenon.pure.core.request.select.strategy.adapter;

import com.sxenon.pure.core.request.select.strategy.SingleSelectStrategy;

import java.util.List;

/**
 * Concrete adapter based
 * Created by Sui on 2017/8/28.
 */

public class SingleSelectInAdapterStrategy extends SingleSelectStrategy implements ISelectInAdapterStrategy {
    @Override
    public void onOptionSelected(List<Boolean> selectedFlags, int position, IPureAdapterWithSelect adapter) {
        int lastSelectedPosition = selectedFlags.indexOf(true);
        if (lastSelectedPosition == position) {
            return;
        }

        if (lastSelectedPosition >= 0) {
            selectedFlags.set(lastSelectedPosition, false);
            adapter.notifySelectChange(lastSelectedPosition);
        }
        selectedFlags.set(position, true);
    }

    @Override
    public void onOptionUnSelected(List<Boolean> selectedFlags,int position, IPureAdapterWithSelect adapter) {
        onOptionUnSelected(selectedFlags,position);
        //adapter.notifySelectChange(position);
    }
}
