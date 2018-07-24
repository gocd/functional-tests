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
import com.thoughtworks.cruise.api.response.JobHistoryItem;
import com.thoughtworks.cruise.api.response.PipelineHistory;
import com.thoughtworks.cruise.api.response.PipelineInstance;
import com.thoughtworks.cruise.api.response.StageHistoryItem;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.materials.Repository;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.CruiseConstants;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UsingPipelineApi {
	private String scheduleUrl;
	private final ScenarioState state;
	private final TalkToCruise talkToCruise;
    private RepositoryState repositoryState;
    private Map<String, String> revisions;
    private Map<String, String> variables;
    private boolean updateMaterialBeforeSchedule;
	private String pipelineName;
	
	protected UsingPipelineApi(ScenarioState state, TalkToCruise talkToCruise, RepositoryState repositoryState){
		this.state = state;
		this.talkToCruise = talkToCruise;
        this.repositoryState = repositoryState;
    }

    @com.thoughtworks.gauge.Step("Using <which> last revision of <materialName>")
	public void usingLastRevisionOf(String which, String materialName) throws Exception {
        String commit = repositoryState.commitRevision(which, materialName, state.pipelineNamed(pipelineName));
        usingRevisionOf(commit, materialName);
		this.updateMaterialBeforeSchedule = false;
    }
    

    @com.thoughtworks.gauge.Step("For pipeline <pipelineName> - Using pipeline api")
	public void forPipeline(String pipelineName) throws Exception {
		this.pipelineName = pipelineName;
		forPipelineNamed(state.pipelineNamed(pipelineName));
		this.updateMaterialBeforeSchedule = true;
	}

	@com.thoughtworks.gauge.Step("Schedule should return code <code>")
	public void scheduleShouldReturnCode(Integer code) throws Exception {
		CruiseResponse response = schedulePipeline();
		Assert.assertThat("Schedule failed " + response.getBody(), response.getStatus(), Is.is(code));
	}
	
	@com.thoughtworks.gauge.Step("Schedule should fail with <code> and message <bodyFragment>")
	public void scheduleShouldFailWithAndMessage(Integer code, String bodyFragment) throws Exception {
        CruiseResponse response = schedulePipeline();
        Assert.assertThat("Schedule failed " + response.getBody(), response.getStatus(), Is.is(code));
        Assert.assertThat(bodyFragment, Matchers.containsString(bodyFragment));
    }

    private CruiseResponse schedulePipeline() throws UnsupportedEncodingException, JSONException {
		StringRequestEntity requestEntity = new StringRequestEntity(
				"{\"environment_variables\": ["+ getVariablesToTriggerWith(variables) + "]," +
						"\"materials\": ["+ getMaterialsToTriggerWith(revisions) + "], \"update_materials_before_scheduling\": " + this.updateMaterialBeforeSchedule + " }",
				"application/json",
				"UTF-8");

		CruiseResponse response = talkToCruise.post(scheduleUrl, requestEntity, "", CruiseConstants.apiV1);
        return response;
    }

	private String getMaterialsToTriggerWith(Map<String, String> map) throws JSONException {
		if (map.isEmpty()) { return ""; }

		String materials = "";
		for(String key : map.keySet()){
			materials = materials.concat(String.format("{ \"fingerprint\": \"%s\", \"revision\": \"%s\" },",getMaterialFingerprint(key), map.get(key)));
		}

		return materials.substring(0, materials.length()-1);

	}

	private String getMaterialFingerprint(String material) throws JSONException {

		CruiseResponse response = talkToCruise.get(Urls.urlFor("/api/config/materials"));
		JSONArray jsonArray = new JSONArray(response.getBody());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			if (obj.get("description").toString().contains(material)) {
				return obj.get("fingerprint").toString();
			}
		}
		return "";
	}

	private String getVariablesToTriggerWith(Map<String, String> map) {

		if (map.isEmpty()){ return ""; }
		String env_variables = "";
		for(String key : map.keySet()){
			env_variables = env_variables.concat(String.format("{ \"name\": \"%s\", \"value\": \"%s\" },",key, map.get(key)));
		}
		return env_variables.substring(0, env_variables.length()-1);
	}
	
	public void unlockShouldReturn(String pipelineName, Integer code, String shouldHaveMessage) throws Exception {
		String url = Urls.urlFor(String.format("/api/pipelines/%s/releaseLock", pipelineName));
		CruiseResponse response = talkToCruise.post(url);
		Assert.assertThat(response.getBody(), Matchers.containsString(shouldHaveMessage));
		Assert.assertThat(response.getStatus(), Matchers.is(code));
	}

	public void waitUntilUnlockReturns(String pipelineName, final Integer code, final String shouldHaveMessage) throws Exception {
		final String url = Urls.urlFor(String.format("/api/pipelines/%s/releaseLock", pipelineName));
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			
			@Override
			public boolean call() throws Exception {
				CruiseResponse response = talkToCruise.post(url);
				return response.getBody().contains(shouldHaveMessage) && response.getStatus() == code;
			}
		});
	}
	
	@com.thoughtworks.gauge.Step("For pipeline named <runtimePipelineName>")
	public void forPipelineNamed(String runtimePipelineName) throws Exception {
		this.scheduleUrl = Urls.urlFor(String.format("/api/pipelines/%s/schedule",runtimePipelineName));
		this.revisions = new HashMap<String,String>();
		this.variables = new HashMap<String,String>();
		this.updateMaterialBeforeSchedule = true;
	}

	@com.thoughtworks.gauge.Step("Using <revision> revision of <material>")
	public void usingRevisionOf(String revision, String material) throws Exception {
		this.revisions.put(state.expand(material), state.expand(revision));
		this.updateMaterialBeforeSchedule = false;
	}
	
	@com.thoughtworks.gauge.Step("Using latest revision of material <materialName> for pipeline <pipelineName>")
	public void usingLatestRevisionOfMaterialForPipeline(String materialName, String pipelineName) throws Exception {
		Repository repo = repositoryState.getRepoByMaterialName(state.pipelineNamed(pipelineName), materialName);
		String revisionNumber = repo.latestRevision().revisionNumber();
		this.revisions.put(state.expand(materialName), state.expand(revisionNumber));
		this.updateMaterialBeforeSchedule = false;
	}
	
	@com.thoughtworks.gauge.Step("With variable <name> set to <value>")
	public void withVariableSetTo(String name, String value) throws Exception {
		this.variables.put(name, value);
		this.updateMaterialBeforeSchedule = true;
	}

	public void usingStageOfUpstreamPipelineWithMaterialNameAndCounter(String stageName,String pipelineName,String materialName, Integer counter)
			throws Exception {
		String actualPipelineName = state.pipelineNamed(pipelineName);
		usingRevisionOf(String.format("%s/%s/%s/1", actualPipelineName, counter, stageName), materialName);
	}

	@com.thoughtworks.gauge.Step("Using stage <stageName> of upstream pipeline <pipelineName> with counter <counter>")
	public void usingStageOfUpstreamPipelineWithCounter(String stageName,String pipelineName,Integer counter) throws Exception {
		usingStageOfUpstreamPipelineWithMaterialNameAndCounter(stageName,pipelineName,state.pipelineNamed(pipelineName),counter);
		this.updateMaterialBeforeSchedule = false;
	}

	@com.thoughtworks.gauge.Step("Verify unauthorized to unlock <pipelineName>")
	public void verifyUnauthorizedToUnlock(String pipelineName) throws Exception {
		unlockShouldReturn(state.pipelineNamed(pipelineName), 403, "user does not have operate permission on the pipeline");
	}

	@com.thoughtworks.gauge.Step("Verify unlocking <pipelineName> is not acceptable because <shouldHaveMessage>")
	public void verifyUnlockingIsNotAcceptableBecause(String pipelineName, String shouldHaveMessage) throws Exception {
		unlockShouldReturn(state.pipelineNamed(pipelineName), 409, shouldHaveMessage);
	}

	@com.thoughtworks.gauge.Step("Verify unlocking <litteralPipelineName> fails as pipeline is not found")
	public void verifyUnlockingFailsAsPipelineIsNotFound(String litteralPipelineName) throws Exception {
		unlockShouldReturn(litteralPipelineName, 404, String.format("pipeline name %s is incorrect", litteralPipelineName));
	}

	@com.thoughtworks.gauge.Step("Verify can unlock <pipelineName>")
	public void verifyCanUnlock(String pipelineName) throws Exception {
		String actualPipelineName = state.pipelineNamed(pipelineName);
		waitUntilUnlockReturns(actualPipelineName, 200, String.format("Pipeline lock released for %s", actualPipelineName));
	}

	@com.thoughtworks.gauge.Step("Unlock <pipelineName>")
	public void unlock(String pipelineName) throws Exception {
		String actualPipelineName = state.pipelineNamed(pipelineName);
		unlockShouldReturn(actualPipelineName, 200, String.format("Pipeline lock released for %s", actualPipelineName));
	}

	@com.thoughtworks.gauge.Step("Using remembered revision <revisionAlias> for material <materialName>")
	public void usingRememberedRevisionForMaterial(String revisionAlias, String materialName) throws Exception {
		usingRevisionOf(repositoryState.getRevisionFromAlias(revisionAlias), state.expand(materialName));
		this.updateMaterialBeforeSchedule = false;
	}

    @com.thoughtworks.gauge.Step("Verify card activity between pipeline <pipelineName> counters <fromCounter> and <toCounter> is <cards> with show _ bisect <showBisect>")
	public void verifyCardActivityBetweenPipelineCountersAndIsWithShow_Bisect(String pipelineName, Integer fromCounter, Integer toCounter,
            String cards, Boolean showBisect) throws Exception {
        String url = Urls.urlFor(String.format("/api/card_activity/%s/%s/to/%s", state.pipelineNamed(pipelineName), fromCounter, toCounter));
        CruiseResponse response = talkToCruise.get(url, new NameValuePair("show_bisect", showBisect.toString()));
        Assert.assertThat(response.getBody(), Matchers.containsString(cards));
        Assert.assertThat(response.getStatus(), Matchers.is(200));
    }

	@com.thoughtworks.gauge.Step("Attempt to pause pipline <pipelineName> with cause <cause> and should return with http status <returnCode>")
	public void attemptToPausePiplineWithCauseAndShouldReturnWithHttpStatus(String pipelineName, String cause, Integer returnCode) throws Exception {
		CruiseResponse response = pauseApiCall(state.pipelineNamed(pipelineName), cause);
		assertThat(response.getStatus(), is(returnCode));
	}

	@com.thoughtworks.gauge.Step("Attempt to unpause pipeline <pipelineName> and should return with http status <returnCode>")
	public void attemptToUnpausePipelineAndShouldReturnWithHttpStatus(String pipelineName, Integer returnCode) throws Exception {
		CruiseResponse response = unpauseApiCall(state.pipelineNamed(pipelineName));
		assertThat(response.getStatus(), is(returnCode));
	}
	
	private CruiseResponse pauseApiCall(String actualPipelineName, String cause) throws UnsupportedEncodingException {
		StringRequestEntity requestEntity = new StringRequestEntity(
				"{\"pauseCause\": \""+ cause + "\"}",
				"application/json",
				"UTF-8");
		String url = Urls.urlFor(String.format("/api/pipelines/%s/pause", actualPipelineName));
		CruiseResponse response = talkToCruise.post(url, requestEntity, "X-GoCD-Confirm", CruiseConstants.apiV1);
		return response;
	}

	private CruiseResponse unpauseApiCall(String actualPipelineName) throws UnsupportedEncodingException {
		String url = Urls.urlFor(String.format("/api/pipelines/%s/unpause", actualPipelineName));
		StringRequestEntity requestEntity = new StringRequestEntity("{}", "application/json", "UTF-8");
		CruiseResponse response = talkToCruise.post(url, requestEntity, "X-GoCD-Confirm", CruiseConstants.apiV1);
		return response;
	}

	@com.thoughtworks.gauge.Step("Attempt to pause non existent pipline <pipelineName> with cause <cause> and should return with http status <returnCode>")
	public void attemptToPauseNonExistentPiplineWithCauseAndShouldReturnWithHttpStatus(String pipelineName, String cause, Integer returnCode) throws Exception {
		CruiseResponse response = pauseApiCall(pipelineName, cause);
		assertThat(response.getStatus(), is(returnCode));
	}

	@com.thoughtworks.gauge.Step("Attempt to unpause non existent pipline <piplineName> and should return with http status <returnCode>")
	public void attemptToUnpauseNonExistentPiplineAndShouldReturnWithHttpStatus(String piplineName, Integer returnCode) throws Exception {
		CruiseResponse response = unpauseApiCall(pipelineName);
		assertThat(response.getStatus(), is(returnCode));
	}
	
	@com.thoughtworks.gauge.Step("Attempt to get scheduled list of jobs should return with status <returnCode>")
	public void attemptToGetScheduledListOfJobsShouldReturnWithStatus(Integer returnCode) throws Exception {		
		String url = Urls.urlFor(String.format("/api/jobs/scheduled.xml"));
		CruiseResponse response = talkToCruise.get(url);
		Assert.assertThat(response.getStatus(), Is.is(returnCode));
	}

	@com.thoughtworks.gauge.Step("Verify <count> instances of <pipelineName> <stageName> <jobName> <status>")
	public void verifyInstancesOf(String count, String pipelineName, String stageName, String jobName, String status) {
		String actualPipelineName = state.pipelineNamed(pipelineName);

		int offset = 0;
		while (Integer.parseInt(count) - offset > 0) {
			String apiEndPoint = "/api/pipelines/%s/history" + (offset == 0 ? "" : "/" + offset);
			int pipelineCounter = Integer.parseInt(count) - offset;
			int currentPageSize = Integer.parseInt(count) - offset > 10 ? 10 : Integer.parseInt(count) - offset;

			hitPipelineHistoryAPIAndVerifyResponse(actualPipelineName, stageName, jobName, status, apiEndPoint, pipelineCounter, Integer.parseInt(count),
					offset, currentPageSize);

			offset = offset + 10;
		}
	}

	@com.thoughtworks.gauge.Step("Verify <pipelineCounter> instance of <pipelineName> <stageName> <jobName> <status>")
	public void verifyInstanceOf(String pipelineCounter, String pipelineName, String stageName, String jobName, String status) {
		String actualPipelineName = state.pipelineNamed(pipelineName);
		String apiEndPoint = "/api/pipelines/%s/instance/%d";
		hitPipelineInstanceAPIAndVerifyResponse(actualPipelineName, stageName, jobName, status, apiEndPoint, Integer.parseInt(pipelineCounter));
	}

	private void hitPipelineHistoryAPIAndVerifyResponse(String actualPipelineName, String stageName, String jobName, String status, String apiEndPoint,
			int pipelineCounter, int totalInstances, int offset, int currentPageSize) {
		String url = Urls.urlFor(String.format(apiEndPoint, actualPipelineName));
		CruiseResponse response = talkToCruise.get(url);
		String historyJSONResponse = response.getBody();
		PipelineHistory pipelineHistory = new Gson().fromJson(historyJSONResponse, PipelineHistory.class);
		verifyPaginationForPipelineHistory(pipelineHistory, totalInstances, offset, currentPageSize);
		verifyPipelineHistoryResponse(actualPipelineName, stageName, jobName, status, pipelineHistory, pipelineCounter);
	}

	private void hitPipelineInstanceAPIAndVerifyResponse(String actualPipelineName, String stageName, String jobName, String status, String apiEndPoint, int pipelineCounter) {
		String url = Urls.urlFor(String.format(apiEndPoint, actualPipelineName, pipelineCounter));
		CruiseResponse response = talkToCruise.get(url);
		String instanceJSONResponse = response.getBody();
		PipelineInstance pipelineInstance = new Gson().fromJson(instanceJSONResponse, PipelineInstance.class);
		verifyPipelineInstanceResponse(actualPipelineName, stageName, jobName, status, pipelineCounter, pipelineInstance);
	}

	private void verifyPaginationForPipelineHistory(PipelineHistory pipelineHistory, int totalInstances, int offset, int currentPageSize) {
		assertThat(pipelineHistory.getPagination().getPageSize(), is(10));
		assertThat(pipelineHistory.getPagination().getTotal(), is(totalInstances));
		assertThat(pipelineHistory.getPagination().getOffset(), is(offset));
		assertThat(pipelineHistory.getPipelines().size(), is(currentPageSize));
	}

	private void verifyPipelineHistoryResponse(String actualPipelineName, String stageName, String jobName, String status, PipelineHistory pipelineHistory,
			int pipelineCounter) {
		for (PipelineInstance pipelineInstance : pipelineHistory.getPipelines()) {
			verifyPipelineInstanceResponse(actualPipelineName, stageName, jobName, status, pipelineCounter--, pipelineInstance);
		}
	}

	private void verifyPipelineInstanceResponse(String actualPipelineName, String stageName, String jobName, String status,
			int pipelineCounter, PipelineInstance pipelineInstance) {
		assertThat(pipelineInstance.getName(), is(actualPipelineName));
		assertThat(pipelineInstance.getCounter(), is(pipelineCounter));

		StageHistoryItem stageHistoryItem = pipelineInstance.getStages().get(0);
		assertThat(stageHistoryItem.getName(), is(stageName));
		assertThat(stageHistoryItem.getCounter(), is("1"));
		assertThat(stageHistoryItem.getResult(), is(status));

		JobHistoryItem jobHistoryItem = stageHistoryItem.getJobs().get(0);
		assertThat(jobHistoryItem.getName(), is(jobName));
		assertThat(jobHistoryItem.getResult(), is(status));
		if (status.equals("Passed") || status.equals("Failed")) {
			assertThat(jobHistoryItem.getState(), is("Completed"));
		}
	}
}
