package com.sj.pure.okhttp3;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.sxenon.pure.protocol.http.IHttpClient;
import com.sxenon.pure.util.Preconditions;

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
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * PureOkHttpClient
 * Created by Sui on 2016/12/13.
 */

public abstract class PureOkHttpClient implements IHttpClient<IOkHttpCallBackHandler, IOkHttpResponseHandler> {

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse(IHttpClient.MEDIA_TYPE_MARKDOWN);
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse(IHttpClient.MEDIA_TYPE_PNG);

    private OkHttpClient mClient;
    private Request.Builder mRequestBuilder;
    private MultipartBody mMultipartBody;

    public PureOkHttpClient(OkHttpClient client) {
        mClient = client;
        mClient.newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(getLoggingLevel()));
    }

    @Override
    public void get(String url, Object tag) {
        mRequestBuilder.url(url);
    }

    @Override
    public void postString(String url, String postBody, Object tag) {
        mRequestBuilder
                .url(url)
                .tag(tag)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody));
    }

    @Override
    public void postStreaming(String url, final InputStream in, Object tag) {
        mRequestBuilder
                .url(url)
                .tag(tag)
                .post(new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return MEDIA_TYPE_MARKDOWN;
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {
                        Source source = Okio.source(in);
                        sink.writeAll(source);
                    }
                });
    }

    @Override
    public void postFile(String url, File file, Object tag) {
        mRequestBuilder
                .url(url)
                .tag(tag)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file));
    }

    @Override
    public void postMultiPart(String url, Object tag) {
        mRequestBuilder
                .url(url)
                .tag(tag)
                .post(Preconditions.checkNotNull(mMultipartBody, ""));
    }

    @Override
    public void postForm(String url, Map<String, String> form, Object tag) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String name : form.keySet()) {
            builder.add(name, form.get(name));
        }
        RequestBody formBody = builder.build();
        mRequestBuilder
                .url(url)
                .tag(tag)
                .post(formBody);
    }

    public PureOkHttpClient resetRequestBuilder(Request.Builder builder) {
        mRequestBuilder = builder;
        return this;
    }

    public PureOkHttpClient resetMultipartBody(MultipartBody body) {
        mMultipartBody = body;
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
    public void execute(IOkHttpResponseHandler responseHandler) throws IOException {
        Response response = mClient.newCall(mRequestBuilder.build()).execute();
        responseHandler.handleResponse(response);
    }

    @Override
    public void enqueue(final IOkHttpCallBackHandler callBackHandler) {
        mClient.newCall(mRequestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackHandler.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBackHandler.onResponse(call, response);
            }
        });
    }

    public HttpLoggingInterceptor.Level getLoggingLevel() {
        return HttpLoggingInterceptor.Level.BASIC;
    }
}
