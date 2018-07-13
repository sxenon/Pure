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

package com.sxenon.pure.core.viewmodule.navigation.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.sxenon.pure.core.Action1;
import com.sxenon.pure.core.Func1;
import com.sxenon.pure.core.util.Preconditions;
import com.sxenon.pure.core.util.PureKeyboardUtil;



/*
 * Base implement for ISearchViewModule
 * Created by Sui on 2016/11/12.
 * DataSource from self
 */

public class BaseSearchViewModule implements ISearchViewModule {
    private final EditText mEditText;
    private Action1<View> mOnCancel;
    private Func1<CharSequence, Boolean> mSearchAction;
    private final View mCancelView;

    public BaseSearchViewModule(@NonNull EditText editText, @NonNull Func1<CharSequence, Boolean> searchAction) {
        this(editText, searchAction, null, null);
    }

    public BaseSearchViewModule(@NonNull EditText editText, @NonNull Func1<CharSequence, Boolean> searchAction, @Nullable View cancelView, @Nullable Action1<View> onCancel) {
        mEditText = editText;
        mCancelView = cancelView;
        mOnCancel = onCancel;
        mSearchAction = searchAction;

        //IME_ACTION_SEARCH：work when
        mEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);//or android:singleLine="true" or android:inputType="text"
        mEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return actionId == EditorInfo.IME_ACTION_SEARCH && performSearch();
            }
        });

        if (mCancelView != null) {
            mCancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Preconditions.checkNotNull(mOnCancel, "").call(v);
                }
            });
        }
    }

    public EditText getEditText() {
        return mEditText;
    }

    public View getCancelView() {
        return mCancelView;
    }

    public void setCancelAction(Action1<View> onCancel) {
        mOnCancel = onCancel;
    }

    public void setSearchAction(@NonNull Func1<CharSequence, Boolean> searchAction) {
        mSearchAction = searchAction;
    }

    @Override
    public void setKeyword(CharSequence keyword) {
        mEditText.setText(keyword);
    }

    public boolean performSearch() {
        return mSearchAction.call(mEditText.getText().toString());
    }

    public void hideKeyBoard() {
        PureKeyboardUtil.hideKeyboard(mEditText);
    }

    @Override
    public Context getContext() {
        return mEditText.getContext();
    }
}
