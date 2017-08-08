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

package com.sj.pure.okgo.callback.decorator;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.sj.pure.okgo.callback.LCallback;
import com.sj.pure.okgo.dispatcher.BaseOkgoLResultDispatcher;

import java.util.List;

/**
 * LCallbackDecorator
 * Created by Sui on 2017/7/27.
 */

public class LCallbackDecorator<R> implements LCallback<R> {
    private  LCallback<R> originalCallback;
    private final BaseOkgoLResultDispatcher<R> resultDispatcher;

    /**
     * Constructor ,use with {@link #decorate(LCallback)}
     * @param resultDispatcher Common
     */
    public LCallbackDecorator(BaseOkgoLResultDispatcher<R> resultDispatcher){
        this.resultDispatcher=resultDispatcher;
    }

    /**
     * Decorate
     * @param originalCallback Special
     */
    public void decorate(LCallback<R> originalCallback){
        this.originalCallback=originalCallback;
    }

    @Override
    public void onStart(Request<List<R>, ? extends Request> request) {
        resultDispatcher.onStart(request);
        originalCallback.onStart(request);
    }

    @Override
    public void onSuccess(Response<List<R>> response) {
        resultDispatcher.onSuccess(response);
        originalCallback.onSuccess(response);
    }

    @Override
    public void onCacheSuccess(Response<List<R>> response) {
        resultDispatcher.onCacheSuccess(response);
        originalCallback.onCacheSuccess(response);
    }

    @Override
    public void onError(Response<List<R>> response) {
        resultDispatcher.onError(response);
        originalCallback.onError(response);
    }

    @Override
    public void onFinish() {
        resultDispatcher.onFinish();
        originalCallback.onFinish();
    }

    @Override
    public void uploadProgress(Progress progress) {
        originalCallback.uploadProgress(progress);
    }

    @Override
    public void downloadProgress(Progress progress) {
        originalCallback.uploadProgress(progress);
    }

    @Override
    public List<R> convertResponse(okhttp3.Response response) throws Throwable {
        return originalCallback.convertResponse(response);
    }
}
