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

import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import net.sf.sahi.client.Browser;

public class AlreadyOnPipelineDashboardPage extends OnPipelineDashboardPage {

	private final UsingPipelineDashboardAPI dashboardApi;

	public AlreadyOnPipelineDashboardPage(ScenarioState scenarioState, CurrentPageState currentPageState, ScenarioHelper scenarioHelper, Browser browser,
			TalkToCruise talkToCruise, Configuration configuration, RepositoryState repositoryState) {
		super(scenarioState, currentPageState, scenarioHelper, true, browser, talkToCruise, configuration, repositoryState);
		this.dashboardApi = new UsingPipelineDashboardAPI(scenarioState, currentPageState, scenarioHelper, false, talkToCruise, configuration, repositoryState);
		//currentPageState.assertCurrentPageIs(Page.PIPELINE_DASHBOARD);
	}

	@com.thoughtworks.gauge.Step("Looking at pipeline <pipelineName> - Already On Pipeline Dashboard Page")
	public void lookingAtPipeline(String pipelineName) throws Exception {
		dashboardApi.lookingAtPipeline(pipelineName);
	}

	@com.thoughtworks.gauge.Step("Verify pipeline is in group <group> - Already On Pipeline Dashboard Page")
	public void verifyPipelineIsInGroup(String group) throws Exception {
		dashboardApi.verifyPipelineIsInGroup(group);
	}

	@com.thoughtworks.gauge.Step("Verify group <groupName> is not visible - Already On Pipeline Dashboard Page")
	public void verifyGroupIsNotVisible(String groupName) throws Exception {
		dashboardApi.verifyGroupIsNotVisible(groupName);
	}

	@com.thoughtworks.gauge.Step("Verify group <groupName> is visible")
	public void verifyGroupIsVisible(String groupName) throws Exception {
		dashboardApi.verifyGroupIsVisible(groupName);
	}

	@com.thoughtworks.gauge.Step("Turn on autoRefresh - Already On Pipeline Dashboard Page")
	public void turnOnAutoRefresh() throws Exception {

	}
	
	@com.thoughtworks.gauge.Step("Verify pipeline <pipelineName> is visible - Already On Pipeline Dashboard Page")
	public void verifyPipelineIsVisible(String pipelineName) throws Exception {
		dashboardApi.verifyPipelineIsVisible(pipelineName);
	}
	
	@com.thoughtworks.gauge.Step("Verify pipeline <pipelineName> is not visible - Already On Pipeline Dashboard Page")
	public void verifyPipelineIsNotVisible(String pipelineName) throws Exception {
		dashboardApi.verifyPipelineIsNotVisible(pipelineName);
	}

    @com.thoughtworks.gauge.Step("Verify stage <indexOfStage> is <stageStatus> on pipeline with label <pipelineLabel> - Already On Pipeline Dashboard Page")
	public void verifyStageIsOnPipelineWithLabel(Integer indexOfStage, String stageStatus, String pipelineLabel) throws Exception {
		dashboardApi.verifyStageIsOnPipelineWithLabel(indexOfStage, stageStatus, pipelineLabel);
    }
	
}
