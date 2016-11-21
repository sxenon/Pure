package com.sxenon.pure.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.sxenon.pure.core.IAsActivity;

/**
 * Created by Sui on 2016/11/21.
 */

public class PureActivity extends AppCompatActivity implements IAsActivity {
    @Override
    public FragmentActivity getActivity() {
        return this;
    }
}
