/*
 * Copyright 2016 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thoughtworks.go.models.data;

public class SvnMaterial extends Material {
    private String url;
    private String username;
    private String password;
    private boolean checkExternals;

    public SvnMaterial(String name, String destination, String ignoreFields, boolean autoUpdate, String url, String username, String password, boolean checkExternals) {
        super(name, destination, ignoreFields, autoUpdate);
        this.url = url;
        this.username = username;
        this.password = password;
        this.checkExternals = checkExternals;
    }

    public boolean isCheckExternals() {
        return checkExternals;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SvnMaterial that = (SvnMaterial) o;

        if (checkExternals != that.checkExternals) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (checkExternals ? 1 : 0);
        return result;
    }
}