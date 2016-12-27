package com.sxenon.pure.core.component.filler.non;

import com.sxenon.pure.core.component.adapter.IPureAdapter;
import com.sxenon.pure.core.component.filler.FillerGroup;
import com.sxenon.pure.core.component.filler.IFetchSingleResultHandler;
import com.sxenon.pure.core.router.IRouter;

/**
 * UnSupport pull!
 * Created by Sui on 2016/12/12.
 */

public class NonPullFillerGroup<T> extends FillerGroup<T, NonPullLayout> {
    public NonPullFillerGroup(IRouter router, IFetchSingleResultHandler<T> singleDataResult) {
        super(router, new NonPullLayout(), singleDataResult);
    }

    public NonPullFillerGroup(IRouter router, IPureAdapter<T> adapter) {
        super(router, new NonPullLayout(), adapter);
    }
}
