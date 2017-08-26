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
package com.sj.pure.demo.result.http;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.nohttp.RequestMethod;

/**
 * Created by Yan Zhenjie on 2016/12/17.
 */
public class EntitySingleRequest<Entity> extends AbstractRequest<Entity> {

    private Class<Entity> aClazz;

    public EntitySingleRequest(String url, RequestMethod requestMethod, Class<Entity> clazz) {
        super(url, requestMethod);
        this.aClazz = clazz;
    }

    @Override
    protected Entity getResult(String responseBody) throws Exception {
        return JSON.parseObject(responseBody, aClazz);
    }
}
