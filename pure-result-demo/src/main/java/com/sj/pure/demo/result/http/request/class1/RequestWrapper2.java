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
import com.sj.pure.demo.result.http.response.Wrapper;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.rest.RestRequest;

/**
 * 起个好名字好难，所以暂时就这样
 * 封装过的Entry
 * Created by Sui on 2017/8/27.
 */

public class RequestWrapper2<T> extends BaseRequestWrapper<T> {
    private RestRequest<Wrapper<T>> baseRequest;

    public RequestWrapper2(RestRequest<Wrapper<T>> baseRequest){
        super(BaseRequestWrapper.getBaseUrl(baseRequest),baseRequest.getRequestMethod());
        this.baseRequest=baseRequest;
    }

    @Override
    protected Result<T> getResult(Headers headers, byte[] body) throws Exception {
        Wrapper<T> result=baseRequest.parseResponse(headers, body);
        return new Result<>(true, result.getEntry(), headers, null);
    }
}
