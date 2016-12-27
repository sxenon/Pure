package com.sxenon.pure.core.component.filler;

/**
 * Created by Sui on 2016/12/27.
 */

public abstract class DemoPullLayout extends BasePullLayout {
    interface BuiltInListener{
        void onRefresh();
        void onLoadMore();
        void onOther();
    }

    public void setBuiltInListener(BuiltInListener listener){
        //eg real layout`s listener
    }

    public void demoUse(){
        final IPullLayout.Delegate delegate=genRealDelegate(new IPullLayout.Delegate() {
            @Override
            public boolean onBeginRefreshing() {
                return false;
            }

            @Override
            public boolean onBeginLoadingMore() {
                return false;
            }
        });

        setBuiltInListener(new BuiltInListener() {
            @Override
            public void onRefresh() {
                delegate.onBeginRefreshing();
            }

            @Override
            public void onLoadMore() {
                delegate.onBeginLoadingMore();
            }

            @Override
            public void onOther() {
                //do something else if need
            }
        });

    }

}
