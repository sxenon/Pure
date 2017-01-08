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

package com.sxenon.pure.core.component.adapter.abs;

import com.sxenon.pure.core.component.adapter.IItemViewTypeEntity;

/**
 * itemViewType --- resourceId --- viewHolder
 * Created by Sui on 2016/12/25.
 */

public class PureAbsListItemViewTypeEntity implements IItemViewTypeEntity {
    private final int resourceId;
    private final Class<? extends PureAbsViewHolder> viewHolderClass;

    public PureAbsListItemViewTypeEntity(int resourceId, Class<? extends PureAbsViewHolder> viewHolderClass) {
        this.resourceId = resourceId;
        this.viewHolderClass = viewHolderClass;
    }

    public Class<? extends PureAbsViewHolder> getViewHolderClass() {
        return viewHolderClass;
    }

    public int getResourceId() {
        return resourceId;
    }
}
