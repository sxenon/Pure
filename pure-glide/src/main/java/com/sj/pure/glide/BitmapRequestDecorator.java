package com.sj.pure.glide;

import com.bumptech.glide.BitmapRequestBuilder;

/**
 * Created by Sui on 2016/12/20.
 */

public interface BitmapRequestDecorator {
    <ModelType, TranscodeType> BitmapRequestBuilder<ModelType, TranscodeType> update(BitmapRequestBuilder<ModelType, TranscodeType> requestBuilder);
}
