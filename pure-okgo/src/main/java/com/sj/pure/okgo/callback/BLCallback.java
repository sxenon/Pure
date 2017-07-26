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

import com.sj.pure.okgo.converter.BLConverter;

import java.util.List;

/**
 * Callback for list of bean
 * Created by Sui on 2017/7/26.
 */

public abstract class BLCallback<T> implements LCallBack<T> {

    private final BLConverter<T> mBLConverter;

    public BLCallback(BLConverter<T> blConverter) {
        mBLConverter = blConverter;
    }

    @Override
    public List<T> convertResponse(okhttp3.Response response) throws Throwable {
        return mBLConverter.convertResponse(response);
    }

}
