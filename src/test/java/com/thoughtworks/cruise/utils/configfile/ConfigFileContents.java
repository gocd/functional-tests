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

/**
 * Understands how to read and write the content of a config file
 */
public abstract class ConfigFileContents {
    public final String read() {
        return fixSingleClosedTags(currentContents());
    }

    public abstract void write(String newContents);

    public abstract void reset();

    protected abstract String currentContents();

    private String fixSingleClosedTags(String contents) {
        return contents.replaceAll("<(\\w+)([^>]*?)\\s*/>", "<$1$2></$1>");
    }
}
