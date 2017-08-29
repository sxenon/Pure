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

import com.sxenon.pure.core.request.select.strategy.adapter.IPureAdapterWithSelect;
import com.sxenon.pure.core.request.select.strategy.adapter.ISelectInAdapterStrategy;

import java.util.Collections;
import java.util.List;

/**
 * Boss直聘上 区域选择的效果实现
 * Created by Sui on 2017/8/29.
 */

public class BossSelectInAdapterStrategy extends BossSelectStrategy implements ISelectInAdapterStrategy {

    private int selectedCount =0;

    private SelectedCountChangedListener selectedCountChangedListener;

    @Override
    public void onOptionSelected(List<Boolean> selectedFlags, int position, IPureAdapterWithSelect adapter) {
        if (selectedFlags.get(position)){
            return;
        }

        if (position == 0){ //选中整体
            if (selectedCount != 0){
                Collections.fill(selectedFlags,false);
                selectedFlags.set(position,true);
                adapter.notifySelectReset();
            }
            selectedCount = 1;
        }else {
            selectedCount += 1;
        }
        if (selectedCountChangedListener!=null){
            selectedCountChangedListener.onSelectedCountChanged(selectedCount,selectedFlags.get(0));
        }
    }

    @Override
    public void onOptionUnSelected(List<Boolean> selectedFlags, int position, IPureAdapterWithSelect adapter) {
        if (position != 0){ //整体不能取消
            selectedFlags.set(position,false);
            if (selectedCount == 1){
                selectedFlags.set(0,true);
                adapter.notifySelectChange(0);
            }else {
                selectedCount -= 1;
            }
            if (selectedCountChangedListener!=null){
                selectedCountChangedListener.onSelectedCountChanged(selectedCount,selectedFlags.get(0));
            }
        }
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCountChangedListener(SelectedCountChangedListener selectedCountChangedListener) {
        this.selectedCountChangedListener = selectedCountChangedListener;
    }

    public interface SelectedCountChangedListener{
        void onSelectedCountChanged(int selectCount,boolean firstSelected);
    }
}
