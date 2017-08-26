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
import com.alibaba.fastjson.JSONObject;
import com.sj.pure.demo.result.http.response.Result;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

/**
 * <p>业务状态码在HttpBody中返回。</p>
 * Created by Yan Zhenjie on 2016/12/17.
 */
public abstract class AbstractRequest2<T> extends RestRequest<Result<T>> {

    public AbstractRequest2(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public Result<T> parseResponse(Headers headers, byte[] body) throws Exception {
        int responseCode = headers.getResponseCode(); // 响应码。

        // 响应码等于200，Http层成功。
        if (responseCode == 200) {
            if (body == null || body.length == 0) {
                // 服务器包体为空。
                return new Result<>(true, null, headers, null);
            } else {
                String bodyString = StringRequest.parseResponseString(headers, body);
                try {
                    JSONObject bodyObject = JSON.parseObject(bodyString);
                    // 业务层成功。
                    if (bodyObject.getIntValue("errorCode") == 1) {
                        String data = bodyObject.getString("data");
                        // 重点、重点、重点：调用子类，解析出真正的数据。
                        T result = getResult(data);
                        return new Result<>(true, result, headers, null);
                    } else {
                        String error = bodyObject.getString("message");
                        return new Result<>(false, null, headers, error);
                    }
                } catch (Exception e) {
                    // 解析异常，测试时通过，正式发布后就是服务器的锅。
                    String error = "服务器返回数据格式错误，请稍后重试";
                    return new Result<>(false, null, headers, error);
                }
            }
        } else { // 其它响应码，如果和服务器没有约定，那就是服务器发生错误了。
            String error = "服务器返回数据格式错误，请稍后重试";
            return new Result<>(false, null, headers, error);
        }
    }

    protected abstract T getResult(String responseBody) throws Exception;
}
