package com.sj.pure.glide;

import com.bumptech.glide.DrawableRequestBuilder;

/**
 * Created by Sui on 2016/12/18.
 */

public interface DrawableRequestDecorator {
    <ModelType> DrawableRequestBuilder<ModelType> update(DrawableRequestBuilder<ModelType> requestBuilder);

}
