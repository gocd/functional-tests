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

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.ElementUtil;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.hamcrest.text.StringContains;
import org.junit.Assert;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class OnPipelineDashboardPage extends CruisePage {
    private static final String FAILED = "Failed";
    private static final String PASSED = "Passed";
    private final CurrentPageState currentPageState;
    private final ScenarioHelper scenarioHelper;
    private final PipelinePartial pipelinePartial;
    private final TalkToCruise talkToCruise;
    private final Configuration configuration;
    private boolean autoRefresh = false;
    private final RepositoryState repositoryState;

    public OnPipelineDashboardPage(ScenarioState scenarioState, CurrentPageState currentPageState, ScenarioHelper scenarioHelper,
                                   Browser browser, TalkToCruise talkToCruise, Configuration configuration, RepositoryState repositoryState) {
        this(scenarioState, currentPageState, scenarioHelper, false, browser, talkToCruise, configuration, repositoryState);
        currentPageState.currentPageIs(Page.PIPELINE_DASHBOARD);
    }

    public OnPipelineDashboardPage(ScenarioState scenarioState, CurrentPageState currentPageState, ScenarioHelper scenarioHelper,
                                   boolean alreadyOn, Browser browser, TalkToCruise talkToCruise, Configuration configuration, RepositoryState repositoryState) {
        super(scenarioState, alreadyOn, browser);
        this.currentPageState = currentPageState;
        this.talkToCruise = talkToCruise;
        this.configuration = configuration;
        this.scenarioHelper = scenarioHelper;
        this.repositoryState = repositoryState;
        this.pipelinePartial = new PipelinePartial(scenarioState, this, browser);
    }

    @com.thoughtworks.gauge.Step("Looking at pipeline <pipelineName>")
    public void lookingAtPipeline(String pipelineName) throws Exception {
        scenarioState.usingPipeline(pipelineName);
    }

    @com.thoughtworks.gauge.Step("Navigate to pipeline dashboard page")
    public void navigateToPipelineDashboardPage() throws Exception {
        navigateToURL();
        currentPageState.currentPageIs(Page.PIPELINE_DASHBOARD);
    }

    @Override
    protected String url() {
        return Urls.urlFor("/old_dashboard?autoRefresh=" + autoRefresh);
    }

    @com.thoughtworks.gauge.Step("Turn on autoRefresh - On Pipeline Dashboard Page")
    public void turnOnAutoRefresh() throws Exception {
        autoRefresh = true;
        open();
    }

    @com.thoughtworks.gauge.Step("Turn off autoRefresh - On Pipeline Dashboard Page")
    public void turnOffAutoRefresh() throws Exception {
        autoRefresh = false;
        open();
    }

    @com.thoughtworks.gauge.Step("On Pipeline Dashboard Page")
    public void goToPipelineDashboradPage() throws Exception {
        navigateToURL();
    }

    protected void reloadPage() {
        if (!autoRefresh) {
            // browser.navigateTo(Urls.urlFor(OnServerDetailPage.URL));
            browserWrapper.reload();
        }
    }

    @com.thoughtworks.gauge.Step("Verify pipeline is in group <pipelineGroupName>")
    public void verifyPipelineIsInGroup(String pipelineGroupName) throws Exception {
        ElementStub groupElement = pipelineGroupElement(pipelineGroupName);
        if (!groupElement.exists()) {
            Assert.fail(String.format("Unable to find pipeline group '%s'", pipelineGroupName));
        }
        if (!browser.link(String.format("/%s/", scenarioState.currentPipeline())).in(groupElement).exists()) {
            Assert.fail(String.format("Unable to find pipeline '%s'", scenarioState.currentRuntimePipelineName()));
        }
    }

    private ElementStub pipelineGroupElement(String pipelineGroupName) {
        return browser.heading2(pipelineGroupName).parentNode("DIV");
    }

    @com.thoughtworks.gauge.Step("Click on pipeline name")
    public void clickOnPipelineName() throws Exception {
        browser.link(scenarioState.currentRuntimePipelineName()).click();
        // driver.findElement(By.xpath(pipelineLinkXpath())).click();
        currentPageState.currentPageIs(CurrentPageState.Page.PIPELINE_HISTORY);
    }

    @com.thoughtworks.gauge.Step("Verify stage <oneBasedIndexOfStage> is <stageStatus> on pipeline with label <pipelineLabel>")
    public void verifyStageIsOnPipelineWithLabel(final Integer oneBasedIndexOfStage, final String stageStatus, final String pipelineLabel) throws Exception {
        Assertions.waitUntil(Timeout.FIVE_MINUTES, new Predicate() {
            public boolean call() throws Exception {
                reloadPage();
                return cssClassOfStageBar(oneBasedIndexOfStage, pipelineLabel).contains(stageStatus);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Wait for stage <stageName> status to be <expectedStatus> with label <pipelineLabel>")
    public void waitForStageStatusToBeWithLabel(final String stageName, final String expectedStatus, final String pipelineLabel) throws Exception {
        Assertions.waitUntil(Timeout.FIVE_MINUTES, new Predicate() {
            public boolean call() throws Exception {
                reloadPage();
                List<ElementStub> stageBars = browserWrapper.collectNear("div", "/stage_bar /", elementPipelineLabel(pipelineLabel));
                for (ElementStub stageBar : stageBars) {
                    if (stageBar.fetch("className").contains(expectedStatus) && stageBar.fetch("title").startsWith(stageName + " ")) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @com.thoughtworks.gauge.Step("Wait for current status of stage <stageName> to be <expectedStatus>")
    public void waitForCurrentStatusOfStageToBe(final String stageName, final String expectedStatus) throws Exception {
        Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
            public boolean call() throws Exception {
                reloadPage();
                ElementStub latestStageElement = browser.div("latest_stage").in(pipelineElement());
                return latestStageElement.exists(true) && latestStageElement.getText().trim().contains(String.format("%s: %s", expectedStatus, stageName));
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify stage <oneBasedIndexOfStage> named <stageName> is <stageStatus> on pipeline with label <pipelineLabel> having stage counter <stageCounter>")
    public void verifyStageNamedIsOnPipelineWithLabelHavingStageCounter(final Integer oneBasedIndexOfStage, final String stageName,
                                                                        final String stageStatus, final String pipelineLabel, final int stageCounter) throws Exception {
        Assertions.waitUntil(Timeout.FIVE_MINUTES, new Predicate() {
            public boolean call() throws Exception {
                reloadPage();
                String stageLinkForThisCounter = String.format("%s/%s/%s/%s", scenarioState.currentRuntimePipelineName(), pipelineLabel, stageName, stageCounter);
                boolean stageWithCounterExists = browser.byXPath("//a[contains(@href, '" + stageLinkForThisCounter + "')]").exists();

                return stageWithCounterExists && cssClassOfStageBar(oneBasedIndexOfStage, pipelineLabel).contains(stageStatus);
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
        if (browser.link(scenarioState.currentPipeline()).exists()) {
            Assert.fail(String.format("Pipeline '%s' is visible", pipelineName));
        }
    }

    @com.thoughtworks.gauge.Step("Verify pipeline <pipelineName> is visible")
    public void verifyPipelineIsVisible(String pipelineName) throws Exception {
        if (!browser.link(scenarioState.pipelineNamed(pipelineName)).exists()) {
            Assert.fail(String.format("Pipeline '%s' is not visible", pipelineName));
        }
    }

    @com.thoughtworks.gauge.Step("Trigger pipeline")
    public void triggerPipeline() throws Exception {
        Assertions.ensure(String.format("Trigger pipeline : %s", scenarioState.currentRuntimePipelineName()), new Function() {
            public Object call() {
                triggerWithoutRequestAssertion();
                return null;
            }
        }, new Predicate() {
            @Override
            public boolean call() throws Exception {
                assertTriggerAcceptedMessage(scenarioState.currentRuntimePipelineName());
                return true;
            }
        }, Timeout.THREE_MINUTES);
    }

    @com.thoughtworks.gauge.Step("Trigger without request assertion")
    public void triggerWithoutRequestAssertion() {
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {

            @Override
            public boolean call() throws Exception {
                browserWrapper.reload();
                ElementStub triggerButton = elementTriggerButton(scenarioState.currentRuntimePipelineName());
                if (isButtonDisabled(triggerButton)) {
                    return false;
                }
                triggerButton.click();
                return true;
            }
        });
    }

    private ElementStub triggerButton(final String pipelineName) {
        return Assertions.waitFor(Timeout.TEN_SECONDS, new Function<ElementStub>() {

            @Override
            public ElementStub call() {
                ElementStub element = elementTriggerButton(pipelineName);
                if (!element.exists() || isButtonDisabled(element))
                    throw new RuntimeException("Element does not exist yet");
                return element;
            }
        });
    }

    @com.thoughtworks.gauge.Step("Pause pipeline with reason <pauseCause>")
    public void pausePipelineWithReason(String pauseCause) throws Exception {
        pauseButtonElement().click();
        ElementStub modalboxContent = browser.div("MB_content");
        browser.textbox("pauseCause").in(modalboxContent).setValue(pauseCause);
        browser.submit(String.format("pause-%s", scenarioState.currentRuntimePipelineName())).in(modalboxContent).click();
    }

    private ElementStub pauseButtonElement() {
        return browser.submit(String.format("confirm-pause-%s", scenarioState.currentRuntimePipelineName()));
    }

    @com.thoughtworks.gauge.Step("Verify pipeline is unpaused")
    public void verifyPipelineIsUnpaused() throws Exception {
        browserWrapper.reload();
        ElementStub pauseButton = pauseButtonElement();
        Assert.assertThat(pauseButton.exists(), Is.is(true));
    }

    @com.thoughtworks.gauge.Step("Verify pipeline is paused with reason <pauseCause> by <pausedBy>")
    public void verifyPipelineIsPausedWithReasonBy(final String pauseCause, final String pausedBy) throws Exception {
        Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
            public boolean call() throws Exception {
                browserWrapper.reload();
                ElementStub pausedByDiv = browser.span(Regex.wholeWord("paused_by"));
                ElementStub pauseMessageDiv = browser.span(Regex.wholeWord("pause_message"));
                return pausedByDiv.getText().contains(pausedBy) && pauseMessageDiv.getText().contains(pauseCause);
            }

            @Override
            public String toString() {
                return String.format("Expected text inside span - paused_by %s, text inside span - pause_message %s", pauseCause, pausedBy);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify pipeline is paused by <pausedBy>")
    public void verifyPipelineIsPausedBy(final String pausedBy) throws Exception {
        Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
            public boolean call() throws Exception {
                browserWrapper.reload();
                ElementStub pausedByDiv = browser.span(Regex.wholeWord("paused_by"));
                return pausedByDiv.getText().contains(pausedBy);
            }

            @Override
            public String toString() {
                return String.format("Expected text inside span - paused_by %s", pausedBy);
            }
        });
    }

    public void verifyPipelineIsPausedWithReason(String pauseCause) throws Exception {
        browserWrapper.reload();
        ElementStub pauseMessageDiv = browser.span(Regex.wholeWord("pause_message"));
        Assert.assertThat(pauseMessageDiv.getText().contains(pauseCause), Is.is(true));
    }

    @com.thoughtworks.gauge.Step("Verify cannot trigger pipeline")
    public void verifyCannotTriggerPipeline() throws Exception {
        Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
            @Override
            public boolean call() throws Exception {
                reloadPage();
                ElementStub submit = browser.submit(Regex.wholeWord(String.format("deploy-%s", scenarioState.currentRuntimePipelineName())));
                return !submit.exists() || "true".equals(submit.fetch("disabled"));
            }
        });
    }

    public void verifyCanOperateOnPipeline() throws Exception {
        Assertions.waitUntil(Timeout.TEN_SECONDS, new Predicate() {
            @Override
            public boolean call() throws Exception {
                reloadPage();
                ElementStub submit = browser.submit(Regex.wholeWord(String.format("deploy-%s", scenarioState.currentRuntimePipelineName())));
                return submit.exists();
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify can trigger pipeline")
    public void verifyCanTriggerPipeline() throws Exception {
        Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
            @Override
            public boolean call() throws Exception {
                browserWrapper.reload();
                ElementStub triggerButton = triggerButton(scenarioState.currentRuntimePipelineName());
                return triggerButton.exists() && "false".equals(triggerButton.fetch("disabled"));
            }
        });
    }

    @com.thoughtworks.gauge.Step("Open trigger with options")
    public void openTriggerWithOptions() {
        pipelinePartial.openTriggerWithOptions();
    }

    @com.thoughtworks.gauge.Step("Unpause pipeline")
    public void unpausePipeline() throws Exception {
        browser.submit(String.format("unpause-%s", scenarioState.currentRuntimePipelineName())).click();
        Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
            @Override
            public boolean call() throws Exception {
                browserWrapper.reload();
                return pauseButtonElement().exists();
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify pipeline has no history")
    public void verifyPipelineHasNoHistory() throws Exception {
        assertThat(browser.span("No historical data").in(pipelinePanel()).exists(), Is.is(true));
    }

    private ElementStub pipelinePanel() {
        return browser.div(String.format("pipeline_%s_panel", scenarioState.currentRuntimePipelineName()));
    }

    @com.thoughtworks.gauge.Step("Verify group <groupName> is visible - On Pipeline Dashboard Page")
    public void verifyGroupIsVisible(final String groupName) throws Exception {
        if (!browser.heading2(groupName).isVisible()) {
            Assert.fail(String.format("Pipeline group '%s' is not visible", groupName));
        }
    }

    @com.thoughtworks.gauge.Step("Verify group <groupName> is not visible")
    public void verifyGroupIsNotVisible(String groupName) throws Exception {
        if (browser.heading2(groupName).isVisible()) {
            Assert.fail(String.format("Pipeline group '%s' is visible", groupName));
        }
    }

    @com.thoughtworks.gauge.Step("Verify that pipeline with label <pipelineCounter> was previously <stageResult>")
    public void verifyThatPipelineWithLabelWasPreviously(final Integer pipelineCounter, String stageResult) throws Exception {
        Assertions.waitUntil(Timeout.TEN_SECONDS, new Predicate() {
            public boolean call() throws Exception {
                return previousStageResultLink(pipelineCounter).exists();
            }
        });
        ElementStub previousStageResultLink = previousStageResultLink(pipelineCounter);
        assertThat(browser.span(0).in(previousStageResultLink).fetch("className"), containsString(stageResult));
        assertThat(previousStageResultLink.getText().trim(), containsString(stageResult));
    }

    private ElementStub previousStageResultLink(Integer pipelineCounter) {
        return browser.link("result").in(browser.div("previously").in(elementPipeline()));
    }

    private ElementStub elementPipeline() {
        return pipelinePanel();
    }

    @com.thoughtworks.gauge.Step("Click on previous result for pipeline with label <pipelineCounter>")
    public void clickOnPreviousResultForPipelineWithLabel(Integer pipelineCounter) throws Exception {
        previousStageResultLink(pipelineCounter).click();
        currentPageState.currentPageIs(Page.STAGE_DETAILS);
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
            triggerPipeline(pipeline);
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

    public void navigateToRunOfPipeline(int counter, String pipelineName) throws Exception {
        browser.link(String.format("/pipelines\\/%s\\/%s/", scenarioState.pipelineNamed(pipelineName), counter)).click();
        currentPageState.currentPageIs(Page.PIPELINE_DETAILS);
    }

    @com.thoughtworks.gauge.Step("Navigate to materials for <pipelineName> <pipelineCounter> <stageName> <stageCounter>")
    public void navigateToMaterialsFor(String pipelineName, String pipelineCounter, String stageName, String stageCounter) throws Exception {
        browser.navigateTo(stageDetailsUrl(pipelineName, pipelineCounter, stageName, stageCounter) + "/materials");
        currentPageState.currentPageIs(CurrentPageState.Page.STAGE_DETAILS);
    }

    @com.thoughtworks.gauge.Step("Verify trigger with option is disabled")
    public void verifyTriggerWithOptionIsDisabled() throws Exception {
        verifytriggerWithOptionsIs("true");
    }

    private void verifytriggerWithOptionsIs(final String disabledBooleanValue) {
        Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
            @Override
            public boolean call() throws Exception {
                reloadPage();
                ElementStub triggerButton = browser.byId(String.format("/deploy-with-options-%s/",
                        scenarioState.currentRuntimePipelineName()));
                return !triggerButton.exists() || triggerButton.fetch("disabled").equals(disabledBooleanValue);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify trigger with option is enabled")
    public void verifyTriggerWithOptionIsEnabled() throws Exception {
        verifytriggerWithOptionsIs("false");
    }

    @com.thoughtworks.gauge.Step("Navigate to stage <stageName> of run <pipelineCounter> having counter <stageCounter>")
    public void navigateToStageOfRunHavingCounter(String stageName, int pipelineCounter, int stageCounter) throws Exception {
        ElementStub link = browser.link(String.format("/\\/%s\\/%s\\/%s\\/%s(\\?.*)?$/", scenarioState.currentRuntimePipelineName(),
                pipelineCounter, stageName, stageCounter));
        link.click();
        currentPageState.currentPageIs(Page.STAGE_DETAILS);
    }

    /* Do not use a link on the current page. Use to go to an older run, for instance. */
    @com.thoughtworks.gauge.Step("Navigate directly to stage <stageName> of run <pipelineCounter> having counter <stageCounter>")
    public void navigateDirectlyToStageOfRunHavingCounter(String stageName, int pipelineCounter, int stageCounter) throws Exception {
        browser.navigateTo(Urls.stageDetailUrlfor(String.format("%s/%s/%s/%s", scenarioState.currentRuntimePipelineName(), pipelineCounter, stageName, stageCounter)));
        assertThat(browser.fetch("document.title"), StringContains.containsString("Stage Detail"));
        currentPageState.currentPageIs(CurrentPageState.Page.STAGE_DETAILS);
    }

    @com.thoughtworks.gauge.Step("Navigate to stage <stageName> of run <pipelineCounter>")
    public void navigateToStageOfRun(String stageName, int pipelineCounter) throws Exception {
        ElementStub stageLink = browser.link(String.format("/%s/", stageName)).in(
                browser.div("pipeline_instance_details").in(elementPipeline()));
        stageLink.click();
        currentPageState.currentPageIs(Page.STAGE_DETAILS);
    }

    @com.thoughtworks.gauge.Step("Navigate to pipeline history of pipeline <pipelineName>")
    public void navigateToPipelineHistoryPageOf(String pipelineName) throws Exception {
        String url = Urls.urlFor(String.format("/tab/pipeline/history/%s", scenarioState.pipelineNamed(pipelineName)));
        browser.navigateTo(url, true);
        currentPageState.currentPageIs(Page.PIPELINE_HISTORY);
    }

    @com.thoughtworks.gauge.Step("Navigate to stage detail page for <pipelineName> <pipelineCounter> <stageName> <stageCounter> with stage history page size <stageHistoryPageSize>")
    public void navigateToStageDetailPageForWithStageHistoryPageSize(String pipelineName, String pipelineCounter, String stageName,
                                                                     String stageCounter, int stageHistoryPageSize) throws Exception {
        browser.navigateTo(Urls.stageDetailUrlfor(String.format("%s/%s/%s/%s?stageHistoryPageSize=%s",
                scenarioState.pipelineNamed(pipelineName), pipelineCounter, stageName, stageCounter, stageHistoryPageSize)));
        currentPageState.currentPageIs(CurrentPageState.Page.STAGE_DETAILS);
    }

    @com.thoughtworks.gauge.Step("Navigate to failed build history for <pipelineName> <pipelineCounter> <stageName> <stageCounter>")
    public void navigateToFailedBuildHistoryFor(String pipelineName, String pipelineCounter, String stageName, String stageCounter) throws Exception {
        browser.navigateTo(stageDetailsUrl(pipelineName, pipelineCounter, stageName, stageCounter) + "/tests");
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {

            @Override
            public boolean call() throws Exception {
                return browser.fetch("document.title").contains("Stage Detail");
            }
        });
        currentPageState.currentPageIs(CurrentPageState.Page.STAGE_DETAILS);
    }

    @com.thoughtworks.gauge.Step("Verify cruise footer")
    @Override
    public void verifyCruiseFooter() throws Exception {
        super.verifyCruiseFooter();
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
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
            @Override
            public boolean call() throws Exception {
                ElementStub showChangesElement = browser.byId(String.format("show_changes_%s_%s",
                        scenarioState.currentRuntimePipelineName(), counter));
                if (!showChangesElement.exists())
                    return false;
                showChangesElement.click();
                return true;
            }
        });
    }

    @com.thoughtworks.gauge.Step("Open pipelines selector")
    public void openPipelinesSelector() throws Exception {
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
            @Override
            public boolean call() throws Exception {
                browser.byId("show_pipelines_selector").click();
                return true;
            }
        });
    }

    @com.thoughtworks.gauge.Step("Navigate to pipeline dependencies for <pipelineName> <pipelineCounter> <stageName> <stageCounter>")
    public void navigateToPipelineDependenciesFor(String pipelineName, String pipelineCounter, String stageName, String stageCounter)
            throws Exception {
        browser.navigateTo(stageDetailsUrl(pipelineName, pipelineCounter, stageName, stageCounter) + "/pipeline", true);
        currentPageState.currentPageIs(CurrentPageState.Page.STAGE_DETAILS);
    }

    @com.thoughtworks.gauge.Step("Logout")
    public void logout() {
        super.logout();
    }

    private boolean isButtonDisabled(ElementStub element) {
        return element.fetch("disabled").equals(true);
    }

    private ElementStub elementTriggerButton(final String pipelineName) {
        return browser.submit(String.format("deploy-%s", pipelineName));
    }

    private boolean assertTriggerAcceptedMessage(final String pipelineName) {
        ElementStub triggerMessage = browser.byId(String.format("trigger-result-%s", pipelineName));
        return triggerMessage.getText().contains(String.format("Request to schedule pipeline %s accepted", pipelineName));
    }

    @com.thoughtworks.gauge.Step("Verify the lock status is <lockStatus> - On Pipeline Dashboard Page")
    public void verifyTheLockStatusIs(final String lockStatus) throws Exception {
        Assertions.waitUntil(Timeout.FIVE_SECONDS, new Predicate() {
            public boolean call() throws Exception {
                reloadPage();
                ElementStub element = browser.byXPath(String.format("//span[contains(@class,'%s')]", lockStatus));
                return element.exists();
            }
        });
    }

    @com.thoughtworks.gauge.Step("Unlock the pipeline - On Pipeline Dashboard Page")
    public void unlockThePipeline() throws Exception {
        Assertions.waitFor(Timeout.FIVE_SECONDS, new Function<ElementStub>() {
            public ElementStub call() {
                return browser.byXPath("//span[contains(@class, 'click_to_unlock')]/a");
            }
        }).click();
    }

    @com.thoughtworks.gauge.Step("Click compare link")
    public void clickCompareLink() throws Exception {
        ElementStub link = browser.link("Compare").in(browser.div("pipeline_" + scenarioState.currentRuntimePipelineName() + "_panel"));
        link.click();
        currentPageState.currentPageIs(Page.COMPARE_PAGE);
    }

    @com.thoughtworks.gauge.Step("Verify pipeline is at label <label> and does not get triggered")
    public void verifyPipelineIsAtLabelAndDoesNotGetTriggered(final int label) throws Exception {
        Assertions.assertOverTime(Timeout.TEN_SECONDS, new Function<Boolean>() {
            public Boolean call() {
                return elementPipelineLabel(String.valueOf(label)).exists();
            }
        });
    }

    private ElementStub elementPipelineLabel(final String pipelineLabel) {
        return browser.div("Instance: " + pipelineLabel).in(pipelinePanel());
    }

    @com.thoughtworks.gauge.Step("Verify current pipeline has label with counter <pipelineCounter> concatenated with value in store with key <key>")
    public void verifyCurrentPipelineHasLabelWithCounterConcatenatedWithValueInStoreWithKey(String pipelineCounter, String key)
            throws Exception {
        String expectedLabel = String.format("%s-%s", pipelineCounter, scenarioState.getValueFromStore(key));
        ElementStub labelContainer = browser.div("label").in(pipelinePanel());
        ElementStub labelLink = browser.link(expectedLabel).in(labelContainer);
        assertThat(String.format("Could not find link named %s", expectedLabel), labelLink.exists(), Is.is(true));
    }

    @com.thoughtworks.gauge.Step("Verify pipeline does not get triggered even once")
    public void verifyPipelineDoesNotGetTriggeredEvenOnce() {
        Assert.assertThat(pipelineStatusMessage(), Is.is("No historical data"));
        Assertions.assertOverTime(Timeout.TEN_SECONDS, new Function<Boolean>() {
            public Boolean call() {
                if ("No historical data".equals(pipelineStatusMessage())) {
                    return true;
                }
                return false;
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify pipeline has not been triggered even once")
    public void verifyPipelineHasNotBeenTriggeredEvenOnce() {
        Assert.assertThat(pipelineStatusMessage(), Is.is("No historical data"));
    }

    @com.thoughtworks.gauge.Step("Verify pipeline is not getting triggered and stays at run <initialPipelineLabel>")
    public void verifyPipelineIsNotGettingTriggeredAndStaysAtRun(final String initialPipelineLabel) throws Exception {
        Assertions.assertOverTime(Timeout.TEN_SECONDS, new Function<Boolean>() {
            public Boolean call() {
                reloadPage();
                boolean stageIsPassed = cssClassOfStageBar(1, initialPipelineLabel).contains("Passed");
                boolean labelIsSameAsInitialOne = elementPipelineLabel(initialPipelineLabel).exists(true);

                if (stageIsPassed && labelIsSameAsInitialOne) {
                    return true;
                }
                return false;
            }
        });
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
        assertTrue(browser.div("schedule_time").in(browser.div("pipeline_" + scenarioState.pipelineNamed(pipeline) + "_panel")).getText().contains("Triggered by " + user));
    }

    @com.thoughtworks.gauge.Step("Verify user <user> is logged in")
    public void verifyUserIsLoggedIn(String user) {
        System.out.println("current_user" + browser.listItem("current_user icon").getText());
    }

    public void verifyTriggerButtonIsPresent() throws Exception {
        browserWrapper.reload();
        ElementStub triggerButton = triggerButton(scenarioState.currentRuntimePipelineName());
        boolean isTriggerButtonDisabled = triggerButton.fetch("disabled").equals("true");
        boolean triggerButtonExists = triggerButton.exists() && !isTriggerButtonDisabled;
        Assert.assertThat(triggerButtonExists, Is.is(true));

    }

    public void verifyTriggerWithOptionsButtonIsPresent() throws Exception {
        reloadPage();
        ElementStub triggerWithOptionsButton = browser.byId(String.format("deploy-with-options-%s",
                scenarioState.currentRuntimePipelineName()));
        boolean isTriggerWithOptionsButtonDisabled = triggerWithOptionsButton.fetch("disabled").equals("true");
        boolean triggerWithOptionsButtonExists = triggerWithOptionsButton.exists() && !isTriggerWithOptionsButtonDisabled;
        Assert.assertThat(triggerWithOptionsButtonExists, Is.is(true));
    }

    @com.thoughtworks.gauge.Step("Navigate to admin templates tab")
    public void navigateToAdminTemplatesTab() {
        String url = Urls.urlFor("/admin/templates");
        browser.navigateTo(url, true);
        currentPageState.currentPageIs(CurrentPageState.Page.EDIT_TEMPLATES_TAB);
    }

    @com.thoughtworks.gauge.Step("Verify templates tab is not visible")
    public void verifyTemplatesTabIsNotVisible() throws Exception {
        Assert.assertThat(browser.link("Templates").exists(), Is.is(false));
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

    @com.thoughtworks.gauge.Step("Rerun stage <stageName> for current pipeline having pipeline label <pipelineLabel>")
    public void rerunStageForCurrentPipelineHavingPipelineLabel(String stageName, Integer pipelineLabel) throws Exception {
        navigateToStageOfRun(stageName, pipelineLabel);
        AlreadyOnStageDetailPage alreadyOnStageDetailPage = new AlreadyOnStageDetailPage(currentPageState, scenarioState, repositoryState, browser, talkToCruise);
        alreadyOnStageDetailPage.rerunStage(stageName);
        navigateToURL();
    }

    @com.thoughtworks.gauge.Step("Navigate to the pipeline history page for pipeline <pipelineName>")
    public void navigateToThePipelineHistoryPageForPipeline(String pipelineName) throws Exception {
        String dynamicPipelineName = scenarioState.pipelineNamed(pipelineName);
        browser.link(dynamicPipelineName).in(browser.div("pipeline_" + dynamicPipelineName + "_panel")).click();
        assertThat(browser.fetch("document.title"), StringContains.containsString("Pipeline Activity"));
        currentPageState.currentPageIs(CurrentPageState.Page.PIPELINE_HISTORY);
    }

    private String pipelineStatusMessage() {
        return browser.span("message").in(pipelineElement()).getText().trim();
    }

    private ElementStub pipelineElement() {
        return browser.div("pipeline_" + scenarioState.pipelineNamed(scenarioState.currentPipeline()) + "_panel");
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
        navigateToStageOfRun(stageName, pipelineCounter);
        AlreadyOnStageDetailPage alreadyOnStageDetailPage = new AlreadyOnStageDetailPage(currentPageState, scenarioState, repositoryState, browser, talkToCruise);
        alreadyOnStageDetailPage.cancel(stageName);
        navigateToURL();
    }

    private String stageName(final String pipelineLabel, final int oneBasedStageIndex) {
        return Assertions.waitFor(Timeout.THIRTY_SECONDS, new Function<String>() {
            @Override
            public String call() {
                try {
                    ElementStub stageBarElement = browser.div(String.format("/stage_bar /[%d]", oneBasedStageIndex - 1)).near(elementPipelineLabel(pipelineLabel));
                    return ElementUtil.getAttribute(stageBarElement, "data-stage");
                } catch (RuntimeException e) {
                    reloadPage();
                    throw e;
                }
            }
        });
    }

    private String currentPipeline() {
        return scenarioState.currentPipeline();
    }

    private String cssClassOfStageBar(final Integer oneBasedIndexOfStage, final String pipelineLabel) {
        return stageBarElementForStage(oneBasedIndexOfStage, pipelineLabel).fetch("className");
    }

    private ElementStub stageBarElementForStage(final Integer oneBasedIndexOfStage, final String pipelineLabel) {
        return browser.div(String.format("/stage_bar /[%d]", oneBasedIndexOfStage - 1)).near(elementPipelineLabel(pipelineLabel));
    }
}
