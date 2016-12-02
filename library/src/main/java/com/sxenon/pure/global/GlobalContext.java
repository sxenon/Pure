package com.sxenon.pure.global;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Sui on 2016/12/2.
 */

public enum GlobalContext {
    @SuppressLint("StaticFieldLeak")
    INSTANCE;

    public Application value;
    //结合Activity的LaunchMode统一管理
    private final Stack<Activity> activityStack = new Stack<>();
    private final String TAG = "ActivityHistoryManager";

    public Activity getCurrentActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    public void onActivityCreate(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 暂时不管onCreate()里面finish的情况
     * 或者可以手动在onCreate()里面加上onActivityDestroy
     *
     */
    public void onActivityDestroy(Activity activity) {
        activityStack.remove(activity);
    }

    public void finishActivity(Class<? extends Activity> activityClass) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && activity.getClass().equals(activityClass)) {
                iterator.remove();
                activity.finish();
                Log.w(TAG, "Finish activity:"+activityClass.getSimpleName());
                break;
            }
        }
    }

    /**
     * 关闭当前与指定Activity之间的所有的Activity
     */
    public void finishActivityInMiddle(
            Class<? extends Activity> bottomActivityClass) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            Log.w(TAG, "There is no activity in history");
            return;
        }
        Class<? extends Activity> currentActivityClass = currentActivity.getClass();

        boolean shouldFinish = false;
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(currentActivityClass)) {
                shouldFinish = false;
            }
            if (shouldFinish) {
                Log.i(TAG, "finishActivityInMiddle " + activity.getClass().getSimpleName());
                activity.finish();
                iterator.remove();
            }
            if (activity.getClass().equals(bottomActivityClass)) {
                shouldFinish = true;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 根据ActivityName获取堆中Activity实例
     *
     * @param activityClass Activity 类型
     * @return activity
     */
    public Activity getActivity(Class<? extends Activity> activityClass) {
        for (Activity activity : activityStack) {
            if (activity != null && activity.getClass().equals(activityClass)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
