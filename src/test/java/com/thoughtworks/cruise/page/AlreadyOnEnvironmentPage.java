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

package com.thoughtworks.cruise.page;

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.EnvironmentState;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import net.sf.sahi.client.Browser;

public class AlreadyOnEnvironmentPage extends OnEnvironmentPage {

	public AlreadyOnEnvironmentPage(ScenarioState scenarioState, CurrentPageState currentPageState, RepositoryState repoState, EnvironmentState envState, ScenarioHelper scenarioHelper,
			Browser browser) {
		super(scenarioState, currentPageState, repoState, envState, scenarioHelper, true, browser);
	}

	public void lookingAtEnvironment(String environmentName) throws Exception {
		super.lookingAtEnvironment(environmentName);
	}

	public void verifyMessageIsPresent(String message) {
		super.verifyMessageIsPresent(message);
	}

	public void clickAddNewEnvironmentLink() throws Exception {
		super.clickAddNewEnvironmentLink();
	}

	public void verifyEnvironmentHasInIt(String message) {
		reloadPage();
		super.verifyEnvironmentHasInIt(message);
	}

	public void verifyPipelineIsVisible(String pipelineName) throws Exception {
		super.verifyPipelineIsVisible(pipelineName);
	}
}
