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

package com.sj.pure.demo.result.http.result;

import com.sxenon.pure.core.result.ResultHandlerType;
import com.sxenon.pure.core.result.handler.IResultHandler;

/**
 * Created by Sui on 2017/8/26.
 */

public abstract class AbsNoHttpSingleResultDispatcher<T> extends BaseNoHttpResultDispatcher<T>{
    public AbsNoHttpSingleResultDispatcher(IResultHandler resultHandler) {
        super(resultHandler);
    }

    public void handleSuccessResult(T result) {
        ResultHandlerType resultHandlerType = getResultHandlerType();
        switch (resultHandlerType) {
            case FETCH_SINGLE: {
                onSingleDataFetched(result);
            }
            break;
            case SUBMIT: {
                onSubmitSuccess(result);
            }
            default: {
                throw new IllegalArgumentException("IResultHandler with wrong type");
            }
        }
    }


}
