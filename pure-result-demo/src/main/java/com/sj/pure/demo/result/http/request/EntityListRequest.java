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

package com.sj.pure.demo.result.http.request;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

import java.util.List;

/**
 * EntitySingleRequest
 * Created by Sui on 2017/8/27.
 */

public class EntityListRequest<Entity> extends RestRequest<List<Entity>> {
    private Class<Entity> aClazz;

    public EntityListRequest(String url, Class<Entity> clazz) {
        super(url);
        this.aClazz = clazz;
    }

    public EntityListRequest(String url, RequestMethod requestMethod, Class<Entity> clazz) {
        super(url, requestMethod);
        this.aClazz = clazz;
    }

    @Override
    public List<Entity> parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        return JSON.parseArray(StringRequest.parseResponseString(responseHeaders,responseBody), aClazz);
    }
}
