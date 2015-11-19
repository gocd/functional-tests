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
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// JUnit Assert framework can be used for verification

public class AlreadyOnStagesPage extends AlreadyOnEditPipelineWizardPage {

	
    public AlreadyOnStagesPage(ScenarioState scenarioState, CurrentPageState currentPageState, Browser browser, Configuration configuration) {
		super(currentPageState,scenarioState,browser);
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_STAGES_PAGE);
	}

	protected String url() {
		return null;
	}
	
    @com.thoughtworks.gauge.Step("Verify <stageName> has <triggerType> trigger option with <numberOfJobs> jobs")
	public void verifyHasTriggerOptionWithJobs(String stageName, String triggerType, String numberOfJobs)
            throws Exception {
        ElementStub stageDetails = browser.cell(stageName).parentNode();
        assertTrue(String.format("Trigger type '%s' not found", triggerType), browser.cell(triggerType).in(stageDetails).exists());
        assertTrue(String.format("Number of jobs is not '%s'", numberOfJobs), browser.cell(numberOfJobs).in(stageDetails).exists());
    }

	@com.thoughtworks.gauge.Step("Open new add stage details page")
	public void openNewAddStageDetailsPage() throws Exception {
		browser.link(Regex.matches("Add new stage")).click();
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_ADD_STAGE_POPUP);
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Assertions.Predicate() {
            public boolean call() throws Exception {
                return browser.heading3("Stage Information").exists();
            }
        });
	}

	@com.thoughtworks.gauge.Step("Verify that stage saved successfully")
	public void verifyThatStageSavedSuccessfully() throws Exception {
		verifySavedSuccessfully();
	}

	@com.thoughtworks.gauge.Step("Open stage <stageName>")
	public void openStage(String stageName) throws Exception {
		stageLink(stageName).click();
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_EDIT_STAGE_PAGE);
	}

    @com.thoughtworks.gauge.Step("Verify the stages are <stagesInOrder>")
	public void verifyTheStagesAre(String stagesInOrder) throws Exception {
        String[] stages = stagesInOrder.split(",");
        List<ElementStub> actualStages = browserWrapper.collect("link", "stage_name_link");
        assertThat(stages.length, Is.is(actualStages.size()));
        for (int i = 0; i < stages.length; i++) {
            assertThat(actualStages.get(i).getText(), is(stages[i].trim()));
        }
    }

    @com.thoughtworks.gauge.Step("Verify cannot move <stageName> up")
	public void verifyCannotMoveUp(String stageName) throws Exception {
        verifyCannotMove(stageName, "up");
    }

    @com.thoughtworks.gauge.Step("Verify cannot move <stageName> down")
	public void verifyCannotMoveDown(String stageName) throws Exception {
        verifyCannotMove(stageName, "down");
    }
    
    @com.thoughtworks.gauge.Step("Move <stageName> up")
	public void moveUp(String stageName) throws Exception {
        elementMove(stageName, "up").click();
    }

    @com.thoughtworks.gauge.Step("Move <stageName> down")
	public void moveDown(String stageName) throws Exception {
        elementMove(stageName, "down").click();
    }

    @com.thoughtworks.gauge.Step("Delete stage <stageName> and confirm deletion")
	public void deleteStageAndConfirmDeletion(String stageName) throws Exception {
		//HACK: reloadPage to remove any existing messages. This is so that we can be 
		//certain the message we are validating is from this delete.
		deleteStage(stageName);
        assertThat(isSavedSuccessfully(), is(true));
    }

    @com.thoughtworks.gauge.Step("Delete stage <stageName>")
	public void deleteStage(String stageName) {
        reloadPage(); 
        ElementStub stageRow = browser.row(String.format("stage_%s", stageName));
        ElementStub deleteSpan = browser.span("icon_remove delete_parent").in(stageRow);
        deleteSpan.click();
        proceedWithConfirmPrompt();
    }

    @com.thoughtworks.gauge.Step("Verify <stageName> stage is present")
	public void verifyStageIsPresent(String stageName) throws Exception {
        assertThat(stageLink(stageName).exists(), Is.is(true));
    }
    
    @com.thoughtworks.gauge.Step("Verify stage <stageName> is not present")
	public void verifyStageIsNotPresent(String stageName) throws Exception {
        assertThat(stageLink(stageName).exists(), Is.is(false));
    }
    
    private void verifyCannotMove(String stageName, String moveType) {
        ElementStub button = elementMove(stageName, moveType);
        assertThat(button.exists(), is(false));
    }

    private ElementStub elementMove(String stageName, String moveType) {
        ElementStub row = browser.row(String.format("stage_%s", stageName));
        return browser.div(Regex.wholeWord("promote_" + moveType)).in(row);
    }
    
    private ElementStub stageLink(String stageName) {
        return browser.link(Regex.matches(stageName));
    }

    public void proceedWithConfirmPrompt() {
        super.proceedWithConfirmPrompt();
    }

    @com.thoughtworks.gauge.Step("Verify global error message <errorMessage> for pipeline <pipelineName> shows up")
	public void verifyGlobalErrorMessageForPipelineShowsUp(String errorMessage, String pipelineName) throws Exception {
        String completeErrorMessage = errorMessage + scenarioState.expand(pipelineName);
        verifyGlobalErrorsContain(completeErrorMessage);
    }

    public void switchToUseTemplate() throws Exception {
        browser.radio("pipeline_configurationType_template").click();
    
    }

    public void selectFromTemplatesDropdown(String templateName) throws Exception {
        browser.select("pipeline[templateName]").choose(templateName);
    }
    
    public void clickSave() throws Exception{
        super.clickSave();
    }
    
    public void clickReset() throws Exception{
        super.clickReset();
    }

    public void clickOnConfirmation(String string1) throws Exception {
        browser.submit("primary submit MB_focusable").click();
    }

    public void openParametersPage() throws Exception {
        super.openParametersPage();
    }
    
    @com.thoughtworks.gauge.Step("Go to environment variables page - Already On Stages Page")
	public void goToEnvironmentVariablesPage() throws Exception {
		super.goToEnvironmentVariablesPage();
	}

    public void verifyGlobalErrorsContain(String message) throws Exception {
        ElementStub error = browser.listItem(message).in(browser.div("errors"));
        Assert.assertThat(error.exists(), Is.is(true));
    }

    public void verifyThatStageHasParameterWithTrigger_Type(String stageName, String parameterName, String triggerType) throws Exception {
        ElementStub stagesOfTemplatedPipelineTable = browser.table("stages_of_templated_pipeline");
        assertThat(stagesOfTemplatedPipelineTable.exists(), Is.is(true));
        
        ElementStub rowContainingStage = browser.row(Regex.wholeWord(stageName)).in(stagesOfTemplatedPipelineTable);
        assertThat(rowContainingStage.exists(), Is.is(true));
        
        ElementStub parameterCell = browser.cell(parameterName).in(rowContainingStage);
        assertThat(parameterCell.exists(), Is.is(true));
        
        ElementStub triggerTypeCell = browser.cell(triggerType).in(rowContainingStage);
        assertThat(triggerTypeCell.exists(), Is.is(true));
    }
    
    public void verifyThatTableHasHeaders(String tableName, String allHeaders) throws Exception {
        super.verifyThatTableHasHeaders(tableName, allHeaders);
    }

	@com.thoughtworks.gauge.Step("Click to edit template")
	public void clickToEditTemplate() throws Exception {
		browser.link(Regex.wholeWord(".edit_template_link")).click();
		currentPageState.currentPageIs(Page.EDIT_TEMPLATE_PAGE);
	}
	
	@com.thoughtworks.gauge.Step("Assert mD5 - Already On Stages Page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }
}
