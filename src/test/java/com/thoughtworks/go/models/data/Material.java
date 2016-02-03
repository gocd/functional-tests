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

public class Material {

    private String name;
    private String destination;
    private String ignoreFields;
    private boolean autoUpdate;

    public Material(String name, String destination, String ignoreFields, boolean autoUpdate) {
        this.name = name;
        this.destination = destination;
        this.ignoreFields = ignoreFields;
        this.autoUpdate = autoUpdate;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public String getIgnoreFields() {
        return ignoreFields;
    }

    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Material material = (Material) o;

        if (autoUpdate != material.autoUpdate) return false;
        if (name != null ? !name.equals(material.name) : material.name != null) return false;
        if (destination != null ? !destination.equals(material.destination) : material.destination != null)
            return false;
        return ignoreFields != null ? ignoreFields.equals(material.ignoreFields) : material.ignoreFields == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (ignoreFields != null ? ignoreFields.hashCode() : 0);
        result = 31 * result + (autoUpdate ? 1 : 0);
        return result;
    }
}