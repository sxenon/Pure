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

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

import okio.Buffer;

/**
 * Helper to gen an input stream with the certificate data.
 * Created by Sui on 2016/12/13.
 */

public class CertificateStreamFactory {
    public static InputStream from(String cerString) {
        return new Buffer().writeUtf8(cerString).inputStream();
    }

    public static InputStream from(Context context, String cerFile) {
        try {
            return context.getAssets().open(cerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
