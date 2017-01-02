package com.sxenon.pure.core.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerViewAdapter;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Dynamic proxy.
 * Created by Sui on 2016/10/25.
 */

public class ViewBinderImpl implements IViewBinder {

    private static final int DEFAULT_SKIP_DURATION =500;

    //View
    @Override
    public Observable<Void> bindViewClicksThrottleFirst(View view) {
        return bindViewClicksThrottleFirst(view,DEFAULT_SKIP_DURATION);
    }

    public Observable<Void> bindViewClicksThrottleFirst(View view, int duration) {
        return RxView.clicks(view).throttleFirst(duration,TimeUnit.MILLISECONDS);
    }

    //CompoundButton
    @Override
    public Observable<Boolean> bindCompoundButtonCheckedChanges(CompoundButton compoundButton) {
        return RxCompoundButton.checkedChanges(compoundButton);
    }

    //TextView
    @Override
    public Observable<CharSequence> bindTextViewTextChangesDebounce(TextView textView) {
        return RxTextView.textChanges(textView).debounce(DEFAULT_SKIP_DURATION,TimeUnit.MILLISECONDS);
    }

    //AdapterView
    @Override
    public <T extends Adapter> Observable<Integer> bindAdapterViewItemClicks(AdapterView<T> adapterView) {
        return RxAdapterView.itemClicks(adapterView);
    }

    //RecyclerViewAdapter
    public <T extends RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> Observable<T> bindRecyclerViewAdapterDataChanges(@NonNull T adapter){
        return RxRecyclerViewAdapter.dataChanges(adapter);
    }


}
