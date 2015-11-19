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

public enum Timeout {
    ZERO_SECOND(0),
    FIVE_SECONDS(5 * 1000),
    TEN_SECONDS(10 * 1000),
    NINETY_SECONDS(90 * 1000),
    TWENTY_SECONDS(20 * 1000),
    THIRTY_SECONDS(30 * 1000),
    ONE_MINUTE(60 * 1000),
    TWO_MINUTES(120 * 1000),
    THREE_MINUTES(180 * 1000),
    FIVE_MINUTES(300 * 1000), TEN_MINUTES(600 * 1000);

    private long timeout;

    private Timeout(int timeout) {
        this.timeout = timeout;
    }

    public long inMillis() {
        return timeout;
    }
}
