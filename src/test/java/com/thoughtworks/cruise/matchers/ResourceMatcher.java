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

package com.thoughtworks.cruise.matchers;

import com.thoughtworks.cruise.util.SystemUtil;
import org.hamcrest.Description;
import org.junit.matchers.TypeSafeMatcher;

public abstract class ResourceMatcher extends TypeSafeMatcher<String> {

    public static final ResourceMatcher RESOURCE_EXISTS = new ResourceMatcher() {
        public boolean matchesSafely(String item) {
            return true;
        }

        public void describeTo(Description description) {
            description.appendText("resource exists");
        }
    };

    public static final ResourceMatcher VALID_CONFIG_FILE = new ResourceMatcher() {
        private String current;

        public boolean matchesSafely(String item) {
            this.current = item;
            return !item.contains("FlashMessageLauncher.error(");
        }

        public void describeTo(Description description) {
            System.err.println("config file:\n" + current);
            description.appendText("valid config file");
        }
    };

    public static TypeSafeMatcher<Boolean> portIsOccupied(final int port) {
        return new TypeSafeMatcher<Boolean>() {
            public boolean matchesSafely(Boolean exptected) {
                return SystemUtil.isLocalPortOccupied(port) == exptected;
            }

            public void describeTo(Description description) {
                description.appendText("port [" + port + "] is free, probably means "
                        + "the application did start up correcly");
            }
        };
    }

}
