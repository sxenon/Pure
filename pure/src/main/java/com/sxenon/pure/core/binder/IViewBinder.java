package com.sxenon.pure.core.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.TextView;

import rx.Observable;

/**
 * {@link ViewBinderImpl}
 * Dynamic Proxy
 * Created by Sui on 2016/10/26.
 */

@SuppressWarnings("UnusedReturnValue")
public interface IViewBinder {

    //View
    Observable<Void> bindViewClicksThrottleFirst(View view);

    Observable<Void> bindViewClicksThrottleFirst(View view, int duration);

    //CompoundButton
    Observable<Boolean> bindCompoundButtonCheckedChanges(CompoundButton compoundButton);

    //TextView
    Observable<CharSequence> bindTextViewTextChangesDebounce(TextView textView);

    //AdapterView
    <T extends Adapter> Observable<Integer> bindAdapterViewItemClicks(AdapterView<T> adapterView);

    //RecyclerViewAdapter
    <T extends RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> Observable<T> bindRecyclerViewAdapterDataChanges(@NonNull T adapter);
}
