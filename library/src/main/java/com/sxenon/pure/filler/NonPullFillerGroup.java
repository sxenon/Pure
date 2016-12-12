package com.sxenon.pure.filler;

import android.content.Context;

import com.sxenon.pure.core.IPureAdapter;
import com.sxenon.pure.filler.pull.BasePullLayout;

/**
 * UnSupport pull!
 * Created by Sui on 2016/12/12.
 */

public class NonPullFillerGroup<T> extends FillerGroup<T, BasePullLayout> {
    public NonPullFillerGroup(Context context, IFetchSingleResultHandler<T> singleDataResult) {
        super(context, new BasePullLayout(), singleDataResult);
    }

    public NonPullFillerGroup(Context context, IPureAdapter<T> adapter) {
        super(context, new BasePullLayout(), adapter);
    }
}
