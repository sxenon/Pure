package com.sxenon.pure.core.global;

import android.util.Log;

import com.sxenon.pure.core.router.IActivity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Sui on 2016/12/24.
 */

public class ActivityHistoryManager {
    //结合Activity的LaunchMode统一管理
    private final Stack<IActivity> activityStack = new Stack<>();
    private final String TAG = "ActivityHistoryManager";

    public IActivity getCurrentActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    public void add(IActivity activity) {
        activityStack.add(activity);
    }

    /**
     * onCreate()里面finish的情况应该手动在onCreate()里面加上remove
     */
    public void remove(IActivity activity) {
        activityStack.remove(activity);
    }

    public void finishActivity(Class<? extends IActivity> activityClass) {
        Iterator<IActivity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            IActivity activity = iterator.next();
            if (activity != null && activity.getClass().equals(activityClass)) {
                iterator.remove();
                activity.finish();
                Log.w(TAG, "Finish activity:" + activityClass.getSimpleName());
                break;
            }
        }
    }

    /**
     * @deprecated {@link #finishBackgroundActivitiesIfNeed} is much better and more useful
     * 关闭当前与指定Activity之间的所有的Activity
     */
    public void finishActivityInMiddle(Class<? extends IActivity> bottomActivityClass) {
        IActivity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            Log.w(TAG, "There is no activity in history");
            return;
        }
        Class<? extends IActivity> currentActivityClass = currentActivity.getClass();

        boolean shouldFinish = false;
        Iterator<IActivity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            IActivity activity = iterator.next();
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
     * Keep what should keep,and finish others.
     * @param what 区分事件
     */
    public void finishBackgroundActivitiesIfNeed(final int what) {
        IActivity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            Log.w(TAG, "There is no activity in history");
            return;
        }

        Iterator<IActivity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            IActivity activity = iterator.next();
            if (!activity.shouldKeepWhenBackground(what)) {
                activity.finish();
                iterator.remove();
            }
        }
        Log.i(TAG,"finishBackgroundActivitiesIfNeedSuccess:what="+what);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (IActivity activity : activityStack) {
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
    public IActivity getActivity(Class<? extends IActivity> activityClass) {
        for (IActivity activity : activityStack) {
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
