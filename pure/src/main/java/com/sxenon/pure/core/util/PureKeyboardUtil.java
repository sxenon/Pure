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
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.sxenon.pure.core.router.PureRouterPresenter;

import cn.dreamtobe.kpswitch.IPanelHeightTarget;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import rx.Observable;
import rx.Observer;

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
        KeyboardUtil.showKeyboard(view);
    }

    public static void hideKeyboard(final View view) {
        KeyboardUtil.hideKeyboard(view);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static void attach(PureRouterPresenter rootPresenter, IPanelHeightTarget target, KeyboardUtil.OnKeyboardShowingListener listener) {
        final Activity activity = rootPresenter.getRouter().getActivityCompact();
        final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = KeyboardUtil.attach(activity, target, listener);
        //noinspection unchecked
        Observable.never().compose(rootPresenter.autoComplete()).subscribe(new Observer() {
            @Override
            public void onCompleted() {
                KeyboardUtil.detach(activity, onGlobalLayoutListener);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    public static void attach(PureRouterPresenter rootPresenter, IPanelHeightTarget target) {
        attach(rootPresenter, target, null);
    }

    public static void attach(PureRouterPresenter rootPresenter, KeyboardUtil.OnKeyboardShowingListener listener) {
        attach(rootPresenter, new IPanelHeightTarget() {
            @Override
            public void refreshHeight(int panelHeight) {

            }

            @Override
            public int getHeight() {
                return 0;
            }

            @Override
            public void onKeyboardShowing(boolean showing) {

            }
        }, listener);
    }

}
