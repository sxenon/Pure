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

package com.sxenon.pure.core.result;

import com.sxenon.pure.core.ApiException;

import java.util.List;

/**
 * Base implement for IResultDispatcher
 * Created by Sui on 2016/12/15.
 */

public class BaseResultDispatcher<R> implements IResultDispatcher<R> {
    private final IResultHandler mResultHandler;
    private ResultHandlerType mResultHandlerType = ResultHandlerType.NONE;

    public BaseResultDispatcher(IResultHandler resultHandler) {
        mResultHandler = resultHandler;
    }

    public ResultHandlerType getResultHandlerType() {
        if (ResultHandlerType.NONE == mResultHandlerType) {
            if (mResultHandler instanceof ISubmitResultHandler) {
                mResultHandlerType = ResultHandlerType.SUBMIT;
            } else if (mResultHandler instanceof IFetchListResultHandler) {
                mResultHandlerType = ResultHandlerType.FETCH_LIST;
            } else if (mResultHandler instanceof IFetchSingleResultHandler) {
                mResultHandlerType = ResultHandlerType.FETCH_SINGLE;
            } else {
                throw new IllegalArgumentException("The type of resultHandle is wrong");
            }
        }
        return mResultHandlerType;
    }

    /**
     * {@link ResultHandlerType#FETCH_LIST}
     */
    @Override
    public void onListDataFetched(List<R> data) {
        //noinspection unchecked
        ((IFetchListResultHandler<R>) mResultHandler).onListDataFetched(data);
    }

    /**
     * {@link ResultHandlerType#FETCH_SINGLE}
     */
    @Override
    public void onSingleDataFetched(R data) {
        //noinspection unchecked
        ((IFetchSingleResultHandler<R>) mResultHandler).onSingleDataFetched(data);
    }

    /**
     * {@link ResultHandlerType#SUBMIT}
     */
    @Override
    public void onSubmitSuccess(R result) {
        //noinspection unchecked
        ((ISubmitResultHandler<R>) mResultHandler).onSubmitSuccess(result);
    }

    /**
     * {@link ResultHandlerType#SUBMIT}
     */
    @Override
    public void onSubmitFailed(R result) {
        //noinspection unchecked
        ((ISubmitResultHandler<R>) mResultHandler).onSubmitFailed(result);
    }

    @Override
    public void onCancel() {
        mResultHandler.onCancel();
    }

    @Override
    public void onException(ApiException exception) {
        mResultHandler.onException(exception);
    }
}
