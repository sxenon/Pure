package com.sj.pure.okhttp3;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;


/**
 * Utils
 * Created by Sui on 2016/12/14.
 */

public class Utils {
    private static final String TAG = "";

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
