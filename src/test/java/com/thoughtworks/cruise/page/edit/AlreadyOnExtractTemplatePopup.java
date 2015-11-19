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

package com.thoughtworks.cruise.page.edit;

import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import static junit.framework.Assert.assertEquals;

public class AlreadyOnExtractTemplatePopup extends AlreadyOnSomeCreateTemplatePopup {

    public AlreadyOnExtractTemplatePopup(Browser browser, ScenarioState state, CurrentPageState pageState) {
        super(browser, state, pageState);
    }

    protected ElementStub elementPipelinesSelectBox() {
        return browser.select("pipeline[pipelineNames]");
    }

    @Override
    public void save() throws Exception {
        // TODO Auto-generated method stub
        super.save();
    }

    @Override
    public void verifyErrorMessage(String expectedMessage) throws Exception {
        // TODO Auto-generated method stub
        super.verifyErrorMessage(expectedMessage);
    }

    @com.thoughtworks.gauge.Step("Enter template name <templateName> - Already on extract template popup")
	@Override
    public void enterTemplateName(String templateName) throws Exception {
        // TODO Auto-generated method stub
        super.enterTemplateName(templateName);
    }

    @com.thoughtworks.gauge.Step("Save for success - Already on extract template popup")
	@Override
    public void saveForSuccess() throws Exception {
        // TODO Auto-generated method stub
        super.saveForSuccess();
    }

    @Override
    public void selectExtractTemplateFromAExistingPipeline() throws Exception {
        // TODO Auto-generated method stub
        super.selectExtractTemplateFromAExistingPipeline();
    }

    @Override
    public void verifyTemplateCanBeExtractedOnlyFromPipelines(String pipelineNamesAsString) throws Exception {
        // TODO Auto-generated method stub
        super.verifyTemplateCanBeExtractedOnlyFromPipelines(pipelineNamesAsString);
    }

    @Override
    public void selectPipeline(String pipelineName) throws Exception {
        // TODO Auto-generated method stub
        super.selectPipeline(pipelineName);
    }

    @com.thoughtworks.gauge.Step("Verify extract template checkbox is disabled - Already on extract template popup")
	@Override
    public void verifyExtractTemplateCheckboxIsDisabled() throws Exception {
        // TODO Auto-generated method stub
        super.verifyExtractTemplateCheckboxIsDisabled();
    }

    @com.thoughtworks.gauge.Step("Verify extract template checkbox is checked")
	@Override
    public void verifyExtractTemplateCheckboxIsChecked() throws Exception {
        // TODO Auto-generated method stub
        super.verifyExtractTemplateCheckboxIsChecked();
    }

    @com.thoughtworks.gauge.Step("Verify pipeline selection dropdown has value <pipelineName>")
	@Override
    public void verifyPipelineSelectionDropdownHasValue(String pipelineName) throws Exception {
        // TODO Auto-generated method stub
        super.verifyPipelineSelectionDropdownHasValue(pipelineName);
    }

    @com.thoughtworks.gauge.Step("Verify pipeline selection dropdown is disabled")
	@Override
    public void verifyPipelineSelectionDropdownIsDisabled() throws Exception {
        // TODO Auto-generated method stub
        super.verifyPipelineSelectionDropdownIsDisabled();
    }

    @Override
    public void verifyExtractTemplateCheckboxIsNotChecked() throws Exception {
        // TODO Auto-generated method stub
        super.verifyExtractTemplateCheckboxIsNotChecked();
    }
    
    @com.thoughtworks.gauge.Step("Assert mD5 - Already on extract template popup")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }
    
    @com.thoughtworks.gauge.Step("Close popup - Already on extract template popup")
	public void closePopup() throws Exception {
    	browser.link("Close window").click();
    }
}
