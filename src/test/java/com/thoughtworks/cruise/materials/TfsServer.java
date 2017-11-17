/*
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
 */

package com.thoughtworks.cruise.materials;

import org.apache.commons.lang.StringUtils;

public class TfsServer {
    public static String getUrl() {
        return getPropertyOrBomb("TFS_SERVER_URL");
    }

    public static String getDefaultTfsCollectionUrl() {
        return getUrl();
    }

    public static String getPassword() {
        return getPropertyOrBomb("TFS_SERVER_PASSWORD");
    }

    public static String getDomain() {
        return getPropertyOrBomb("TFS_SERVER_DOMAIN");
    }

    public static String getUsername() {
        return getPropertyOrBomb("TFS_SERVER_USERNAME");
    }

    private static String getPropertyOrBomb(String propertyName) {
        String username = System.getenv(propertyName);
        if (StringUtils.isBlank(username)) throw new RuntimeException(String.format("%s is not set", propertyName));
        return username;
    }
}

