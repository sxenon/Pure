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

package com.sj.pure.okhttp3;

import com.sxenon.pure.core.result.IResultDispatcher;
import com.sxenon.pure.core.protocol.http.IHttpClient;
import com.sxenon.pure.core.util.Preconditions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * PureHttpClient
 * Created by Sui on 2016/12/13.
 */

public abstract class PureHttpClient<RD extends IResultDispatcher> implements IHttpClient<RD> {

    private final OkHttpClient mClient;
    private ThreadLocal<Request.Builder> requestBuilderThreadLocal =new ThreadLocal<>();
    private ThreadLocal<MultipartBody> multipartBodyThreadLocal=new ThreadLocal<>();

    public PureHttpClient(OkHttpClient client) {
        mClient = client;
    }

    @Override
    public IHttpClient buildGetRequest(String url, Object tag) {
        requestBuilderThreadLocal.get().url(url);
        return this;
    }

    @Override
    public IHttpClient buildPostStringRequest(String url, String postBody, Object tag) {
        requestBuilderThreadLocal.get()
                .url(url)
                .tag(tag)
                .post(RequestBody.create(OkHttpConstants.MEDIA_TYPE_MARKDOWN, postBody));
        return this;
    }

    @Override
    public IHttpClient buildPostStreamingRequest(String url, final InputStream in, Object tag) {
        requestBuilderThreadLocal.get()
                .url(url)
                .tag(tag)
                .post(new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return OkHttpConstants.MEDIA_TYPE_MARKDOWN;
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {
                        Source source = Okio.source(in);
                        sink.writeAll(source);
                    }
                });
        return this;
    }

    @Override
    public IHttpClient buildPostFileRequest(String url, File file, Object tag) {
        requestBuilderThreadLocal.get()
                .url(url)
                .tag(tag)
                .post(RequestBody.create(OkHttpConstants.MEDIA_TYPE_MARKDOWN, file));
        return this;
    }

    @Override
    public IHttpClient buildPostMultiPartRequest(String url, Object tag) {
        requestBuilderThreadLocal.get()
                .url(url)
                .tag(tag)
                .post(Preconditions.checkNotNull(multipartBodyThreadLocal.get(), ""));
        return this;
    }

    @Override
    public IHttpClient buildPostFormRequest(String url, Map<String, String> form, Object tag) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String name : form.keySet()) {
            builder.add(name, form.get(name));
        }
        RequestBody formBody = builder.build();
        requestBuilderThreadLocal.get()
                .url(url)
                .tag(tag)
                .post(formBody);
        return this;
    }

    /**
     * Call it before build_xxx_Request
     */
    public PureHttpClient resetRequestBuilder(Request.Builder builder) {
        requestBuilderThreadLocal.set(builder);
        return this;
    }

    /**
     * Call it before buildPostMultiPartRequest
     */
    public PureHttpClient resetMultipartBody(MultipartBody body) {
        multipartBodyThreadLocal.set(body);
        return this;
    }

    @Override
    public void cancel(Object tag) {
        for (Call call : mClient.dispatcher().queuedCalls()) {
            if (tag == null || tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mClient.dispatcher().runningCalls()) {
            if (tag == null || tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    @Override
    public void execute(RD resultDispatcher) {
        Response response;
        Call newCall = mClient.newCall(requestBuilderThreadLocal.get().build());
        try {
            response = newCall.execute();
            preParseResponse(newCall, response, resultDispatcher);
        } catch (IOException e) {
            preParseFailure(newCall, e, resultDispatcher);
        }
    }

    @Override
    public void enqueue(final RD resultDispatcher) {
        mClient.newCall(requestBuilderThreadLocal.get().build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                preParseFailure(call, e, resultDispatcher);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                preParseResponse(call, response, resultDispatcher);
            }
        });
    }

    protected abstract void preParseFailure(Call call, IOException e, RD resultDispatcher);

    protected abstract void preParseResponse(Call call, Response response, RD resultDispatcher);

}
