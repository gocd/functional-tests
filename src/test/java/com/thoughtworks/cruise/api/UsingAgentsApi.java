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
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.cglib.transform.impl.InterceptFieldCallback;
import net.sf.sahi.client.ElementStub;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UsingAgentsApi {
	private CruiseResponse cruiseResponse;
	private final ScenarioState state;
	private final TalkToCruise talkToCruise;
	
	public UsingAgentsApi(ScenarioState state, TalkToCruise talkToCruise) {
		this.state = state;
		this.talkToCruise = talkToCruise;
	}

	@com.thoughtworks.gauge.Step("Enable <uuid>")
	public void enable(String uuid) {		
		operating(uuid, "Enabled", "{\"agent_config_state\": \"Enabled\"}");
	}

	private void operating(String uuid, String action, String body) {
		String url = Urls.urlFor(String.format("/api/agents/%s",uuid));
		if(action == "delete"){
			this.cruiseResponse = talkToCruise.delete(url, true, CruiseConstants.apiV4);
		}else {
			this.cruiseResponse = talkToCruise.patch(url, body);
		}
	}

	public void disable(String uuid) {		 
		operating(uuid, "Disabled", "{\"agent_config_state\": \"Disabled\"}");
	}

	@com.thoughtworks.gauge.Step("Verify return code is <status>")
	public void verifyReturnCodeIs(int status) {
		if (cruiseResponse.getStatus() != status) {
			Assert.fail(String.format("Expected status: %s, Actual: %s", status, cruiseResponse));
		}
	}

	@com.thoughtworks.gauge.Step("Delete <uuid> - Using Agents Api")
	public void delete(String uuid) throws Exception {
		operating(uuid, "delete","");
	}


	@com.thoughtworks.gauge.Step("Verify any operation on agents return code <code> - Using Agents Api")
	public void verifyOperatePermission(String code) throws Exception {
		disable(getAgentUUIDForTheOnlyIdleAgent());
		assertTrue(cruiseResponse.getStatus() == Integer.parseInt(code));
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

	public Integer getLiveAgentsCount() {
		Integer count = 0;
		try {
			AgentInformation[] agents = listInformationOfAllAgents();

			for (AgentInformation agent : agents) {
				if (agent.getAgent_state().equals("Idle") && agent.getAgent_config_state().equals("Enabled")) {
					++count;
				}
			}
		}catch(Exception e){e.printStackTrace();}

		return count;
	}

	public Integer getAgentsCountWithStatus(String status) {
		Integer count = 0;
		try {
			AgentInformation[] agents = listInformationOfAllAgents();

			for (AgentInformation agent : agents) {
				if (agent.getAgent_state().equals(status)) {
					++count;
				}
			}
		}catch(Exception e){e.printStackTrace();}

		return count;
	}

	public Integer getAgentsCount() {
		Integer count = 0;
		try {
			AgentInformation[] agents = listInformationOfAllAgents();
			count = agents.length;
		}catch(Exception e){e.printStackTrace();}

		return count;
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

	@com.thoughtworks.gauge.Step("Verify the pipeline is building only on agents in <environmentName> - Using Agents API")
	public void verifyAgentWithAnEnvironmentIsBuilding(String environmentName) throws Exception {

		AgentInformation[] agents = listInformationOfAllAgents();
		for (AgentInformation agent : agents) {
			if(agent.getAgent_state().equals("Building")){assertTrue(agent.getEnvironments().contains(environmentName));}
		}
	}

	@com.thoughtworks.gauge.Step("Add environment <environmentName> to all agents - Using Agents API")
	public void addEnvironmentToAllAgents(String environmentName) throws Exception {

		AgentInformation[] agents = listInformationOfAllAgents();
		for (AgentInformation agent : agents) {
				operating(agent.getUuid(), "", "{\"environments\": [\"" + environmentName + "\"]}");
				assertTrue(cruiseResponse.getStatus() == 200);
			}
	}

	@com.thoughtworks.gauge.Step("Add resource <resource> to an agent - Using Agents API")
	public void addEnvironmentToAnAgent(String resource) throws Exception {

		AgentInformation[] agents = listInformationOfAllAgents();
		for (AgentInformation agent : agents) {
			if(agent.getResources().isEmpty() && agent.getAgent_state().equals("Idle")) {
				operating(agent.getUuid(), "", "{\"resources\": [\"" + resource + "\"]}");
				assertTrue(cruiseResponse.getStatus() == 200);
				break;
			}
		}
	}

	@com.thoughtworks.gauge.Step("Add environment <environmentName> to any <count> Idle agents - Using Agents API")
	public void addEnvironmentToIdleAgents(String environmentName,
									    String idleCount) throws Exception {

		int count = 0;
		AgentInformation[] agents = listInformationOfAllAgents();

		for (AgentInformation agent : agents) {
			if (agent.getAgent_state().equals("Idle") && agent.getAgent_config_state().equals("Enabled")) {
				operating(agent.getUuid(), "", "{\"environments\": [\"" + environmentName + "\"]}");
				assertTrue(cruiseResponse.getStatus() == 200);
				count +=1;
			}
			if (count == Integer.parseInt(idleCount)) break;
		}
		Assert.assertTrue("Add environment "+environmentName+" to "+idleCount+" agents failed",count == Integer.parseInt(idleCount));

	}

	@com.thoughtworks.gauge.Step("Add resource <resource> to all agents - Using Agents API")
	public void addResourceToAllAgents(String resource) throws Exception {

		AgentInformation[] agents = listInformationOfAllAgents();
		for (AgentInformation agent : agents) {
			operating(agent.getUuid(), "", "{\"resources\": [\"" + resource + "\"]}");
			assertTrue(cruiseResponse.getStatus() == 200);
		}
	}


	@com.thoughtworks.gauge.Step("Verify the <uuid> agent has <space> free space - Using Agents API")
	public void verifyAgentSpace(String uuid,
									   String space) throws Exception {
		AgentInformation[] agents = listInformationOfAllAgents();

		for (AgentInformation agent : agents) {
			if (agent.getUuid().equals(uuid)) {
				assertTrue(agent.getFree_space().equals(space));
			}
		}
	}

	@com.thoughtworks.gauge.Step("Verify an agent has environments <environment> - Using Agents API")
	public void verifyAnAgentIsInEnvironment(String environment) throws Exception {
		AgentInformation[] agents = listInformationOfAllAgents();

		boolean hasEnv = false;
		for (AgentInformation agent : agents) {
			if (agent.getEnvironments().contains(environment)) {
				hasEnv = true;
			}
		}
		assertTrue("No agents is associated with environment "+environment,hasEnv);
	}

	@com.thoughtworks.gauge.Step("Enable an agent showing status <status> - Using Agents API")
	public void enableAgentWithStatus(String status) throws Exception {
		AgentInformation[] agents = listInformationOfAllAgents();

		for (AgentInformation agent : agents) {
			if (agent.getAgent_config_state().equals(status)) {
				enable(agent.getUuid());
			}
		}
	}

	@com.thoughtworks.gauge.Step("Disable an agent showing status <status> - Using Agents API")
	public void disableAgentWithStatus(String status) throws Exception {
		AgentInformation[] agents = listInformationOfAllAgents();

		for (AgentInformation agent : agents) {
			if (agent.getAgent_state().equals(status)) {
				disable(agent.getUuid());
			}
		}
	}

	@com.thoughtworks.gauge.Step("Verify has <count> idle agents - Using Agents API")
	public void verifyIdleAgentsCount(String count) throws Exception {
		assertTrue(getLiveAgentsCount() == Integer.parseInt(count));
	}

	@com.thoughtworks.gauge.Step("Wait for <count> agent to show status <status> - Using Agents API")
	public void waitForAgentsCountWithStatus(String count, String status) throws Exception {
		waitForAgentStatusWithTimeout(status, Integer.parseInt(count), Timeout.ONE_MINUTE);
	}

	@com.thoughtworks.gauge.Step("Disabling a <state> and <configstate> agent should return <returnCode>")
	public void disablingAAgentShouldReturn(String state, String configstate, Integer returnCode)
			throws Exception {
		AgentInformation[] agents = listInformationOfAllAgents();

		for (AgentInformation agent : agents) {
			if (agent.getAgent_state().equals(state) && agent.getAgent_config_state().equals(configstate)) {
				disable(agent.getUuid());
				break;
			}
		}
		verifyReturnCodeIs(returnCode);
	}

	@com.thoughtworks.gauge.Step("Enabling a <status> and <config_state> agent should return <returnCode>")
	public void enablingAAgentShouldReturn(String status, String config_state, Integer returnCode)
			throws Exception {
		AgentInformation[] agents = listInformationOfAllAgents();

		for (AgentInformation agent : agents) {
			if (agent.getAgent_state().equals(status) && agent.getAgent_config_state().equals(config_state)) {
				enable(agent.getUuid());
				break;
			}
		}
		verifyReturnCodeIs(returnCode);
	}

	@com.thoughtworks.gauge.Step("Deleting a <status> and <config_state> agent should return <returnCode>")
	public void deletingAAgentShouldReturn(String status, String config_state, Integer returnCode)
			throws Exception {
		AgentInformation[] agents = listInformationOfAllAgents();

		for (AgentInformation agent : agents) {
			if (agent.getAgent_state().equals(status) && agent.getAgent_config_state().equals(config_state)) {
				delete(agent.getUuid());
				break;
			}
		}
		verifyReturnCodeIs(returnCode);
	}

	@com.thoughtworks.gauge.Step("Verify <configStatus> agent count is <count>")
	public void verifyAgentCountIs(final String configStatus, final int count)
			throws Exception {
		int actual=0;
		AgentInformation[] agents = listInformationOfAllAgents();

		for (AgentInformation agent : agents) {
			if (agent.getAgent_config_state().equals(configStatus)) {
				actual +=1;
			}
		}

		assertTrue("Expected number of agents in state "+configStatus+" is "+actual+" does not match expected count "+count, actual == count);
	}

	@com.thoughtworks.gauge.Step("Verify there are <count> agents with state <state>")
	public void verifyAgentWithStateCountIs( final int count, final String state)
			throws Exception {
		int actual=0;
		AgentInformation[] agents = listInformationOfAllAgents();

		for (AgentInformation agent : agents) {
			if (agent.getAgent_state().equals(state)) {
				actual +=1;
			}
		}

		assertTrue("Expected number of agents in state "+state+" is "+actual+" does not match expected count "+count, actual == count);
	}


	private Boolean waitForAgentStatusWithTimeout(final String status, final Integer count,
												 final Timeout timeout) {
		return Assertions.waitFor(timeout, new Assertions.Function<Boolean>() {
			public Boolean call() {
				return getAgentsCountWithStatus(status) == count;
			}
		});
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
