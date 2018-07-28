/*
 * Copyright (c) 2017  sxenon
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

package com.sxenon.pure.core.global;

import android.util.Log;

import com.sxenon.pure.core.controller.IActivity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Manage activity history
 * Use with activity`s launchMode will be better.
 * Created by Sui on 2016/12/24.
 */

public class ActivityHistoryManager {
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
     *
     * @param finishForWhat by event
     */
    public void finishBackgroundActivitiesIfNeed(final int finishForWhat) {
        IActivity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            Log.w(TAG, "There is no activity in history");
            return;
        }

        Iterator<IActivity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            IActivity activity = iterator.next();
            if (!activity.shouldKeepWhenInBackground(finishForWhat)) {
                activity.finish();
                iterator.remove();
            }
        }
        Log.i(TAG, "finishBackgroundActivitiesIfNeedSuccess:finishForWhat=" + finishForWhat);
    }

    public void finishAllActivity() {
        for (IActivity activity : activityStack) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * find activity by class name
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
     * Exit app
     */
    public void appExit() {
        finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
