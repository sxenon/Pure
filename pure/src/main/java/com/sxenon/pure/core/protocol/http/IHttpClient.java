/*
 * Copyright (c) 2017 sxenon
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

package com.sxenon.pure.core.protocol.http;

import com.sxenon.pure.core.result.IResultDispatcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Common interface for any http library`s wrapper
 * Created by Sui on 2016/12/12.
 */

public interface IHttpClient<RD extends IResultDispatcher> {
    String MEDIA_TYPE_MARKDOWN = "text/x-markdown; charset=utf-8";
    String MEDIA_TYPE_PNG = "image/png";

    IHttpClient buildGetRequest(String url, Object tag);

    IHttpClient buildPostStringRequest(String url, String postBody, Object tag);

    IHttpClient buildPostStreamingRequest(String url, InputStream in, Object tag);

    IHttpClient buildPostFileRequest(String url, File file, Object tag);

    IHttpClient buildPostFormRequest(String url, Map<String, String> form, Object tag);

    IHttpClient buildPostMultiPartRequest(String url, Object tag);

    void cancel(Object tag);

    void execute(RD resultDispatcher);

    void enqueue(RD resultDispatcher);
}
