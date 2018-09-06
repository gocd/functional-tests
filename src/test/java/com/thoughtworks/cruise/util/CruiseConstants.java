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

package com.thoughtworks.cruise.util;

import java.util.Date;

public class CruiseConstants {
    public static final Date NEVER = new Date(0, 0, 1);
    public static final String PROJECT_STATUS_IN_BUILDING = "Building";
    /**
     * This will force the browser to clear the cache only for this page.
     * If any other pages need to clear the cache, we might want to move this
     * logic to an intercepter.
     */
    public static final String CACHE_CONTROL = "max-age=1, no-cache";
    public static final String DEFAULT_APPROVED_BY = "cruise";
    public static final String BASE_URL_PATTERN = "^(.+://.+?)/";
    public static final String ERROR_FOR_PAGE = "errorMessage";
    public static final String ERROR_FOR_JSON = "error";

    /*this is the error message to be displayed everypage.*/
    public static final String ERROR_FOR_GLOBAL_MESSAGE = "global_error_message";
    public static final String SUCCESS_MESSAGE = "success_message";
    public static final String RESPONSE_CHARSET = "text/plain; charset=utf-8";
    public static final String CRUISE_PIPELINE_LABEL = "cruise_pipeline_label";
    public static final String CRUISE_PIPELINE_COUNTER = "cruise_pipeline_counter";
    public static final String CRUISE_STAGE_COUNTER = "cruise_stage_counter";
    public static final String CRUISE_AGENT = "cruise_agent";
    public static final String CRUISE_RESULT = "cruise_job_result";
    public static final String CRUISE_JOB_DURATION = "cruise_job_duration";
    public static final String CRUISE_JOB_ID = "cruise_job_id";
    public static final String CRUISE_DATE_PATTERN = "cruise_date_pattern";
    public static final String CRUISE_TIMESTAMP = "cruise_timestamp_";
    public static final String THOUGHTWORKS_LICENSE_URL =
            "http://studios.thoughtworks.com/cruise/buy-now";
    public static final String CRUISE_ENTERPRISE = "<a href='" + THOUGHTWORKS_LICENSE_URL
            + "' target='_license'>Cruise Enterprise Edition</a>";
    public static final String CRUISE_FREE = "<a href='" + THOUGHTWORKS_LICENSE_URL
            + "' target='_license'>Cruise Free Edition</a>";
    public static final String CRUISE_LICENSE = "<a href='" + THOUGHTWORKS_LICENSE_URL
            + "' target='_license'>license</a>";
    public static final String EXPIRY_DATE = "expiry_date";
    public static final String MAX_AGENTS = "max_agents";
    public static final String MAX_USERS = "max_users";
    public static final String EDITION = "edition";
    public static final String SUCCESSFULLY_CHANGED_LICENSE = "Your license has been updated successfully.";

    public static final int CONFIG_SCHEMA_VERSION = 113;

    public static final String APPROVAL_SUCCESS = "success";
    public static final String APPROVAL_MANUAL = "manual";
    public static final int PUBLISH_MAX_RETRIES = 3;
    public static final String CURRENT_REVISION = System.getenv("GO_VERSION") != null ? System.getenv("GO_VERSION") : "16.1.0";
    public static final String TEST_EMAIL_SUBJECT = "Cruise Email Notification";
    public static final int DEFAULT_TIMEOUT = 60 * 1000;
    public static final String LICENSE_LIMITATION_ERROR = "License limitation error";
    public static final long MEGA_BYTE = 1024 * 1024;
    public static final String USE_COMPRESSED_JAVASCRIPT = "rails.use.compressed.js";
    public static final String I18N_CACHE_LIFE = "cruise.i18n.cache.life";
    public static final String SAHI_TESTS = System.getenv("SAHI_TESTS") != null ? System.getenv("SAHI_TESTS") : "Y";
    public static final String WEBDRIVER_TESTS = System.getenv("WEBDRIVER_TESTS") != null ? System.getenv("WEBDRIVER_TESTS") : "N";
    public static final String FILE_BASED_PLUGIN_ID = "cd.go.authentication.passwordfile";
    public static final String LDAP_AUTHENTICATION_PLUGIN_ID = "cd.go.authentication.ldap";

    public static final String apiV1 = "application/vnd.go.cd.v1+json";
    public static final String apiV2 = "application/vnd.go.cd.v2+json";
    public static final String apiV3 = "application/vnd.go.cd.v3+json";
    public static final String apiV4 = "application/vnd.go.cd.v4+json";

}
