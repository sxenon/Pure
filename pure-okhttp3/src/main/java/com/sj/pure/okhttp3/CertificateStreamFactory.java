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
