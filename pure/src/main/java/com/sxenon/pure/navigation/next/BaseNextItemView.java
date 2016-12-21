package com.sxenon.pure.navigation.next;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sui on 2016/11/11.
 */

public class BaseNextItemView implements INextItemView {
    private final ImageView mNextIcon;
    private final TextView mNextText;

    public BaseNextItemView(ImageView nextIcon, TextView nextText) {
        mNextIcon = nextIcon;
        mNextText = nextText;
    }

    public ImageView getNextIcon() {
        return mNextIcon;
    }

    public TextView getTextView() {
        return mNextText;
    }

    @Override
    public void setNextDelegate(final NextDelegate nextDelegate) {
        ((ViewGroup)mNextText.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDelegate.onNext();
            }
        });
    }
}
