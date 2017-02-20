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

package com.sxenon.pure.core.result.impl;

import android.content.Intent;

import com.sxenon.pure.core.global.IntentManager;
import com.sxenon.pure.core.result.IFetchSingleResultHandler;

import java.io.File;

/**
 * ResultHandler for update info
 * Created by Sui on 2017/2/20.
 */

public abstract class UpdateInfoResultHandler<R> implements IFetchSingleResultHandler<R> {
    public abstract boolean isVersionIngored(String version);
    public abstract void ingoreVersion(String version);
    public abstract void downloadNewVersionApk(String url,String toPath);

    public Intent getInstallAppIntent(File appFile){
        return IntentManager.getInstallAppIntent(appFile);
    }
}
