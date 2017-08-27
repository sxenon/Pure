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

import com.alibaba.fastjson.JSONException;
import com.sj.pure.demo.result.http.response.Result;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.RestRequest;

import java.lang.reflect.Field;

/**
 * <p>业务状态码用Http响应码返回。</p>
 * @param <T> 可以是任何类型（包括Entry，String，Bitmap，File...）
 * Created by Sui on 2017/8/27.
 */

public abstract class BaseRequestWrapper<T> extends RestRequest<Result<T>> {
    public BaseRequestWrapper(String url) {
        super(url);
    }

    public BaseRequestWrapper(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public Result<T> parseResponse(Headers headers, byte[] body) throws Exception {
        int responseCode = headers.getResponseCode();
        // 响应码正确，且包体不为空。
        if (responseCode == 200 && body != null && body.length > 0) {
            try {
                return getResult(headers, body);
            } catch (JSONException e){
                String error = "JSON解析错误";
                return new Result<>(false, null, headers, error);
            } catch (Exception e) { // 解析发生错误。
                String error = "服务器返回数据格式错误，请稍后重试";
                return new Result<>(false, null, headers, error);
            }
        } else if (responseCode >= 400) { // 其它响应码处理。
            String error = "服务器发生错误，请稍后重试";
            return new Result<>(false, null, headers, error);
        } else {
            String error = "服务器返回数据格式错误，请稍后重试";
            return new Result<>(false, null, headers, error);
        }
    }

    /**
     * Request 里面为什么会有parseResponse？应该解耦的
     */
    protected static String getBaseUrl(RestRequest request){
        String url="";
        try {
            Field urlField = request.getClass().getField("url");
            url= (String) urlField.get(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    protected abstract Result<T> getResult(Headers headers, byte[] body) throws Exception;
}
