/*************************GO-LICENSE-START*********************************
 * Copyright 2018 ThoughtWorks, Inc.
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

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import org.hamcrest.core.Is;
import org.hamcrest.text.StringContains;
import org.junit.Assert;

import static org.hamcrest.MatcherAssert.assertThat;

public class OnPipelineDashboardPage extends CruisePage {
    private static final String FAILED = "Failed";
    private static final String PASSED = "Passed";
    private final CurrentPageState currentPageState;
    private final ScenarioHelper scenarioHelper;
    private final TalkToCruise talkToCruise;
    private final Configuration configuration;
    private boolean autoRefresh = false;
    private final RepositoryState repositoryState;
    private final UsingPipelineDashboardAPI dashboardApi;

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
        this.dashboardApi = new UsingPipelineDashboardAPI(scenarioState, currentPageState, scenarioHelper, false, talkToCruise, configuration, repositoryState);
    }



    @Override
    protected String url() {
        return Urls.urlFor("/dashboard?autoRefresh=" + autoRefresh);
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


    @com.thoughtworks.gauge.Step("Navigate to stage <stageName> of run <pipelineCounter> having counter <stageCounter>")
    public void navigateToStageOfRunHavingCounter(String stageName, int pipelineCounter, int stageCounter) throws Exception {
        navigateDirectlyToStageOfRunHavingCounter(stageName, pipelineCounter, stageCounter);
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
        navigateDirectlyToStageOfRunHavingCounter(stageName, pipelineCounter, 1);
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


    @com.thoughtworks.gauge.Step("Verify user <user> is logged in")
    public void verifyUserIsLoggedIn(String user) {
        System.out.println("current_user" + browser.listItem("current_user icon").getText());
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
        navigateToPipelineHistoryPageOf(dynamicPipelineName);
        currentPageState.currentPageIs(CurrentPageState.Page.PIPELINE_HISTORY);
    }

    @com.thoughtworks.gauge.Step("Compare pipeline instance <subject> with <comparand>")
    public void clickCompareLink(String subject, String comparand) throws Exception {
        browser.navigateTo(Urls.urlFor(String.format("/compare/%s/%s/with/%s", scenarioState.currentRuntimePipelineName(), subject, comparand)));
        currentPageState.currentPageIs(Page.COMPARE_PAGE);
    }


}
