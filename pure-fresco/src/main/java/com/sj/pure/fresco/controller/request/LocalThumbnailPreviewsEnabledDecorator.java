package com.sj.pure.fresco.controller.request;

import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * This option is supported only for local URIs, and only for images in the JPEG format and has a thumbnail stored in its EXIF metadata.
 * Created by Sui on 2016/12/20.
 */

public class LocalThumbnailPreviewsEnabledDecorator implements ImageRequestDecorator {
    @Override
    public ImageRequestBuilder update(ImageRequestBuilder requestBuilder) {
        return requestBuilder.setLocalThumbnailPreviewsEnabled(true);
    }
}
