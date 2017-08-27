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
import com.sxenon.pure.core.result.handler.IFetchListResultHandler;
import com.sxenon.pure.core.result.handler.IFetchSingleResultHandler;
import com.sxenon.pure.core.result.handler.IResultHandler;
import com.sxenon.pure.core.result.handler.ISubmitResultHandler;

import java.util.List;

/**
 * Base implement for IResultDispatcher
 * Created by Sui on 2016/12/15.
 */

public class BaseResultDispatcher<R> implements IResultDispatcher {
    private final IResultHandler mResultHandler;

    public BaseResultDispatcher(IResultHandler resultHandler) {
        mResultHandler = resultHandler;
    }

    public void handleResult(R data) {
        if (mResultHandler instanceof ISubmitResultHandler) {
            //noinspection unchecked
            ((ISubmitResultHandler) mResultHandler).onResultFetched(data);
        } else if (mResultHandler instanceof IFetchListResultHandler) {
            //noinspection unchecked
            ((IFetchListResultHandler) mResultHandler).onListDataFetched((List) data);
        } else if (mResultHandler instanceof IFetchSingleResultHandler) {
            //noinspection unchecked
            ((IFetchSingleResultHandler) mResultHandler).onSingleDataFetched(data);
        } else {
            throw new IllegalArgumentException("The type of resultHandle is wrong");
        }
    }

    @Override
    public void onCancel() {
        mResultHandler.onCancel();
    }

    @Override
    public void onApiException(ApiException apiException) {
        mResultHandler.onApiException(apiException);
    }
}
