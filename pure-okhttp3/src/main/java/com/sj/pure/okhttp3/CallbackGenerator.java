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

package com.sj.pure.okhttp3;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Generate callback from ResultDispatcher
 * Created by Sui on 2017/7/26.
 */

public class CallbackGenerator implements Callback {
    private final BaseOkHttpResultDispatcher<R> resultDispatcher;

    public CallbackGenerator(BaseOkHttpResultDispatcher<R> resultDispatcher){
        this.resultDispatcher=resultDispatcher;
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        resultDispatcher.onFailure(call,e);
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        resultDispatcher.onResponse(call, response);
    }
}
