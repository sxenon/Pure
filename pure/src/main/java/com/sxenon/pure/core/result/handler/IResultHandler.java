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

package com.sxenon.pure.core.result.handler;

import com.sxenon.pure.core.ApiException;

/**
 * Interface for common result handler
 * Direct subclasses:
 * {@link IFetchListResultHandler},{@link IFetchSingleResultHandler},{@link ISubmitResultHandler}
 * {@link com.sxenon.pure.core.mvp.IView}s should implement one of the above if need.
 * Created by Sui on 2016/11/20.
 */

public interface IResultHandler {
    void onCancel();

    void onException(ApiException exception);
}