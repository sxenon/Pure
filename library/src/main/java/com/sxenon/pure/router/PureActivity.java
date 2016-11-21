package com.sxenon.pure.router;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.sxenon.pure.core.IRouter;

/**
 * Created by Sui on 2016/11/21.
 */

public abstract class PureActivity extends AppCompatActivity implements IRouter {
    @Override
    public FragmentActivity getActivity() {
        return this;
    }
}
