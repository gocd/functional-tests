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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.api.response.AgentInformation;
import com.thoughtworks.cruise.api.response.AgentJobRunHistory;
import com.thoughtworks.cruise.api.response.JobInstance;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.CruiseConstants;
import com.thoughtworks.cruise.utils.Assertions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UsingAgentsApi {
	private CruiseResponse cruiseResponse;
	private final ScenarioState state;
	private final TalkToCruise talkToCruise;
	private String apiv2 = "application/vnd.go.cd.v2+json";
	private String contentType = "application/json";
	
	public UsingAgentsApi(ScenarioState state, TalkToCruise talkToCruise) {
		this.state = state;
		this.talkToCruise = talkToCruise;
	}

	@com.thoughtworks.gauge.Step("Enable <uuid>")
	public void enable(String uuid) {		
		operating(uuid, "Enabled");
	}

	private void operating(String uuid, String action) {
		String url = Urls.urlFor(String.format("/api/agents/%s",uuid));
		if(action == "delete"){
			this.cruiseResponse = talkToCruise.delete(url, true, CruiseConstants.apiV2);
		}else {
			String body = "{\"agent_config_state\": \"" + action + "\"}";
			this.cruiseResponse = talkToCruise.patch(url, body);
		}
	}

	public void disable(String uuid) {		 
		operating(uuid, "Disabled");
	}

	@com.thoughtworks.gauge.Step("Verify return code is <status>")
	public void verifyReturnCodeIs(int status) {
		if (cruiseResponse.getStatus() != status) {
			Assert.fail(String.format("Expected status: %s, Actual: %s", status, cruiseResponse));
		}
	}

	@com.thoughtworks.gauge.Step("Delete <uuid> - Using Agents Api")
	public void delete(String uuid) throws Exception {
		operating(uuid, "delete");
	}

	public AgentInformation[] listInformationOfAllAgents() throws JSONException {
		String url = Urls.urlFor("/api/agents");
		this.cruiseResponse = talkToCruise.get(url);
		String jsonResponse = this.cruiseResponse.getBody();
		JSONObject jsonObj = new JSONObject(jsonResponse);
		JSONArray agents = jsonObj.getJSONObject("_embedded").getJSONArray("agents");

		return new Gson().fromJson(agents.toString(), AgentInformation[].class);
	}

	@com.thoughtworks.gauge.Step("Verify <count> instances of <pipelineName> <stageName> <jobName> <status> - Using Agents Api")
	public void verifyInstancesOf(String count, String pipelineName, String stageName, String jobName, String status) {
		String actualPipelineName = state.pipelineNamed(pipelineName);

		String agentUUID = getAgentUUIDForTheOnlyIdleAgent();

		int offset = 0;
		while (Integer.parseInt(count) - offset > 0) {
			String apiEndPoint = "/api/agents/%s/job_run_history" + (offset == 0 ? "" : "/" + offset);
			int pipelineCounter = Integer.parseInt(count) - offset;
			int currentPageSize = Integer.parseInt(count) - offset > 10 ? 10 : Integer.parseInt(count) - offset;

			hitAgentJobRunHistoryAPIAndVerifyResponse(agentUUID, actualPipelineName, stageName, jobName, status, apiEndPoint, pipelineCounter,
					Integer.parseInt(count), offset, currentPageSize);

			offset = offset + 10;
		}
	}

	private String getAgentUUIDForTheOnlyIdleAgent() {
		String agentUUID = null;

		try {
			AgentInformation[] agents = listInformationOfAllAgents();

		for (AgentInformation agent : agents) {
			if (!(agent.getAgent_state().equals("Missing") || agent.getAgent_state().equals("Disabled"))) {
				agentUUID = agent.getUuid();
				break;
			}
		}
		}catch(Exception e){e.printStackTrace();}

		if (agentUUID == null) {
			throw new RuntimeException("could not find idle agent");
		}
		return agentUUID;
	}

	@com.thoughtworks.gauge.Step("Verify last job <pipelineName> <stageName> <jobName> <status>")
	public void verifyLastJob(String pipelineName, String stageName, String jobName, String status) {
		String actualPipelineName = state.pipelineNamed(pipelineName);

		String agentUUID = getAgentUUIDForTheOnlyIdleAgent();

		String apiEndPoint = "/api/agents/%s/job_run_history";
		String url = Urls.urlFor(String.format(apiEndPoint, agentUUID));
		CruiseResponse response = talkToCruise.get(url);
		String historyJSONResponse = response.getBody();
		AgentJobRunHistory agentJobRunHistory = new Gson().fromJson(historyJSONResponse, AgentJobRunHistory.class);

		JobInstance jobInstance = agentJobRunHistory.getJobs().get(0);
		verifyJobInstance(actualPipelineName, stageName, jobName, status, 1, jobInstance);
	}

	private void hitAgentJobRunHistoryAPIAndVerifyResponse(String agentUUID, String actualPipelineName, String stageName, String jobName, String status,
			String apiEndPoint, int pipelineCounter, int totalInstances, int offset, int currentPageSize) {
		String url = Urls.urlFor(String.format(apiEndPoint, agentUUID));
		CruiseResponse response = talkToCruise.get(url);
		String historyJSONResponse = response.getBody();
		AgentJobRunHistory agentJobRunHistory = new Gson().fromJson(historyJSONResponse, AgentJobRunHistory.class);
		verifyPaginationForAgentJobRunHistory(agentJobRunHistory, totalInstances, offset, currentPageSize);
		verifyAgentJobRunHistoryResponse(actualPipelineName, stageName, jobName, status, agentJobRunHistory, pipelineCounter);
	}

	private void verifyPaginationForAgentJobRunHistory(AgentJobRunHistory agentJobRunHistory, int totalInstances, int offset, int currentPageSize) {
		assertThat(agentJobRunHistory.getPagination().getPageSize(), is(10));
		assertThat(agentJobRunHistory.getPagination().getTotal(), is(totalInstances));
		assertThat(agentJobRunHistory.getPagination().getOffset(), is(offset));
		assertThat(agentJobRunHistory.getJobs().size(), is(currentPageSize));
	}

	private void verifyAgentJobRunHistoryResponse(String actualPipelineName, String stageName, String jobName, String status,
			AgentJobRunHistory agentJobRunHistory, int pipelineCounter) {
		for (JobInstance jobInstance : agentJobRunHistory.getJobs()) {
			verifyJobInstance(actualPipelineName, stageName, jobName, status, pipelineCounter, jobInstance);
			pipelineCounter--;
		}
	}

	private void verifyJobInstance(String actualPipelineName, String stageName, String jobName, String status, int pipelineCounter, JobInstance jobInstance) {
		assertThat(jobInstance.getName(), is(jobName));
		assertThat(jobInstance.getResult(), is(status));
		if (status.equals("Passed") || status.equals("Failed")) {
			assertThat(jobInstance.getState(), is("Completed"));
		}

		assertThat(jobInstance.getPipelineName(), is(actualPipelineName));
		assertThat(jobInstance.getPipelineCounter(), is(pipelineCounter));
		assertThat(jobInstance.getStageName(), is(stageName));
		assertThat(jobInstance.getStageCounter(), is("1"));
	}
}
