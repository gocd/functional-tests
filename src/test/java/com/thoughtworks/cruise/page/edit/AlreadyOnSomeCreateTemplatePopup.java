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

import com.thoughtworks.cruise.page.CruisePage;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.CommaSeparatedParams;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public abstract class AlreadyOnSomeCreateTemplatePopup extends CruisePage {
    private CurrentPageState currentPageState;

    public AlreadyOnSomeCreateTemplatePopup(Browser browser, ScenarioState state, CurrentPageState pageState) {
        super(state, true, browser);
        this.currentPageState = pageState;
        pageState.assertCurrentPageIs(Page.NEW_TEMPLATE_POPUP);
    }
    
    @Override
    protected String url() {
        return null;
    }

    public void save() throws Exception {
        browser.submit("SAVE").click();
    }

    public void verifyErrorMessage(String expectedMessage) throws Exception {
        ElementStub errorDiv = browser.div("form_error");
        Assert.assertThat(errorDiv.exists(), Is.is(true));
        Assert.assertThat(errorDiv.getText(), Is.is(expectedMessage));
    }

    public void enterTemplateName(String templateName) throws Exception {
        browser.textbox("pipeline[template][name]").setValue(templateName);
    }
    
    public void saveForSuccess() throws Exception {
        save();
        currentPageState.currentPageIs(Page.EDIT_TEMPLATE_PAGE);
    }

    public void selectExtractTemplateFromAExistingPipeline() throws Exception {
        browser.checkbox("pipeline[useExistingPipeline]").click();
    
    }

    public void verifyTemplateCanBeExtractedOnlyFromPipelines(String pipelineNamesAsString) throws Exception {
        List<String> pipelineNames = new ArrayList<String>();
        for (String param : new CommaSeparatedParams(pipelineNamesAsString)) {
            pipelineNames.add(scenarioState.pipelineNamed(param));
        }
        List<ElementStub> options = browserWrapper.collectIn("option", "", elementPipelinesSelectBox());
        assertThat(pipelineNames.size(), Is.is(options.size()));
        for (int i = 0; i < options.size(); i++) {
            ElementStub option = options.get(i);
        }
    }
    
    protected abstract ElementStub elementPipelinesSelectBox();

    public void selectPipeline(String pipelineName) throws Exception {
        elementPipelinesSelectBox().choose(scenarioState.pipelineNamed(pipelineName));
    }

    public void verifyExtractTemplateCheckboxIsDisabled() throws Exception {
        assertThat(browser.checkbox("pipeline_useExistingPipeline").fetch("disabled"), Is.is("true"));
    }
    
    public void verifyExtractTemplateCheckboxIsChecked() throws Exception {
        assertTrue(browser.checkbox("pipeline_useExistingPipeline").checked());
    }

    public void verifyPipelineSelectionDropdownHasValue(String pipelineName) throws Exception {
        assertThat(elementPipelinesSelectBox().value(), Is.is(scenarioState.pipelineNamed(pipelineName)));
    }

    public void verifyPipelineSelectionDropdownIsDisabled() throws Exception {
        assertThat(elementPipelinesSelectBox().fetch("disabled"), Is.is("true"));
    }

    public void verifyExtractTemplateCheckboxIsNotChecked() throws Exception {
        assertFalse(browser.checkbox("pipeline_useExistingPipeline").checked());
    }
    
}
