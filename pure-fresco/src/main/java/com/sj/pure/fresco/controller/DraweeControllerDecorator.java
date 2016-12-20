package com.sj.pure.fresco.controller;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;

/**
 * Created by Sui on 2016/12/20.
 */

public interface DraweeControllerDecorator {
    PipelineDraweeControllerBuilder update(PipelineDraweeControllerBuilder draweeControllerBuilder);
}
