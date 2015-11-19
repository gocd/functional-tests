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
import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

// JUnit Assert framework can be used for verification

public class AlreadyOnJobEditPage extends AlreadyOnEditPipelineWizardPage {

	private final CurrentPageState currentPageState;

	public AlreadyOnJobEditPage(Browser browser, CurrentPageState currentPageState, ScenarioState scenarioState) {
		super(currentPageState,scenarioState,browser);
		this.currentPageState= currentPageState;
		currentPageState.assertCurrentPageIs(Page.PIPELINE_WIZARD_JOB_EDIT_PAGE);
	}

	@Override
	protected String url() {
		return null;
	}
    
    @com.thoughtworks.gauge.Step("Open tasks")
	public void openTasks() {
    	browser.link(Regex.matches("Tasks")).click();
		currentPageState.currentPageIs(Page.PIPELINE_WIZARD_TASK_LISTING_PAGE);
    }
    
    @com.thoughtworks.gauge.Step("Open job settings")
	public void openJobSettings() {
        browser.link(Regex.matches("Job Settings")).click();
        currentPageState.assertCurrentPageIs(Page.PIPELINE_WIZARD_JOB_EDIT_PAGE);
    }

	@com.thoughtworks.gauge.Step("Open artifacts")
	public void openArtifacts() throws Exception {
		browser.link(Regex.matches("Artifacts")).click();
		currentPageState.currentPageIs(Page.PIPELINE_WIZARD_ARTIFACTS_LISTING_PAGE);	
	}

    @com.thoughtworks.gauge.Step("Verify presence of <index> index task as <taskType> with runif <runIf> and properties <properties> and <onCancelTaskType> on cancel task")
	public void verifyPresenceOfIndexTaskAsWithRunifAndPropertiesAndOnCancelTask(Integer index, String taskType, String runIf, String properties, String onCancelTaskType) throws Exception {
        ElementStub taskRow = browser.row(Regex.matches(String.format("task_%d", index-1)));
        Assert.assertThat(browser.cell(1).in(taskRow).text(), Is.is(taskType));
        Assert.assertThat(browser.cell(2).in(taskRow).text(), Is.is(runIf));
        Assert.assertThat(browser.cell(4).in(taskRow).text(), Is.is(onCancelTaskType));
        String[] propertiesList = properties.split(",");
        for (String prop : propertiesList) {
            String[] nameValuePair = prop.split(":");
            browser.span(nameValuePair[0].trim() + ":").in(browser.cell(3).in(taskRow)).text();
            browser.span(nameValuePair[1].trim()).in(browser.cell(3).in(taskRow)).text();
        }
    }

    @com.thoughtworks.gauge.Step("Add new <taskType> task")
	public void addNewTask(String taskType) throws Exception {
        browser.link(taskType).in(browser.div("new_task_popup")).click();
        currentPageState.currentPageIs(Page.PIPELINE_WIZARD_TASK_EDIT_POPUP);
    }

    @com.thoughtworks.gauge.Step("Verify that job saved sucessfully")
	public void verifyThatJobSavedSucessfully() throws Exception {
        super.verifySavedSuccessfully();
    }

    @com.thoughtworks.gauge.Step("Check run on all")
	public void checkRunOnAll() throws Exception {
    	String useNewRails = System.getenv("USE_NEW_RAILS");
        if(useNewRails != null && useNewRails.equals("N")) {
        	ElementStub runOnAllCheckBox = browser.checkbox("job_runOnAllAgents");
        	if(!runOnAllCheckBox.checked()) runOnAllCheckBox.check();
        } else {
        	ElementStub runOnAllRadioButton = browser.radio("jobRunType_runOnAllAgents");
        	if(!runOnAllRadioButton.checked()) runOnAllRadioButton.check();
        }
    }

    @com.thoughtworks.gauge.Step("Check run multiple instance with <instanceCount>")
	public void checkRunMultipleInstanceWith(String instanceCount) throws Exception {
    	ElementStub runMultipleInstanceRadioButton = browser.radio("jobRunType_runMultipleInstances");
    	if(!runMultipleInstanceRadioButton.checked()) runMultipleInstanceRadioButton.check();

    	browser.textbox("job[runInstanceCount]").setValue(instanceCount);
    }

    @com.thoughtworks.gauge.Step("Enter <resources> for resources - Already on Job edit page")
	public void enterForResources(String resources) throws Exception {
        browser.textbox("job[resources]").setValue(resources);
    }

    @com.thoughtworks.gauge.Step("Click save - Already On Job Edit Page")
	public void clickSave() throws Exception {
        super.clickSave();
    }

	@com.thoughtworks.gauge.Step("Set job name as <jobName>")
	public void setJobNameAs(String jobName) throws Exception {
		jobNameField().setValue(jobName);
	}

	private ElementStub jobNameField() {
		return browser.textbox("job[name]");
	}

	@com.thoughtworks.gauge.Step("Verify that job is named <jobName>")
	public void verifyThatJobIsNamed(String jobName) throws Exception {
		Assert.assertThat(jobNameField().getValue(), Is.is(jobName));
	}

    @com.thoughtworks.gauge.Step("Verify save failed - Already On Job Edit Page")
	public void verifySaveFailed() {
        assertThat(isMessagePresent("Save failed, see errors below"), is(true));
    }
    
    @com.thoughtworks.gauge.Step("Verify error message <errorMessage> is shown - Already On Job Edit Page")
	public void verifyErrorMessageIsShown(String errorMessage) {
    	assertThat(isMessagePresent(errorMessage), is(true));
    }

    @com.thoughtworks.gauge.Step("Open custom tabs")
	public void openCustomTabs() throws Exception {
        browser.link(Regex.matches("Custom Tabs")).click();
        currentPageState.currentPageIs(Page.PIPELINE_WIZARD_JOB_TABS_LISTING_PAGE);
    }

	@com.thoughtworks.gauge.Step("Override job time out with <timeout> minutes")
	public void overrideJobTimeOutWithMinutes(String timeout) throws Exception {
		browser.textbox("job_timeout").setValue(timeout);
	}

	@com.thoughtworks.gauge.Step("Select override option")
	public void selectOverrideOption() throws Exception {
		browser.radio("jobTimeout_override").click();
	}

	@com.thoughtworks.gauge.Step("Select never option")
	public void selectNeverOption() throws Exception {
		browser.radio("jobTimeout_never").click();
	}

	@com.thoughtworks.gauge.Step("Go to environment variables page - Already on Job edit page")
	public void goToEnvironmentVariablesPage() throws Exception {
		super.goToEnvironmentVariablesPage();
	}
	
	@com.thoughtworks.gauge.Step("Assert mD5 - Already on Job edit page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }
}
