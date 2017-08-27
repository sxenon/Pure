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

import java.util.List;

/**
 * UserInfoEntity
 * Created by Yan Zhenjie on 2016/12/17.
 */
public class UserInfoEntity implements Parcelable {

    private String blog;
    private String name;
    private String url;
    private List<ProjectEntity> projectList;

    public UserInfoEntity() {
    }

    public UserInfoEntity(String blog, String name, String url, List<ProjectEntity> projectList) {
        this.blog = blog;
        this.name = name;
        this.url = url;
        this.projectList = projectList;
    }

    protected UserInfoEntity(Parcel in) {
        blog = in.readString();
        name = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(blog);
        dest.writeString(name);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfoEntity> CREATOR = new Creator<UserInfoEntity>() {
        @Override
        public UserInfoEntity createFromParcel(Parcel in) {
            return new UserInfoEntity(in);
        }

        @Override
        public UserInfoEntity[] newArray(int size) {
            return new UserInfoEntity[size];
        }
    };

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
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

    public List<ProjectEntity> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectEntity> projectList) {
        this.projectList = projectList;
    }
}
