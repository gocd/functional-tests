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
import com.thoughtworks.cruise.SahiBrowserWrapper;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.URL;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hamcrest.text.StringContains;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AlreadyOnStageDetailPage extends CruisePage {
	private CurrentPageState currentPageState;
	private boolean autoRefresh;
	private StageHeaderPartial stageHeaderPartial;
	private final RepositoryState repositoryState;

	public AlreadyOnStageDetailPage(CurrentPageState currentPageState, ScenarioState scenarioState, RepositoryState repositoryState, Browser browser, TalkToCruise talkToCruise) {
		super(scenarioState, true, browser);
		this.currentPageState = currentPageState;
		this.repositoryState = repositoryState;
		this.autoRefresh = new SahiBrowserWrapper(browser).isAutoRefresh();
		currentPageState.assertCurrentPageIs(CurrentPageState.Page.STAGE_DETAILS);
		this.stageHeaderPartial = new StageHeaderPartial(scenarioState, this, browser);
	}

	@Override
	protected String url() {
		return browserWrapper.getCurrentUrl();
	}

	@com.thoughtworks.gauge.Step("Verify stage bar is displaying run <runNumber> of <totalRuns>")
	public void verifyStageBarIsDisplayingRunOf(Integer runNumber, Integer totalRuns) throws Exception {
		Assert.assertThat(currentStageRun(), Matchers.is(formatStageRun(runNumber, totalRuns)));
	}

	public void waitTillStageBarIsDisplayingRunOf(final int runNumber, final int totalRuns) throws Exception {
		Assertions.waitUntil(Timeout.TEN_SECONDS, new Predicate() {
			public boolean call() throws Exception {
				reloadPage();
				return currentStageRun().equals(formatStageRun(runNumber, totalRuns));
			}
		});
	}

	private String formatStageRun(Integer runNumber, Integer totalRuns) {
		return runNumber + " of " + totalRuns;
	}

	private String currentStageRun() {
		return currentStageRunElement().getText();
	}

	private ElementStub currentStageRunElement() {
		return browser.byId("current_stage_run");
	}

	@com.thoughtworks.gauge.Step("Click on stage bar run <runNumber> of <totalRuns>")
	public void clickOnStageBarRunOf(final Integer runNumber, final Integer totalRuns) throws Exception {
		currentStageRunElement().click();
		browser.link(String.format("/%s of %s/", runNumber, totalRuns)).in(browser.byId("other_stage_runs")).click();
		// driver.findElement(By.id("other_stage_runs")).findElement(By.xpath(String.format("//a[contains(., '%s of %s')]",
		// runNumber, totalRuns))).click();
		Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
			public boolean call() throws Exception {
				return currentStageRun().contains(formatStageRun(runNumber, totalRuns));
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify stage <stage> has action <actionName>")
	public void verifyStageHasAction(String stage, String actionName) throws Exception {
		stageHeaderPartial.verifyStageHasAction(stage, actionName);
	}

	@com.thoughtworks.gauge.Step("Verify stage <stage> does not have action <actionName>")
	public void verifyStageDoesNotHaveAction(String stage, String actionName) throws Exception {
		stageHeaderPartial.verifyStageDoesNotHaveAction(stage, actionName);
	}

	@com.thoughtworks.gauge.Step("Verify stage <stage> does not have any action")
	public void verifyStageDoesNotHaveAnyAction(String stage) throws Exception {
		stageHeaderPartial.verifyStageDoesNotHaveAnyAction(stage);
	}

	@com.thoughtworks.gauge.Step("Reload page - Already On Stage Detail Page")
	public void reloadPage() {
		if (!autoRefresh) {
			reloadPageWithAutoRefreshSetCorrectly();
		}
	}

	private void reloadPageWithAutoRefreshSetCorrectly() {
		String currentUrl = browserWrapper.getCurrentUrl();
		URL url = new URL(currentUrl);
		url.addParameter("autoRefresh", Boolean.toString(autoRefresh));
		browser.navigateTo(url.toString(), true);
	}

	@com.thoughtworks.gauge.Step("Rerun stage <stage>")
	public void rerunStage(String stage) throws Exception {
		stageHeaderPartial.rerunStage(stage);
	}

	@com.thoughtworks.gauge.Step("Trigger stage <stage>")
	public void triggerStage(String stage) throws Exception {
		stageHeaderPartial.triggerStage(stage);
	}

	@com.thoughtworks.gauge.Step("Cancel <stage> - Already On Stage Detail Page")
	public void cancel(String stage) throws Exception {
		this.autoRefresh = new SahiBrowserWrapper(browser).isAutoRefresh();
		stageHeaderPartial.cancel(stage);
	}

	@com.thoughtworks.gauge.Step("Verify stage <stage> does not have actions link")
	public void verifyStageDoesNotHaveActionsLink(String stage) throws Exception {
		stageHeaderPartial.verifyStageDoesNotHaveAnyAction(stage);
	}

	@com.thoughtworks.gauge.Step("Wait for shine feed to update")
	public void waitForShineFeedToUpdate() throws Exception {
		Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {
			@Override
			public boolean call() throws Exception {
				return totalCount("failures").contains("Failures") && totalCount("errors").contains("Errors");
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify total runs <integer1> failures <failures> errors <errors>")
	public void verifyTotalRunsFailuresErrors(Integer integer1, String failures, String errors) throws Exception {
		Assert.assertThat(totalCount("failures"), Matchers.is("Total Failures: " + failures));
		Assert.assertThat(totalCount("errors"), Matchers.is("Total Errors: " + errors));
	}

	@com.thoughtworks.gauge.Step("Verify pipeline <pipelineLabel> has <failures> failures <errors> errors")
	public void verifyPipelineHasFailuresErrors(String pipelineLabel, String failures, String errors) throws Exception {
		Assert.assertThat(countForPipeline(pipelineLabel, "failures"), Matchers.is("Unique Failures: " + failures));
		Assert.assertThat(countForPipeline(pipelineLabel, "errors"), Matchers.is("Unique Errors: " + errors));
	}

	private String totalCount(String type) {
		return browser.span(type).in(browser.div(Regex.wholeWord("counts")).in(browser.div(Regex.wholeWord("non_passing_tests")))).getText();
	}

	private String countForPipeline(String pipelineLabel, String type) {
		return browser.span(type).near(fbhPipelineLabelSpan(pipelineLabel)).in(browser.div("/non_passing_tests/")).getText();
	}

	@com.thoughtworks.gauge.Step("Verify pipeline <pipelineLabel> has test <testName> with <failureType> type")
	public void verifyPipelineHasTestWithType(String pipelineLabel, String testName, String failureType) throws Exception {
		String failingTestName = failingTestNameForPipeline(pipelineLabel);
		Assert.assertThat(failingTestName, StringContains.containsString(testName));
	}

	private String failingTestNameForPipeline(String pipelineLabel) {
		return browser.span(Regex.wholeWord("name")).near(fbhPipelineLabelSpan(pipelineLabel)).getText();
	}

	@com.thoughtworks.gauge.Step("Verify jobs shows <title> collapsed")
	public void verifyJobsShowsCollapsed(String title) throws Exception {
		ElementStub hideRevealDiv = findHideRevealDiv(title);
		Assert.assertThat(hideRevealDiv.fetch("className"), Matchers.containsString("hidereveal_collapsed"));
	}

	@com.thoughtworks.gauge.Step("Verify jobs shows <title> open")
	public void verifyJobsShowsOpen(String title) throws Exception {
		ElementStub hideRevealDiv = findHideRevealDiv(title);
		Assert.assertThat(hideRevealDiv.fetch("className"), IsNot.not(Matchers.containsString("hidereveal_collapsed")));
	}

	@com.thoughtworks.gauge.Step("Wait for jobs to show <title> with jobs <listOfJobs>")
	public void waitForJobsToShowWithJobs(final String title, final String listOfJobs) throws Exception {
		Assertions.waitUntil(Timeout.FIVE_MINUTES, new Predicate() {
			@Override
			public boolean call() throws Exception {
				ElementStub hideRevealDiv = findHideRevealDiv(title);
				Assert.assertThat(hideRevealDiv.fetch("className"), Matchers.containsString("job_grouping"));

				String[] jobs = listOfJobs.split(",");
				for (String jobName : jobs) {
					ElementStub findElement = browser.link(jobName).in(hideRevealDiv);
					Assert.assertNotNull("Should show job " + jobName, findElement);
				}
				return true;
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify jobs shows <title> open with jobs <listOfJobs>")
	public void verifyJobsShowsOpenWithJobs(String title, String listOfJobs) throws Exception {
		ElementStub hideRevealDiv = findHideRevealDiv(title);
		Assert.assertThat(hideRevealDiv.fetch("className"), IsNot.not(Matchers.containsString("hidereveal_collapsed")));

		String[] jobs = listOfJobs.split(",");
		for (String jobName : jobs) {
			ElementStub findElement = browser.link(jobName).in(hideRevealDiv);
			Assert.assertNotNull("Should show job " + jobName, findElement);
		}
	}

	private ElementStub findHideRevealDiv(String title) {
		return browser.span(title).parentNode();
	}

	@com.thoughtworks.gauge.Step("Turn on auto refresh")
	public void turnOnAutoRefresh() throws Exception {
		autoRefresh = true;
		reloadPageWithAutoRefreshSetCorrectly();
	}

	@com.thoughtworks.gauge.Step("Turn off auto refresh")
	public void turnOffAutoRefresh() throws Exception {
		autoRefresh = false;
		reloadPage();
	}

	@com.thoughtworks.gauge.Step("Verify message <message> shows up - Already On Stage Detail Page")
	public void verifyMessageShowsUp(final String message) throws Exception {
		Assertions.waitFor(Timeout.TWENTY_SECONDS, new Function<ElementStub>() {
			public ElementStub call() {
				return browser.span("message").in(browser.div("/non_passing_tests/"));
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify job <pipelineName> <pipelineCounter> <stageName> <stageCounter> <job> links to the job detail page")
	public void verifyJobLinksToTheJobDetailPage(String pipelineName, int pipelineCounter, String stageName, int stageCounter, String job) throws Exception {
		navigateToJob(job);
		Assert.assertThat(browserWrapper.getCurrentUrl(), StringContains.containsString("tab/build/detail/" + scenarioState.pipelineNamed(pipelineName) + "/" + pipelineCounter + "/" + stageName + "/" + stageCounter + "/" + job));
	}

	@com.thoughtworks.gauge.Step("Navigate to job <job>")
	public void navigateToJob(String job) {
		browser.link(job).click();
		currentPageState.currentPageIs(Page.JOB_DETAILS);
		Assert.assertThat(browser.byId("build_console").getAttribute("className"), Is.is("current_tab"));
	}

	@com.thoughtworks.gauge.Step("Verify stage history has <stageHistories>")
	public void verifyStageHistoryHas(final String stageHistories) throws Exception {
		final String[] history = stageHistories.split("\\s*,\\s*");
		List<ElementStub> historyEntries = Assertions.waitFor(Timeout.THIRTY_SECONDS, new Function<List<ElementStub>>() {
			public List<ElementStub> call() {
				List<ElementStub> entries = browserWrapper.collectIn("div", "/^stage/", browser.div("stage_history").in(browser.div("/overview_widget/")));
				if (entries.size() == history.length) {
					return entries;
				}
				throw new RuntimeException(String.format("Expected the page to have %s entries. Instead found %s entries.", history.length, entries.size()));
			}

		});
		for (int i = 0; i < historyEntries.size(); i++) {
			ElementStub stageEntry = historyEntries.get(i);
			Assert.assertThat(stageEntry.getText(), Is.is(history[i]));
		}
	}

	@com.thoughtworks.gauge.Step("Verify selected stage history entry is <selected>")
	public void verifySelectedStageHistoryEntryIs(String selected) throws Exception {
		ElementStub selectedStageEntry = browser.link("/selected/").in(browser.div("/stage_history/").in(browser.div("/overview_widget/")));
		Assert.assertThat(selectedStageEntry.getText(), Is.is(selected));
	}

	@com.thoughtworks.gauge.Step("Verify stage bar does not have other runs")
	public void verifyStageBarDoesNotHaveOtherRuns() throws Exception {
		ElementStub otherStages = otherStageRunsElement();
		Assert.assertThat(browserWrapper.collectIn("list", "", otherStages).size(), Is.is(0));
	}

	private ElementStub otherStageRunsElement() {
		return browser.byId("other_stage_runs");
	}

	@com.thoughtworks.gauge.Step("Wait for stage bar to show <otherRunLabel> in other runs")
	public void waitForStageBarToShowInOtherRuns(final String otherRunLabel) throws Exception {
		Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {
			public boolean call() throws Exception {
				browser.byId("show_other_stage_runs").click();
				boolean visible = browser.link("/" + otherRunLabel + "/").in(otherStageRunsElement()).isVisible();
				if (!visible) {
					throw new Exception("Not visible");
				}
				return visible;
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify pipeline bar shows <stageName> as <status>")
	public void verifyPipelineBarShowsAs(final String stageName, final String status) throws Exception {
		final ElementStub element = browser.link("/\\bstage_bar\\b/").near(browser.link(stageName)).in(browser.div("pipeline_status_bar"));
		Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
			@Override
			public boolean call() throws Exception {
				return element.fetch("className").contains(status);
			}

			public String toString() {
				return String.format("Expected stage [%s] status ot be [%s]", stageName, status);
			}

		});
	}

	@com.thoughtworks.gauge.Step("Verify stage history shows current stage as <status>")
	public void verifyStageHistoryShowsCurrentStageAs(String status) throws Exception {
		ElementStub stageBar = browser.div("/color_code_stage/").in(browser.link("/selected/").in(browser.div("stage_history")));
		assertThat(stageBar.fetch("className"), Matchers.containsString(status));
	}

	@com.thoughtworks.gauge.Step("Verify stage result shows <status>")
	public void verifyStageResultShows(String status) throws Exception {
		ElementStub stageBar = browser.span("/message/").in(browser.div("stage_result"));
		assertThat(stageBar.getText(), Matchers.containsString(status));
	}

	@com.thoughtworks.gauge.Step("Wait for stage result to show <result>")
	public void waitForStageResultToShow(final String result) throws Exception {
		 /* looks like a system latency issue, when cancelled the stage on stage details page and then try to reload the page immediatly
		 the refresh is not successful, so added this forced wait. Should be removed after analysing the API latency if any */
		Assertions.sleepMillis(10000);
		Assertions.waitUntil(Timeout.FIVE_MINUTES, new Predicate() {

			@Override
			public boolean call() throws Exception {
				reloadPage();
				ElementStub stageBar = browser.span("/message/").in(browser.div("stage_result"));
				return stageBar.getText().contains(result);
			}
		});
	}

	public void verifyStageBarDurationShows(String duration) throws Exception {
		ElementStub stageDuration = browser.span("/message/").in(browser.div("stage_result"));
		assertThat(stageDuration.getText(), Matchers.containsString(duration));
	}

	@com.thoughtworks.gauge.Step("Verify stage bar duration shows a time")
	public void verifyStageBarDurationShowsATime() throws Exception {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			String duration = "NOT FOUND";

			public boolean call() throws Exception {
				ElementStub stageDuration = browser.span("time").near(browser.span("Duration:").in(browser.div("stage_run_details")));
				duration = stageDuration.getText();
				return Pattern.matches("\\d{2}\\:\\d{2}\\:\\d{2}", duration);
			}

			@Override
			public String toString() {
				return "Expected duration to be a time but was " + duration;
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify stage bar triggered by shows <user>")
	public void verifyStageBarTriggeredByShows(String user) throws Exception {
		ElementStub stageTriggeredBy = browser.span(Regex.wholeWord("who")).in(browser.div("schedule_info"));
		assertThat(stageTriggeredBy.getText(), Matchers.containsString(user));
	}

	@com.thoughtworks.gauge.Step("Verify stage bar triggered automatically by changes")
	public void verifyStageBarTriggeredAutomaticallyByChanges() throws Exception {
		ElementStub stageTriggeredBy = browser.span("label").in(browser.div("schedule_info"));
		assertThat(stageTriggeredBy.getText(), Is.is("Automatically triggered"));
	}

	@com.thoughtworks.gauge.Step("Verify stage bar triggered at shows a date")
	public void verifyStageBarTriggeredAtShowsADate() throws Exception {
		ElementStub stageTriggeredBy = browser.span(Regex.wholeWord("time")).in(browser.div("schedule_info"));
		assertTrue(Pattern.matches("\\d{2} \\w{3} \\d{4} at \\d{2}:\\d{2}:\\d{2} .*", stageTriggeredBy.getText()));
	}

	@com.thoughtworks.gauge.Step("Verify the lock status is <lockStatus>")
	public void verifyTheLockStatusIs(final String lockStatus) throws Exception {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			public boolean call() throws Exception {
				reloadPage();
				ElementStub element = browser.div(Regex.matches("locked_instance"));
				return element.getText().contains(lockStatus);
			}
		});
	}

	@com.thoughtworks.gauge.Step("Unlock the pipeline")
	public void unlockThePipeline() throws Exception {
		Assertions.waitFor(Timeout.FIVE_SECONDS, new Function<ElementStub>() {
			public ElementStub call() {
				return browser.link("Click to unlock");
			}
		}).click();
	}

	@com.thoughtworks.gauge.Step("Verify shows history page <pageNumber>")
	public void verifyShowsHistoryPage(Integer pageNumber) throws Exception {
		assertThat(pageNumber(pageNumber).getText(), Is.is(String.valueOf(pageNumber)));
	}

	private ElementStub pageNumber(Integer pageNumber) {
		return browser.byId("stage_history_" + pageNumber);
	}

	@com.thoughtworks.gauge.Step("Click on history page <pageNumber>")
	public void clickOnHistoryPage(final Integer pageNumber) throws Exception {
		pageNumber(pageNumber).click();
	}

	@com.thoughtworks.gauge.Step("Verfy pipeline <pipelineLabel> modified by <users>")
	public void verfyPipelineModifiedBy(String pipelineLabel, String users) throws Exception {
		ElementStub userElement = browser.div("users").near(fbhPipelineLabelSpan(pipelineLabel));
		String[] individualUsers = users.split(",");
		String actualUsers = userElement.getText();
		assertThat(actualUsers.startsWith("By "), Is.is(true));
		for (String user : individualUsers) {
			assertThat(actualUsers, StringContains.containsString(user));
		}
	}

	@com.thoughtworks.gauge.Step("Verify that <stageLocator> stage is displayed")
	public void verifyThatStageIsDisplayed(String stageLocator) throws Exception {
		assertThat(browserWrapper.getCurrentUrl(), containsString(scenarioState.expand(stageLocator)));
	}



	private String userErrorMessage() {
		return browser.div("/message_pane/").getText();
	}

	public void assertUserErrorMessageContains(final String s) throws Exception {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			public boolean call() throws Exception {
				return userErrorMessage().contains(s);
			}
		});
	}

	public void assertUserErrorMessageIsEmpty() throws Exception {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			public boolean call() throws Exception {
				return userErrorMessage().trim().length() == 0;
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify cruise footer - Already On Stage Detail Page")
	@Override
	public void verifyCruiseFooter() throws Exception {
		super.verifyCruiseFooter();
	}

	@com.thoughtworks.gauge.Step("Verify commit <revisionAlias> is shown with user <userName> and comment <comment> for material <materialName> of type <materialType>")
	public void verifyCommitIsShownWithUserAndCommentForMaterialOfType(String revisionAlias, String userName, String comment, String materialName, String materialType) throws Exception {
		ElementStub materialSection = browser.div(AlreadyOnStageDetailMaterialsTab.materialMessage(materialName, materialType)).parentNode();
		assertThat(materialSection.exists(), Is.is(true));
		String rev = repositoryState.getRevisionFromAlias(revisionAlias);
		String text = materialSection.getText();
		assertThat(text, containsString(rev));
		assertThat(text, containsString(userName));
		assertThat(text, containsString(comment));
	}

	//Temporary solution until package is being implemented as full fledged material
	
	@com.thoughtworks.gauge.Step("Verify commit <revision> is shown with user <userName> and comment <comment> for package material <materialName>")
	public void verifyCommitIsShownWithUserAndCommentForPackageMaterial(String revision, String userName, String comment, String materialName) throws Exception {
		ElementStub materialSection = browser.div(AlreadyOnStageDetailMaterialsTab.materialMessage(materialName, "Package")).parentNode();
		assertThat(materialSection.exists(), Is.is(true));
		String text = materialSection.getText();
		assertThat(text, containsString(revision));
		assertThat(text, containsString(userName));
		assertThat(text, containsString(comment));
	}
	
	private void assertSectionIsVisible(ElementStub materialSection, String commentMessage) {
		ElementStub commentSection = browser.div(commentMessage).in(materialSection);
		assertThat(commentSection.isVisible(), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Go to materials tab")
	public void goToMaterialsTab() {
		goToTab("Materials", CurrentPageState.Page.STAGE_DETAIL_MATERIALS_TAB);
	}

	public void verifyLabelForMaterial(String label, String pipelineName) throws Exception {
		ElementStub materialSection = browser.div(AlreadyOnStageDetailMaterialsTab.materialMessage(scenarioState.expand(pipelineName), "Pipeline")).parentNode();
		assertSectionIsVisible(materialSection, AlreadyOnStageDetailMaterialsTab.dependencyRevisionLabelMessage(label));
	}

	@com.thoughtworks.gauge.Step("Verify revision <revision> having label <label> is shown for material <pipelineName>")
	public void verifyRevisionHavingLabelIsShownForMaterial(String revision, String label, String pipelineName) throws Exception {
		ElementStub materialSection = browser.div(AlreadyOnStageDetailMaterialsTab.materialMessage(scenarioState.expand(pipelineName), "Pipeline")).parentNode();
		assertThat(materialSection.exists(), Is.is(true));
		String text = materialSection.getText();
		assertThat(text.contains(scenarioState.expand(revision)), Is.is(true));
		assertThat(text.contains(label), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Go to jobs tab")
	public void goToJobsTab() throws Exception {
		goToTab("Jobs", CurrentPageState.Page.STAGE_DETAIL_JOBS_TAB);
	}

	@com.thoughtworks.gauge.Step("Go to overview tab")
	public void goToOverviewTab() throws Exception {
		goToTab("Overview", CurrentPageState.Page.STAGE_DETAILS);
	}

	private void goToTab(String tabName, Page expectedPage) {
		browser.link(tabName).in(browser.div("sub_tabs_container")).click();
		currentPageState.currentPageIs(expectedPage);
	}

	@com.thoughtworks.gauge.Step("Verify pipeline <pipelineCounter> having label <pipelineLabel> has version <revision>")
	public void verifyPipelineHavingLabelHasVersion(String pipelineCounter, String pipelineLabel, String revision) throws Exception {
		final ElementStub changesLink = browser.link(String.format("changes_button_for_pipeline_%s", pipelineLabel)).near(fbhPipelineLabelSpan(pipelineLabel));
		final String rev = repositoryState.getRevisionFromAlias(revision);

		String useNewRails = System.getenv("USE_NEW_RAILS");
		final String revisionString = (useNewRails != null && useNewRails.equals("N")) ? rev : rev + " - VSM";

		assertThat(browser.isVisible(browser.cell(revisionString)), Is.is(false));
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
			@Override
			public boolean call() throws Exception {
				ElementStub cell = browser.cell(revisionString);
				boolean visible = browser.isVisible(cell);
				if (!visible)
					changesLink.click();
				return visible;
			}
		});
	}

	private ElementStub fbhPipelineLabelSpan(String pipelineLabel) {
		return browser.span("Pipeline Label: " + pipelineLabel);
	}

	@com.thoughtworks.gauge.Step("Wait for pipeline with label <label> to appear")
	public void waitForPipelineWithLabelToAppear(final String label) throws Exception {
		 /* looks like a system latency issue, when schedule the pipeline thru API and immediately
		 start reloading the page on browser, the browser not showing up the latest pipeline results, so added this forced wait. Should be removed after analysing the API latency if any */
		Assertions.sleepMillis(10000);
		Assertions.waitUntil(Timeout.THREE_MINUTES, new Predicate() {
			@Override
			public boolean call() throws Exception {
				reloadPage();
				return fbhPipelineLabelSpan(label).isVisible();
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify failure details for job <jobName> suite <suiteName> test <testName> contains <message> with stacktrace <stacktrace>")
	public void verifyFailureDetailsForJobSuiteTestContainsWithStacktrace(String jobName, String suiteName, String testName, String message, String stacktrace) throws Exception {
		String detailsLink = String.format("//div[@class='suite' and ./span/.='%s']/..//td[@class='test_name' and ./span[.='%s']]//a[.='[Trace]' and ../a[.='%s']]", suiteName, testName, jobName);
		browser.byXPath(detailsLink).click();
		assertThat(browser.byXPath("//div[@id='fbh_build_cause_content']//tr[@class='failure_message']").text(), Matchers.containsString(message));
		for (String stackTraceLine : stacktrace.split(",")) {
			assertThat(browser.byXPath("//div[@id='fbh_build_cause_content']").text(), Matchers.containsString(stackTraceLine));
		}
	}

	public void verifyOnStageDetailsPageFor(String stageLocator) throws Exception {
		String url = browser.fetch("window.location.href");
		assertThat(url, Matchers.containsString(scenarioState.expand(stageLocator)));
	}

	@com.thoughtworks.gauge.Step("Click compare link - Already on stage Detail Page")
	public void clickCompareLink() throws Exception {
		browser.link("Compare").click();
		currentPageState.currentPageIs(Page.COMPARE_PAGE);
	}

	@com.thoughtworks.gauge.Step("Click compare link for pipeline counter <pipelineCounter>")
	public void clickCompareLinkForPipelineCounter(String pipelineCounter) throws Exception {
		List<ElementStub> entries = browserWrapper.collectIn("div", "/^stage/", browser.div("stage_history").in(browser.div("/overview_widget/")));
		for (ElementStub entry : entries) {
			if (pipelineCounter.equals(browser.span("pipeline_label wrapped_word").in(entry).text())) {
				browser.link(1).in(entry).click();
				currentPageState.currentPageIs(Page.COMPARE_PAGE);
				return;
			}
		}
		throw new RuntimeException(String.format("Expected the page to have %s pipeline counter under the Stage History widget.", pipelineCounter));
	}

	@com.thoughtworks.gauge.Step("Goto config tab")
	public void gotoConfigTab() throws Exception {
		goToTab("Config", CurrentPageState.Page.STAGE_DETAIL_CONFIG_TAB);
	}

	@com.thoughtworks.gauge.Step("Verify config changed marker after pipeline counter <pipelineCounter> stage counter <stageCounter> is a link")
	public void verifyConfigChangedMarkerAfterPipelineCounterStageCounterIsALink(String pipelineCounter, String stageCounter) throws Exception {
		verifyConfigChangedMarkerAfterPipelineCounter(pipelineCounter, stageCounter, true);
	}

	@com.thoughtworks.gauge.Step("Verify config changed marker after pipeline counter <pipelineCounter> stage counter <stageCounter> is not a link")
	public void verifyConfigChangedMarkerAfterPipelineCounterStageCounterIsNotALink(String pipelineCounter, String stageCounter) throws Exception {
		verifyConfigChangedMarkerAfterPipelineCounter(pipelineCounter, stageCounter, false);
	}
	
	@com.thoughtworks.gauge.Step("Click on config changed link after pipeline counter <pipelineCounter> stage counter <stageCounter>")
	public void clickOnConfigChangedLinkAfterPipelineCounterStageCounter(String pipelineCounter, String stageCounter) throws Exception {
		ElementStub configChangedContainer = browser.div(String.format("config_change counter_%s_%s", pipelineCounter, stageCounter));
		browser.link("Config Changed").in(configChangedContainer).click();
		currentPageState.currentPageIs(Page.STAGE_CONFIG_CHANGES_POPUP);
	}
	
	private void verifyConfigChangedMarkerAfterPipelineCounter(String pipelineCounter, String stageCounter, boolean isLink) {
		ElementStub configChangedContainer = browser.div(String.format("config_change counter_%s_%s", pipelineCounter, stageCounter));
		Assert.assertThat("Could find config changed marker", configChangedContainer.exists(), Is.is(true));
		Assert.assertThat(configChangedContainer.getText(), Is.is("Config Changed"));
		Assert.assertThat(browser.link("Config Changed").in(configChangedContainer).exists(), Is.is(isLink));
	}

	@com.thoughtworks.gauge.Step("Verify no config changed marker is present")
	public void verifyNoConfigChangedMarkerIsPresent() throws Exception {
		Assert.assertThat("Should not have seen any markers indicating that config changed, but did.", browser.span("Config Changed").exists(), Is.is(false));
	}

	@com.thoughtworks.gauge.Step("Verify rerun is disabled for stage <stageName>")
	public void verifyRerunIsDisabledForStage(String stageName) throws Exception {
		ElementStub rerunLink = browser.byId("stage_bar_rerun_" + stageName);
		assertThat(rerunLink.exists(), Is.is(false));
	}

	@com.thoughtworks.gauge.Step("Verify rerun is enabled for stage <stageName>")
	public void verifyRerunIsEnabledForStage(String stageName) throws Exception {
		ElementStub rerunLink = browser.byId("stage_bar_rerun_" + stageName);
		assertThat(rerunLink.exists(), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Verify that unauthorized access message is shown - Already On Stage Detail Page")
	public void verifyThatUnauthorizedAccessMessageIsShown() throws Exception {
		super.verifyThatUnauthorizedAccessMessageIsShown();
	}

	@com.thoughtworks.gauge.Step("Click on revision <revision>")
	public void clickOnRevision(String revision) throws Exception {
		browser.link(scenarioState.expand(revision)).click();
	}

	@com.thoughtworks.gauge.Step("Verify user can view config tab contents")
	public void verifyUserCanViewConfigTabContents() throws Exception {
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
			@Override
			public boolean call() throws Exception {
				browser.link("Config").in(browser.div("sub_tabs_container")).click();
				return (notificationElement().getText().trim().contains("This version of config"));
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify user cannot view config tab contents")
	public void verifyUserCannotViewConfigTabContents() throws Exception {
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
			@Override
			public boolean call() throws Exception {
				browser.link("Config").in(browser.div("sub_tabs_container")).click();
				return (notificationElement().getText().trim().contains("Historical configuration is available only for Go Administrators"));
			}
		});
	}

	private ElementStub notificationElement() {
		return browser.byXPath("//div[@id='ran_with_config']//p[@class='information']");
	}
	
	@com.thoughtworks.gauge.Step("Go to tests tab")
	public void goToTestsTab() {
		goToTab("Tests", CurrentPageState.Page.STAGE_DETAIL_TESTS_TAB);
	}

	@com.thoughtworks.gauge.Step("Verify that there are <numberOfJobsExpected> jobs")
	public void verifyThatThereAreJobs(Integer numberOfJobsExpected) throws Exception {
		List<ElementStub> jobList = new ArrayList<ElementStub>();
		for (int i = 0; i < 100; i++) {
			ElementStub row = browser.row(String.format("/job/[%d]", i));
			if (!row.exists())
				break;
			jobList.add(row);
		}
		assertThat(jobList.size(), is(numberOfJobsExpected));
	}
}
