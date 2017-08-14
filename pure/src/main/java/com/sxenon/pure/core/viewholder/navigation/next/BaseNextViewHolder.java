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

package com.sxenon.pure.core.viewholder.navigation.next;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Base implement for INextViewHolder
 * Created by Sui on 2016/11/11.
 */

public class BaseNextViewHolder implements INextViewHolder {
    private final ImageView mNextIcon;
    private final TextView mNextText;

    public BaseNextViewHolder(ImageView nextIcon, TextView nextText) {
        mNextIcon = nextIcon;
        mNextText = nextText;
    }

    public ImageView getNextIcon() {
        return mNextIcon;
    }

    public TextView getTextView() {
        return mNextText;
    }

    @Override
    public void setNextDelegate(final NextDelegate nextDelegate) {
        ((ViewGroup) mNextText.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDelegate.onNext();
            }
        });
    }
}
