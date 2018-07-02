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

import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;

import static junit.framework.Assert.assertEquals;

// JUnit Assert framework can be used for verification

public class AlreadyOnArtifactsListingPage extends AlreadyOnEditPipelineWizardPage

{

	
	public AlreadyOnArtifactsListingPage(Browser browser, CurrentPageState currentPageState, ScenarioState scenarioState) {
		super(currentPageState,scenarioState,browser);
		currentPageState.assertCurrentPageIs(Page.PIPELINE_WIZARD_ARTIFACTS_LISTING_PAGE);	
	}

	
	
	@com.thoughtworks.gauge.Step("Enter artifact detail <listingId> <source> <destination> <type>")
	public void enterArtifactDetail(Integer listingId,String source, String destination, String type)
	{
		
		String listingIdString = "";
		if(!artifactExists(listingId)) createEmptyArtifact(listingId, type);
		listingId = listingId -1 ;
		
		listingIdString = (listingId == 0) ? "" : "[" + listingId + "]" ;
		
		browser.textbox("job[artifactConfigs][][source]" + listingIdString).setValue(source);
		browser.textbox("job[artifactConfigs][][destination]" + listingIdString).setValue(destination);
	}
	
	
	private void createEmptyArtifact(Integer listingId, String artifactType) {
		browser.select("select_artifact_type").choose(artifactType);
		browser.link("Add").click(); 
		
	}

	private boolean artifactExists(Integer listingId) {
		
		listingId--;
		return ( (listingId == 0) ? browser.textbox("job[artifactConfigs][][source]").exists() : browser.textbox("job[artifactConfigs][][source][" + listingId + "]").exists());
		

	}

	
	@com.thoughtworks.gauge.Step("Click save - Already on Artifacts Listing Page")
	public void clickSave() throws Exception {
		super.clickSave();
	}

	@com.thoughtworks.gauge.Step("Verify saved successfully - Already on Artifacts Listing Page")
	public void verifySavedSuccessfully() {
		super.verifySavedSuccessfully();
	}

	@com.thoughtworks.gauge.Step("Assert mD5 - Already On Artifacts Listing Page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }
	
	  @com.thoughtworks.gauge.Step("Go to environment variables page - Already On Artifacts Listing Page")
	public void goToEnvironmentVariablesPage() throws Exception {
	        super.goToEnvironmentVariablesPage();
	    }

}
