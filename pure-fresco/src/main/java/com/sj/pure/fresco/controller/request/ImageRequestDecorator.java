package com.sj.pure.fresco.controller.request;

import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Sui on 2016/12/20.
 */

public interface ImageRequestDecorator {
    ImageRequestBuilder update(ImageRequestBuilder requestBuilder);
}
