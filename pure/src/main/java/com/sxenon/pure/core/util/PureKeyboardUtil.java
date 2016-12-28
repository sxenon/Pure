package com.sxenon.pure.core.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.sxenon.pure.core.router.PureBaseRootPresenter;
import com.sxenon.pure.core.router.RouterEvent;

import cn.dreamtobe.kpswitch.IPanelHeightTarget;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import rx.Observable;
import rx.functions.Action0;

/**
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

    //TODO RxLifeCycle 是否是正确的打开方式
    public static void attach(PureBaseRootPresenter rootPresenter, IPanelHeightTarget target, KeyboardUtil.OnKeyboardShowingListener listener) {
        final Activity activity = rootPresenter.getRouter().getActivityCompact();
        final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = KeyboardUtil.attach(activity, target, listener);
        //noinspection unchecked
        Observable.just(onGlobalLayoutListener)
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        KeyboardUtil.detach(activity, onGlobalLayoutListener);
                    }
                })
                .compose(rootPresenter.<ViewTreeObserver.OnGlobalLayoutListener>bindUntilEvent(RouterEvent.DESTROY))
                .subscribe();
    }

    public static void attach(PureBaseRootPresenter rootPresenter, IPanelHeightTarget target) {
        attach(rootPresenter, target, null);
    }

    public static void attach(PureBaseRootPresenter rootPresenter, KeyboardUtil.OnKeyboardShowingListener listener) {
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
