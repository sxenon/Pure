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

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;


/**
 * Utils for okHttp
 * Created by Sui on 2016/12/14.
 */

public class OKHttpUtils {
    private static final String TAG = "OKHttpUtils";

    public static boolean writeResponseBodyToDisk(ResponseBody body, String outputFileName) {
        File futureStudioIconFile = new File(outputFileName);
        byte[] fileReader = new byte[4096];

        long fileSize = body.contentLength();
        long fileSizeDownloaded = 0;

        try (InputStream inputStream = body.byteStream(); OutputStream outputStream = new FileOutputStream(futureStudioIconFile)) {
            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
                fileSizeDownloaded += read;
                Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
            }
            outputStream.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
