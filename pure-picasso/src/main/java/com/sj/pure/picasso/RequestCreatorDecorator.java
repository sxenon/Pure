package com.sj.pure.picasso;

import com.squareup.picasso.RequestCreator;

/**
 * Created by Sui on 2016/12/20.
 */

public interface RequestCreatorDecorator {
    RequestCreator update(RequestCreator requestCreator);
}
