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
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AlreadyOnStageDetailJobsTab extends CruisePage {

	private boolean autoRefresh;
    private final CurrentPageState currentPageState;

    public AlreadyOnStageDetailJobsTab(CurrentPageState currentPageState, ScenarioState scenarioState, Browser browser) {
		super(scenarioState, true, browser);
        this.currentPageState = currentPageState;
        this.autoRefresh = browserWrapper.isAutoRefresh();
		currentPageState.assertCurrentPageIs(CurrentPageState.Page.STAGE_DETAIL_JOBS_TAB);
	}

	@Override
	protected String url() {
		String url = browserWrapper.getCurrentUrl();
		url = fixAutoRefreshParam(url);
		return url;
	}

	private String fixAutoRefreshParam(String url) {
		url = url.replace("autoRefresh=" + !autoRefresh, "autoRefresh=" + autoRefresh);
		if (!url.contains("autoRefresh")) {
			if (!url.contains("?")) {
				url = url + "?";
			}
			url = url + "autoRefresh=" + autoRefresh;
		}
		return url;
	}

	@com.thoughtworks.gauge.Step("Turn on auto refresh - Already On Stage Detail Jobs Tab")
	public void turnOnAutoRefresh() throws Exception {
		autoRefresh = true;
		browser.navigateTo(url());
	}

	protected void reloadPage() {
		if (!autoRefresh) {
			browserWrapper.reload();
		}
	}

	@com.thoughtworks.gauge.Step("Verify <job> ends with state <state> and result <result>")
	public void verifyEndsWithStateAndResult(final String job, final String state, final String result) throws Exception {
		Assertions.waitUntil(Timeout.THREE_MINUTES, new Predicate() {
			@Override
			public boolean call() throws Exception {
				reloadPage();
                return verifyJobStateAndResult(job, state, result);
			}
		});
	}

    private boolean verifyJobStateAndResult(String job, String state, String result) {
        ElementStub jobDetails = browser.cell(job).parentNode();
        return browser.cell(state).in(jobDetails).exists() && browser.cell(Regex.matches(result)).in(jobDetails).exists();
    }

    @com.thoughtworks.gauge.Step("Verify rerun button is disabled")
	public void verifyRerunButtonIsDisabled() throws Exception {
        assertRerunButtonState(false);
	}

	private void assertRerunButtonState(boolean status) {
		assertThat(browser.submit("RERUN SELECTED").isVisible(), is(status));
	}

	@com.thoughtworks.gauge.Step("Rerun <jobNames> jobs")
	public void rerunJobs(String jobNames) throws Exception {
        for (String jobName : jobNames.split("\\s*,\\s*")) {
            ElementStub selector = browser.checkbox(jobName);
            selector.check();
        }
        browser.submit("RERUN SELECTED").click();
	}

	@com.thoughtworks.gauge.Step("Verify looking at <stageName> having counter <stageCounter>")
	public void verifyLookingAtHavingCounter(String stageName, Integer stageCounter) throws Exception {
		assertThat(url().contains(String.format("%s/%s", stageName, stageCounter)), is(true));
	}

	@com.thoughtworks.gauge.Step("Verify job <jobName> has state <jobState> and result <jobResult>")
	public void verifyJobHasStateAndResult(String jobName, String jobState, String jobResult) throws Exception {
        assertThat(verifyJobStateAndResult(jobName, jobState, jobResult), is(true));
	}

	@com.thoughtworks.gauge.Step("Navigate to <jobName> job")
	public void navigateToJob(String jobName) throws Exception {
	    browser.link(jobName).click();
        currentPageState.currentPageIs(CurrentPageState.Page.JOB_DETAILS);
	}

	@com.thoughtworks.gauge.Step("Verify rerun button is enabled")
	public void verifyRerunButtonIsEnabled() throws Exception {
		assertRerunButtonState(true);
	}

	@com.thoughtworks.gauge.Step("Verify rerun failed with cause <cause>")
	public void verifyRerunFailedWithCause(String cause) throws Exception {
        ElementStub flashPane = browser.div("message_pane");
        String errorMessage = flashPane.text();
        assertThat(errorMessage, containsString(cause));
    }

	@com.thoughtworks.gauge.Step("Remember that job <jobName> ran on agent <agentAlias>")
	public void rememberThatJobRanOnAgent(String jobName, String agentAlias) throws Exception {
		scenarioState.rememberAgentByAlias(agentAlias, getAgentDetailsForJob(jobName));
	}

	@com.thoughtworks.gauge.Step("Verify job <jobName> ran on agent <agentAlias>")
	public void verifyJobRanOnAgent(String jobName, String agentAlias) throws Exception {
		String exepectedAgentDetails = getAgentDetailsForJob(jobName);
		String actualAgentDetails = scenarioState.getAgentByAlias(agentAlias);
		assertThat(actualAgentDetails, is(exepectedAgentDetails));
	}

	private String getAgentDetailsForJob(String jobName) {
		ElementStub jobDetails = browser.cell(jobName).parentNode();
		return browser.cell("agent").in(jobDetails).text();
	}

	@com.thoughtworks.gauge.Step("Click on agent for job <jobName>")
	public void clickOnAgentForJob(String jobName) throws Exception {
		ElementStub jobDetails = browser.cell(jobName).parentNode();
		ElementStub agentCell = browser.cell("agent").in(jobDetails);
		String agentAlias = browser.cell("agent").in(jobDetails).text();
		ElementStub agentLink = browser.link(agentAlias).in(agentCell);
		assertThat("Agent link does not exist", agentLink.exists(), is(true));
		agentLink.click();
		currentPageState.currentPageIs(Page.AGENT_DETAILS);
	}
}
