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
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// JUnit Assert framework can be used for verification

public class AlreadyOnEditStagePage extends AlreadyOnEditPipelineWizardPage {

	private final CurrentPageState currentPageState;
    private final Configuration configuration;
	private ElementStub currentElement;


	public AlreadyOnEditStagePage(Browser browser, CurrentPageState currentPageState, ScenarioState scenarioState, Configuration configuration) {
		super(currentPageState,scenarioState,browser);
		this.currentPageState= currentPageState;
		this.configuration = configuration;
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_EDIT_STAGE_PAGE);
	}

	@Override
	protected String url() {
		// TODO Auto-generated method stub
		return null;
	}
	

	@com.thoughtworks.gauge.Step("Select stage type <stageTypeValue>")
	public void selectStageType(String stageTypeValue) throws Exception {
		
			ElementStub stageTypeRadioButton = browser.radio(stageTypeValue);
			if (!stageTypeRadioButton.checked()) stageTypeRadioButton.check();
	}

	@com.thoughtworks.gauge.Step("Select clean working directory")
	public void selectCleanWorkingDirectory() throws Exception {
			ElementStub workingDirectoryCheckBox = browser.checkbox("stage_cleanWorkingDir");
			if(!workingDirectoryCheckBox.checked()) workingDirectoryCheckBox.check();
	}

	@com.thoughtworks.gauge.Step("Select fetch materials")
	public void selectFetchMaterials() throws Exception {
			ElementStub fetchMaterialsCheckBox = browser.checkbox("stage_fetchMaterials");
			if(!fetchMaterialsCheckBox.checked()) fetchMaterialsCheckBox.check();
	}

	@com.thoughtworks.gauge.Step("Open stage listing page - Already On Edit Stage Page")
	public void openStageListingPage() throws Exception {
		browser.link(scenarioState.currentRuntimePipelineName()).in(browser.div("pipeline_header")).click();
		browser.link(Regex.startsWith("Stages")).click();
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_STAGES_PAGE);
	}
	
	@com.thoughtworks.gauge.Step("Set stage name as <stageName>")
	public void setStageNameAs(String stageName) {
		stageNameField().setValue(stageName);
	}

	private ElementStub stageNameField() {
		return browser.textbox("stage[name]");
	}

	@com.thoughtworks.gauge.Step("Click save - Already on Edit Stage Page")
	public void clickSave() throws Exception {
		super.clickSave();				
	}

	@com.thoughtworks.gauge.Step("Verify that stage saved successfully - Already on edit stage page")
	public void verifyThatStageSavedSuccessfully() throws Exception {
		super.verifySavedSuccessfully();
	}

    @com.thoughtworks.gauge.Step("Select stage type as <stageType>")
	public void selectStageTypeAs(String stageType) throws Exception {
        ElementStub stageTypeControl = browser.byId(stageType);
        Assert.assertThat(stageTypeControl.exists(), Is.is(true));
        stageTypeControl.click();
    }

    @com.thoughtworks.gauge.Step("Click reset - Already on edit stage page")
	public void clickReset() throws Exception {
        super.clickReset();
    }

    @com.thoughtworks.gauge.Step("Proceed with dirty check prompt - Already on edit stage page")
	public void proceedWithDirtyCheckPrompt(){
        super.proceedWithDirtyCheckPrompt();
    }
    
    @com.thoughtworks.gauge.Step("Go to environment variables page - Already on edit stage page")
	public void goToEnvironmentVariablesPage() throws Exception {
        super.goToEnvironmentVariablesPage();
    }
    
    @com.thoughtworks.gauge.Step("Open jobs")
	public void openJobs() {
    	browser.link("/Jobs/").click();
		currentPageState.currentPageIs(Page.EDIT_STAGE_WIZARD_JOBS_PAGE);
    }
    

    @com.thoughtworks.gauge.Step("Navigate to <tabName> tab")
	public void navigateToTab(String tabName) throws Exception {
        browser.link(tabName).click();
    }

    @com.thoughtworks.gauge.Step("Verify that <label> is selected")
	public void verifyThatIsSelected(String label) throws Exception {
        assertThat(elementPermissionsFrom(label).checked(), is(true));
    }

    private ElementStub elementPermissionsFrom(String label) {
        return browser.byId(permissionFrom(label));
    }

    private String permissionFrom(String label) {
        if ("Inherit from the pipeline group".equals(label)) {
            return "inherit_permissions";
        }
        return "define_permissions";
    }

    @com.thoughtworks.gauge.Step("Verify that the message <message> shows up - Already On Edit Stage Page")
	public void verifyThatTheMessageShowsUp(String message) throws Exception {
        assertThat(String.format("Expected to have the message '%s' on the UI. Did not find it however.", message), browser.div(message).exists(), is(true));
    }

    @com.thoughtworks.gauge.Step("Select <label>")
	public void select(String label) throws Exception {
        elementPermissionsFrom(label).check();
        verifyThatIsSelected(label);
    }

    @com.thoughtworks.gauge.Step("Enter <name> as user name")
	public void enterAsUserName(String name) throws Exception {
        List<ElementStub> allUsers = allUsers();
        allUsers.get(allUsers.size() - 1).setValue(name);
    }

    private List<ElementStub> allInherittedUsers() {
        return allInherittedUsersOrRoles("user", "users");
    }
    
    private List<ElementStub> allUsers() {
        return allUsersOrRoles("user", "users");
    }

    private List<ElementStub> allUsersOrRoles(String textClass, String divClass) {
        return browserWrapper.collect("textbox", Regex.matches(String.format("permissions_%s_name", textClass)));
    }
    
    private List<ElementStub> allInherittedUsersOrRoles(String textClass, String divClass) {
        return browserWrapper.collectIn("textbox", Regex.wholeWord("user_role_name"), browser.div(divClass).in(browser.div(Regex.wholeWord("inherited_permissions"))));
    }

    @com.thoughtworks.gauge.Step("Auto complete should show up <suggestion>")
	public void autoCompleteShouldShowUp(String suggestion) throws Exception {
        new AutoCompleteSuggestions(browser, browserWrapper).autoCompletesShouldShowSuggestion(suggestion);
    }

    @com.thoughtworks.gauge.Step("Enter <name> as role name")
	public void enterAsRoleName(String name) throws Exception {
        allRoles().get(0).setValue(name);
    }
    
    private List<ElementStub> allInherittedRoles() {
        return allInherittedUsersOrRoles("role", "roles");
    }

    private List<ElementStub> allRoles() {
        return allUsersOrRoles("role", "roles");
    }

    @com.thoughtworks.gauge.Step("Save - Already On Edit Stage Page")
	public void save() throws Exception {
        clickSave();
    }

    @com.thoughtworks.gauge.Step("Select option <option>")
	public void selectOption(String option) throws Exception {
        new AutoCompleteSuggestions(browser, browserWrapper).selectFirstOption();
    }

    @com.thoughtworks.gauge.Step("Verify that user <userName> is authorized to operate on the stage")
	public void verifyThatUserIsAuthorizedToOperateOnTheStage(final String userName) throws Exception {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            
            @Override
            public boolean call() throws Exception {
                final CruiseConfigDom dom = configuration.provideDomAsAdmin();
                return dom.isUserAuthorizedOnStage(scenarioState.currentRuntimePipelineName(), "defaultStage", userName);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify that role <roleName> is authorized to operate on the stage")
	public void verifyThatRoleIsAuthorizedToOperateOnTheStage(final String roleName) throws Exception {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            
            @Override
            public boolean call() throws Exception {
                CruiseConfigDom dom = configuration.provideDomAsAdmin();
                return dom.isRoleAuthorizedOnStage(scenarioState.currentRuntimePipelineName(), "defaultStage", roleName);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Auto complete should not show up <suggestion> - Already On Edit Stage Page")
	public void autoCompleteShouldNotShowUp(String suggestion) throws Exception {
        new AutoCompleteSuggestions(browser, browserWrapper).autoCompleteShouldNotHave(suggestion);
    }

    @com.thoughtworks.gauge.Step("Verify that user <username> is already added")
	public void verifyThatUserIsAlreadyAdded(String username) throws Exception {
        for (ElementStub element : allUsers()) {
            if (element.getValue().equals(username)) {
                return;
            }
        }
        throw new RuntimeException("Expected to find the username '" + username + "'. But did not find it.");
    }

    @com.thoughtworks.gauge.Step("Verify that role <roleName> is already added")
	public void verifyThatRoleIsAlreadyAdded(String roleName) throws Exception {
        for (ElementStub element : allRoles()) {
            if (element.getValue().equals(roleName)) {
                return;
            }
        }
        throw new RuntimeException("Expected to find the rolename '" + roleName + "'. But did not find it.");
    }

    @com.thoughtworks.gauge.Step("Verify that user <username> is inheritted")
	public void verifyThatUserIsInheritted(String username) throws Exception {
        for (ElementStub element : allInherittedUsers()) {
            if (element.getValue().equals(username) && element.fetch("disabled").equals("true")) {
                return;
            }
        }
        throw new RuntimeException("Expected to find the username '" + username + "'. But did not find it.");
    }

    @com.thoughtworks.gauge.Step("Verify that role <roleName> is inheritted")
	public void verifyThatRoleIsInheritted(String roleName) throws Exception {
        for (ElementStub element : allInherittedRoles()) {
            if (element.getValue().equals(roleName) && element.fetch("disabled").equals("true")) {
                return;
            }
        }
        throw new RuntimeException("Expected to find the rolename '" + roleName + "'. But did not find it.");
    }

    @com.thoughtworks.gauge.Step("On the field <fieldName> with value <value>")
	public void onTheFieldWithValue(String fieldName, String value) throws Exception {
        ElementStub element;
        if (fieldName.equals("user")) {
            element = userWithName(value);            
        } else {
            element = roleWithName(value);
        }
        this.currentElement = element;
    }

    private ElementStub roleWithName(String value) {
        List<ElementStub> roles = allRoles();
        for (ElementStub element : roles) {
            if (element.getValue().equals(value)) return element;
        }
        throw new RuntimeException(String.format("User with name '%s' is not found", value));
    }

    private ElementStub userWithName(String value) {
        List<ElementStub> users = allUsers();
        for (ElementStub element : users) {
            if (element.getValue().equals(value)) return element;
        }
        throw new RuntimeException(String.format("User with name '%s' is not found", value));
    }

    @com.thoughtworks.gauge.Step("Verify message <message> shows up - Already On Edit Stage Page")
	public void verifyMessageShowsUp(String message) throws Exception {
        assertThat(browser.div("name_value_error").in(currentElement.parentNode("td")).getText(), is(message));
    }

	@com.thoughtworks.gauge.Step("Verify that stage is named <stageName>")
	public void verifyThatStageIsNamed(String stageName) throws Exception {
		assertThat(stageNameField().getValue(), is(stageName));
	}	
	
    @com.thoughtworks.gauge.Step("Verify save failed")
	public void verifySaveFailed() {
        assertThat(isMessagePresent("Save failed, see errors below"), is(true));
    }
    
    @com.thoughtworks.gauge.Step("Verify error message <errorMessage> is shown")
	public void verifyErrorMessageIsShown(String errorMessage) {
    	assertThat(isMessagePresent(errorMessage), is(true));
    }

    public void verifyThatNeverCleanUpArtifactsIsNotSelected() throws Exception {
        assertThat(elementProhibitArtifactCleanup().checked(), is(false));
    }

    public ElementStub elementProhibitArtifactCleanup() {
        return browser.checkbox("stage_artifactCleanupProhibited");
    }

    public void selectNeverCleanUpArtifacts() throws Exception {
        elementProhibitArtifactCleanup().check();
    }

    public void verifyThatNeverCleanUpArtifactsIsSelected() throws Exception {
        Assertions.waitUntil(Timeout.TEN_SECONDS, new Predicate() {
            
            @Override
            public boolean call() throws Exception {
                return elementProhibitArtifactCleanup().checked();
            }
        });
    }

    public void unselectNeverCleanUpArtifacts() throws Exception {
        elementProhibitArtifactCleanup().uncheck();
    }

    public void enterUserDefinedApprovalParameterAs(String param) throws Exception {
        browser.textbox("stage_approval_customType").setValue(param);
    }

	@com.thoughtworks.gauge.Step("Verify aproval type is set to <expectedApprovalType>")
	public void verifyAprovalTypeIsSetTo(String expectedApprovalType) throws Exception {
		if("auto".equals(expectedApprovalType)){
			Assert.assertThat(browser.radio("auto").checked(), Is.is(true));
			Assert.assertThat(browser.radio("manual").checked(), Is.is(false));
		}
		else{
			Assert.assertThat(browser.radio("auto").checked(), Is.is(false));
			Assert.assertThat(browser.radio("manual").checked(), Is.is(true));			
		}
	}

	@com.thoughtworks.gauge.Step("Change approval type to <approvalType>")
	public void changeApprovalTypeTo(String approvalType) throws Exception {
		if("manual".equals(approvalType)){
			browser.radio("manual").check();
		}
		else{
			browser.radio("auto").check();
		}
	} 
	
	@com.thoughtworks.gauge.Step("Assert mD5 - Already on edit stage page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }
}
