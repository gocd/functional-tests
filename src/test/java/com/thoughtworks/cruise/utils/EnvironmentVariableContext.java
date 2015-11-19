/*************************GO-LICENSE-START*********************************
 * Copyright 2015 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *************************GO-LICENSE-END***********************************/

package com.thoughtworks.cruise.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @understands a set of variables to be passed to the Agent for a job
 */
public class EnvironmentVariableContext implements Serializable {

    public static class EnvironmentVariable implements Serializable {
        private String name;
        private String value;

        EnvironmentVariable() {
        }

        public EnvironmentVariable(String name, String value) {
            this.name = name;
            this.value = value;
        }


        public String name() {
            return name;
        }

        public String value() {
            return value;
        }

        @Override public String toString() {
            return String.format("%s => %s",name,value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            EnvironmentVariable that = (EnvironmentVariable) o;

            if (!name.equals(that.name)) {
                return false;
            }
            if (!value.equals(that.value)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }
    }

    public static final String CRUISE_ENVIRONMENT_NAME = "CRUISE_ENVIRONMENT_NAME";

    private final List<EnvironmentVariable> properties = new ArrayList<EnvironmentVariable>();

    public EnvironmentVariableContext() {
    }

    public EnvironmentVariableContext(String key, String value) {
        setProperty(key, value);
    }

    public void setProperty(String key, String value) {
        properties.add(new EnvironmentVariable(key, value));
    }

    public void setPropertyWithEscape(String key, String value) {
        properties.add(new EnvironmentVariable(escapeEnvironmentVariable(key), value));
    }

    public String getProperty(String key) {
        for (int i = properties.size()-1; i >= 0 ; i--) {
            EnvironmentVariable property = properties.get(i);
            if (property.name().equals(key)) {
                return property.value();
            }
        }
        return null;
    }

    /**
     * @deprecated This should not be used  - only in tests
     */
    public Map<String, String> getProperties() {
        HashMap<String, String> map = new HashMap<String, String>();
        for (EnvironmentVariable property : properties) {
            map.put(property.name(), property.value());
        }
        return map;
    }

    public static String escapeEnvironmentVariable(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("[^A-Za-z0-9_]", "_").toUpperCase();
    }

    public void addAll(EnvironmentVariableContext another) {
        this.properties.addAll(another.properties);
    }

    @Override public String toString() {
        return "EnvironmentVariableContext{" +
                "properties=" + properties +
                '}';
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }

        return equals((EnvironmentVariableContext) that);
    }

    private boolean equals(EnvironmentVariableContext that) {
        if (!this.properties.equals(that.properties)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return properties != null ? properties.hashCode() : 0;
    }

    public String reportText() {
        throw new RuntimeException("IMPLEMENT ME");
    }

    public void setupRuntimeEnvironment(Map<String, String> env, ConsoleOutputStreamConsumer consumer) {
        for (EnvironmentVariable property : properties) {
            String name = property.name();
            String value = property.value();
            if (value != null) {
                if (env.containsKey(name)) {
                    consumer.stdOutput(
                            "[go] overriding environment variable '" + name + "' with value '" + value + "'");
                } else {
                    consumer.stdOutput(
                            "[go] setting environment variable '" + name + "' to value '" + value + "'");
                }
                env.put(name, value);
            }
        }

    }
}
