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
import com.sj.pure.demo.result.http.response.result.Result;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

/**
 * <p>业务状态码用Http响应码返回。</p>
 * Created by Yan Zhenjie on 2016/12/17.
 */
public abstract class AbstractRequest<T> extends RestRequest<Result<T>> {

    public AbstractRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public Result<T> parseResponse(Headers headers, byte[] body) throws Exception {
        int responseCode = headers.getResponseCode();
        // 响应码正确，且包体不为空。
        if (responseCode == 200 && body != null && body.length > 0) {
            String result = StringRequest.parseResponseString(headers, body);
            try {
                T t = getResult(result);
                return new Result<>(true, t, headers, null);
            } catch (Exception e) { // 解析发生错误。
                String error = "服务器返回数据格式错误，请稍后重试";
                return new Result<>(false, null, headers, error);
            }
        } else if (responseCode >= 400) { // 其它响应码处理。
            String result = StringRequest.parseResponseString(headers, body);

            String error = "服务器发生错误，请稍后重试";
            // 错误响应码时正常解析说明是服务器返回的json数据。
            // 非正常解析说明是服务器返回的崩溃信息html等。
            try {
                JSONObject jsonObject = JSON.parseObject(result);
                error = jsonObject.getString("message");
            } catch (Exception ignored) {
            }
            return new Result<>(false, null, headers, error);
        } else {
            String error = "服务器返回数据格式错误，请稍后重试";
            return new Result<>(false, null, headers, error);
        }
    }

    protected abstract T getResult(String responseBody) throws Exception;
}
