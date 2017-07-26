/*
 * Copyright (c) 2017  sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sj.pure.okgo.callback;

import com.lzy.okgo.callback.AbsCallback;
import com.sj.pure.okgo.converter.BConverter;

/**
 * Callback for bean
 * Created by Sui on 2017/7/26.
 */

public abstract class BCallBack<T> extends AbsCallback<T> {
    private final BConverter<T> mBConverter;

    public BCallBack(BConverter<T> bConverter){
        mBConverter=bConverter;
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        return mBConverter.convertResponse(response);
    }
}
