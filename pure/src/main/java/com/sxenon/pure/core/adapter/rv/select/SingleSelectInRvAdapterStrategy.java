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

package com.sxenon.pure.core.adapter.rv.select;

import com.sxenon.pure.core.adapter.IPureAdapterWithSelect;
import com.sxenon.pure.core.request.select.strategy.SingleSelectStrategy;

/**
 * Concrete adapter based
 * Created by Sui on 2017/8/28.
 */

public class SingleSelectInRvAdapterStrategy extends SingleSelectStrategy implements ISelectInRvAdapterStrategy {
    @Override
    public void onOptionSelected(int position, IPureAdapterWithSelect adapter) {
        int lastSelectedPosition = getLastSelectedPosition();
        if (getLastSelectedPosition() == position) {
            return;
        }

        onOptionSelected(position);
        if (lastSelectedPosition >= 0) {
            adapter.notifySelectChange(lastSelectedPosition);
        }
    }

    @Override
    public void onOptionUnSelected(int position, IPureAdapterWithSelect adapter) {
        onOptionUnSelected(position);
        //adapter.notifySelectChange(position);
    }
}
