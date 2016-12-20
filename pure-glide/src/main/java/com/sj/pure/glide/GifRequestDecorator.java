package com.sj.pure.glide;

import com.bumptech.glide.GifRequestBuilder;

/**
 * Created by Sui on 2016/12/20.
 */

public interface GifRequestDecorator {
    <ModelType> GifRequestBuilder<ModelType> update(GifRequestBuilder<ModelType> requestBuilder);
}
