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

package com.sxenon.pure.core.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Based on https://github.com/Jacksgong/JKeyboardPanelSwitch.Thanks
 * Created by Sui on 2016/12/27.
 */

public class PureKeyboardUtil {
    private static final String TAG = "PureKeyboardUtil";

    public static void showKeyboardSafe(final EditText editText) {
        if (EditorInfo.TYPE_NULL != editText.getInputType()) {
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showKeyboard(editText);
                }
            }, 100);
        } else {
            Log.w(TAG, "KeyBoard is disabled for this editText");
        }
    }

    /**
     * Not always work，use {@link #showKeyboardSafe(EditText)} if possible.
     * 1、View得是能够接受键盘输入的，但不一定是EditText，see http://www.jianshu.com/p/2b483febec72 {@link View#onCheckIsTextEditor()} {@link View#onCreateInputConnection(EditorInfo)}
     * 2、当前布局必须已经完成加载，如果还未绘制完成，则showSoftInput()方法不起作用，比方说在Activity的onCreate()，onResume()，onAttachedToWindow()中
     */
    public static void showKeyboard(final View view) {
        view.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    public static void hideKeyboard(final View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

}
