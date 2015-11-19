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

package com.thoughtworks.cruise.editpipelinewizard;

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;

import static junit.framework.Assert.assertEquals;

// JUnit Assert framework can be used for verification

public class AlreadyOnProjectManagementPage extends AlreadyOnEditPipelineWizardPage {

	
	public AlreadyOnProjectManagementPage(ScenarioState scenarioState, Browser browser, CurrentPageState currentPageState) {
		super(currentPageState,scenarioState,browser);		
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_PROJECT_MANAGEMENT_PAGE);
	}

	@Override
	protected String url() {
		// TODO Auto-generated method stub
		return null;
	}

	@com.thoughtworks.gauge.Step("Enter <trackingToolRegex> tracking tool regex")
	public void enterTrackingToolRegex(String trackingToolRegex) throws Exception {
		browser.textbox("pipeline_trackingTool_regex").setValue(trackingToolRegex);
	}

	@com.thoughtworks.gauge.Step("Enter <tracingToolURL> for tracking tool uRL")
	public void enterForTrackingToolURL(String tracingToolURL) throws Exception {
		browser.textbox("pipeline_trackingTool_link").setValue(tracingToolURL);
	}

	@com.thoughtworks.gauge.Step("Enter <mingleURL> for mingle uRL")
	public void enterForMingleURL(String mingleURL) throws Exception {
		browser.textbox("pipeline_mingleConfig_baseUrl").setValue(mingleURL);
	
	}

	@com.thoughtworks.gauge.Step("Enter <mingleProjectIdentifier> for mingle project identifier")
	public void enterForMingleProjectIdentifier(String mingleProjectIdentifier) throws Exception {
		browser.textbox("pipeline_mingleConfig_projectIdentifier").setValue(mingleProjectIdentifier);
	}

	@com.thoughtworks.gauge.Step("Enter <groupingConditions> for mQA grouping conditions")
	public void enterForMQAGroupingConditions(String groupingConditions) throws Exception {
		browser.textbox("pipeline_mingleConfig_mqlCriteria_mql").setValue(groupingConditions);
	
	}
	
	public void selectNoneOptionForTrackingTool() {
	    browser.radio("pipeline_none").check();
	}
	
	@com.thoughtworks.gauge.Step("Select mingle option for tracking tool")
	public void selectMingleOptionForTrackingTool() {
        browser.radio("pipeline_mingle").check();
    }   

	@com.thoughtworks.gauge.Step("Select custom option for tracking tool")
	public void selectCustomOptionForTrackingTool() {
        browser.radio("pipeline_trackingTool").check();
    }
	
	@com.thoughtworks.gauge.Step("Go to materials page")
	public void goToMaterialsPage() throws Exception {
		super.goToMaterialsPage();
	}

	@com.thoughtworks.gauge.Step("Verify reset button exists - Already on project management page")
	public void verifyResetButtonExists() throws Exception {
		super.verifyResetButtonExists();
	}

	@com.thoughtworks.gauge.Step("Click save - Already On Project Management Page")
	public void clickSave() throws Exception {
		super.clickSave();
	}

	@com.thoughtworks.gauge.Step("Verify <message> message is displayed - Already on project management page")
	public void verifyMessageIsDisplayed(String message) throws Exception {
		super.verifyMessageIsDisplayed(message);
	}	
	
	public boolean isMessagePresent(String message) {
		return super.isMessagePresent(message);
	}

	@com.thoughtworks.gauge.Step("Go to environment variables page - Already on project management page")
	public void goToEnvironmentVariablesPage() throws Exception {
		super.goToEnvironmentVariablesPage();
	}

	@com.thoughtworks.gauge.Step("Open parameters page - Already on project management page")
	public void openParametersPage() throws Exception {
		super.openParametersPage();
	}

    @com.thoughtworks.gauge.Step("Verify <regex> as tracking tool regex")
	public void verifyAsTrackingToolRegex(String regex) throws Exception {
        assertEquals(browser.textbox("pipeline_trackingTool_regex").getText(), regex);
    }

    @com.thoughtworks.gauge.Step("Verify <url> as tracking tool uRL")
	public void verifyAsTrackingToolURL(String url) throws Exception {
        assertEquals(browser.textbox("pipeline_trackingTool_link").getText(), url);
    }

    @com.thoughtworks.gauge.Step("Verify custom as option for tracking tool")
	public void verifyCustomAsOptionForTrackingTool() throws Exception {
        assertEquals(browser.radio("pipeline_trackingTool").checked(), true);
    }
	
    @com.thoughtworks.gauge.Step("Assert mD5 - Already on Project Management Page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }

}
