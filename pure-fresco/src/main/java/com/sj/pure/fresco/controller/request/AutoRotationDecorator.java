package com.sj.pure.fresco.controller.request;

import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * for JPEGs with EXIF metadata only
 * Created by Sui on 2016/12/20.
 */

public class AutoRotationDecorator implements ImageRequestDecorator {
    @Override
    public ImageRequestBuilder update(ImageRequestBuilder requestBuilder) {
        return requestBuilder.setRotationOptions(RotationOptions.autoRotate());
    }
}
