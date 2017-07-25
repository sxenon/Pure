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

import com.sj.pure.okhttp3.convert.Convert;
import com.sj.pure.okhttp3.convert.LConvert;
import com.sxenon.pure.core.result.BaseResultDispatcher;
import com.sxenon.pure.core.result.IResultHandler;
import com.sxenon.pure.core.result.ResultHandlerType;
import com.sxenon.pure.core.util.Preconditions;

import java.util.List;

import okhttp3.Response;

/**
 * BaseResultDispatcher for OkHttp
 * Created by Sui on 2016/12/15.
 */

public abstract class BaseOkHttpResultDispatcher<R> extends BaseResultDispatcher<R> {
    private Convert<R> mConvert;
    private LConvert<R> mLConvert;

    public BaseOkHttpResultDispatcher(IResultHandler resultHandler, Convert<R> convert) {
        super(resultHandler);
        mConvert = convert;
    }

    public BaseOkHttpResultDispatcher(IResultHandler resultHandler, LConvert<R> converter) {
        super(resultHandler);
        mLConvert =converter;
    }

    /**
     * 是业务意义上的Success！
     */
    protected void handleSuccessResult(Response response)  throws Exception {
        ResultHandlerType resultHandlerType=getResultHandlerType();
        switch (resultHandlerType){
            case FETCH_LIST:{
                List<R> result= Preconditions.checkNotNull(mLConvert,"").convertResponse(response);
                onListDataFetched(result);
            }
            case FETCH_SINGLE:{
                R result=Preconditions.checkNotNull(mConvert,"").convertResponse(response);
                onSingleDataFetched(result);
            }
            case SUBMIT:
            default:{
                R result=Preconditions.checkNotNull(mConvert,"").convertResponse(response);
                onSuccess(result);
            }
        }
    }
}
