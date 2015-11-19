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


import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class AlreadyOnConfigChangesPopup {

	private Browser browser;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	private final CurrentPageState currentPageState;

	public AlreadyOnConfigChangesPopup(Browser browser, CurrentPageState currentPageState) {
		this.browser = browser;
		this.currentPageState = currentPageState;
		currentPageState.assertCurrentPageIs(Page.STAGE_CONFIG_CHANGES_POPUP);
	}

	@com.thoughtworks.gauge.Step("Verify added changes contains lines <line>")
	public void verifyAddedChangesContainsLines(String line) throws Exception {
		ElementStub addedLines = browser.row("add").in(browser.div("config-changes"));
		assertThat(addedLines.getText(), containsString(line));
	}

	@com.thoughtworks.gauge.Step("Verify removed changes contains lines <line>")
	public void verifyRemovedChangesContainsLines(String line) throws Exception {
		ElementStub addedLines = browser.row("remove").in(browser.div("config-changes"));
		assertThat(addedLines.getText(), containsString(line));
	}

}