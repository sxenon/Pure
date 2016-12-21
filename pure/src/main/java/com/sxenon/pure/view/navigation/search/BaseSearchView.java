package com.sxenon.pure.view.navigation.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;


import com.sxenon.pure.util.Preconditions;

import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Base implement for ISearchView
 * Created by Sui on 2016/11/12.
 */

public class BaseSearchView implements ISearchView {
    private final EditText mEditText;
    private Action0 mOnCancel;
    private Func1<CharSequence,Boolean> mSearchAction;
    private final View mCancelView;

    public BaseSearchView(@NonNull EditText editText, @NonNull Func1<CharSequence,Boolean> searchAction) {
        this(editText, searchAction, null, null);
    }

    public BaseSearchView(@NonNull EditText editText, @NonNull Func1<CharSequence,Boolean> searchAction, @Nullable View cancelView, @Nullable Action0 onCancel) {
        mEditText = editText;
        mCancelView = cancelView;
        setCancelAction(onCancel);
        setSearchAction(searchAction);

        //IME_ACTION_SEARCH：work when
        mEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);//or android:singleLine="true" or android:inputType="text"
        mEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = mEditText.getText().toString();
                    Preconditions.checkNotNull(mSearchAction, "");
                    return  mSearchAction.call(keyword);
                }
                return false;
            }
        });

        if (mCancelView != null) {
            mCancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Preconditions.checkNotNull(mOnCancel, "");
                    mOnCancel.call();
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

    @Override
    public void setCancelAction(Action0 onCancel) {
        mOnCancel = onCancel;
    }

    @Override
    public void setSearchAction(Func1<CharSequence, Boolean> searchAction) {
        mSearchAction=searchAction;
    }

    @Override
    public void resetKeyword(CharSequence keyword) {
        mEditText.setText(keyword);
        mEditText.setSelection(keyword.toString().length());
    }

    public void performSearch(){
        mSearchAction.call(mEditText.getText().toString());
    }
}
