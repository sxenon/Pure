package com.sj.pure.fresco.controller;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;

/**
 * For animated GIF and WebP images.
 * Created by Sui on 2016/12/20.
 */

public class AutoPlayAnimationsDecorator implements DraweeControllerDecorator {
    @Override
    public PipelineDraweeControllerBuilder update(PipelineDraweeControllerBuilder draweeControllerBuilder) {
        return draweeControllerBuilder.setAutoPlayAnimations(true);
    }
}
