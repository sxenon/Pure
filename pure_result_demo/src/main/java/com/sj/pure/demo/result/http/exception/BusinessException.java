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

package com.sj.pure.demo.result.http.exception;

import com.sxenon.pure.core.ApiException;
import com.yanzhenjie.nohttp.Headers;

/**
 * Created by Sui on 2017/8/27.
 */

public class BusinessException implements ApiException {
    private final Headers headers;

    private final String error;

    private final int what;

    public Headers getHeaders() {
        return headers;
    }

    public String getError() {
        return error;
    }

    public int getWhat() {
        return what;
    }

    public BusinessException(Headers headers, String error, int what) {
        this.headers = headers;
        this.error = error;
        this.what = what;
    }
}
