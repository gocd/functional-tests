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
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThat;

// JUnit Assert framework can be used for verification

public class AlreadyOnJobListingPage extends AlreadyOnEditPipelineWizardPage {

	private final CurrentPageState currentPageState;

	public AlreadyOnJobListingPage(Browser browser, CurrentPageState currentPageState, ScenarioState scenarioState) {
		super(currentPageState,scenarioState,browser);
		this.currentPageState= currentPageState;
		currentPageState.assertCurrentPageIs(Page.EDIT_STAGE_WIZARD_JOBS_PAGE);
	}

	@Override
	protected String url() {
		return null;
	}
    
    @com.thoughtworks.gauge.Step("Open job <job>")
	public void openJob(String job) {
    	browser.link(Regex.matches(job)).click();
		currentPageState.currentPageIs(Page.PIPELINE_WIZARD_JOB_EDIT_PAGE);
    }
    
    @com.thoughtworks.gauge.Step("Open add new job to this stage page")
	public void openAddNewJobToThisStagePage() throws Exception {
        browser.link(Regex.matches("Add new job")).click();
        currentPageState.currentPageIs(Page.EDIT_STAGE_WIZARD_ADD_JOB_POPUP);
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Assertions.Predicate() {
            public boolean call() throws Exception {
                return browser.heading3("Job Information").exists();
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify that job saved sucessfully - Already on Job listing Page")
	public void verifyThatJobSavedSucessfully() throws Exception {
        verifySavedSuccessfully();
    }

    @com.thoughtworks.gauge.Step("Verify job <jobName> with resources as <resources> and run on all as <runOnAll>")
	public void verifyJobWithResourcesAsAndRunOnAllAs(String jobName, String resources, String runOnAll) throws Exception {
        ElementStub jobDetails = browser.cell(jobName).parentNode();
        assertTrue(String.format("Resources '%s' not found", resources), browser.cell(resources).in(jobDetails).exists());
        assertTrue(String.format("Run-on-all is not '%s'", runOnAll), browser.cell(runOnAll).in(jobDetails).exists());
    }

    @com.thoughtworks.gauge.Step("Delete job <jobName>")
	public void deleteJob(String jobName) throws Exception {
		//HACK: reloadPage to remove any existing messages. This is so that we can be 
		//certain the message we are validating is from this delete.
		reloadPage(); 
        ElementStub jobRow = browser.link(jobName).in(browser.table(Regex.matches("list_table reorderable_table jobs_list_table"))).parentNode().parentNode();
        browser.span(Regex.matches("icon_remove delete_parent")).in(jobRow).click();
        proceedWithConfirmPrompt();
    }

    @com.thoughtworks.gauge.Step("Verify <jobName> job is present")
	public void verifyJobIsPresent(String jobName) throws Exception {
        assertThat(jobLink(jobName).exists(), Is.is(true));
    }

    @com.thoughtworks.gauge.Step("Verify job <jobName> is not present")
	public void verifyJobIsNotPresent(String jobName) throws Exception {
        assertThat(jobLink(jobName).exists(), Is.is(false));
    }
    
    private ElementStub jobLink(String jobName) {
        return browser.link(Regex.matches(jobName));
    }
    
    @com.thoughtworks.gauge.Step("Assert mD5 - Already on Job listing Page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }

}
