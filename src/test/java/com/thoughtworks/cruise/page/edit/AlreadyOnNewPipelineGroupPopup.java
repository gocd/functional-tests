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

package com.thoughtworks.cruise.page.edit;

import com.thoughtworks.cruise.page.CruisePage;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class AlreadyOnNewPipelineGroupPopup extends CruisePage {
	private Browser browser;
	private CurrentPageState currentPageState;

	public AlreadyOnNewPipelineGroupPopup(Browser browser, ScenarioState state,
			CurrentPageState pageState) {
		super(state, true, browser);
		this.browser = browser;
		this.currentPageState = pageState;
		pageState.assertCurrentPageIs(Page.NEW_PIPELINE_GROUP_POPUP);
	}

	@Override
	protected String url() {
		// TODO Auto-generated method stub
		return null;
	}

	@com.thoughtworks.gauge.Step("Save - Already On New Pipeline Group Popup")
	public void save() throws Exception {
		browser.submit("SAVE").click();
	}

	@com.thoughtworks.gauge.Step("Verify error message <expectedMessage> - Already On New Pipeline Group Popup")
	public void verifyErrorMessage(String expectedMessage) throws Exception {
		ElementStub errorDiv = browser.div("form_error");
		Assert.assertThat(errorDiv.exists(), Is.is(true));
		Assert.assertThat(errorDiv.getText(), Is.is(expectedMessage));
	}

	@com.thoughtworks.gauge.Step("Enter pipeline group name <pipelineGroupName> - Already On New Pipeline Group Popup")
	public void enterPipelineGroupName(String pipelineGroupName) throws Exception {
		browser.textbox("group[group]").setValue(pipelineGroupName);
	}

	@com.thoughtworks.gauge.Step("Save for success - Already On New Pipeline Group Popup")
	public void saveForSuccess() throws Exception {
		save();
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_GROUP);
	}
}
