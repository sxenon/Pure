/*
 * Copyright (c) 2017 sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
