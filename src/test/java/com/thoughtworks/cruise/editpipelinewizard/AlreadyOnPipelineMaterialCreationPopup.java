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

import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.RuntimeVariableSubstituter;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AlreadyOnPipelineMaterialCreationPopup extends AlreadyOnEditMaterialPopup {

	public AlreadyOnPipelineMaterialCreationPopup(CurrentPageState currentPageState, ScenarioState scenarioState, Browser browser) {
		super(currentPageState, scenarioState, browser);
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
	}

	@com.thoughtworks.gauge.Step("Enter material name <materialName> - Already on pipeline material creation popup")
	public void enterMaterialName(String materialName) throws Exception {
		super.enterMaterialName(materialName);
	}

	@com.thoughtworks.gauge.Step("Enter pipeline <pipelineName> and stage <stageName>")
	public void enterPipelineAndStage(String pipelineName, String stageName) throws Exception {
	    elementPipelineNameStageName().setValue(concatenate(scenarioState.pipelineNamed(pipelineName), stageName));
	}

    private String concatenate(String pipelineName, String stageName) {
        return  pipelineName + " [" + stageName +"]";
    }
	
	@com.thoughtworks.gauge.Step("Enter to get suggestion <string>")
	public void enterToGetSuggestion(String string) throws Exception {
		elementPipelineNameStageName().setValue(string);
	}

    private ElementStub elementPipelineNameStageName() {
        return browser.textbox("material[pipelineStageName]");
    }

	@com.thoughtworks.gauge.Step("Click save - Already on pipeline material creation popup")
	public void clickSave() throws Exception {
		browser.submit("primary finish submit MB_focusable").click();
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE);
	}

    private String replaceRuntimeVariables(final String value) {
        return new RuntimeVariableSubstituter(new RuntimeVariableSubstituter.Replacer() {
            @Override
            public String replacementFor(String variable) {
                return scenarioState.replacementFor(variable);
            }
        }).replaceRuntimeVariables(value);

    }

    @com.thoughtworks.gauge.Step("Verify suggestion <pipelineName> <stageName> shows up")
	public void verifySuggestionShowsUp(String pipelineName, String stageName) throws Exception {
        new AutoCompleteSuggestions(browser, browserWrapper).autoCompletesShouldShowSuggestion(suggestion(pipelineName, stageName));
    }

    @com.thoughtworks.gauge.Step("Select <pipelineName> <stageName>")
	public void select(String pipelineName, String stageName) throws Exception {
        new AutoCompleteSuggestions(browser, browserWrapper).selectFirstOption();
    }

    private String suggestion(String pipelineName, String stageName) {
        return concatenate(replaceRuntimeVariables(pipelineName), stageName);
    }

    @com.thoughtworks.gauge.Step("Verify pipeline and stage are <pipelineName> <stageName>")
	public void verifyPipelineAndStageAre(String pipelineName, String stageName) throws Exception {
        assertThat(elementPipelineNameStageName().getText(), is(suggestion(pipelineName, stageName)));
    }
}
