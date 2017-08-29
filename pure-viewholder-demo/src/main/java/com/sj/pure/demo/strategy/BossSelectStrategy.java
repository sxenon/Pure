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

package com.sj.pure.demo.strategy;

import com.sxenon.pure.core.request.select.strategy.ISelectStrategy;

import java.util.Collections;
import java.util.List;

/**
 * Boss直聘上 区域选择的效果实现
 * Created by Sui on 2017/8/29.
 */

public class BossSelectStrategy implements ISelectStrategy {

    @Override
    public void onOptionSelected(List<Boolean> selectedFlags, int position) {
        if (position == 0){ //选中整体
            Collections.fill(selectedFlags,false);
        }
        selectedFlags.set(position,true);
    }

    @Override
    public void onOptionUnSelected(List<Boolean> selectedFlags, int position) {
        if (position != 0){ //整体不能取消
            selectedFlags.set(position,false);
            if (!selectedFlags.contains(true)){
                selectedFlags.set(0,true);
            }
        }
    }
}
