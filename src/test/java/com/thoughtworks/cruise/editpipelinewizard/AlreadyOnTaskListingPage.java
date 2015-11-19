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
import org.junit.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

// JUnit Assert framework can be used for verification

public class AlreadyOnTaskListingPage extends AlreadyOnEditPipelineWizardPage {

	private final CurrentPageState currentPageState;

	public AlreadyOnTaskListingPage(Browser browser, CurrentPageState currentPageState, ScenarioState scenarioState) {
		super(currentPageState,scenarioState,browser);
		this.currentPageState= currentPageState;
		currentPageState.assertCurrentPageIs(Page.PIPELINE_WIZARD_TASK_LISTING_PAGE);
	}

	@Override
	protected String url() {
		return null;
	}
    
    @com.thoughtworks.gauge.Step("Open task <taskIndex>")
	public void openTask(Integer taskIndex) {
    	//0 is the header row :-(
    	ElementStub taskEditLink = browser.link(0).in(browser.row(taskIndex));
    	taskEditLink.click();
    	currentPageState.currentPageIs(Page.PIPELINE_WIZARD_TASK_EDIT_POPUP);
    }

	@com.thoughtworks.gauge.Step("Open new task form <taskType>")
	public void openNewTaskForm(String taskType) throws Exception {
		ElementStub addNewTaskLink = browser.link("Add new task");
		Assert.assertThat(addNewTaskLink.exists(), is(true));
		addNewTaskLink.click();
		
		ElementStub newTaskLink = browser.link(taskType).in(browser.div("new_task_popup"));
		Assert.assertThat(newTaskLink.exists(), is(true));
		newTaskLink.click();
		currentPageState.currentPageIs(Page.PIPELINE_WIZARD_TASK_EDIT_POPUP);
	}

    Pattern PROPERTY_PATTERN = Pattern.compile("^\\s*(.+?)\\s*:\\s*(.*?)\\s*$");

	@com.thoughtworks.gauge.Step("Verify task <oneBasedIndex> is <taskType> task without on cancel and properties <properties> that runs if state is <runIfConditions>")
	public void verifyTaskIsTaskWithoutOnCancelAndPropertiesThatRunsIfStateIs(Integer oneBasedIndex, String taskType, String properties, String runIfConditions)	throws Exception {
		ElementStub taskRow = verifyAndLoadTaskRow(oneBasedIndex, taskType, properties, runIfConditions);
		Assert.assertThat(browser.cell("No").in(taskRow).exists(), is(true));
    }

	//Ideally these assertions should be under(browser.cell("...")) but that doesn't work
	private ElementStub verifyAndLoadTaskRow(Integer oneBasedIndex, String taskType, String properties, String runIfConditions) {
        ElementStub taskRow = taskRow(oneBasedIndex);
		Assert.assertThat(browser.cell(taskType).in(taskRow).exists(), is(true));
        Assert.assertThat(browser.cell(runIfConditions).in(taskRow).exists(), is(true));
        assertExistenceOfProperties(properties, taskRow, true);
		return taskRow;
	}

    private ElementStub taskRow(Integer oneBasedIndex) {
        return browser.row(oneBasedIndex).in(tasksTable());
    }

    private ElementStub tasksTable() {
        return browser.table("/list_table/");
    }

    private void assertExistenceOfProperties(String properties, ElementStub parent, boolean shouldExist) {
        for (String property : properties.split("\\s*,\\s*")) {
            Matcher matcher = PROPERTY_PATTERN.matcher(property);
            if (matcher.matches()) {
                String propertyName = matcher.group(1);
                String propertyValue = matcher.group(2);
                propertyValue = scenarioState.expand(propertyValue);
                boolean hasTitle = browser.span(propertyName + ":").in(parent).isVisible();
                boolean hasValue = browser.span(propertyValue).in(parent).isVisible();
				assertThat("Unable to find property: '" + propertyName + "' with value: '" + propertyValue + "'", hasTitle && hasValue, is(shouldExist));
            }
        }
    }

	@com.thoughtworks.gauge.Step("Verify task <nth> is <taskType> task with cancel <onCancelTaskType> and properties <properties> that runs if state is <runIfConditions>")
	public void verifyTaskIsTaskWithCancelAndPropertiesThatRunsIfStateIs(Integer nth, String taskType, String onCancelTaskType, String properties, String runIfConditions) throws Exception {
		ElementStub taskRow = verifyAndLoadTaskRow(nth, taskType, properties, runIfConditions);
        Assert.assertThat(browser.cell(onCancelTaskType).in(taskRow).exists(), is(true));
	}

	@com.thoughtworks.gauge.Step("Delete task <nth>")
	public void deleteTask(Integer nth) throws Exception {
		reloadPage();
		browser.span(Regex.matches("icon_remove")).in(taskRow(nth)).click();
		proceedWithConfirmPrompt();
        verifySavedSuccessfully();
	}

	@com.thoughtworks.gauge.Step("Verify no task having <propertiesFragment> exists")
	public void verifyNoTaskHavingExists(String propertiesFragment) throws Exception {
        assertExistenceOfProperties(propertiesFragment, tasksTable(), false);
	}

	@com.thoughtworks.gauge.Step("Move down task <oneBasedTaskIndex>")
	public void moveDownTask(Integer oneBasedTaskIndex) throws Exception {
		ElementStub downButton = getReOrderingButton(oneBasedTaskIndex, "promote_down");
		downButton.click();
	}

	@com.thoughtworks.gauge.Step("Move up task <oneBasedTaskIndex>")
	public void moveUpTask(Integer oneBasedTaskIndex) throws Exception {
		ElementStub upButton = getReOrderingButton(oneBasedTaskIndex, "promote_up");
		upButton.click();
	}
	
	private ElementStub getReOrderingButton(Integer oneBasedTaskIndex, String buttonClass) {
		ElementStub taskRow = browser.row(Regex.matches(String.format("task_%d",oneBasedTaskIndex -1 )));
		ElementStub moveDownButton = browser.div(buttonClass).in(taskRow).parentNode();
		assertThat("Move down button is not present", moveDownButton.exists(), is(true));
		return moveDownButton;
	}

    @com.thoughtworks.gauge.Step("Verify no tasks exists")
	public void verifyNoTasksExists() throws Exception {
        ElementStub taskTable = browser.table("list_table");
        ElementStub zerothTask = browser.byClassName("task_0").in(taskTable);
        assertThat("should not have had any tasks", zerothTask.exists(), is(false));
    }
    
    @com.thoughtworks.gauge.Step("Open home for pipeline <pipelineName>")
	public void openHomeForPipeline(String pipelineName) throws Exception {
    	String instancePipelineName = scenarioState.pipelineNamed(pipelineName);
    	ElementStub pipelineLink = browser.link(instancePipelineName);
    	pipelineLink.click();
    	currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_GENERAL_PAGE);
    	
	}
    
    @com.thoughtworks.gauge.Step("Assert mD5 - Already on Task listing page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }
    
    @com.thoughtworks.gauge.Step("Open artifacts - Already on Task listing page")
	public void openArtifacts() throws Exception {
		browser.link(Regex.matches("Artifacts")).click();
		currentPageState.currentPageIs(Page.PIPELINE_WIZARD_ARTIFACTS_LISTING_PAGE);	
	}
}
