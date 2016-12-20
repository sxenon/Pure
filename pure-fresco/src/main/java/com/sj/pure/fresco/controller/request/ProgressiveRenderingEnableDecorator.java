package com.sj.pure.fresco.controller.request;

import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * For progressive JPEGs only
 * Created by Sui on 2016/12/20.
 */

public class ProgressiveRenderingEnableDecorator implements ImageRequestDecorator {
    @Override
    public ImageRequestBuilder update(ImageRequestBuilder requestBuilder) {
        return requestBuilder.setProgressiveRenderingEnabled(true);
    }
}
