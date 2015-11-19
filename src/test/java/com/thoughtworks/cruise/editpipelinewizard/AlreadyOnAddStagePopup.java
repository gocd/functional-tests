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
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;

import static org.junit.Assert.assertThat;

// JUnit Assert framework can be used for verification

public class AlreadyOnAddStagePopup extends CruisePage {

	private final CurrentPageState currentPageState;

	public AlreadyOnAddStagePopup(CurrentPageState currentPageStage, ScenarioState scenarioState,  Browser browser) {
		super(scenarioState,true,browser);
		this.currentPageState = currentPageStage;
		currentPageStage.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_ADD_STAGE_POPUP);
	}

	@Override
	protected String url() {
		// TODO Auto-generated method stub
		return null;
	}

	@com.thoughtworks.gauge.Step("Enter for stage name <stageName>")
	public void enterForStageName(String stageName) throws Exception {
		browser.textbox("stage[name]").setValue(stageName);
	}

	@com.thoughtworks.gauge.Step("Select <triggerForStage> for stage trigger")
	public void selectForStageTrigger(String triggerForStage) throws Exception {
		browser.radio(triggerForStage).click();
	}
	
	public void selectUserDefinedParameterForStageTrigger() throws Exception {
	    selectForStageTrigger("customType");
    }

	@com.thoughtworks.gauge.Step("Enter for job name <jobName>")
	public void enterForJobName(String jobName) throws Exception {
		browser.textbox("stage[jobs][][name]").setValue(jobName);
	}

	@com.thoughtworks.gauge.Step("Select <taskType> as task type")
	public void selectAsTaskType(String taskType) throws Exception {
		browser.select("stage[jobs][][tasks][taskOptions]").choose(taskType);
	}

	@com.thoughtworks.gauge.Step("Enter as command <command>")
	public void enterAsCommand(String command) throws Exception {
		browser.textbox("stage[jobs][][tasks][exec][command]").setValue(command);
	}

	@com.thoughtworks.gauge.Step("Enter as arguments <argument>")
	public void enterAsArguments(String argument) throws Exception {
		browser.textarea("stage[jobs][][tasks][exec][argListString]").setValue(argument);
	}
	
	@com.thoughtworks.gauge.Step("Add stage")
	public void addStage() throws Exception {
			clickSave();
			currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_STAGES_PAGE);
	}

	@com.thoughtworks.gauge.Step("Click save - Already on Add Stage Popup")
	public void clickSave() throws Exception {
		browser.submit(Regex.matches("finish submit")).click();		
	}

	@com.thoughtworks.gauge.Step("Verify <message> message exists")
	public void verifyMessageExists(String message)	{
		assertThat(browser.div(message).exists(), Is.is(true));
	}

    @com.thoughtworks.gauge.Step("Verify <field> has error message <message>")
	public void verifyHasErrorMessage(String field, String message) throws Exception {
        ElementStub formItemOfField = browser.textbox(Regex.matches("\\[" + field + "\\]")).parentNode().parentNode();
        assertThat(browser.div(Regex.matches("form_error")).in(formItemOfField).getText().trim(), Is.is(message));
    }
    
    @com.thoughtworks.gauge.Step("Mark stage manual")
	public void markStageManual() {
    	browser.radio("manual").click();
    }

    public void enterUserDefinedApprovalParameterAs(String param) throws Exception {
        browser.textbox("stage_approval_customType").setValue(param);
    }   
    
	@com.thoughtworks.gauge.Step("Enter <textToEnter> in command lookup autocomplete box - Already on Add Stage Popup")
	public void enterInCommandLookupAutocompleteBox(String textToEnter) throws Exception {
		browser.textbox(Regex.startsWith("lookup_command")).setValue(textToEnter);
	}
    
	@com.thoughtworks.gauge.Step("Auto complete should show suggestions <expectedSuggestions> - Already on Add Stage Popup")
	public void autoCompleteShouldShowSuggestions(String expectedSuggestions) throws Exception {
		waitForAutocompleteDelay();
        super.autoCompleteShouldShowSuggestions(expectedSuggestions);
	}
	
	@com.thoughtworks.gauge.Step("Select option <oneBasedOptionIndex> from command lookup dropdown - Already on Add Stage Popup")
	public void selectOptionFromCommandLookupDropdown(Integer oneBasedOptionIndex) throws Exception {
       super.selectOptionFromCommandLookupDropdown(oneBasedOptionIndex);	
    }
	
	private void waitForAutocompleteDelay() throws InterruptedException {
		super.sleepFor(600);
	}

	@com.thoughtworks.gauge.Step("Verify command is set to <command> with arguments <arguments> - Already on Add Stage Popup")
	public void verifyCommandIsSetToWithArguments(String command, String arguments) throws Exception {
		super.assertThatTextBoxHasValue("stage[jobs][][tasks][exec][command]", command);
		super.assertThatTextAreaHasValue("stage[jobs][][tasks][exec][argListString]", arguments.replaceAll(",", "\n"));
   }
    
}
