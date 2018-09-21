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

import com.google.gson.Gson;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.api.response.*;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import org.hamcrest.core.Is;
import org.junit.Assert;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UsingStageApi {

	private ScenarioState state;
	private TalkToCruise talkToCruise;
	protected UsingStageApi(ScenarioState state, TalkToCruise talkToCruise,
			RepositoryState repositoryState) {
		this.state = state;
		this.talkToCruise = talkToCruise;
	}

	@com.thoughtworks.gauge.Step("Rerun stage <stageName> for current pipeline having pipeline label <pipelineLabel> - Using Stage Api")
	public void rerunStageForCurrentPipelineHavingPipelineLabel(
			String stageName, String pipelineLabel) throws Exception {
		String url = Urls.urlFor(String.format("/run/%s/%s/%s",
				state.currentRuntimePipelineName(), pipelineLabel, stageName));
		System.err.println("posting to " + url);
		CruiseResponse response = talkToCruise.post(url);
		Assert.assertThat(String.format(
				"Got back return code %s-%s from url %s", response.getStatus(),
				response.getBody(), url), response.isSuccess(), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Run stage <stageName> for current pipeline having pipeline label <pipelineLabel> - Using Stage Api")
	public void runStageForCurrentPipeline(String stageName, String pipelineLabel) throws Exception {
		rerunStageForCurrentPipelineHavingPipelineLabel(stageName, pipelineLabel);
	}


	@com.thoughtworks.gauge.Step("Attempt to cancel running stage <stageName> of the current running pipeline should return with status <returnCode>")
	public void attemptToCancelRunningStageOfTheCurrentRunningPipelineShouldReturnWithStatus(
			String stageName, Integer returnCode) throws Exception {
		String url = Urls.urlFor(String.format("/api/stages/%s/%s/cancel",state.currentRuntimePipelineName(),stageName));
		CruiseResponse response = talkToCruise.post(url);
		Assert.assertThat(response.getStatus(), Is.is(returnCode));
	
	}
	
	@com.thoughtworks.gauge.Step("Attempt to cancel stage <stageName> of the given pipeline <pipelineName> should return with status <returnCode>")
	public void attemptToCancelStageOfTheGivenPipelineShouldReturnWithStatus(
			 String stageName, String pipelineName, Integer returnCode) throws Exception {
		String url = Urls.urlFor(String.format("/api/stages/%s/%s/cancel",state.pipelineNamed(pipelineName),stageName));
		CruiseResponse response = talkToCruise.post(url);
		Assert.assertThat(response.getStatus(), Is.is(returnCode));
	
	}
	
	@com.thoughtworks.gauge.Step("Attempt to cancel stage of a non existing pipeline should return with status <returnCode>")
	public void attemptToCancelStageOfANonExistingPipelineShouldReturnWithStatus(
			 Integer returnCode) throws Exception {
		String url = Urls.urlFor(String.format("/api/stages/nonexistingpipeline/invalidStage/cancel"));
		CruiseResponse response = talkToCruise.post(url);
		Assert.assertThat(response.getStatus(), Is.is(returnCode));
	
	}

	@com.thoughtworks.gauge.Step("Verify <count> instances of <pipelineName> <stageName> <jobName> <status> - Using Stage Api")
	public void verifyInstancesOf(String count, String pipelineName, String stageName, String jobName, String status) {
		String actualPipelineName = state.pipelineNamed(pipelineName);

		int offset = 0;
		while (Integer.parseInt(count) - offset > 0) {
			String apiEndPoint = "/api/stages/%s/%s/history" + (offset == 0 ? "" : "/" + offset);
			int pipelineCounter = Integer.parseInt(count) - offset;
			int currentPageSize = Integer.parseInt(count) - offset > 10 ? 10 : Integer.parseInt(count) - offset;

			hitStageHistoryAPIAndVerifyResponse(actualPipelineName, stageName, jobName, status, apiEndPoint, pipelineCounter, Integer.parseInt(count), offset,
					currentPageSize);

			offset = offset + 10;
		}
	}

	@com.thoughtworks.gauge.Step("Verify <pipelineCounter> instance of <pipelineName> <stageName> <jobName> <status> - Using Stage Api")
	public void verifyInstanceOf(String pipelineCounter, String pipelineName, String stageName, String jobName, String status) {
		String actualPipelineName = state.pipelineNamed(pipelineName);
		String apiEndPoint = "/api/stages/%s/%s/instance/%d/1";
		hitStageInstanceAPIAndVerifyResponse(actualPipelineName, stageName, jobName, status, apiEndPoint, Integer.parseInt(pipelineCounter));
	}

	private void hitStageHistoryAPIAndVerifyResponse(String actualPipelineName, String stageName, String jobName, String status, String apiEndPoint,
			int pipelineCounter, int totalInstances, int offset, int currentPageSize) {
		String url = Urls.urlFor(String.format(apiEndPoint, actualPipelineName, stageName));
		CruiseResponse response = talkToCruise.get(url);
		String historyJSONResponse = response.getBody();
		StageHistory stageHistory = new Gson().fromJson(historyJSONResponse, StageHistory.class);
		verifyPaginationForStageHistory(stageHistory, totalInstances, offset, currentPageSize);
		verifyStageHistoryResponse(actualPipelineName, stageName, jobName, status, stageHistory, pipelineCounter);
	}

	private void hitStageInstanceAPIAndVerifyResponse(String actualPipelineName, String stageName, String jobName, String status, String apiEndPoint, int pipelineCounter) {
		String url = Urls.urlFor(String.format(apiEndPoint, actualPipelineName, stageName, pipelineCounter));
		CruiseResponse response = talkToCruise.get(url);
		String instanceJSONResponse = response.getBody();
		StageInstanceModel stageInstance = new Gson().fromJson(instanceJSONResponse, StageInstanceModel.class);
		verifyStageInstanceResponse(actualPipelineName, stageName, jobName, status, stageInstance, pipelineCounter);
	}

	private void verifyPaginationForStageHistory(StageHistory stageHistory, int totalInstances, int offset, int currentPageSize) {
		assertThat(stageHistory.getPagination().getPageSize(), is(10));
		assertThat(stageHistory.getPagination().getTotal(), is(totalInstances));
		assertThat(stageHistory.getPagination().getOffset(), is(offset));
		assertThat(stageHistory.getStages().size(), is(currentPageSize));
	}

	private void verifyStageHistoryResponse(String actualPipelineName, String stageName, String jobName, String status, StageHistory stageHistory,
			int pipelineCounter) {
		for (StageInstance stageInstance : stageHistory.getStages()) {
			assertThat(stageInstance.getName(), is(stageName));
			assertThat(stageInstance.getCounter(), is("1"));
			assertThat(stageInstance.getResult(), is(status));

			assertThat(stageInstance.getPipelineName(), is(actualPipelineName));
			assertThat(stageInstance.getPipelineCounter(), is(pipelineCounter--));

			JobHistoryItem jobHistoryItem = stageInstance.getJobs().get(0);
			assertThat(jobHistoryItem.getName(), is(jobName));
			assertThat(jobHistoryItem.getResult(), is(status));
			if (status.equals("Passed") || status.equals("Failed")) {
				assertThat(jobHistoryItem.getState(), is("Completed"));
			}
		}
	}

	private void verifyStageInstanceResponse(String actualPipelineName, String stageName, String jobName, String status,
			StageInstanceModel stageInstance, int pipelineCounter) {
		assertThat(stageInstance.getName(), is(stageName));
		assertThat(stageInstance.getCounter(), is("1"));
		assertThat(stageInstance.getResult(), is(status));

		assertThat(stageInstance.getPipelineName(), is(actualPipelineName));
		assertThat(stageInstance.getPipelineCounter(), is(pipelineCounter));

		JobInstance jobInstance = stageInstance.getJobs().get(0);
		assertThat(jobInstance.getName(), is(jobName));
		assertThat(jobInstance.getResult(), is(status));
		if (status.equals("Passed") || status.equals("Failed")) {
			assertThat(jobInstance.getState(), is("Completed"));
		}
	}
}
