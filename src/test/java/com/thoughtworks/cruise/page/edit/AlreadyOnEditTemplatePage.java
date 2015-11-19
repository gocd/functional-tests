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
import net.sf.sahi.client.Browser;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class AlreadyOnEditTemplatePage extends CruisePage {
    
    private final CurrentPageState currentPageState;

    public AlreadyOnEditTemplatePage(ScenarioState scenarioState, Browser browser, CurrentPageState currentPageState) {
        super(scenarioState, true, browser);
        this.browser = browser;
        this.currentPageState = currentPageState;
        currentPageState.assertCurrentPageIs(Page.EDIT_TEMPLATE_PAGE); 
    }

    @Override
    protected String url() {
        return null;
    }

    @com.thoughtworks.gauge.Step("Verify template being edited is <templateName>")
	public void verifyTemplateBeingEditedIs(String templateName) throws Exception {
        Assert.assertThat(browser.div("pipeline_header").getText(), Is.is(templateName));
    }

    @com.thoughtworks.gauge.Step("Open stages tab")
	public void openStagesTab() throws Exception {
        browser.link("Stages").click();
        currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_STAGES_PAGE);
    }

	@com.thoughtworks.gauge.Step("Verify template name <templateName> cannot be edited")
	public void verifyTemplateNameCannotBeEdited(String templateName) throws Exception {
		Assert.assertThat(browser.textbox("template_name").fetch("disabled"), Is.is("true"));
	}
}
