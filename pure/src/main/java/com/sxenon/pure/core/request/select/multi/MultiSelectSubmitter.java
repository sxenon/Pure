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

package com.sxenon.pure.core.request.select.multi;

import com.sxenon.pure.core.request.select.BaseSelectSubmitter;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * MultiSelectSubmitter
 * Created by Sui on 2017/8/20.
 */

public class MultiSelectSubmitter extends BaseSelectSubmitter {

    public MultiSelectSubmitter(List<Boolean> selectedFlags) {
        super(new MultiSelectStrategy(),selectedFlags);
    }

    public void onAllOptionsSelected(){
        Collections.fill(getSelectedFlags(),true);
    }

    public void onAllOptionsUnSelected(){
        Collections.fill(getSelectedFlags(),false);
    }

    public void onAllOptionsReversed(){
        List<Boolean> selectedFlags=getSelectedFlags();
        int size=selectedFlags.size();
        for (int position=0;position<size;position++){
            selectedFlags.set(position,!selectedFlags.get(position));
        }
    }

    public void onSelectedOptionsDeleted(){
        List<Boolean> selectedFlags=getSelectedFlags();
        Iterator<Boolean> iterator=selectedFlags.iterator();
        while (iterator.hasNext()){
            if (iterator.next()){
                iterator.remove();
            }
        }
    }
}
