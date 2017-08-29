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
import com.sxenon.pure.core.request.select.strategy.MultiSelectStrategy;

/**
 * Concrete adapter based
 * Created by Sui on 2017/8/28.
 */

public class MultiSelectInRvAdapterStrategy extends MultiSelectStrategy implements ISelectInRvAdapterStrategy {
    @Override
    public void onOptionSelected(int position, IPureAdapterWithSelect adapter) {
        onOptionSelected(position);
        //adapter.notifySelectChange(position);
    }

    @Override
    public void onOptionUnSelected(int position, IPureAdapterWithSelect adapter) {
        onOptionUnSelected(position);
        //adapter.notifySelectChange(position);
    }
}
