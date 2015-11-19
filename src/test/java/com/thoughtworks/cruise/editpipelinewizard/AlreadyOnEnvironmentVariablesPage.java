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
import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.dom4j.Element;
import org.dom4j.Node;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.Assert;

import static junit.framework.Assert.assertEquals;

// JUnit Assert framework can be used for verification

public class AlreadyOnEnvironmentVariablesPage extends AlreadyOnEditPipelineWizardPage {
    
    private final Configuration configuration;

	public AlreadyOnEnvironmentVariablesPage(Browser browser, CurrentPageState currentPageState, ScenarioState scenarioState, Configuration configuration) {
		super(currentPageState,scenarioState, browser);
		this.configuration = configuration;
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_ENVIRONMENT_PAGE);
		
	}

	public void enterForEnvironmentVariableName(String environmentVariableName) throws Exception {
		browser.textbox("pipeline[variables][][name]").setValue(environmentVariableName);
	}

	public void enterForEnvironmentVariableValue(String environmentVariableValue) throws Exception {
		browser.textbox("pipeline[variables][][value]").setValue(environmentVariableValue);	
	}

		
	@Override
	protected String url() {
		// TODO Auto-generated method stub
		return null;
	}

	@com.thoughtworks.gauge.Step("Open parameters page - Already on environment variables page")
	public void openParametersPage() throws Exception {
		super.openParametersPage();
	}

	@com.thoughtworks.gauge.Step("Verify reset button exists - Already on environment variables page")
	public void verifyResetButtonExists() throws Exception {
		super.verifyResetButtonExists();
	}

	@com.thoughtworks.gauge.Step("Click save - Already On Environment Variables Page")
	public void clickSave() throws Exception {
		super.clickSave();
	}

	@com.thoughtworks.gauge.Step("Verify <message> message is displayed - Already On Environment Variables Page")
	public void verifyMessageIsDisplayed(String message) throws Exception {
		super.verifyMessageIsDisplayed(message);
	}
	
	@com.thoughtworks.gauge.Step("Open custom tabs - Already on environment variables page")
	public void openCustomTabs() throws Exception {
	        browser.link(Regex.matches("Custom Tabs")).click();
	        currentPageState.currentPageIs(Page.PIPELINE_WIZARD_JOB_TABS_LISTING_PAGE);
	    }

	@com.thoughtworks.gauge.Step("Enter environment variable <location> name <variableName> and value <variableValue>")
	public void enterEnvironmentVariableNameAndValue(Integer location, String variableName, String variableValue) throws Exception {
		addVariableWithValueForAt(variableName, variableValue, "pipeline", location);
	}
	
    @com.thoughtworks.gauge.Step("Enter secure environment variable <location> name <variableName> and value <variableValue>")
	public void enterSecureEnvironmentVariableNameAndValue(Integer location, String variableName, String variableValue) throws Exception {
        if (!secureEnvironmentFieldExists(location)) {
            createSecureEmptyVariable();
        }
        browser.textbox(getEnvironmentNameFieldId("pipeline",location)).in(browser.byId("variables_secure")).setValue(variableName);
        browser.password(getEnvironmentValueFieldId("pipeline",location)).in(browser.byId("variables_secure")).setValue(variableValue);
    }

    private boolean secureEnvironmentFieldExists(Integer location) {
        ElementStub table = browser.table(Regex.wholeWord("variables")).in(browser.div("variables_secure"));
        return browser.row(location).in(table).exists(true);
    }

    private void createEmptyVariable() {
        browser.link("Add").in(browser.byId("variables")).click();
    }

    private void createSecureEmptyVariable() {
        browser.link("Add").in(browser.byId("variables_secure")).click();
    }

	private boolean environmentFieldExists(Integer location) {
		ElementStub textbox = browser.textbox(getEnvironmentNameFieldId("pipeline",location)).in(browser.byId("variables"));
        return textbox.exists();
	}

	private String getEnvironmentValueFieldId(String entityType, Integer location) {
		return String.format("%s[variables][][valueForDisplay]", entityType) + getLocationString(location);
	}

	private String getLocationString(Integer location) {
		return ((location == 1) ? "" :"[" + (--location).toString() + "]").toString();
	}

	private String getEnvironmentNameFieldId(String entityType, Integer location) {
		return String.format("%s[variables][][name]", entityType) + getLocationString(location);
	}

	@com.thoughtworks.gauge.Step("Open general options page - Already on environment variables page")
	public void openGeneralOptionsPage() throws Exception {
		super.openGeneralOptionsPage();
	}

	@com.thoughtworks.gauge.Step("Go to project management page - Already on environment variables page")
	public void goToProjectManagementPage() throws Exception {
		super.goToProjectManagementPage();
	}
	
    @com.thoughtworks.gauge.Step("Verify heading <expectedHeading>")
	public void verifyHeading(String expectedHeading) throws Exception {
        Assert.assertThat(browser.heading3(expectedHeading).exists(), Is.is(true));
    }

    public void deleteVariable() throws Exception {
        browser.span("icon_remove delete_parent").click();
    }

    @com.thoughtworks.gauge.Step("Verify that <pipelineName> has variable named <name> with value <value>")
	public void verifyThatHasVariableNamedWithValue(final String pipelineName, final String name, final String value) throws Exception {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            @Override
             public boolean call() throws Exception {
                 CruiseConfigDom dom = configuration.provideDomAsAdmin();
                 Element pipeline = dom.getPipeline(scenarioState.pipelineNamed(pipelineName));
                 String xpath = String.format("//variable[@name='%s']/value", name);
                 Node node = pipeline.selectSingleNode(xpath);
                 if (node != null) {
                     Assert.assertThat(node.getText(), Is.is(value));
                     return true;
                 }
                 return false;
             }
         });
    }

    @com.thoughtworks.gauge.Step("Verify that <pipelineName> has secure variable named <name>")
	public void verifyThatHasSecureVariableNamed(final String pipelineName, final String name) throws Exception {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            @Override
             public boolean call() throws Exception {
                 CruiseConfigDom dom = configuration.provideDomAsAdmin();
                 Element pipeline = dom.getPipeline(scenarioState.pipelineNamed(pipelineName));
                 String variableXpath = String.format("//variable[@name='%s'][@secure='true']/encryptedValue", name);
                 Node encryptedValueNode = pipeline.selectSingleNode(variableXpath);
                 if (encryptedValueNode != null) {
                     Assert.assertThat(encryptedValueNode.getText(), Is.is(IsNot.not(Matchers.nullValue())));
                     return true;
                 }
                 return false;
             }
         });
    }

	@com.thoughtworks.gauge.Step("Open stage listing page - Already On Environment Variables Page")
	public void openStageListingPage() throws Exception {
		super.openStageListingPage();
	}
	
	@com.thoughtworks.gauge.Step("Navigate to tab <tabName>")
	public void navigateToTab(String tabName) throws Exception {
        browser.link(tabName).click();
    }

	@com.thoughtworks.gauge.Step("Verify environment variable <variable> has value <value> for <entityType>")
	public void verifyEnvironmentVariableHasValueFor(String variable, String value, String entityType)
			throws Exception {
		int count = browser.count("_textbox", String.format("%s[variables][][name]", entityType));
		ElementStub variableName = null;
		for (int i = 0; i < count - 1; i++) {
			variableName = i > 0 ? browser.textbox(String.format(
					"%s[variables][][name][%d]",entityType, i)) : browser.textbox(String.format("%s[variables][][name]", entityType));
			if (variableName.getValue().equals(variable)) {
				break;
			}
		}
		Assert.assertNotNull(variableName);
		Assert.assertThat(variableName.exists(), Is.is(true));
		ElementStub variableValue = browser.textbox(String.format("%s[variables][][valueForDisplay]", entityType)).in(variableName.parentNode().parentNode());
		Assert.assertThat(variableValue.getValue(), Is.is(value));
	}

	@com.thoughtworks.gauge.Step("Open jobs - Already On Environment Variables Page")
	public void openJobs() {
    	browser.link("/Jobs/").click();
		currentPageState.currentPageIs(Page.EDIT_STAGE_WIZARD_JOBS_PAGE);
    }

	@com.thoughtworks.gauge.Step("Delete variable <variable> for <entityType>")
	public void deleteVariableFor(String variable, String entityType) throws Exception {
		
		int count = browser.count("_textbox", String.format("%s[variables][][name]", entityType));
		ElementStub variableName = null;
		for (int i = 0; i < count - 1; i++) {
			variableName = i > 0 ? browser.textbox(String.format(
					"%s[variables][][name][%d]",entityType, i)) : browser.textbox(String.format("%s[variables][][name]", entityType));
			if (variableName.getValue().equals(variable)) {
				break;
			}
		}
		Assert.assertNotNull(variableName);
		Assert.assertThat(variableName.exists(), Is.is(true));
		ElementStub tableRow = variableName.parentNode().parentNode();
		browser.span("icon_remove delete_parent").in(tableRow).click();
	}

	@com.thoughtworks.gauge.Step("Add variable <variableName> with value <variableValue> for <entityType> at <location>")
	public void addVariableWithValueForAt(String variableName, String variableValue,
			String entityType, Integer location) throws Exception {
	    if (!environmentFieldExists(location)) {
	        createEmptyVariable();
	    }
		browser.textbox(getEnvironmentNameFieldId(entityType, location)).in(browser.byId("variables")).setValue(variableName);
		browser.textbox(getEnvironmentValueFieldId(entityType, location)).in(browser.byId("variables")).setValue(variableValue);

	}
	
	@com.thoughtworks.gauge.Step("Assert mD5 - Already on environment variables page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }
	

}
