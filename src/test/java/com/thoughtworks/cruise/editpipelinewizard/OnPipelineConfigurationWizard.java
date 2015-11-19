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

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.page.CruisePage;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;

public class OnPipelineConfigurationWizard extends CruisePage {
	private final CurrentPageState currentPageState;
	
	public OnPipelineConfigurationWizard(ScenarioState scenarioState, Browser browser, CurrentPageState currentPageState){
		super(scenarioState,browser);
		this.currentPageState = currentPageState;
	}

	@Override
	protected String url() {
		return Urls.urlFor("/go/admin/pipelines/" + scenarioState.currentPipeline() + "/general");		
	}


	@com.thoughtworks.gauge.Step("On Pipeline Configuration wizard")
	public void goToPipelineConfigurationWizard() throws Exception {
       navigateToURL();
	}

	@com.thoughtworks.gauge.Step("Click on pipeline <pipelineName> for editing")
	public void clickOnPipelineForEditing(String pipelineName) throws Exception {
		browser.expectConfirm("/Are you sure you want to navigate away from this page/", true);
	   browser.navigateTo(Urls.urlFor("/go/admin/pipelines/" + scenarioState.pipelineNamed(pipelineName) + "/general"));	
	   currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_GENERAL_PAGE);
	}

}
