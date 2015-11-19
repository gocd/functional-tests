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

package com.thoughtworks.cruise.utils.matchers;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.io.IOException;

public class HttpMatcher {
    public static TypeSafeMatcher<HttpClient> get200(final GetMethod get) {
        return new TypeSafeMatcher<HttpClient>() {
            private int i;

            public boolean matchesSafely(HttpClient client) {
                try {
                    i = client.executeMethod(get);
                    return i == 200;
                } catch (IOException e) {
                    return false;
                }
            }

            public void describeTo(Description description) {
                description.appendText("expected 200 but the response is " + i);
            }
        };
    }
}
