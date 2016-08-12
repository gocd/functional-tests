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

import com.jayway.restassured.config.Config;
import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.SahiBrowserWrapper;
import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.matchers.StringContains;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class AlreadyOnAddEnvironmentPage extends CruisePage {

	private final CurrentPageState currentPageState;
	private final Configuration config;
	private String md5key= "md5_form";
	
	
	

	public AlreadyOnAddEnvironmentPage(Browser browser, ScenarioState scenarioState, CurrentPageState currentPageState) {
		super(scenarioState, true, browser);
		this.currentPageState = currentPageState;
		this.config = new Configuration(scenarioState);
		
	}

	@com.thoughtworks.gauge.Step("Enter name <environmentName>")
	public void enterName(String environmentName) throws Exception {
		envNameField().setValue(environmentName);
		if (environmentName.isEmpty()) {
			browser.div("body_content").click();
		}
	}

	private ElementStub envNameField() {
		ElementStub envNameField = browser.textbox("environment_name");
		return envNameField;
	}

	@com.thoughtworks.gauge.Step("Finish <sectionName>")
	public void finish(String sectionName) throws Exception {
		ElementStub section = browser.div(tabId(sectionName));
		ElementStub button = browser.span("FINISH").in(section).parentNode();
		button.click();
		currentPageState.currentPageIs(Page.ENVIONMENT_SHOW_PAGE);		
	}

	@com.thoughtworks.gauge.Step("Verify error <message> message shows up")
	public void verifyErrorMessageShowsUp(String message) throws Exception {
		Assert.assertThat(browser.div("message_pane").text(), StringContains.containsString(message));
	}

	@com.thoughtworks.gauge.Step("Verify pipelines <pipelineNames> are present")
	public void verifyPipelinesArePresent(String pipelineNames) throws Exception {
		List<String> pipelines = toActualNameList(pipelineNames.split(","));
		SahiBrowserWrapper wrapper = new SahiBrowserWrapper(browser);
		List<ElementStub> pipelineLabels = wrapper.collectIn("label", "label", browser.div("selector_pipeline"));
		for (ElementStub label : pipelineLabels) {
			assertThat(pipelines, hasItem(label.getText().trim()));
		}
	}

	private List<String> toActualNameList(String[] pipelineNames) {
		List<String> stringList = new ArrayList<String>();
		for (String pipelineName : pipelineNames) {
			stringList.add(scenarioState.pipelineNamed(pipelineName.trim()));
		}
		return stringList;
	}

	@com.thoughtworks.gauge.Step("Select pipelines <pipelineNames>")
	public void selectPipelines(String pipelineNames) throws Exception {
		checkListOfPipelines(pipelineNames, true);
	}
	
	@com.thoughtworks.gauge.Step("UnSelect pipelines <pipelineNames>")
	public void unSelectPipelines(String pipelineNames) throws Exception {
		checkListOfPipelines(pipelineNames, false);
	}

	private void checkListOfPipelines(String pipelineNames, boolean state) {
		List<String> pipelines = toActualNameList(pipelineNames.split(","));
		for (String pipeline : pipelines) {
			ElementStub checkbox = elementPipelineCheckbox(pipeline);
			if (state) { 
				checkbox.check(); 
			}
			else {
				checkbox.uncheck();
			}
			assertThat(checkbox.checked(), is(state));
		}
	}

	private ElementStub elementPipelineCheckbox(String pipeline) {
		return browser.checkbox("pipeline_" + pipeline);
	}

	@com.thoughtworks.gauge.Step("Verify pipelines <pipelineNamesString> belongs to environment <environmentName>")
	public void verifyPipelinesBelongsToEnvironment(String pipelineNamesString, String environmentName) throws Exception {
		List<String> pipelines = toActualNameList(pipelineNamesString.split(","));
		SahiBrowserWrapper wrapper = new SahiBrowserWrapper(browser);
		List<ElementStub> pipelineLabels = wrapper.collectIn("label", "label", browser.div("unavailable_pipelines"));
		for (String pipelineName : pipelines) {		
			for (ElementStub label : pipelineLabels) {
				assertThat(pipelines, hasItem(label.getText().trim()));
			}
		}		
	}
	
	@com.thoughtworks.gauge.Step("Verify pipelines <pipelineNamesString> are hidden")
	public void verifyPipelinesAreHidden(String pipelineNamesString) throws Exception {
		assertPipelinesvisible(pipelineNamesString, false);
	}

	private void assertPipelinesvisible(String pipelineNamesString, boolean shouldBeVisible) {
		String[] pipelineNames = pipelineNamesString.split(",");
		for(String pipelineName : pipelineNames) {
			assertThat(pipelineEntry(pipelineName).isVisible(), is(shouldBeVisible));
		}
	}

	private ElementStub pipelineEntry(String pipelineName) {
//		ElementStub pipelineEntry = browser.byXPath(String.format("//tr[.//span[.='%s']]",  scenarioState.pipelineNamed(pipelineName)));
	    ElementStub pipelineEntry = browser.label(scenarioState.pipelineNamed(pipelineName));
		return pipelineEntry;
	}

	@Override
	public void open() {
	}
	
	@Override
	protected String url() {
		return "";
	}

	@com.thoughtworks.gauge.Step("Select agent <agentHostname>")
	public void selectAgent(String agentHostname) throws Exception {
		agentCheckbox(agentHostname).check();
	}

	private ElementStub agentCheckbox(String agentHostname) {
		return browser.checkbox(0).near(browser.cell(agentHostname));
	}

	public void addEnvironmentVariable(String name, String value) throws Exception {
		browser.textbox("environment_variable_name").setValue(name);
		browser.textbox("environment_variable_value").setValue(value);
		browser.link("Add Pair").click();
	}

	public void deleteEnvironmentVariable(String name) throws Exception {
		browser.link("Remove").near(browser.cell(name)).click();
		assertFalse(browser.cell(name).exists());
	}

	public void verifyAddEnvironmentFailsWithError(String errorMessage) throws Exception {
		assertThat(browser.span("env_error_message").getText(),is(errorMessage));
	}

	public void assertEnvironmentVariableTableHasEntriesFor(String names) throws Exception {
		String[] envNames = names == null ? new String[0] : names.split(",");
		ElementStub table = browser.table(Regex.wholeWord("variables_set"));
		List<ElementStub> variableNameCells = browserWrapper.collectIn("cell", "name", table);
		assertThat(variableNameCells.size() ,is(envNames.length));
		for (int i = 0; i < variableNameCells.size(); i++) {
			ElementStub cell = variableNameCells.get(i);
			assertThat(cell.getText(), is(envNames[i]));
		}
	}

	public void assertEnvironmentVariableTableHasNoEntries() throws Exception {
		assertEnvironmentVariableTableHasEntriesFor(null);
	}

	public void verifyAgentsSectionHasInIt(String message) throws Exception {
		assertThat(browser.div("input").in(browser.div("agents_selection_table")).getText(), is(message));
	}

	private String tabId(String sectionName) {
		return "tab-content-of-" + sectionName;
	}

	@com.thoughtworks.gauge.Step("Select tab <tabName>")
	public void selectTab(String tabName) throws Exception {
		browser.link("tab-link-of-" + tabName).click();
	}

	public void verifyPipelinesAreVisible(String pipelineNames) throws Exception {
		assertPipelinesvisible(pipelineNames, true);
	}

	public void enableShowAllPipelines() throws Exception {
		browser.checkbox("show_unavailable_pipelines").check();
	}
	
	public void disableShowAllPipelines() throws Exception {
		browser.checkbox("show_unavailable_pipelines").uncheck();
	}

	@com.thoughtworks.gauge.Step("Verify pipelines <pipelines> is visible")
	public void verifyPipelinesIsVisible(String pipelines) throws Exception {
		verifyPipelinesAreVisible(pipelines);
	}

	@com.thoughtworks.gauge.Step("Verify name is <name>")
	public void verifyNameIs(String name) throws Exception {
		assertThat(envNameField().getValue(), is(name));
	}

	@com.thoughtworks.gauge.Step("Verify pipelines <pipelineNames> are selected")
	public void verifyPipelinesAreSelected(String pipelineNames) throws Exception {
		List<String> pipelines = toActualNameList(pipelineNames.split(","));
		for (String pipeline : pipelines) {
			ElementStub checkbox = elementPipelineCheckbox(pipeline);
			assertThat(checkbox.checked(), is(true));
		}
	}

	@com.thoughtworks.gauge.Step("Verify agent <agentName> is selected")
	public void verifyAgentIsSelected(String agentName) throws Exception {
		assertThat(agentCheckbox(agentName).checked(), is(true));
	}

	@com.thoughtworks.gauge.Step("Verify tab <tabName> is enabled")
	public void verifyTabIsEnabled(String tabName) throws Exception {
		verifyTabsAreEnabled(tabName);
	}

	@com.thoughtworks.gauge.Step("Verify tabs <tabNames> are enabled")
	public void verifyTabsAreEnabled(String tabNames) throws Exception {
		verifyTabsAreDisabled(tabNames, false);
	}
	
	@com.thoughtworks.gauge.Step("Verify tabs <tabNames> are disabled")
	public void verifyTabsAreDisabled(String tabNames) throws Exception {
		verifyTabsAreDisabled(tabNames, true);
	}

	private void verifyTabsAreDisabled(String tabNames, boolean disabled) {
		for(String tabName : tabNames.split(",")) {
			ElementStub tabButton = browser.byXPath(String.format("//li[contains(@class, 'disabled') and ./a[@class='tab_button_body_match_text' and .='%s']]", tabName));
			assertThat(tabButton.isVisible(), is(disabled));
		}
	}

	@com.thoughtworks.gauge.Step("Verify <buttonName> button in <sectionName> is enabled")
	public void verifyButtonInIsEnabled(String buttonName, String sectionName) throws Exception {
		verifyButtonInIsDisabled(buttonName, sectionName, "false");
	}
	
	@com.thoughtworks.gauge.Step("Verify <buttonName> button in <sectionName> is disabled")
	public void verifyButtonInIsDisabled(String buttonName, String sectionName) throws Exception {
		verifyButtonInIsDisabled(buttonName, sectionName, "true");
	}

	private void verifyButtonInIsDisabled(String buttonName,
			String sectionName, String disabled) {
		assertThat(button(buttonName, sectionName).fetch("disabled"), is(disabled));
	}

	private ElementStub button(String buttonName, String sectionName) {
		return browser.byXPath(String.format("//div[@id='%s']//button[./span[.='%s']]", tabId(sectionName), buttonName));
	}

	@com.thoughtworks.gauge.Step("Cancel <sectionName>")
	public void cancel(String sectionName) throws Exception {
//		ElementStub tab = browser.div(tabId(sectionName));
//		ElementStub cancelButton = browser.button("/cancel_button/").in(tab);
		ElementStub cancelButton = browser.byXPath(String.format("//div[@id='%s']//button[contains(@class, 'cancel_button')]", tabId(sectionName)));
		button("Cancel", sectionName).click();
	}

	@com.thoughtworks.gauge.Step("Verify on <sectionName> tab")
	public void verifyOnTab(String sectionName) throws Exception {
		ElementStub tabLink = browser.byXPath(String.format("//li[./a[@class='tab_button_body_match_text'][.='%s']]", sectionName));
		assertThat(tabLink.fetch("className"), Matchers.containsString("current_tab"));
		assertThat(browser.div(tabId(sectionName)).isVisible(), is(true));
	}

	@com.thoughtworks.gauge.Step("Click <buttonName> button in <sectionName>")
	public void clickButtonIn(String buttonName, String sectionName) throws Exception {
		button(buttonName, sectionName).click();
	}

	@com.thoughtworks.gauge.Step("Click no <buttonName> button exists in <sectionName>")
	public void clickNoButtonExistsIn(String buttonName, String sectionName) throws Exception {
		assertThat(button(buttonName, sectionName).exists(), is(false));
	}

    @com.thoughtworks.gauge.Step("Click to see unavailable pipelines")
	public void clickToSeeUnavailablePipelines() throws Exception {
        browser.byId("show_unavailable_pipelines").click();
    
    }

	@com.thoughtworks.gauge.Step("Verify title of modal box is <title>")
	public void verifyTitleOfModalBoxIs(String title) throws Exception {
		ElementStub modalBoxTitle = browser.div("MB_caption");
		Assert.assertThat(modalBoxTitle.text(), containsString(title));
	
	}
	
	@com.thoughtworks.gauge.Step("Remember md5")
	public void rememberMd5() throws Exception {
		//browser.getValue(browser.hidden("cruise_config_md5"))
		scenarioState.putValueToStore(md5key,config.provideCurrentConfigmd5());
	}

	@com.thoughtworks.gauge.Step("Verify md5 is same")
	public void verifyMd5IsSame() throws Exception {
		String md5value = scenarioState.getValueFromStore(md5key);
		assertEquals(config.provideCurrentConfigmd5(), md5value);
	}
		
	public void verifyMd5IsNotSame() throws Exception {
		String md5value = scenarioState.getValueFromStore(md5key);
		String md5check = config.provideCurrentConfigmd5();
		Assert.assertThat(md5check.equalsIgnoreCase(md5value), Is.is(false));
	}
	
	@com.thoughtworks.gauge.Step("Click on save - Already on Add Environment Page")
	public void clickOnSave() throws Exception {
		   browser.submit("primary finish submit MB_focusable").click();
	
	}
	
	@com.thoughtworks.gauge.Step("Verify error message <errorMessage> is present - Already on Add Environment Page")
	public void verifyErrorMessageIsPresent(String errorMessage) throws Exception {
		Assert.assertThat(browser.div(errorMessage).isVisible(), is(true));
	}
	
	@com.thoughtworks.gauge.Step("Assert mD5 - Already on Add Environment Page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(config.provideCurrentConfigmd5(), md5value);
    }
	
	@com.thoughtworks.gauge.Step("Close popup - Already on Add Environment Page")
	public void closePopup() throws Exception {
		browser.link("Close window").click();
	
	}
	
	@com.thoughtworks.gauge.Step("Verify error message is valid <errorMessage>")
	public void verifyErrorMessageIsValid(String errorMessage) throws Exception {
		String splitErrorMessage[]=errorMessage.split("pipeline");
		String pipelineName=splitErrorMessage[1].trim();
		String newErrorMessage=splitErrorMessage[0]+"pipeline \'"+scenarioState.expand(pipelineName)+"\'.";
	    System.out.println(newErrorMessage);
	    Assert.assertThat(browser.div("env_form_error_box").getText(), Is.is(newErrorMessage));
	}

}
