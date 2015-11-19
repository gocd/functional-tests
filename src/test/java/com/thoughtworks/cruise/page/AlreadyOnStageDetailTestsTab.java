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

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.Matchers;
import org.junit.Assert;

public class AlreadyOnStageDetailTestsTab extends CruisePage{
	
	public AlreadyOnStageDetailTestsTab(CurrentPageState currentPageState,ScenarioState scenarioState, Browser browser) {
		super(scenarioState,true,browser);
		currentPageState.assertCurrentPageIs(CurrentPageState.Page.STAGE_DETAIL_TESTS_TAB);
	}

	@Override
	protected String url() {
		return browserWrapper.getCurrentUrl();
	}
	
	@com.thoughtworks.gauge.Step("Verify test runs <total> failures <failures> errors <errors>")
	public void verifyTestRunsFailuresErrors(Integer total, String failures, String errors) throws Exception {
		Assert.assertThat(totalCount("failures"), Matchers.is("Total Failures: " + failures));
		Assert.assertThat(totalCount("errors"), Matchers.is("Total Errors: " + errors));
	}
	
	private String totalCount(String type) {
		return browser.span(type).in(browser.div(Regex.wholeWord("counts")).in(browser.div(Regex.wholeWord("non_passing_tests")))).getText();
	}

	@com.thoughtworks.gauge.Step("Verify message <message> shows up - Already on Stage Detail Tests Tab")
	public void verifyMessageShowsUp(final String message) throws Exception {
		Assertions.waitFor(Timeout.TWENTY_SECONDS, new Function<ElementStub>() {
			public ElementStub call() {
				return browser.span("message").in(browser.div("/non_passing_tests/"));
			}
		});
	}
	

}

