package com.sxenon.pure.core.component.filler;

import com.sxenon.pure.core.util.Preconditions;

/**
 * Created by Sui on 2016/12/27.
 */

public abstract class BasePullLayout implements IPullLayout {
    private FillerGroup mFillerGroup;

    void bindFillerGroup(FillerGroup fillerGroup) {
        mFillerGroup = fillerGroup;
    }

    public IPullLayout.Delegate genRealDelegate(IPullLayout.Delegate originalDelegate) {
        return Preconditions.checkNotNull(mFillerGroup, "Create filler group with this pullLayout first!").processDelegate(originalDelegate);
    }

}
