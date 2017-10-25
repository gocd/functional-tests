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
import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AlreadyOnGeneralOptionsPage extends AlreadyOnEditPipelineWizardPage {
    public AlreadyOnGeneralOptionsPage(ScenarioState  scenarioState, Browser browser, CurrentPageState currentPageState) {
		super(currentPageState,scenarioState,browser);
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_GENERAL_PAGE);		
	}

	@com.thoughtworks.gauge.Step("Enter <timeSpecifierValue> for cron time specifier")
	public void enterForCronTimeSpecifier(String timeSpecifierValue) throws Exception {
	    browser.textbox("pipeline_timer_timerSpec").setValue(timeSpecifierValue);
	}

	@com.thoughtworks.gauge.Step("Enter <textValue> for label template")
	public void enterForLabelTemplate(String textValue) throws Exception {
	   browser.textbox("pipeline_labelTemplate").setValue(textValue);
	}

	@com.thoughtworks.gauge.Step("Verify reset button exists")
	public void verifyResetButtonExists() throws Exception {
		super.verifyResetButtonExists();
	}

	@com.thoughtworks.gauge.Step("Click save - Already On General Options Page")
	public void clickSave() throws Exception {
		super.clickSave();
	}
	
	@com.thoughtworks.gauge.Step("Click reset")
	public void clickReset() throws Exception {
	    super.clickReset();
	}

	@com.thoughtworks.gauge.Step("Verify <message> message is displayed")
	public void verifyMessageIsDisplayed(String message) throws Exception {
		super.verifyMessageIsDisplayed(message);
	}

	@com.thoughtworks.gauge.Step("Go to project management page")
	public void goToProjectManagementPage() throws Exception {
		super.goToProjectManagementPage();
	}

	@com.thoughtworks.gauge.Step("Open parameters page")
	public void openParametersPage() throws Exception {
		super.openParametersPage();
	}

	@com.thoughtworks.gauge.Step("Go to environment variables page")
	public void goToEnvironmentVariablesPage() throws Exception {
		super.goToEnvironmentVariablesPage();
	
	}

	@com.thoughtworks.gauge.Step("Open stage listing page")
	public void openStageListingPage() throws Exception {
		super.openStageListingPage();
	}
	
	@com.thoughtworks.gauge.Step("Open material listing page")
	public void openMaterialListingPage() throws Exception {
		super.openMaterialListingPage();
		
	}

    @com.thoughtworks.gauge.Step("Verify that dirty check is invoked")
	public void verifyThatDirtyCheckIsInvoked() throws Exception {
        Assert.assertThat(browser.span("Unsaved Changes").exists(), Is.is(true));
        Assert.assertThat(browser.div("There are unsaved changes on your form. 'Proceed' will discard these changes.").exists(), Is.is(true));
    }

    @com.thoughtworks.gauge.Step("Cancel dirty check prompt")
	public void cancelDirtyCheckPrompt() throws Exception {
        super.cancelDirtyCheckPrompt();
    }

    @com.thoughtworks.gauge.Step("Select automatic pipeline locking")
	public void checkAutomaticPipelineLocking() throws Exception {
        browser.byId("pipeline_lockBehavior_lockonfailure").check();
    }

    @com.thoughtworks.gauge.Step("Proceed with dirty check prompt")
	public void proceedWithDirtyCheckPrompt(){
        super.proceedWithDirtyCheckPrompt();
    }

    @com.thoughtworks.gauge.Step("Verify saved successfully - Already On General Options Page")
	@Override
    public void verifySavedSuccessfully() {
        super.verifySavedSuccessfully();
    }

	@com.thoughtworks.gauge.Step("Verify created successfully")
	public void verifyCreatedSuccessfully() {
		super.verifyCreatedSuccessfully();
	}

    @com.thoughtworks.gauge.Step("Verify pipeline uses template <templateName>")
	public void verifyPipelineUsesTemplate(String templateName) throws Exception {
        assertThat(browser.link(templateName).in(browser.div(Regex.wholeWord("template_name")).in(browser.byId("pipeline_config_tree"))).exists(), Is.is(true));
    }
    
    
    @com.thoughtworks.gauge.Step("Verify template <templateName> used by pipeline is not editable")
	public void verifyTemplateUsedByPipelineIsNotEditable(String templateName) throws Exception {
        assertThat(browser.link(templateName).in(browser.div(Regex.wholeWord("template_name")).in(browser.byId("pipeline_config_tree"))).exists(), Is.is(false));
    }
 

	@com.thoughtworks.gauge.Step("Verify auto scheduling is set to <expected>")
	public void verifyAutoSchedulingIsSetTo(Boolean expected) throws Exception {
		Assert.assertThat(autoSchedulePipelineCheckbox().checked(), Is.is(expected));
	}

	@com.thoughtworks.gauge.Step("Update auto scheduling to <autoSchedule>")
	public void updateAutoSchedulingTo(Boolean autoSchedule) throws Exception {
		ElementStub approval = autoSchedulePipelineCheckbox();
		if (autoSchedule) {
			approval.check();
		} else {
			approval.uncheck();
		}
	
	}

	@com.thoughtworks.gauge.Step("Verify auto scheduling checkbox is disabled")
	public void verifyAutoSchedulingCheckboxIsDisabled() throws Exception {
		verifyElementIsDisabled(autoSchedulePipelineCheckbox(),"true");
	}

	private ElementStub autoSchedulePipelineCheckbox() {
		return browser.checkbox("pipeline_approval_type");
	}

	@com.thoughtworks.gauge.Step("Select onlyOnChanges flag to trigger pipeline only on new material")
	public void selectOnlyOnChangesFlagToTriggerPipelineOnlyOnNewMaterial()
			throws Exception {
		getOnlyOnChangesCheckbox().check();
	}

	@com.thoughtworks.gauge.Step("Verify only on changes checkbox is disabled")
	public void verifyOnlyOnChangesCheckboxIsDisabled() throws Exception {
		verifyElementIsDisabled(getOnlyOnChangesCheckbox(),"true");
	}

	private void verifyElementIsDisabled(ElementStub element, String isDisabled ) {
		Assert.assertThat(element.fetch("disabled"), Is.is(isDisabled));
	}

	private ElementStub getOnlyOnChangesCheckbox() {
		return browser.checkbox("pipeline_timer_onlyOnChanges");
	}

	@com.thoughtworks.gauge.Step("Verify only on changes checkbox is enabled")
	public void verifyOnlyOnChangesCheckboxIsEnabled() throws Exception {
		verifyElementIsDisabled(getOnlyOnChangesCheckbox(),"false");
	}
	
	@com.thoughtworks.gauge.Step("Assert mD5 - Already on general options page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }

	@com.thoughtworks.gauge.Step("Open view template popup")
	public void openViewTemplatePopup() throws Exception {
		browser.link("Preview").click();
		currentPageState.currentPageIs(Page.VIEW_TEMPLATE_POPUP);
	}
}
