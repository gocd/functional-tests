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

import com.thoughtworks.cruise.page.CruiseAdminPage;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.StringUtil;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class AlreadyOnClonePipelinePopup extends CruiseAdminPage {
    private Browser browser;
    private CurrentPageState currentPageState;

    public AlreadyOnClonePipelinePopup(Browser browser, ScenarioState state,
            CurrentPageState pageState) {
        super(state, true, browser);
        this.browser = browser;
        this.currentPageState = pageState;
        pageState.assertCurrentPageIs(Page.CLONE_PIPELINE_POPUP);
    }

    @Override
    protected String url() {
        // TODO Auto-generated method stub
        return null;
    }

    @com.thoughtworks.gauge.Step("Save for success")
	public void saveForSuccess() throws Exception {
        save();
        currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_GENERAL_PAGE);
    }
    
    @com.thoughtworks.gauge.Step("Save - Already On Clone Pipeline Popup")
	public void save() throws Exception {
        browser.submit("SAVE").click();
    }

    @com.thoughtworks.gauge.Step("Verify error message <expectedMessage> - Already On Clone Pipeline Popup")
	public void verifyErrorMessage(String expectedMessage) throws Exception {
        ElementStub errorDiv = browser.div("form_error");
        Assert.assertThat(errorDiv.exists(), Is.is(true));
        Assert.assertThat(errorDiv.getText(), Is.is(expectedMessage));
    }

    @com.thoughtworks.gauge.Step("Enter pipeline name <pipelineName>")
	public void enterPipelineName(String pipelineName) throws Exception {
        String actualName = pipelineName + StringUtil.shortUUID();
        browser.textbox("pipeline_group[pipeline][name]").setValue(actualName);
        scenarioState.pushPipeline(pipelineName, actualName);
    }

    @com.thoughtworks.gauge.Step("Enter pipeline group name <pipelineGroupName>")
	public void enterPipelineGroupName(String pipelineGroupName) throws Exception {
        browser.textbox("pipeline_group[group]").setValue(pipelineGroupName);
    }
    @com.thoughtworks.gauge.Step("Assert mD5 - Already On Clone Pipeline Popup")
	public void assertMD5() throws Exception {
    	super.assertMD5();
    }
    
    
    @com.thoughtworks.gauge.Step("Close popup - Already On Clone Pipeline Popup")
	public void closePopup() throws Exception {
    	browser.link("Close window").click();
    }
}
