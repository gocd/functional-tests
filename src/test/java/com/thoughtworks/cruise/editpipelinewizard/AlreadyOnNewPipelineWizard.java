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

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.materials.TfsServer;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.StringUtil;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AlreadyOnNewPipelineWizard extends AlreadyOnEditPipelineWizardPage {
	private static final int COMMAND_LOOKUP_TEXTBOX_INDEX = 1;
    private String currentTab;
    private String md5value;
    private PackageMaterialCreator packageMaterialCreator;

    public AlreadyOnNewPipelineWizard(CurrentPageState currentPageState, ScenarioState scenarioState, Browser browser) {
        super(currentPageState,scenarioState,browser);
        packageMaterialCreator = new PackageMaterialCreator(browser, browserWrapper);
        currentPageState.assertCurrentPageIs(Page.ADD_NEW_PIPELINE_WIZARD);
        
    }

    private List<FormField> formFields(String representations) {
        List<FormField> expectedFields = new ArrayList<FormField>();
        for(String fieldRepresentation : representations.split("\\s*,\\s*")) {
            expectedFields.add(new FormField(scenarioState.expand(fieldRepresentation.trim())));
        }
        return expectedFields;
    }

    @com.thoughtworks.gauge.Step("Set <formFieldValues>")
	public void set(String formFieldValues) throws Exception {
        for(FormField formField : formFields(formFieldValues)) {
            formField.setValue(browser);
        }
    }
    
    @com.thoughtworks.gauge.Step("Set tfs collection <collection>")
	public void setTfsCollection(String collection) throws Exception {
    	FormField formField = new FormField(scenarioState.expand(String.format("pipeline_group[pipeline][materials][TfsMaterial][url](text_field): %s%s", TfsServer.getUrl(), collection)));
        formField.setValue(browser);
    }

    @com.thoughtworks.gauge.Step("Go next from settings")
	public void goNextFromSettings() throws Exception {
        ElementStub nextButtonOnBasicSettings = browser.byId("next_to_settings");
        Assert.assertThat(nextButtonOnBasicSettings.exists(), Is.is(true));
        nextButtonOnBasicSettings.click();
    }
    
    @com.thoughtworks.gauge.Step("Go back from stage and job")
	public void goBackFromStageAndJob() throws Exception {
        ElementStub prevButtonOnStageAndJob = browser.byId("prev_to_stage_and_job");
        Assert.assertThat(prevButtonOnStageAndJob.exists(), Is.is(true));
        prevButtonOnStageAndJob.click();
    }
    
    @com.thoughtworks.gauge.Step("Go back from materials")
	public void goBackFromMaterials() throws Exception {
        ElementStub prevButtonOnMaterials = browser.byId("prev_to_materials");
        Assert.assertThat(prevButtonOnMaterials.exists(), Is.is(true));
        prevButtonOnMaterials.click();
    }

    @com.thoughtworks.gauge.Step("Select material type as <materialType>")
	public void selectMaterialTypeAs(String materialType) throws Exception {
        browser.select("pipeline_group[pipeline][materials][materialType]").choose(materialType);
    }

    @com.thoughtworks.gauge.Step("Go next from materials")
	public void goNextFromMaterials() throws Exception {
        ElementStub nextButtonOnMaterials = browser.byId("next_to_materials");
        Assert.assertThat(nextButtonOnMaterials.exists(), Is.is(true));
        nextButtonOnMaterials.click();
    }

    @com.thoughtworks.gauge.Step("Select task type as <type>")
	public void selectTaskTypeAs(String type) throws Exception {
        browser.select("pipeline_group[pipeline][stage][jobs][][tasks][taskOptions]").choose(type);
    }

    @com.thoughtworks.gauge.Step("Save pipeline <pipelineName> successfully")
	public void savePipelineSuccessfully(String pipelineName) throws Exception {
        savePipeline();
        if (!scenarioState.hasPipeline(pipelineName)) {
        	scenarioState.pushPipeline(pipelineName, pipelineName);
        }
        currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_GENERAL_PAGE);        
    }

    @com.thoughtworks.gauge.Step("Save pipeline")
	public void savePipeline() throws Exception {
        ElementStub finishButton = browser.submit("FINISH");
        Assert.assertThat(finishButton.exists(), Is.is(true));
        finishButton.click();
    }

    @com.thoughtworks.gauge.Step("Verify validation message <message>")
	public void verifyValidationMessage(String message) throws Exception {
        Assert.assertThat(browser.div(scenarioState.expand(message)).exists(), Is.is(true));
    }

    @com.thoughtworks.gauge.Step("Verify inline validation message <message>")
	public void verifyInlineValidationMessage(String message) throws Exception {
        Assert.assertThat(browser.label(scenarioState.expand(message)).exists(), Is.is(true));
        verifyStillOnTheSameTab();
    }

    private void verifyStillOnTheSameTab() {
        Assert.assertThat(currentTab(), Matchers.is(this.currentTab));
    }

    @com.thoughtworks.gauge.Step("Remember current tab")
	public void rememberCurrentTab() throws Exception {
        this.currentTab = currentTab();
    }

    private String currentTab() {
        return browser.listItem(Regex.matches("current_tab")).fetch("id");
    }

    @com.thoughtworks.gauge.Step("Verify transitioned successfully")
	public void verifyTransitionedSuccessfully() throws Exception {
        Assert.assertThat(currentTab(), Matchers.not(this.currentTab));
    }

    @com.thoughtworks.gauge.Step("Use template")
	public void useTemplate() throws Exception {
        browser.label("Use Template").click();
    }
    
    public void useDefineStages() throws Exception {
        browser.label("Define Stages").click();
    }

    @com.thoughtworks.gauge.Step("Select template named <templateName>")
	public void selectTemplateNamed(String templateName) throws Exception {
        browser.select("pipeline_group[pipeline][templateName]").choose(templateName);
    }

    @com.thoughtworks.gauge.Step("Verify error <message> is shown on top")
	public void verifyErrorIsShownOnTop(String message) throws Exception {
        ElementStub li = browser.listItem(message);
        String cssClass = li.fetch("className");
        Assert.assertThat(cssClass, Matchers.containsString("error"));
    }

    @com.thoughtworks.gauge.Step("Verify has field named <name> with value <value>")
	public void verifyHasFieldNamedWithValue(String name, String value) throws Exception {
        //ElementStub field = browser.textbox(value);
        String input = browser.fetch("jQuery(\"input[name='" + name + "'][value='" + value + "']\").get(0)");
        //Assert.assertThat(field.fetch("name"), Matchers.is(name));
        //Assert.assertThat(field.isVisible(), Matchers.is(true));
        Assert.assertThat(input, Matchers.not("undefined"));
    }

    @com.thoughtworks.gauge.Step("Verify that group name autocomplete is not present")
	public void verifyThatGroupNameAutocompleteIsNotPresent() throws Exception {
        ElementStub pipelineGroup = browser.textbox("pipeline_group[group]");
        Assert.assertThat(pipelineGroup.exists(), Is.is(false));
    }

    @com.thoughtworks.gauge.Step("Verify that group name select box is present and contains <groups>")
	public void verifyThatGroupNameSelectBoxIsPresentAndContains(String groups) throws Exception {
        ElementStub pipelineGroup = browser.select("pipeline_group[group]");
        Assert.assertThat(pipelineGroup.exists(), Is.is(true));
        String[] allGroups = groups.split(",");
        for (String groupName : allGroups) {
            Assert.assertThat(browser.option(groupName.trim()).in(pipelineGroup).exists(), Is.is(true));
        }
    }
    
    public void createANewPipelineInPipelineGroup(String pipelineName, String pipelineGroup) throws Exception {
		String actualName = pipelineName + StringUtil.shortUUID();
		browser.textbox("pipeline_group[pipeline][name]").setValue(actualName);
		browser.textbox("pipeline_group[group]").setValue(pipelineGroup);
		browser.select("pipeline_group[pipeline][materials][materialType]").choose("Git");
		browser.textbox("pipeline_group[pipeline][materials][GitMaterial][url]").setValue("http://git.url");
		browser.select("pipeline_group[pipeline][stage][jobs][][tasks][taskOptions]").choose("Ant");
		savePipeline();
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_GENERAL_PAGE);
		scenarioState.pushPipeline(pipelineName, actualName);		
	}

	@com.thoughtworks.gauge.Step("Set pipeline and stage <pipelineName> <stageName>")
	public void setPipelineAndStage(String pipelineName, String stageName) throws Exception {
		String instancePipelineName = scenarioState.pipelineNamed(pipelineName);
		browser.textbox("material_pipelineStageName").setValue(instancePipelineName + " [" + stageName + "]");
	}

	@com.thoughtworks.gauge.Step("Enter pipeline name <pipelineName> - Already on new pipeline wizard")
	public void enterPipelineName(String pipelineName) throws Exception {
		String actualName = pipelineName + StringUtil.shortUUID();
		browser.textbox("pipeline_group[pipeline][name]").setValue(actualName);
		scenarioState.pushPipeline(pipelineName, actualName);
	}
	
	public void verifyShowsUpInCommandLookupDropdown(String commandNames) throws Exception {
		String[] commands = commandNames.split(",");
		ElementStub commandList = browser.select("lookup_command");
		for (String command : commands){
			assertEquals(commandList.containsText(command), true);
		 }
		}
		
	public void selectFromCommandLookupDropdown(String command) throws Exception {
			browser.select("lookup_command").choose(command);
		}
		
	@com.thoughtworks.gauge.Step("Verify command is set to <command> with arguments <arguments>")
	public void verifyCommandIsSetToWithArguments(String command, String arguments) throws Exception {
			assertEquals(browser.textbox("pipeline_group[pipeline][stage][jobs][][tasks][exec][command]").getValue(), command);
			assertEquals(browser.textarea("pipeline_group[pipeline][stage][jobs][][tasks][exec][argListString]").getValue(), arguments.replaceAll(",", "\n"));
         }

	@com.thoughtworks.gauge.Step("Enter <textToEnter> in command lookup autocomplete box")
	public void enterInCommandLookupAutocompleteBox(String textToEnter)	throws Exception {
		browser.textbox(Regex.startsWith("lookup_command")).setValue(textToEnter);
	}

	@com.thoughtworks.gauge.Step("Auto complete should show suggestions <expectedSuggestions>")
	public void autoCompleteShouldShowSuggestions(String expectedSuggestions) throws Exception {
		waitForAutocompleteDelay();
        AutoCompleteSuggestions suggestions = new AutoCompleteSuggestions(browser, browserWrapper);
        String[] expected = expectedSuggestions.split(",");
        for (String suggestion : expected){
        	suggestions.autoCompletesShouldShowSuggestion(COMMAND_LOOKUP_TEXTBOX_INDEX, suggestion.trim());
        }
        assertThat("Found: " + suggestions.allSuggestionTexts(COMMAND_LOOKUP_TEXTBOX_INDEX), suggestions.allSuggestion(COMMAND_LOOKUP_TEXTBOX_INDEX).size(), Is.is(expected.length));
	}

	@com.thoughtworks.gauge.Step("Select option <oneBasedOptionIndex> from command lookup dropdown")
	public void selectOptionFromCommandLookupDropdown(Integer oneBasedOptionIndex) throws Exception {
        AutoCompleteSuggestions suggestions = new AutoCompleteSuggestions(browser, browserWrapper);
        suggestions.selectOption(oneBasedOptionIndex - 1, COMMAND_LOOKUP_TEXTBOX_INDEX);
	}

	private void waitForAutocompleteDelay() throws InterruptedException {
		Thread.sleep(600);
	}

	@com.thoughtworks.gauge.Step("Verify snippet details are shown with name <name> description <description> author <author> with authorlink <authorLink> and more info <moreInfoLink>")
	public void verifySnippetDetailsAreShownWithNameDescriptionAuthorWithAuthorlinkAndMoreInfo(String name, String description, String author, String authorLink, String moreInfoLink) throws Exception {
		new CommandRepository(browser).verifySnippetDetailsAreShownWithNameDescriptionAuthorWithAuthorlinkAndMoreInfo("", name, description, author, authorLink, moreInfoLink);
	}

	@com.thoughtworks.gauge.Step("Remember md5 - Already on new pipeline wizard")
	public void rememberMd5() throws Exception {
       md5value = browser.getValue(browser.hidden("config_md5"));
	}

	@com.thoughtworks.gauge.Step("Verify md5 is same - Already on new pipeline wizard")
	public void verifyMd5IsSame() throws Exception {
		assertEquals(browser.hidden("config_md5").getValue(), md5value);
	}
	
	public void verifyMd5IsNotSame() throws Exception {
		String md5check = browser.hidden("config_md5").getValue();
		Assert.assertThat(md5check.equalsIgnoreCase(md5value), Is.is(false));
	}
	
	public void selectRepository(String repositoryName) throws Exception {
		packageMaterialCreator.selectRepository(repositoryName);
	}

	public void selectPackage(String packageName) throws Exception {
		packageMaterialCreator.selectPackage(packageName);
	}
	
	public void verifyReadonlyConfigurationForPackageWithNameAndSpecIsDisplayed(
			String packageName, String packageSpec) throws Exception {
		packageMaterialCreator.verifyReadonlyConfigurationForPackageWithNameAndSpecIsDisplayed(packageName, packageSpec);
	}

	public void selectOption(String option) throws Exception {
		packageMaterialCreator.selectOption(option);
	}

	public void enterPackageNameAndSpec(String packageName, String packageSpec) throws Exception {
		packageMaterialCreator.enterPackageNameAndSpec(packageName, packageSpec);
	}

	public void verifyErrorMessageIsShown(String message) throws Exception {
		packageMaterialCreator.verifyErrorMessageIsShown(message);
	}

	public void verifyGlobalErrorsHasError(String message) throws Exception {
		packageMaterialCreator.verifyGlobalErrorsHasError(message);
	}

	public void verifyMissingPluginErrorIsShown() throws Exception {
		packageMaterialCreator.verifyMissingPluginErrorIsShown();
	}

	public void verifyThatSaveIs(String state) throws Exception {
		packageMaterialCreator.verifyThatSaveIs(state);
	}

	public void verifyRadioButtonsAre(String radioButtonState) {
		packageMaterialCreator.verifyRadioButtonsAre(radioButtonState);
	}

	public void verifyThatMessageIsShown(String message) {
		packageMaterialCreator.verifyThatMessageIsShown(message);
	}
	
	
	@com.thoughtworks.gauge.Step("Verify inline error message <validationMessage>")
	public void verifyInlineErrorMessage(String validationMessage) throws Exception {
        Assert.assertThat(browser.span(validationMessage).exists(), Is.is(true));

	}
	
}
