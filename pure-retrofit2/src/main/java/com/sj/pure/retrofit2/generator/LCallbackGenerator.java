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

package com.sj.pure.retrofit2.generator;

import android.support.annotation.NonNull;

import com.sj.pure.retrofit2.dispatcher.Retrofit2LResultDispatcher;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Generate Callback from ResultDispatcher for list
 * Created by Sui on 2017/7/27.
 */

public class LCallbackGenerator<R> implements Callback<List<R>> {
    private final Retrofit2LResultDispatcher<R> resultDispatcher;

    public LCallbackGenerator(Retrofit2LResultDispatcher<R> resultDispatcher){
        this.resultDispatcher=resultDispatcher;
    }

    @Override
    public void onResponse(@NonNull Call<List<R>> call, @NonNull Response<List<R>> response) {
        resultDispatcher.onResponse(call, response);
    }

    @Override
    public void onFailure(@NonNull Call<List<R>> call, @NonNull Throwable t) {
        resultDispatcher.onFailure(call, t);
    }
}
