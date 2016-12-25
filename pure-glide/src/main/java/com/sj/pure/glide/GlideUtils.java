package com.sj.pure.glide;

import android.app.Activity;
import android.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.sxenon.pure.core.router.IRouter;

/**
 * Created by Sui on 2016/12/19.
 */

public class GlideUtils {
    public static RequestManager with(IRouter router) {
        switch (router.getRouterType()) {
            case ACTIVITY:
            case COMPACT_ACTIVITY:
                return Glide.with((Activity) router);
            case FRAGMENT:
                return Glide.with((Fragment) router);
            case SUPPORT_FRAGMENT:
                return Glide.with((android.support.v4.app.Fragment) router);
            default:
                //Won`t happen
                throw new IllegalArgumentException("Glide.with don`t support:" + router.getClass());
        }
    }
}
