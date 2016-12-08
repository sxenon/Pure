package com.sxenon.pure.filler;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.IPureAdapter;
import com.sxenon.pure.core.IViewComponentGroup;
import com.sxenon.pure.util.Preconditions;

import java.util.List;

/**
 * Created by Sui on 2016/12/8.
 */

public class FillerGroup<T> implements IViewComponentGroup {
    private int mCurrentPageCount;
    private int tempPageCount;
    private int eventWhat;

    private IPureAdapter<T> mAdapter;
    private ISingleValueResult<T> mSingleValueResult;

    public Event getEventForSave(){
        Event event=new Event();
        event.what=eventWhat;
        event.arg1=mCurrentPageCount;
        if (mAdapter!=null){
            event.obj=mAdapter.getValues();
        }else {
            event.obj=mSingleValueResult.getValue();
        }
        return event;
    }

    public void restoreEvent(Event savedEvent){
        mCurrentPageCount=tempPageCount=savedEvent.arg1;
        eventWhat=savedEvent.what;
        Object object=savedEvent.obj;
        if (object instanceof List){
            Preconditions.checkNotNull(mAdapter,"");
            //noinspection unchecked
            mAdapter.resetAllItems((List<T>) object);
        }else {
            Preconditions.checkNotNull(mSingleValueResult,"");
            //noinspection unchecked
            mSingleValueResult.onSingleValueFetched((T) object);
        }
    }
}
