/*
 * Copyright (c) 2017  sxenon
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

package com.sj.pure.okhttp3.converter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;

/**
 * Converter to list of bean
 * Created by Sui on 2017/7/25.
 */

public abstract class BLConverter<T> implements LConverter<T> {
    private final Type mType;

    public BLConverter(Type type) {
        mType = type;
    }

    @Override
    public List<T> convertResponse(Response response) throws IOException {
        String beanListString = new StringConverter().convertResponse(response);
        if (beanListString == null) {
            return null;
        }
        return string2BeanList(beanListString, mType);
    }

    public abstract List<T> string2BeanList(String beanListString, Type type);
}
