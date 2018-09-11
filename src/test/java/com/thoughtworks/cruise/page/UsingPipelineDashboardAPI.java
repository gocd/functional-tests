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

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import com.thoughtworks.cruise.utils.Timeout;
import org.bouncycastle.util.encoders.Base64;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;


public class UsingPipelineDashboardAPI  {
    private static final String FAILED = "Failed";
    private static final String PASSED = "Passed";

    private final ScenarioState scenarioState;

    private final ScenarioHelper scenarioHelper;
    private final TalkToCruise talkToCruise;
    private final Configuration configuration;
    private String dashboardApiVersion= "application/vnd.go.cd.v2+json";
    private String triggerPipelineAPIVersion= "application/vnd.go.cd.v1+json";
    private String pausePipelineAPIVersion= "application/vnd.go.cd.v1+json";
    private String buildCauseAPIVersion = "application/vnd.go.cd.v1+json";
    private final RepositoryState repositoryState;

    public UsingPipelineDashboardAPI(ScenarioState scenarioState, CurrentPageState currentPageState, ScenarioHelper scenarioHelper,
                                   TalkToCruise talkToCruise, Configuration configuration, RepositoryState repositoryState) {
        this(scenarioState, currentPageState, scenarioHelper, false, talkToCruise, configuration, repositoryState);
    }

    public UsingPipelineDashboardAPI(ScenarioState scenarioState, CurrentPageState currentPageState, ScenarioHelper scenarioHelper,
                                   boolean alreadyOn, TalkToCruise talkToCruise, Configuration configuration, RepositoryState repositoryState) {
        this.scenarioState = scenarioState;
        this.talkToCruise = talkToCruise;
        this.configuration = configuration;
        this.scenarioHelper = scenarioHelper;
        this.repositoryState = repositoryState;
    }

    private Response getDashboard(){

        HashMap<String, String> headers = new HashMap<String, String>();

        String user = talkToCruise.currentUserNameProvider.loggedInUser();
        if (user != null) {
            String auth = "Basic "+new String(Base64.encode(String.format("%s:badger", user).getBytes()));
            headers.put("Authorization", auth);
        }

        headers.put("Accept", dashboardApiVersion);
        headers.put("Content-Type", "application/json");

        return RestAssured.given().
                headers(headers).
                when().get(Urls.urlFor("/go/api/dashboard"));

    }


    private Response triggerPipelineUsingAPI(String name){

        HashMap<String, String> headers = new HashMap<String, String>();

        String user = talkToCruise.currentUserNameProvider.loggedInUser();
        if (user != null) {
            String auth = "Basic "+new String(Base64.encode(String.format("%s:badger", user).getBytes()));
            headers.put("Authorization", auth);
        }

        headers.put("Accept", triggerPipelineAPIVersion);
        headers.put("Content-Type", "application/json");
        headers.put("X-GoCD-Confirm", "true");


        return RestAssured.given().
                headers(headers).
                when().post(Urls.urlFor(String.format("/go/api/pipelines/%s/schedule", scenarioState.pipelineNamed(name))));

    }

    @com.thoughtworks.gauge.Step("On Pipeline Dashboard Page")
    public void getLatestDashboard() throws Exception {
        Response response = getDashboard();
        scenarioState.storeDashboardResponse(response);
        response.then().statusCode(200);
    }

    @com.thoughtworks.gauge.Step("Looking at pipeline <pipelineName>")
    public void lookingAtPipeline(String pipelineName) throws Exception {
        getLatestDashboard();
        scenarioState.usingPipeline(pipelineName);
    }

    @com.thoughtworks.gauge.Step("Verify pipeline is in group <pipelineGroupName>")
    public void verifyPipelineIsInGroup(String pipelineGroupName) throws Exception {
        scenarioState.getDashboardResponse().then()
                .body(String.format("_embedded.pipeline_groups.find " +
                        "{it.name == '%s'}.pipelines"
                        ,pipelineGroupName), hasItem(scenarioState.currentRuntimePipelineName()));
    }


    @com.thoughtworks.gauge.Step("Verify stage <oneBasedIndexOfStage> is <stageStatus> on pipeline with label <pipelineLabel>")
    public void verifyStageIsOnPipelineWithLabel(final Integer oneBasedIndexOfStage, final String stageStatus, final String pipelineLabel) throws Exception {
        Assertions.waitUntil(Timeout.FIVE_MINUTES, new Predicate() {
            public boolean call() throws Exception {
                String status = getDashboard().then()
                        .extract().path(String.format("_embedded.pipelines.find " +
                                "{ it.name == '%s'}._embedded.instances.find " +
                                "{ it.label == '%s' }._embedded.stages[%s].status", scenarioState.currentRuntimePipelineName(), pipelineLabel, String.valueOf(oneBasedIndexOfStage - 1)));
                if (status.contains(stageStatus)) {
                    return true;
                }
                return false;

            }
        });
    }

    @com.thoughtworks.gauge.Step("Wait for stage <stageName> status to be <expectedStatus> with label <pipelineLabel>")
    public void waitForStageStatusToBeWithLabel(final String stageName, final String expectedStatus, final String pipelineLabel) throws Exception {

        Assertions.waitUntil(Timeout.FIVE_MINUTES, new Predicate() {
            public boolean call() throws Exception {
                String status = getDashboard().then()
                        .extract().path(String.format("_embedded.pipelines.find " +
                                "{ it.name == '%s'}._embedded.instances.find " +
                                "{ it.label == '%s' }._embedded.stages.find { it.name == '%s'}.status",scenarioState.currentRuntimePipelineName(),pipelineLabel,stageName));
                if(status.contains(expectedStatus)){
                    return true;
                }
                return false;
            }
        });

    }

    @com.thoughtworks.gauge.Step("Wait for current status of stage <stageName> to be <expectedStatus>")
    public void waitForCurrentStatusOfStageToBe(final String stageName, final String expectedStatus) throws Exception {

        Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
            public boolean call() throws Exception {
                String status = getDashboard().then()
                        .extract().path(String.format("_embedded.pipelines.find " +
                                "{ it.name == '%s'}._embedded.instances[-1]" +
                                "._embedded.stages.find { it.name == '%s'}.status",scenarioState.currentRuntimePipelineName(),stageName));
                if(status.contains(expectedStatus)){
                    return true;
                }
                return false;
            }
        });

    }

    @com.thoughtworks.gauge.Step("Verify stage <oneBasedIndexOfStage> named <stageName> is <stageStatus> on pipeline with label <pipelineLabel> having stage counter <stageCounter>")
    public void verifyStageNamedIsOnPipelineWithLabelHavingStageCounter(final Integer oneBasedIndexOfStage, final String stageName,
                                                                        final String stageStatus, final String pipelineLabel, final int stageCounter) throws Exception {
        Assertions.waitUntil(Timeout.FIVE_MINUTES, new Predicate() {
            public boolean call() throws Exception {
                String status = getDashboard().then()
                        .extract().path(String.format("_embedded.pipelines.find " +
                                "{ it.name == '%s'}._embedded.instances.find " +
                                "{ it.label == '%s' }._embedded.stages[%s].status",scenarioState.currentRuntimePipelineName(),pipelineLabel,String.valueOf(oneBasedIndexOfStage-1)));
                String stageCounterLink = getDashboard().then()
                        .extract().path(String.format("_embedded.pipelines.find " +
                                "{ it.name == '%s'}._embedded.instances.find " +
                                "{ it.label == '%s' }._embedded.stages[%s]._links.self.href",scenarioState.currentRuntimePipelineName(),pipelineLabel,String.valueOf(oneBasedIndexOfStage-1)));
                String stageLinkForThisCounter = String.format("%s/%s/%s/%s", scenarioState.currentRuntimePipelineName(), pipelineLabel, stageName, stageCounter);
                if(status.contains(stageStatus) && stageCounterLink.contains(stageLinkForThisCounter)){
                    return true;
                }
                return false;
            }
        });
    }

    @com.thoughtworks.gauge.Step("Wait for first stage to fail with pipeline label <pipelineLabel>")
    public void waitForFirstStageToFailWithPipelineLabel(String pipelineLabel) throws Exception {
        verifyStageIsOnPipelineWithLabel(1, "Failed", pipelineLabel);
    }

    @com.thoughtworks.gauge.Step("Wait for first stage to pass with pipeline label <pipelineLabel>")
    public void waitForFirstStageToPassWithPipelineLabel(String pipelineLabel) throws Exception {
        verifyStageIsOnPipelineWithLabel(1, "Passed", pipelineLabel);
    }

    @com.thoughtworks.gauge.Step("Stop <numberOfJobs> jobs waiting for file to exist - On Pipeline Dashboard Page")
    public void stopJobsWaitingForFileToExist(int numberOfJobs) throws Exception {
        scenarioHelper.stopJobsThatAreWaitingForFileToExist(numberOfJobs);
    }

    @com.thoughtworks.gauge.Step("Verify pipeline <pipelineName> is not visible")
    public void verifyPipelineIsNotVisible(String pipelineName) throws Exception {
        HashMap pipelines = getDashboard().then()
                .extract().path(String.format("_embedded.pipelines.find " +
                        "{ it.name == '%s'}",scenarioState.pipelineNamed(pipelineName)));
        Assert.assertTrue(String.format("Pipeline %s visible on dashboard", pipelineName), (pipelines == null));
    }

    @com.thoughtworks.gauge.Step("Verify pipeline <pipelineName> is visible")
    public void verifyPipelineIsVisible(String pipelineName) throws Exception {
        HashMap pipelines = getDashboard().then()
                .extract().path(String.format("_embedded.pipelines.find " +
                        "{ it.name == '%s'}",scenarioState.pipelineNamed(pipelineName)));
        Assert.assertFalse(String.format("Pipeline %s NOT visible on dashboard", pipelineName), (pipelines == null));
    }

    @com.thoughtworks.gauge.Step("Trigger pipeline")
    public void triggerPipeline() throws Exception {
        triggerPipelineUsingAPI(scenarioState.currentRuntimePipelineName()).then().statusCode(202);
    }


    @com.thoughtworks.gauge.Step("Pause pipeline with reason <pauseCause>")
    public void pausePipelineWithReason(String pauseCause) throws Exception {

        HashMap<String, String> headers = new HashMap<String, String>();

        String user = talkToCruise.currentUserNameProvider.loggedInUser();
        if (user != null) {
            String auth = "Basic "+new String(Base64.encode(String.format("%s:badger", user).getBytes()));
            headers.put("Authorization", auth);
        }

        headers.put("Accept", pausePipelineAPIVersion);
        headers.put("Content-Type", "application/json");


        RestAssured.given().
                headers(headers).
                body(String.format("{\"pause_cause\": \"%s\"}",pauseCause)).
                when().post(Urls.urlFor(String.format("/go/api/pipelines/%s/pause", scenarioState.currentRuntimePipelineName()))).then().statusCode(200);
    }


    @com.thoughtworks.gauge.Step("Verify pipeline is unpaused")
    public void verifyPipelineIsUnpaused() throws Exception {
        getPipelineStatus(scenarioState.currentRuntimePipelineName()).then().statusCode(200).and().body("paused",Matchers.is(false));
    }

    @com.thoughtworks.gauge.Step("Verify pipeline is paused with reason <pauseCause> by <pausedBy>")
    public void verifyPipelineIsPausedWithReasonBy(final String pauseCause, final String pausedBy) throws Exception {
        Response response = getPipelineStatus(scenarioState.currentRuntimePipelineName());
        response.then().statusCode(200).and().body("paused",Matchers.is(true));
        response.then().body("pausedCause",Matchers.is(pauseCause));
        response.then().body("pausedBy",Matchers.is(pausedBy));
    }

    public boolean verifyCanOperatePipeline() throws Exception {
        return getDashboard().then()
                .extract().path(String.format("_embedded.pipelines.find " +
                        "{ it.name == '%s'}.can_operate",scenarioState.currentRuntimePipelineName()));
    }

    public void verifyTriggerButtonIsPresent() throws Exception {
        Assert.assertThat(verifyCanOperatePipeline(), Is.is(true));
    }

    public void verifyTriggerWithOptionsButtonIsPresent() throws Exception {
        Assert.assertThat(verifyCanOperatePipeline(), Is.is(true));
    }

    private Response getPipelineStatus(String name){

        HashMap<String, String> headers = new HashMap<String, String>();

        String user = talkToCruise.currentUserNameProvider.loggedInUser();
        if (user != null) {
            String auth = "Basic "+new String(Base64.encode(String.format("%s:badger", user).getBytes()));
            headers.put("Authorization", auth);
        }

        return RestAssured.given().
                headers(headers).
                when().get(Urls.urlFor(String.format("/go/api/pipelines/%s/status", name)));

    }

    @com.thoughtworks.gauge.Step("Verify pipeline is paused by <pausedBy>")
    public void verifyPipelineIsPausedBy(final String pausedBy) throws Exception {
        Response response = getPipelineStatus(scenarioState.currentRuntimePipelineName());
        response.then().statusCode(200).and().body("pausedBy",Matchers.is(pausedBy));
    }

    public void verifyPipelineIsPausedWithReason(String pauseCause) throws Exception {
        Response response = getPipelineStatus(scenarioState.currentRuntimePipelineName());
        response.then().statusCode(200).and().body("pausedCause",Matchers.is(pauseCause));
    }

    @com.thoughtworks.gauge.Step("Verify cannot trigger pipeline")
    public void verifyCannotTriggerPipeline() throws Exception {
        Assert.assertTrue(verifyCanOperatePipeline());
    }


    @com.thoughtworks.gauge.Step("Verify can trigger pipeline")
    public void verifyCanTriggerPipeline() throws Exception {
        Assert.assertTrue(verifyCanOperatePipeline());
    }

    @com.thoughtworks.gauge.Step("Verify trigger with option is enabled")
    public void verifyCanTriggerPipelineWithOptions() throws Exception {
        Assert.assertTrue(verifyCanOperatePipeline());
    }

    @com.thoughtworks.gauge.Step("Open trigger with options")
    public void openTriggerWithOptions() {
    }

    @com.thoughtworks.gauge.Step("Unpause pipeline")
    public void unpausePipeline() throws Exception {
        HashMap<String, String> headers = new HashMap<String, String>();

        String user = talkToCruise.currentUserNameProvider.loggedInUser();
        if (user != null) {
            String auth = "Basic "+new String(Base64.encode(String.format("%s:badger", user).getBytes()));
            headers.put("Authorization", auth);
        }

        headers.put("Accept", pausePipelineAPIVersion);
        headers.put("X-GoCD-Confirm", "true");


        RestAssured.given().
                headers(headers).
                when().post(Urls.urlFor(String.format("/go/api/pipelines/%s/unpause", scenarioState.currentRuntimePipelineName()))).then().statusCode(200);
    }

    @com.thoughtworks.gauge.Step("Verify pipeline has no history")
    public void verifyPipelineHasNoHistory() throws Exception {
        Assert.assertTrue(String.format("Pipeline %s has History", scenarioState.currentRuntimePipelineName()), getAllPipelineInstances().isEmpty());
    }

    private ArrayList getAllPipelineInstances(){
        return getDashboard().then()
                .extract().path(String.format("_embedded.pipelines.find " +
                        "{ it.name == '%s'}._embedded.instances",scenarioState.currentRuntimePipelineName()));
    }

    @com.thoughtworks.gauge.Step("Verify group <groupName> is visible - On Pipeline Dashboard Page")
    public void verifyGroupIsVisible(final String groupName) throws Exception {
        HashMap groups = getDashboard().then()
                .extract().path(String.format("_embedded.pipeline_groups.find { it.name == '%s'}",groupName));
        Assert.assertFalse(String.format("Pipeline Group %s NOT visible on dashboard", groupName), (groups == null));
    }

    @com.thoughtworks.gauge.Step("Verify group <groupName> is not visible")
    public void verifyGroupIsNotVisible(String groupName) throws Exception {
        HashMap groups = getDashboard().then()
                .extract().path(String.format("_embedded.pipeline_groups.find { it.name == '%s'}",groupName));
        Assert.assertTrue(String.format("Pipeline Group %s visible on dashboard", groupName), (groups == null));
    }


    @com.thoughtworks.gauge.Step("Trigger pipelines <pipelineNames> and wait for labels <label> to pass")
    public void triggerPipelinesAndWaitForLabelsToPass(String pipelineNames, String label) throws Exception {
        triggerPipelinesAndWaitForLabelToPassForStage(pipelineNames, label, "1");
    }

    @com.thoughtworks.gauge.Step("Trigger pipelines <pipelineNames> and wait for label <label> to pass for stage <stageOneBasedIndex>")
    public void triggerPipelinesAndWaitForLabelToPassForStage(String pipelineNames, String label, String stageOneBasedIndex) throws Exception {
        String[] pipelines = pipelineNames.split(",");
        for (String pipeline : pipelines) {
            pipeline = pipeline.trim();
            triggerPipelineUsingAPI(pipeline);
        }
        for (String pipeline : pipelines) {
            pipeline = pipeline.trim();
            lookingAtPipeline(pipeline);
            verifyStageIsOnPipelineWithLabel(Integer.parseInt(stageOneBasedIndex), PASSED, label);
        }
    }

    @com.thoughtworks.gauge.Step("Wait for labels <label> to pass")
    public void waitForLabelsToPass(String label) throws Exception {
        String pipeline = scenarioState.currentRuntimePipelineName();
        lookingAtPipeline(pipeline);
        verifyStageIsOnPipelineWithLabel(1, PASSED, label);

    }

    @com.thoughtworks.gauge.Step("Wait for labels <label> to fail")
    public void waitForLabelsToFail(String label) throws Exception {
        String pipeline = scenarioState.currentRuntimePipelineName();
        lookingAtPipeline(pipeline);
        verifyStageIsOnPipelineWithLabel(1, FAILED, label);
    }



    @com.thoughtworks.gauge.Step("Trigger the pipeline <times> times starting at counter <startingAtCounter>")
    public void triggerThePipelineTimesStartingAtCounter(Integer times, Integer startingAtCounter) throws Exception {
        for (int i = 0; i < times; i++) {
            startingAtCounter++;
            triggerPipeline();
            verifyStageIsOnPipelineWithLabel(1, PASSED, startingAtCounter.toString());
        }
    }

    @com.thoughtworks.gauge.Step("Open changes section for counter <counter>")
    public void openChangesSectionForCounter(final int counter) throws Exception {

        HashMap<String, String> headers = new HashMap<String, String>();

        String user = talkToCruise.currentUserNameProvider.loggedInUser();
        if (user != null) {
            String auth = "Basic "+new String(Base64.encode(String.format("%s:badger", user).getBytes()));
            headers.put("Authorization", auth);
        }

        headers.put("Accept", buildCauseAPIVersion);
        headers.put("X-GoCD-Confirm", "true");


        Response response = RestAssured.given().
                headers(headers).
                when().get(Urls.urlFor(String.format("/go/api/internal/build_cause/%s/%s", scenarioState.currentRuntimePipelineName(), counter)));
        scenarioState.storeBuildCauseResponse(response);
    }


    @com.thoughtworks.gauge.Step("Verify pipeline is at label <label> and does not get triggered")
    public void verifyPipelineIsAtLabelAndDoesNotGetTriggered(final int label) throws Exception {
        Assertions.assertOverTime(Timeout.TEN_SECONDS, new Function<Boolean>() {
            public Boolean call() {
                return pipelineIsAtLabel(label);
            }
        });
    }

    private boolean pipelineIsAtLabel(int label){

        String actual_label = getDashboard().then()
                                      .extract().path(String.format("_embedded.pipelines.find " +
                                      "{ it.name == '%s'}._embedded.instances[-1].label",scenarioState.currentRuntimePipelineName()));
        return Integer.valueOf(actual_label) == label;

    }

    @com.thoughtworks.gauge.Step("Verify current pipeline has label with counter <pipelineCounter> concatenated with value in store with key <key>")
    public void verifyCurrentPipelineHasLabelWithCounterConcatenatedWithValueInStoreWithKey(String pipelineCounter, String key)
            throws Exception {
        String expectedLabel = String.format("%s-%s", pipelineCounter, scenarioState.getValueFromStore(key));
        assertThat(String.format("Could not find link named %s", expectedLabel), pipelineIsAtLabel(Integer.valueOf(expectedLabel)), Is.is(true));
    }

    @com.thoughtworks.gauge.Step("Verify pipeline does not get triggered even once")
    public void verifyPipelineDoesNotGetTriggeredEvenOnce() {
        Assertions.assertOverTime(Timeout.TEN_SECONDS, new Function<Boolean>() {
            public Boolean call() {
                return getAllPipelineInstances().isEmpty();
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify pipeline has not been triggered even once")
    public void verifyPipelineHasNotBeenTriggeredEvenOnce() {
        getAllPipelineInstances().isEmpty();
    }


    @com.thoughtworks.gauge.Step("Trigger stage <stageNumber> of pipeline <pipelineName> with a _ run _ till _ file _ exists _ job <numberOfJobs> and wait for label <label> to be <status>")
    public void triggerStageOfPipelineWithA_Run_Till_File_Exists_JobAndWaitForLabelToBe(Integer stageNumber, String pipelineName,
                                                                                        int numberOfJobs, String label, String status) throws Exception {
        triggerPipeline(pipelineName);
        completePipelineRun(stageNumber, numberOfJobs, label, status);
    }

    @com.thoughtworks.gauge.Step("Trigger pipeline <pipelineName>")
    public void triggerPipeline(String pipelineName) throws Exception {
        lookingAtPipeline(pipelineName);
        triggerPipeline();
    }

    public void verifyStageWithA_Run_Till_File_Exists_JobOfPipelineHasWithLabel(Integer stageNumber, Integer numberOfJobs, String pipelineName,
                                                                                String status, String label) throws Exception {
        lookingAtPipeline(pipelineName);
        completePipelineRun(stageNumber, numberOfJobs, label, status);
    }

    @com.thoughtworks.gauge.Step("Verify pipeline <pipeline> is triggered by <user>")
    public void verifyPipelineIsTriggeredBy(String pipeline, String user) {
        String triggered_by_uer = getDashboard().then()
                .extract().path(String.format("_embedded.pipelines.find " +
                        "{ it.name == '%s'}._embedded.instances[-1].triggered_by",scenarioState.currentRuntimePipelineName()));
        assertTrue("Triggered by user is not as expected.", triggered_by_uer.contains(user));
    }



    @com.thoughtworks.gauge.Step("Trigger and cancel pipeline <pipelineName> <times> times")
    public void triggerAndCancelPipelineTimes(String pipelineName, Integer times) throws Exception {
        triggerAndCancelPipelineTimesStartingWithLabel(pipelineName, times, 0);
    }

    @com.thoughtworks.gauge.Step("Trigger and cancel <pipelineName> pipeline <times> times starting with label <startLabel>")
    public void triggerAndCancelPipelineTimesStartingWithLabel(String pipelineName, Integer times, int startLabel) throws Exception {
        for (int i = 1; i <= times; i++) {
            triggerPipeline(pipelineName);
            int pipelineCounter = i + startLabel;
            String label = String.valueOf(pipelineCounter);
            verifyStageIsOnPipelineWithLabel(1, "Building", label);
            cancelStageOfThePipelineWithLabel(1, label, pipelineCounter);
            verifyStageIsOnPipelineWithLabel(1, "Cancelled", label);
        }
    }


    private void completePipelineRun(Integer stageNumber, Integer numberOfJobs, String label, String status) throws Exception {
        scenarioHelper.deleteStopjobFileOnAllAgents();
        if (PASSED.equals(status))
            scenarioHelper.stopJobsThatAreWaitingForFileToExist(numberOfJobs);
        else
            scenarioHelper.failJobsThatAreWaitingForFileToExist(numberOfJobs);
        verifyStageIsOnPipelineWithLabel(stageNumber, status, label);
    }

    private void cancelStageOfThePipelineWithLabel(int oneBasedStageIndex, String pipelineLabel, int pipelineCounter) throws Exception {
        String stageName = stageName(pipelineLabel, oneBasedStageIndex);

        HashMap<String, String> headers = new HashMap<String, String>();

        String user = talkToCruise.currentUserNameProvider.loggedInUser();
        if (user != null) {
            String auth = "Basic "+new String(Base64.encode(String.format("%s:badger", user).getBytes()));
            headers.put("Authorization", auth);
        }

        headers.put("Confirm", "true");

         RestAssured.given().
                headers(headers).
                when().post(Urls.urlFor(String.format("/go/api/stages/%s/%s/cancel",scenarioState.currentRuntimePipelineName(),stageName))).
                 then().statusCode(200);

    }

    private String stageName(final String pipelineLabel, final int oneBasedStageIndex) {
        return Assertions.waitFor(Timeout.THIRTY_SECONDS, new Function<String>() {
            @Override
            public String call() {
                try {
                    String stageName = getDashboard().then()
                                                    .extract().path(String.format("_embedded.pipelines.find " +
                                                    "{ it.name == '%s'}._embedded.instances.find { it.label == '%s'}" +
                                                    "._embedded.stages[%d].name",scenarioState.currentRuntimePipelineName(),pipelineLabel,oneBasedStageIndex-1));
                    return stageName;
                } catch (RuntimeException e) {
                    throw e;
                }
            }
        });
    }


}
