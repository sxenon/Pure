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

package com.sxenon.pure.core.viewmodule.navigation.next;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxenon.pure.core.Action1;


/**
 * Base implement for INextViewModule
 * Created by Sui on 2016/11/11.
 */

public class BaseNextViewModule implements INextViewModule {
    private final ImageView mNextIcon;
    private final TextView mNextText;

    public BaseNextViewModule(ImageView nextIcon, TextView nextText, final Action1<View> onNext) {
        mNextIcon = nextIcon;
        mNextText = nextText;
        ((ViewGroup) mNextText.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNext.call(v);
            }
        });
    }

    public ImageView getNextIcon() {
        return mNextIcon;
    }

    public TextView getTextView() {
        return mNextText;
    }

    @Override
    public Context getContext() {
        return mNextText.getContext();
    }
}
