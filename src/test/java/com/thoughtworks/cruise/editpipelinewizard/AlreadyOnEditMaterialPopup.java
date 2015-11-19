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
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AlreadyOnEditMaterialPopup extends AlreadyOnEditPipelineWizardPage {

    public AlreadyOnEditMaterialPopup(CurrentPageState currentPageState, ScenarioState scenarioState, Browser browser) {
        super(currentPageState, scenarioState, browser);
    }

    public void enterMaterialName(String aMaterialName) throws Exception {
    	super.enterInTextBox("material[materialName]", aMaterialName);
    }

    private ElementStub elementMaterialName() {
        return browser.textbox("material[materialName]");
    }

    public void verifyMaterialNameIs(String materialName) throws Exception {
        assertThat(elementMaterialName().getText(), is(materialName));
    }

    public void enterUrl(String url) throws Exception {
    	super.enterInTextBox("material[url]", url);
    }

    private ElementStub elementUrl() {
        return browser.textbox("material[url]");
    }

    public void enterDestinationDirectory(String destinationDirectory) throws Exception {
    	super.enterInTextBox("material[folder]", destinationDirectory);
    }

    private ElementStub elementDesitnationDirectory() {
        return browser.textbox("material[folder]");
    }

    public void enterBlackList(String blackList) throws Exception {
    	elementBlackList().setValue(blackList);
    }

    private ElementStub elementBlackList() {
        return browser.textarea("material[filterAsString]");
    }

    @com.thoughtworks.gauge.Step("Make autoupdate to be <shouldCheck> - Already on edit material popup")
	public void makeAutoupdateToBe(Boolean shouldCheck) throws Exception {
        ElementStub shouldAutoUpdate = elementShouldAutoUpdate();
        if (shouldCheck(shouldCheck, shouldAutoUpdate) || shouldUncheck(shouldCheck, shouldAutoUpdate)) {
            shouldAutoUpdate.click();    
        }
    }

    protected boolean shouldUncheck(Boolean shouldCheck, ElementStub shouldAutoUpdate) {
        return !shouldCheck && shouldAutoUpdate.checked();
    }

    protected boolean shouldCheck(Boolean shouldCheck, ElementStub shouldAutoUpdate) {
        return shouldCheck && !shouldAutoUpdate.checked();
    }

    private ElementStub elementShouldAutoUpdate() {
        return browser.checkbox("material[autoUpdate]");
    }

    public void verifyUrlIs(String url) throws Exception {
        assertThat(elementUrl().getText(), is(url));
    }

    public void verifyDestinationDirectoryIs(String dest) throws Exception {
        assertThat(elementDesitnationDirectory().getText(), is(dest));
    }

    public void verifyAutoupdateIs(Boolean selection) throws Exception {
        assertThat(elementShouldAutoUpdate().checked(), is(selection));
    }

    public void verifyBlackListIs(String blackList) throws Exception {
        assertThat(elementBlackList().getText(), is(blackList));
    }

    protected void setValueOf(String name, String value) {
        browser.textbox(name).setValue(value);
    }

    public void usingData(String attributes) {
        String[] nameValues = attributes.split(",");
        for (String nameValue : nameValues) {
            String[] nameAndValue = nameValue.split("=");
            setValueOf(String.format("material[%s]", nameAndValue[0]), nameAndValue[1]);
        }
    }

    public void close() {
        browser.byId("MB_close");
        currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE);
    }

    @com.thoughtworks.gauge.Step("Save changes - Already on edit material popup")
	public void saveChanges(){
    	browser.submit("SAVE");
    	currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE);
    }
	public void verifyFieldIsAndContains(String fieldName, String state,
			String value) throws Exception {
				ElementStub passwordField = browser.password(String.format("material[%s]",fieldName));
				assertThat(passwordField.getText(), is(value));
				assertThat(browserWrapper.isEnabled(passwordField), is(!state.equals("disabled")));
			}

	public void checkChangePasswordFor(String fieldName) throws Exception {
		browser.check(browser.checkbox(String.format("material[%sChanged]",fieldName)));
	}

	public void checkConnectivityShouldBeSuccessful() {
		checkConnectivityWithMessage("ok_message");
	}

	void checkConnectivityWithMessage(final String className) {
		browser.button("check_connection_hg").click();
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			
			@Override
			public boolean call() throws Exception {
				return browser.byId("vcsconnection-message_hg").fetch("className").contains(className);
			}
		});
	}

	public void checkConnectivityShouldFail() {
		checkConnectivityWithMessage("error_message");
	}
}