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

package com.thoughtworks.cruise.api;

import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.ScenarioState;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class JobDetailApi {

	protected CruiseResponse cruiseResponse;
	protected final TalkToCruise talkToCruise;
	protected String locator;
	protected final ScenarioState scenarioState;

	public JobDetailApi(TalkToCruise talkToCruise, ScenarioState scenarioState) {
		this.talkToCruise = talkToCruise;	
		this.scenarioState = scenarioState;
	}

	public void verifyReturnCodeIs(int status) {
		Assert.assertThat(cruiseResponse.getStatus(),Is.is(status));
	}

	public void forPipelineLabelStageCounterJob(String pipelineName, String pipelineLabel, String stageName, String stageCounter, String jobName) throws Exception {
		this.locator = String.format("%s/%s/%s/%s/%s", scenarioState.pipelineNamed(pipelineName), pipelineLabel, stageName, stageCounter, jobName);
	}

}
