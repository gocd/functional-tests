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
import com.thoughtworks.cruise.page.CruisePage;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.CommaSeparatedParams;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

// JUnit Assert framework can be used for verification

public class AlreadyOnAddJobPopup extends CruisePage {

	private final CurrentPageState currentPageState;

	public AlreadyOnAddJobPopup(CurrentPageState currentPageStage, ScenarioState scenarioState,  Browser browser) {
		super(scenarioState,true,browser);
		this.currentPageState = currentPageStage;
		currentPageStage.assertCurrentPageIs(Page.EDIT_STAGE_WIZARD_ADD_JOB_POPUP);
	}

	@Override
	protected String url() {
		return null;
	}

	@com.thoughtworks.gauge.Step("Enter <jobName> for job name")
	public void enterForJobName(String jobName) throws Exception {
		browser.textbox("job[name]").setValue(jobName);
	}

	@com.thoughtworks.gauge.Step("Select as task type <taskType>")
	public void selectAsTaskType(String taskType) throws Exception {
		browser.select("job[tasks][taskOptions]").choose(taskType);
	}

	@com.thoughtworks.gauge.Step("Enter <command> as command")
	public void enterAsCommand(String command) throws Exception {
		browser.textbox("job[tasks][exec][command]").setValue(command);
	}

    @com.thoughtworks.gauge.Step("Enter <resources> for resources")
	public void enterForResources(String resources) throws Exception {
        browser.textbox("job[resources]").setValue(resources);
    }

	@com.thoughtworks.gauge.Step("Add job")
	public void addJob() throws Exception {
		clickSave();
		currentPageState.currentPageIs(Page.EDIT_STAGE_WIZARD_JOBS_PAGE);
	}

	@com.thoughtworks.gauge.Step("Click save - Already on Add Job Popup")
	public void clickSave() throws Exception {
		browser.submit(Regex.matches("finish submit")).click();		
	}

	@com.thoughtworks.gauge.Step("Verify <message> message exists - Already on Add Job Popup")
	public void verifyMessageExists(String message)	{
		assertThat(browser.div(message).exists(), Is.is(true));
	}

    public void verifyHasErrorMessage(String field, String message) throws Exception {
        ElementStub formItemOfField = browser.textbox(Regex.matches("\\[" + field + "\\]")).parentNode().parentNode();
        assertThat(browser.div(Regex.matches("form_error")).in(formItemOfField).getText().trim(), Is.is(message));
    }

    @com.thoughtworks.gauge.Step("Verify dropdown contains <resourceList>")
	public void verifyDropdownContains(String resourceList) throws Exception {
        for (String resourceName : new CommaSeparatedParams(resourceList)) {
            ElementStub itemInAutoComplete = browser.listItem(resourceName).in(browser.div(Regex.wholeWord("ac_results")));
            assertThat(itemInAutoComplete.exists(), is(true));
        }
    }

    @com.thoughtworks.gauge.Step("Select <resourceName> from the dropdown")
	public void selectFromTheDropdown(String resourceName) throws Exception {
        browser.listItem(resourceName).in(browser.div(Regex.wholeWord("ac_results"))).click();
    }

	public void enterInCommandLookupAutocompleteBox(String textToEnter) throws Exception {
		browser.textbox(Regex.startsWith("lookup_command")).setValue(textToEnter);
	}

	public void autoCompleteShouldShowSuggestions(String expectedSuggestions) throws Exception {
		waitForAutocompleteDelay();
        super.autoCompleteShouldShowSuggestions(expectedSuggestions);
	}
	
	public void selectOptionFromCommandLookupDropdown(Integer oneBasedOptionIndex) throws Exception {
		super.selectOptionFromCommandLookupDropdown(oneBasedOptionIndex);
	}
	
	private void waitForAutocompleteDelay() throws InterruptedException {
		super.sleepFor(600);
	}

	public void verifyCommandIsSetToWithArguments(String command, String arguments) throws Exception {
		super.assertThatTextBoxHasValue("job[tasks][exec][command]", command);
		super.assertThatTextAreaHasValue("job[tasks][exec][argListString]", arguments.replaceAll(",", "\n"));
   } 
}
