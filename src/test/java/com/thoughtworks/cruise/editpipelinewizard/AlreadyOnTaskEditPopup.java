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
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

// JUnit Assert framework can be used for verification

public class AlreadyOnTaskEditPopup extends AlreadyOnEditPipelineWizardPage {

	public AlreadyOnTaskEditPopup(Browser browser, CurrentPageState currentPageState, ScenarioState scenarioState) {
        super(currentPageState, scenarioState, browser);
		currentPageState.assertCurrentPageIs(Page.PIPELINE_WIZARD_TASK_EDIT_POPUP);
	}

    @com.thoughtworks.gauge.Step("Verify has <formFieldRepresentations>")
	public void verifyHas(String formFieldRepresentations) throws Exception {
        for(FormField expectedField : formFields(formFieldRepresentations)) {
			expectedField.assertExistance(browser);
		}
	}

	private List<FormField> formFields(String representations) {
		List<FormField> expectedFields = new ArrayList<FormField>();
	    for(String fieldRepresentation : representations.split("\\s*,\\s*")) {
	    	expectedFields.add(new FormField(scenarioState.expand(fieldRepresentation.trim())));
	    }
	    return expectedFields;
	}

	@com.thoughtworks.gauge.Step("Set <formFieldValues> - Already on Task edit popup")
	public void set(String formFieldValues) throws Exception {
        for(FormField formField : formFields(formFieldValues)) {
            formField.setValue(browser);
        }
	}
	
	@com.thoughtworks.gauge.Step("Set url as <formFieldValues>")
	public void setUrlAs(String formFieldValues) throws Exception {
		String[] parts= formFieldValues.split("/");
		String pipelineName=parts[3];
				
		String actualPipelineName= scenarioState.pipelineNamed(pipelineName);
		
		String[] splitParts= formFieldValues.split(":");
		String url=splitParts[1];
		
		String replacedUrl= url.replace(pipelineName, actualPipelineName);
		String newUrl=Urls.urlFor(replacedUrl);
		
		String newFormFieldValues = formFieldValues.replace(url, newUrl);
		
		
		for(FormField formField : formFields(newFormFieldValues)) {
            formField.setValue(browser);
        }
	}

	@com.thoughtworks.gauge.Step("Save and verify saved successfully")
	public void saveAndVerifySavedSuccessfully() throws Exception {
        clickSave();
        currentPageState.currentPageIs(Page.PIPELINE_WIZARD_TASK_LISTING_PAGE);
        verifySavedSuccessfully();
	}

    @com.thoughtworks.gauge.Step("Save and verify save failed")
	public void saveAndVerifySaveFailed() throws Exception {
        clickSave();
        verifySaveFailed();
    }

	@com.thoughtworks.gauge.Step("Verify validation failed for <fieldErrorRepresentations>")
	public void verifyValidationFailedFor(String fieldErrorRepresentations) throws Exception {
		for(FormField expectedField : formFields(scenarioState.expand(fieldErrorRepresentations))) {
			expectedField.verifyError(browser);
		}
	} 
	
	@com.thoughtworks.gauge.Step("Auto complete should show up <suggestion> - Already on Task edit popup")
	public void autoCompleteShouldShowUp(String suggestion) throws Exception {
	    suggestion = scenarioState.expand(suggestion);
        new AutoCompleteSuggestions(browser, browserWrapper).autoCompletesShouldShowSuggestion(suggestion);
	}
	
	@com.thoughtworks.gauge.Step("Auto complete should show up <suggestion> for textBox <textboxIndex>")
	public void autoCompleteShouldShowUpForTextBox(String suggestion, int textboxIndex) throws Exception {
        suggestion = scenarioState.expand(suggestion);
        new AutoCompleteSuggestions(browser, browserWrapper).autoCompletesShouldShowSuggestion(textboxIndex, new AutoCompleteSuggestions.FindSuggestion(suggestion));
    }
    
	@com.thoughtworks.gauge.Step("Select option <option> - Already on Task edit popup")
	public void selectOption(String option) throws Exception {
        option = scenarioState.expand(option);
        new AutoCompleteSuggestions(browser, browserWrapper).selectFirstOption();
    }
	
    @com.thoughtworks.gauge.Step("Select option from suggestion at index <suggestionIndex> for text box <textboxIndex>")
	public void selectOptionFromSuggestionAtIndexForTextBox(int suggestionIndex, int textboxIndex) throws Exception {
        new AutoCompleteSuggestions(browser, browserWrapper).selectOption(suggestionIndex, textboxIndex);
    }

	@com.thoughtworks.gauge.Step("Select task <taskType>")
	public void selectTask(String taskType) throws Exception {
		browser.select("on_cancel_task_options").choose(taskType);
	}

	@com.thoughtworks.gauge.Step("Auto complete should show suggestions <expectedSuggestions> - Already on Task edit popup")
	public void autoCompleteShouldShowSuggestions(String expectedSuggestions) throws Exception {
		waitForAutocompleteDelay();
        super.autoCompleteShouldShowSuggestions(expectedSuggestions);
	}

	@com.thoughtworks.gauge.Step("Verify <commandNames> does not show up in <commandDropDownSection> command lookup dropdown")
	public void verifyDoesNotShowUpInCommandLookupDropdown(String commandNames, String commandDropDownSection) throws Exception {
		waitForAutocompleteDelay();
		String[] commands = commandNames.split(",");
        AutoCompleteSuggestions suggestions = new AutoCompleteSuggestions(browser, browserWrapper);
        for (String command : commands){
        	suggestions.autoCompleteShouldNotHave(command.trim());
        }
	}

	private void waitForAutocompleteDelay() throws InterruptedException {
		super.sleepFor(600);
	}
	
	private ElementStub getCommandAutoComplete(String commandDropDownSection) {
		return browser.textbox(Regex.startsWith("lookup_command")).in(sectionForAutocomplete(commandDropDownSection));
	}

	private ElementStub sectionForAutocomplete(String commandDropDownSection) {
	   return browser.byXPath(xPathForSectionForAutocomplete(commandDropDownSection));
	}

	private String xPathForSectionForAutocomplete(String commandDropDownSection) {
		if (commandDropDownSection.equals("Advanced Options")) {
			return "//div[@id='task']//div[contains(@class, 'on_cancel_task')]";
	    }
	   return "//div[@id='task']/div[contains(@class, 'task')]";
	}

	@com.thoughtworks.gauge.Step("Enter <textToEnter> in <commandDropDownSection> command lookup autocomplete box")
	public void enterInCommandLookupAutocompleteBox(String textToEnter, String commandDropDownSection)	{
		getCommandAutoComplete(commandDropDownSection).setValue(textToEnter);
	}

	@com.thoughtworks.gauge.Step("Verify command is set to <command> with arguments <arguments> - Already on Task edit popup")
	public void verifyCommandIsSetToWithArguments(String command, String arguments) throws Exception {
		super.assertThatTextBoxHasValue("task[command]", command);
		super.assertThatTextAreaHasValue("task[argListString]", arguments.replaceAll(",", "\n"));
	}   

	@com.thoughtworks.gauge.Step("Verify command is set to <command> with arguments <arguments> in advanced options")
	public void verifyCommandIsSetToWithArgumentsInAdvancedOptions(String command, String arguments) throws Exception {
		super.assertThatTextBoxHasValue( "task[onCancelConfig][execOnCancel][command]", command);
		super.assertThatTextAreaHasValue("task[onCancelConfig][execOnCancel][argListString]", arguments.replaceAll(",", "\n"));
		
	} 
	@com.thoughtworks.gauge.Step("Verify that message <message> is shown")
	public void verifyThatMessageIsShown(String message) throws Exception {
		ElementStub warning = browser.div("warnings");
		Assert.assertThat(warning.text(), containsString(message));
	}
	
	@com.thoughtworks.gauge.Step("Click on details")
	public void clickOnDetails() throws Exception {
		browser.link("show_snippets").click();
	}
	
	@com.thoughtworks.gauge.Step("Verify that invalid snippet <snippetName> appears with error message <message>")
	public void verifyThatInvalidSnippetAppearsWithErrorMessage(String snippetName,	String message) throws Exception {
		ElementStub parentNode = browser.span(snippetName).parentNode();
		ElementStub childMessage = browser.span(message).in(parentNode);
		Assert.assertThat(childMessage.exists(), is(true));
	}

	private ElementStub commandLookupElement() {
		return browser.textbox(Regex.startsWith("lookup_command"));
	}

	public void setCommandToWithArguments(String command, String arguments)	throws Exception {
		ElementStub textbox = browser.textbox("task[command]");
		Assert.assertThat(textbox.exists(), is(true));
		textbox.setValue(command);
		browser.textarea("task[argListString]").setValue(arguments);
	}

	@com.thoughtworks.gauge.Step("Set target to <target> with working directory <workingDir>")
	public void setTargetToWithWorkingDirectory(String target, String workingDir){
		ElementStub textbox = browser.textbox("task[target]");
		Assert.assertThat(textbox.exists(), is(true));
		textbox.setValue(target);
		browser.textbox("task[workingDirectory]").setValue(workingDir);
	}

	@com.thoughtworks.gauge.Step("Select option <oneBasedOptionIndex> from command lookup dropdown - Already on Task edit popup")
	public void selectOptionFromCommandLookupDropdown(Integer oneBasedOptionIndex) throws Exception {
        super.selectOptionFromCommandLookupDropdown(oneBasedOptionIndex);
	}

	@com.thoughtworks.gauge.Step("Select option <oneBasedOptionIndex> from command lookup dropdown in advanced options")
	public void selectOptionFromCommandLookupDropdownInAdvancedOptions(Integer oneBasedOptionIndex) throws Exception {
        AutoCompleteSuggestions suggestions = new AutoCompleteSuggestions(browser, browserWrapper);
        suggestions.selectOption(oneBasedOptionIndex - 1, 1);
	}

	@com.thoughtworks.gauge.Step("Verify snippet details in <commandRepoSection> are shown with name <name> description <description> author <author> with authorlink <authorLink> and more info <moreInfoLink>")
	public void verifySnippetDetailsInAreShownWithNameDescriptionAuthorWithAuthorlinkAndMoreInfo(String commandRepoSection, String name, String description, String author, String authorLink, String moreInfoLink) throws Exception {
		String xPathForTheCorrectCommandRepoSection = xPathForSectionForAutocomplete(commandRepoSection);
		new CommandRepository(browser).verifySnippetDetailsAreShownWithNameDescriptionAuthorWithAuthorlinkAndMoreInfo(xPathForTheCorrectCommandRepoSection, name, description, author, authorLink, moreInfoLink);
	}
	
	@com.thoughtworks.gauge.Step("Verify snippet details in <commandRepoSection> are shown with name <name> only")
	public void verifySnippetDetailsInAreShownWithNameOnly(String commandRepoSection, String name) throws Exception {
		String xPathForTheCorrectCommandRepoSection = xPathForSectionForAutocomplete(commandRepoSection);
		new CommandRepository(browser).verifySnippetDetailsAreShownWithNameOnly(xPathForTheCorrectCommandRepoSection, name);
	}

    @com.thoughtworks.gauge.Step("Auto complete should not show up <suggestion>")
	public void autoCompleteShouldNotShowUp(String suggestion) throws Exception {
        new AutoCompleteSuggestions(browser, browserWrapper).autoCompleteShouldNotHave(suggestion);
    }


	@com.thoughtworks.gauge.Step("Select secureConnection as <secureConnectionValue>")
	public void selectSecureConnectionAs(String secureConnectionValue) throws Exception {
		browser.byId("secureConnection"+secureConnectionValue).click();
	
	}

	@com.thoughtworks.gauge.Step("Select requestType as <requestType>")
	public void selectRequestTypeAs(String requestType) throws Exception {
		browser.select("task[RequestType]").choose(requestType);
	
	}
	
    @com.thoughtworks.gauge.Step("Verify inline validation message <message> - Already on Task edit popup")
	public void verifyInlineValidationMessage(String message) throws Exception {
        Assert.assertThat(browser.span(message).exists(), Is.is(true));
    }


	
}
