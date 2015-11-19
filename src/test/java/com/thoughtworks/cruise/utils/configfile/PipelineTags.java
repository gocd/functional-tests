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

package com.thoughtworks.cruise.utils.configfile;

import java.util.LinkedHashMap;

import static com.thoughtworks.cruise.client.Job.DEFAULT_JOB;

public class PipelineTags {
    public static CruiseConfigTag pipelinesTag() {
        return new SimpleCruiseConfigTag("pipelines");
    }

    public static CruiseConfigTag pipelineTag(final String pipelineName) {
        return new SimpleCruiseConfigTag("pipeline", "name", pipelineName);
    }

    public static CruiseConfigTag resourceTag(final String resource) {
        SimpleCruiseConfigTag resourceTag = new SimpleCruiseConfigTag("resource", resource);
        return new SimpleCruiseConfigTag("resources", "\n" + resourceTag.asString());
    }

    public static CruiseConfigTag anyStageTag() {
        return new CruiseConfigTag() {
            protected String startTag() {
                return "<stage ";
            }

            protected String endTag() {
                return "</stage>";
            }

            protected String asString() {
                throw new UnsupportedOperationException("Unable to insert anyStageTag.");
            }
        };
    }

    public static CruiseConfigTag stageTag(final String stageName) {
        CruiseConfigTag jobTag = jobTag(DEFAULT_JOB);
        SimpleCruiseConfigTag jobsTag = new SimpleCruiseConfigTag("jobs", "\n" + jobTag.asString());
        return new SimpleCruiseConfigTag("stage", "\n" + jobsTag.asString(), "name", stageName);
    }

    public static CruiseConfigTag jobTag(final String name) {
        return new SimpleCruiseConfigTag("job", "name", name);
    }

    public static CruiseConfigTag approvalTag(final String approvalType) {
        return new SimpleCruiseConfigTag("approval", "type", approvalType);
    }

    public static CruiseConfigTag tasksTag() {
        return new SimpleCruiseConfigTag("tasks");
    }

    public static CruiseConfigTag antTag(final String antTarget) {
        return new SimpleCruiseConfigTag("ant", "target", antTarget);
    }

    public static CruiseConfigTag antTagWithBuildFile(final String buildFile) {
        return new SimpleCruiseConfigTag("ant", "buildfile", buildFile);
    }

    public static CruiseConfigTag tabsTag() {
        return new SimpleCruiseConfigTag("tabs");
    }

    public static CruiseConfigTag tabTag(final String tabName, final String path) {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
        attributes.put("name", tabName);
        attributes.put("path", path);
        return new SimpleCruiseConfigTag("tab", attributes);
    }

    public static CruiseConfigTag authTag() {
        return new SimpleCruiseConfigTag("authorization");
    }

    public static CruiseConfigTag userTag(final String username) {
        return new SimpleCruiseConfigTag("user", username);
    }

    public static CruiseConfigTag roleTag(final String rolename) {
        return new SimpleCruiseConfigTag("role", rolename);
    }

    public static CruiseConfigTag agentsTag() {
        return new SimpleCruiseConfigTag("agents");
    }

    public static CruiseConfigTag agentTag(final String name, final String ip, final String uuid) {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
        attributes.put("hostname", name);
        attributes.put("ipaddress", ip);
        attributes.put("uuid", uuid);
        return new SimpleCruiseConfigTag("agent", attributes);
    }

    public static CruiseConfigTag cruiseTag() {
        return new SimpleCruiseConfigTag("cruise");
    }

    public static CruiseConfigTag propertyTag(final String propertyName, final String src, final String xpath) {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
        attributes.put("name", propertyName);
        attributes.put("src", src);
        attributes.put("xpath", xpath);
        return new SimpleCruiseConfigTag("property", attributes);
    }

    public static CruiseConfigTag propertiesTag() {
        return new SimpleCruiseConfigTag("properties");
    }

    public static CruiseConfigTag trackingTool(final String link, final String regex) {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<String, String>();
        attributes.put("link", link);
        attributes.put("regex", regex);
        return new SimpleCruiseConfigTag("trackingtool", attributes);
    }

}