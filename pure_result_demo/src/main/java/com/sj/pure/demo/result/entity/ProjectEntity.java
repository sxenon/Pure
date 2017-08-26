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
package com.sj.pure.demo.result.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yan Zhenjie on 2016/12/17.
 */
public class ProjectEntity implements Parcelable {

    private String comment;
    private String id;
    private String name;
    private String url;

    public ProjectEntity() {
    }

    public ProjectEntity(String comment, String id, String name, String url) {
        this.comment = comment;
        this.id = id;
        this.name = name;
        this.url = url;
    }

    protected ProjectEntity(Parcel in) {
        comment = in.readString();
        id = in.readString();
        name = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comment);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectEntity> CREATOR = new Creator<ProjectEntity>() {
        @Override
        public ProjectEntity createFromParcel(Parcel in) {
            return new ProjectEntity(in);
        }

        @Override
        public ProjectEntity[] newArray(int size) {
            return new ProjectEntity[size];
        }
    };

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
