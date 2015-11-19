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
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

import static junit.framework.Assert.assertEquals;

// JUnit Assert framework can be used for verification

public class AlreadyOnJobCustomTabsPage extends AlreadyOnEditPipelineWizardPage {
    public AlreadyOnJobCustomTabsPage(Browser browser, CurrentPageState currentPageState, ScenarioState scenarioState) {
        super(currentPageState, scenarioState, browser);
        currentPageState.assertCurrentPageIs(Page.PIPELINE_WIZARD_JOB_TABS_LISTING_PAGE);
    }

    @com.thoughtworks.gauge.Step("Click save - Already on Job Custom Tabs Page")
	public void clickSave() throws Exception {
        super.clickSave();
    }

    @com.thoughtworks.gauge.Step("Verify saved successfully - Already on Job Custom Tabs Page")
	public void verifySavedSuccessfully() {
        super.verifySavedSuccessfully();
    }

    @com.thoughtworks.gauge.Step("Enter tab name <tabName> with path <path>")
	public void enterTabNameWithPath(String tabName, String path) throws Exception {
        browser.textbox("job[tabs][][name]").setValue(tabName);
        browser.textbox("job[tabs][][path]").setValue(path);
    }

    @com.thoughtworks.gauge.Step("Verify save failed - Already on Job Custom Tabs Page")
	public void verifySaveFailed() {
        super.verifySaveFailed();
    }

    @com.thoughtworks.gauge.Step("Verify error message <message> on name on tab <index>")
	public void verifyErrorMessageOnNameOnTab(String message, Integer index) throws Exception {
        foo(message, index);
    }

    private void foo(String message, Integer index) {
        String textIndex = index == 0 ? "" : String.format("[%d]", index);
        ElementStub cell = browser.textbox(String.format("job[tabs][][name]", textIndex)).parentNode().parentNode();
        String actualError = browser.div("name_value_error").in(cell).getText();
        Assert.assertThat(actualError, Is.is(message));
    }

    @com.thoughtworks.gauge.Step("Enter new tab name <tabName> with path <path>")
	public void enterNewTabNameWithPath(String tabName, String path) throws Exception {
        browser.textbox("job[tabs][][name]").in(browser.row("dirtyform")).setValue(tabName);
        browser.textbox("job[tabs][][path]").in(browser.row("dirtyform")).setValue(path);
    }
    
    @com.thoughtworks.gauge.Step("Assert mD5 - Already on Job Custom Tabs Page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }

}
