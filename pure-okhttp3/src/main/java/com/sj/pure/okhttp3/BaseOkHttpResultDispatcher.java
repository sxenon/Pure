/*
 * Copyright (c) 2017 sxenon
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

package com.sj.pure.okhttp3;

import com.sxenon.pure.core.result.BaseResultDispatcher;
import com.sxenon.pure.core.result.IResultHandler;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * BaseResultDispatcher for OkHttp
 * Created by Sui on 2016/12/15.
 */

public abstract class BaseOkHttpResultDispatcher<R> extends BaseResultDispatcher<R> {
    private final Type mTypeForConvert;

    public BaseOkHttpResultDispatcher(IResultHandler resultHandler, Type type) {
        super(resultHandler);
        mTypeForConvert=type;
    }

    protected Type getTypeForConvert(){
        return mTypeForConvert;
    }

    protected abstract R convert(ResponseBody responseBody,Type type);
}
