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

public class GitMaterial extends Material {
    private String url;
    private String branch;

    public GitMaterial(String name, String destination, String ignoreFields, boolean autoUpdate, String url, String branch) {
        super(name, destination, ignoreFields, autoUpdate);
        this.url = url;
        this.branch = branch;
    }

    public String getUrl() {
        return url;
    }

    public String getBranch() {
        return branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GitMaterial that = (GitMaterial) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return branch != null ? branch.equals(that.branch) : that.branch == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (branch != null ? branch.hashCode() : 0);
        return result;
    }
}