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

package com.thoughtworks.cruise.context;

import com.thoughtworks.cruise.plugin.YumHttpRepository;
import com.thoughtworks.cruise.state.ScenarioState;
import org.apache.commons.lang.RandomStringUtils;

public class SetupHttpBasedYumRepo {
    private static final String DEFAULT_HTTP_REPO_URL_NAME_IN_CONFIG = "http_repo1";
    private ScenarioState scenarioState;
    private YumHttpRepository yumRepository;

    public SetupHttpBasedYumRepo(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
        this.yumRepository = new YumHttpRepository(scenarioState);
    }

    @com.thoughtworks.gauge.Step("Setup http based yum repo - setup")
	public void setUp() throws Exception {
        if (!scenarioState.hasPackageRepositoryHttpRepoNamed(DEFAULT_HTTP_REPO_URL_NAME_IN_CONFIG)) {
            scenarioState.pushPackageRepositoryHttpRepoName(DEFAULT_HTTP_REPO_URL_NAME_IN_CONFIG, "pkgrepo-" + RandomStringUtils.randomAlphanumeric(10));
        }
        yumRepository.create(this.scenarioState.packageRepositoryHttpRepoNamed(DEFAULT_HTTP_REPO_URL_NAME_IN_CONFIG));
    }

    @com.thoughtworks.gauge.Step("Setup http based yum repo - teardown")
	public void tearDown() throws Exception {
        yumRepository.remove(this.scenarioState.packageRepositoryHttpRepoNamed(DEFAULT_HTTP_REPO_URL_NAME_IN_CONFIG));
    }
}
