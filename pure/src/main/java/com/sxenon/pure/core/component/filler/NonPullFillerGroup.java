package com.sxenon.pure.core.component.filler;

import com.sxenon.pure.core.component.adapter.IPureAdapter;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.component.filler.pull.BasePullLayout;

/**
 * UnSupport pull!
 * Created by Sui on 2016/12/12.
 */

public class NonPullFillerGroup<T> extends FillerGroup<T, BasePullLayout> {
    public NonPullFillerGroup(IRouter router, IFetchSingleResultHandler<T> singleDataResult) {
        super(router, new BasePullLayout(), singleDataResult);
    }

    public NonPullFillerGroup(IRouter router, IPureAdapter<T> adapter) {
        super(router, new BasePullLayout(), adapter);
    }
}
