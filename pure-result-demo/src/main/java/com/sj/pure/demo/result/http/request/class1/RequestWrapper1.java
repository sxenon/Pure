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

package com.sj.pure.demo.result.http.request.class1;

import com.sj.pure.demo.result.http.response.Result;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.rest.RestRequest;

/**
 * 起个好名字好难，所以暂时就这样
 * 原始的Entry，String，Bitmap，File...
 * Created by Sui on 2017/8/27.
 */

public class RequestWrapper1<T> extends BaseRequestWrapper<T> {

    private RestRequest<T> baseRequest;

    public RequestWrapper1(RestRequest<T> baseRequest){
        super(getBaseUrl(baseRequest),baseRequest.getRequestMethod());
        this.baseRequest=baseRequest;
    }

    @Override
    protected Result<T> getResult(Headers headers, byte[] body) throws Exception{
        T t = baseRequest.parseResponse(headers, body);
        return new Result<>(true, t, headers, null);
    }
}
