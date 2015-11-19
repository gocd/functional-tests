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

package com.thoughtworks.cruise.client;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;

public class Job {
    public static final String DEFAULT_JOB = "defaultJob";

    private final String name;
    private final String status;
    private String path;

    public Job(String name, String status, String path) {
        this.name = name;
        this.status = status;
        this.path = path;
    }

    public boolean isScheduled() {
        return equalsIgnoreCase("scheduled", status);
    }

    public boolean isCompleted() {
        return !equalsIgnoreCase("unknown", status)
                && !equalsIgnoreCase("scheduled", status)
                && !equalsIgnoreCase("assigned", status)
                && !equalsIgnoreCase("preparing", status)
                && !equalsIgnoreCase("building", status)
                && !equalsIgnoreCase("completing", status);
    }

    public boolean hasSameName(String name) {
        return equalsIgnoreCase(this.name, name);
    }

    public String getLocator() {
        return path;
    }
}
