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

package com.thoughtworks.cruise.page;

import com.thoughtworks.cruise.SahiBrowserWrapper;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import net.sf.sahi.client.ExecutionException;

import java.util.List;

import static java.lang.String.format;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class AlreadyOnPipelinesSelectorSection {
    private final Browser browser;
    private final ScenarioState scenarioState;
    
    public AlreadyOnPipelinesSelectorSection(ScenarioState scenarioState, Browser browser) {
        this.scenarioState = scenarioState;
        this.browser = browser;
    }
    
	@com.thoughtworks.gauge.Step("Verify groups <groupNames> are visible - Already on pipelines selector section")
	public void verifyGroupsAreVisible(String groupNames) throws Exception {
		String[] groups = groupNames.split(",");
		for (String group : groups) {			
			assertThat(groupSelector(group),is(not(nullValue())));
		}
	}

	@com.thoughtworks.gauge.Step("Verify groups <groupNames> are not visible - Already on pipelines selector section")
	public void verifyGroupsAreNotVisible(String groupNames) throws Exception {
		String[] groups = groupNames.split(",");
		for (String group : groups) {			
			try {
				groupSelector(group).getText();
				fail("expected not to find group: " + group);
			} catch (ExecutionException e) {
			}
		}
	}

    private ElementStub groupSelector(String group) {
        return findElementByScopedId(format("select_group_%s", group.trim()));
    }

    private ElementStub pipelineSelector(String pipeline) {
        return findElementByScopedId(format("select_pipeline_%s", pipeline.trim()));
    }
	
	private ElementStub findElementByScopedId(String id){
	    return browser.byId(id).in(elementSelectorWidget());
//		return driver.findElement(By.xpath(IN_PIPELINE_SELECTOR + driver.byIdXPath(id)));
	}

    private ElementStub elementSelectorWidget() {
        return browser.byId("pipelines_selector");
    }	
	
    private ElementStub elementSelectorWidgetPipelineSelections() {
    	return browser.byId("pipelines_selector_pipelines");
    }
    
	@com.thoughtworks.gauge.Step("Verify all pipelines are selected")
	public void verifyAllPipelinesAreSelected() throws Exception {
		verifyCheckboxesAreSelected(true);
	}

	private void verifyCheckboxesAreSelected(boolean selected) {
		verifyCheckboxesAreSelected("", selected);
	}

	private void verifyCheckboxesAreSelected(String subScope, boolean selected) {
	    List<ElementStub> checkBoxes = new SahiBrowserWrapper(browser).collectIn("checkbox", subScope, elementSelectorWidgetPipelineSelections());
//		List<WebElement> checkBoxes = driver.findElements(By.xpath(IN_PIPELINE_SELECTOR + subScope + "//input[@type='checkbox']"));
		for (ElementStub checkBox : checkBoxes) {
			assertThat(checkBox.checked(), is(selected));
		}
	}

	@com.thoughtworks.gauge.Step("Deselect all pipelines")
	public void deselectAllPipelines() throws Exception {
		findElementByScopedId("select_no_pipelines").click();
	}

	@com.thoughtworks.gauge.Step("Select all pipelines")
	public void selectAllPipelines() throws Exception {
		findElementByScopedId("select_all_pipelines").click();		
	}

	@com.thoughtworks.gauge.Step("Verify no pipelines are selected")
	public void verifyNoPipelinesAreSelected() throws Exception {
		verifyCheckboxesAreSelected(false);
	}

	@com.thoughtworks.gauge.Step("Deselect group <group>")
	public void deselectGroup(String group) throws Exception {
		selectGroup(group, false);
	}
	
	@com.thoughtworks.gauge.Step("Select group <group>")
	public void selectGroup(String group) throws Exception {
		selectGroup(group, true);
	}

	private void selectGroup(String group, boolean select) {
		ElementStub groupCheckBox = groupSelector(group);
		select(groupCheckBox, select);
	}

	private void select(ElementStub checkBox, boolean select) {
		assertChecked(checkBox,!select);		
		checkBox.click();
		assertChecked(checkBox,select);
	}

    private void assertChecked(ElementStub checkBox, boolean select) {
        assertThat(format("Expected '%s' selected to be %s", checkBox.fetch("id"), select), checkBox.checked(), is(select));
    }

    @com.thoughtworks.gauge.Step("Verify all pipelines in group <group> are deselected")
	public void verifyAllPipelinesInGroupAreDeselected(String group)throws Exception {
        verifyCheckboxesAreSelected(format("selector_group_%s", group), false);
    }

    @com.thoughtworks.gauge.Step("Verify all pipelines in group <group> are selected")
	public void verifyAllPipelinesInGroupAreSelected(String group)throws Exception {
        verifyCheckboxesAreSelected(format("selector_group_%s", group), true);
	}

	@com.thoughtworks.gauge.Step("Deselect pipeline <pipeline> and verify <group> is deselected")
	public void deselectPipelineAndVerifyIsDeselected(String pipeline, String group)throws Exception {
		select(pipelineSelector(scenarioState.pipelineNamed(pipeline)), false);
		assertChecked(groupSelector(group), false);
	}

	@com.thoughtworks.gauge.Step("Select pipeline <pipeline> and verify <group> is selected")
	public void selectPipelineAndVerifyIsSelected(String pipeline, String group)throws Exception {
		select(pipelineSelector(scenarioState.pipelineNamed(pipeline)), true);
		assertChecked(groupSelector(group), true);
	}

	@com.thoughtworks.gauge.Step("Apply selections")
	public void applySelections() throws Exception {
		findElementByScopedId("apply_pipelines_selector").click();
	}
	
	
	@com.thoughtworks.gauge.Step("Set show newly created pipelines option status as <expectedStatus>")
	public void setShowNewlyCreatedPipelinesOptionStatusAs(String expectedStatus) throws Exception {
		String actualStatus = actualShowNewlyCreatedPipelinesOptionStatus();

		if (expectedStatus != actualStatus) {
			showNewlyCreatedPipelinesOptionElement().click();			
		}
	}

	@com.thoughtworks.gauge.Step("Verify show newly created pipelines option status is <expectedStatus>")
	public void verifyShowNewlyCreatedPipelinesOptionStatusIs(String expectedStatus) throws Exception {
		String actualStatus = actualShowNewlyCreatedPipelinesOptionStatus(); 

		assertEquals("Expected Option Status is: "+expectedStatus+"but actual value is: "+ actualStatus, expectedStatus, actualStatus);


	}
	
	private String actualShowNewlyCreatedPipelinesOptionStatus() throws Exception {
		String actualStatus;

		ElementStub showNewPipelinesElement = showNewlyCreatedPipelinesOptionElement();
		boolean checkStatus= showNewPipelinesElement.checked();

		if (checkStatus == true){
			actualStatus = "checked";
		} else {
			actualStatus = "unchecked";
		}
		return actualStatus;
	}

	private ElementStub showNewlyCreatedPipelinesOptionElement() {
		return browser.byId("show_new_pipelines").in(browser.div("show_new_pipelines_container"));
	}
	
	public void verifyGroupIsSelected(String group) throws Exception  {
		assertChecked(groupSelector(group), true);

	}
	
	@com.thoughtworks.gauge.Step("Verify group <group> is deselected")
	public void verifyGroupIsDeselected(String group) throws Exception  {
		assertChecked(groupSelector(group), false);

	}

		
}
