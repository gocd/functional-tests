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


import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.materials.TfsServer;
import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThat;

// JUnit Assert framework can be used for verification

public class AlreadyOnParametersPage extends AlreadyOnEditPipelineWizardPage {
	
	private final Configuration configuration;

	public AlreadyOnParametersPage(Configuration configuration,CurrentPageState currentPageState,
			ScenarioState scenarioState, Browser browser) {
		super(currentPageState, scenarioState, browser);
		this.configuration = configuration;
		currentPageState
				.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_PARAMETERS_PAGE);
	}
	
	@Override
	protected String url() {
		// TODO Auto-generated method stub
		return null;
	}

	@com.thoughtworks.gauge.Step("Open stage listing page - Already on parameters page")
	public void openStageListingPage() throws Exception {
		super.openStageListingPage();
	}

	@com.thoughtworks.gauge.Step("Verify reset button exists - Already on parameters page")
	public void verifyResetButtonExists() throws Exception {
		super.verifyResetButtonExists();
	}

	@com.thoughtworks.gauge.Step("Click save")
	public void clickSave() throws Exception {
		super.clickSave();
	}

	@com.thoughtworks.gauge.Step("Verify <message> message is displayed - Already on parameters page")
	public void verifyMessageIsDisplayed(String message) throws Exception {
		super.verifyMessageIsDisplayed(message);
	}

	@com.thoughtworks.gauge.Step("Open general options page")
	public void openGeneralOptionsPage() throws Exception {
		super.openGeneralOptionsPage();
	}

	@com.thoughtworks.gauge.Step("Enter tfs url as parameter <parameterLocation> with name <name> and value <collection>")
	public void enterTfsUrlAsParameterWithNameAndValue(Integer parameterLocation, String name, String collection) throws Exception {
		enterParameterNameAndValue(parameterLocation, name, TfsServer.getUrl());
	}

    @com.thoughtworks.gauge.Step("Enter parameter <parameterLocation> name <parameterName> and value <parameterValue>")
	public void enterParameterNameAndValue(Integer parameterLocation, String parameterName, String parameterValue) throws Exception {
        if (!parameterFieldExists(parameterLocation))
            createEmptyParameter();

        getParamTextBox(getParameterNameFieldId(parameterLocation)).setValue(parameterName);
        getParamTextBox(getParameterValueFieldId(parameterLocation)).setValue(parameterValue);
    }

    @com.thoughtworks.gauge.Step("Enter parameter <parameterLocation> name <parameterName> and derive value from environment variable <environmentVariable>")
	public void enterParameterNameAndDeriveValueFromEnvVar(Integer parameterLocation, String parameterName, String environmentVariable) throws Exception {
		enterParameterNameAndValue(parameterLocation, parameterName, System.getenv(environmentVariable));
	}

	@com.thoughtworks.gauge.Step("Enter material url for parameter <parameterLocation> name <parameterName> and material name <materialName> associated with pipeline <parentPipelineName>")
	public void enterMaterialUrlForParameterNameAndMaterialNameAssociatedWithPipeline(
			Integer parameterLocation, String parameterName, String materialName, String parentPipelineName)
			throws Exception {
		CruiseConfigDom dom = configuration.provideDomAsAdmin();
		String materialurl = dom.getMaterialUrl(scenarioState.pipelineNamed(parentPipelineName) , materialName, materialName);
		enterParameterNameAndValue(parameterLocation, parameterName, materialurl);
	}

	
	private void createEmptyParameter() {
		browser.link("Add").click();
	}

    private boolean parameterFieldExists(Integer parameterLocation) {
        return getParamTextBox(getParameterNameFieldId(parameterLocation)).exists(true);
    }

	private String getParameterValueFieldId(Integer parameterLocation) {
		return "pipeline[params][][valueForDisplay]" + getLocationString(parameterLocation);
	}

	private String getLocationString(Integer parameterLocation) {
		return ((parameterLocation == 1) ? "" :"[" + (--parameterLocation).toString() + "]").toString();
	}

	private String getParameterNameFieldId(Integer parameterLocation) {
		return "pipeline[params][][name]" + getLocationString(parameterLocation);
	}

	@com.thoughtworks.gauge.Step("Go to project management page - Already on parameters page")
	public void goToProjectManagementPage() throws Exception {
		super.goToProjectManagementPage();
	}

	@com.thoughtworks.gauge.Step("Verify saved successfully")
	@Override
	public void verifySavedSuccessfully() {
		super.verifySavedSuccessfully();
	
	}

    @com.thoughtworks.gauge.Step("Verify parameter <name> has value <value>")
	public void verifyParameterHasValue(String name, String value) throws Exception {
        assertThat(browser.textbox("pipeline[params][][name]").getText(), Is.is(name));
        assertThat(browser.textbox("pipeline[params][][valueForDisplay]").getText(), Is.is(value));
    }
    
    @com.thoughtworks.gauge.Step("Assert mD5 - Already on parameters page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }

    private ElementStub getParamTextBox(String parameterNameFieldId) {
        ElementStub tableOfParams = browser.table(Regex.wholeWord("params")).in(browser.div("params"));
        return browser.textbox(parameterNameFieldId).in(tableOfParams);
    }
}
