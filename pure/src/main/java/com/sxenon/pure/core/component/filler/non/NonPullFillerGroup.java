package com.sxenon.pure.core.component.filler.non;

import android.content.Context;

import com.sxenon.pure.core.component.adapter.IPureAdapter;
import com.sxenon.pure.core.component.filler.FillerGroup;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

/**
 * UnSupport pull!
 * Created by Sui on 2016/12/12.
 */

public class NonPullFillerGroup<T> extends FillerGroup<T, NonPullLayout> {
    public NonPullFillerGroup(Context context, IFetchSingleResultHandler<T> singleDataResult) {
        super(context, new NonPullLayout(), singleDataResult);
    }

    public NonPullFillerGroup(Context context, IPureAdapter<T> adapter) {
        super(context, new NonPullLayout(), adapter);
    }
}
