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

import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// JUnit Assert framework can be used for verification

public class AlreadyOnSubversionMaterialAddPopup extends
		AlreadyOnEditMaterialPopup {

	public AlreadyOnSubversionMaterialAddPopup(
			CurrentPageState currentPageState, ScenarioState scenarioState,
			Browser browser) {
		super(currentPageState, scenarioState, browser);
		currentPageState
				.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
	}

	@com.thoughtworks.gauge.Step("Enter username <userName>")
	public void enterUsername(String userName) throws Exception {
		elementUsername().setValue(userName);
	}

	private ElementStub elementUsername() {
		return browser.textbox("material[userName]");
	}

	@com.thoughtworks.gauge.Step("Enter password <password>")
	public void enterPassword(String password) throws Exception {
		elementPassword().setValue(password);
	}

	private ElementStub elementPassword() {
		return browser.password("material[password]");
	}

	@com.thoughtworks.gauge.Step("Verify <fieldName> field is <state> and contains <value> - Already on Subversion Material Add Popup")
	public void verifyFieldIsAndContains(String fieldName, String state,
			String value) throws Exception {
		super.verifyFieldIsAndContains(fieldName, state, value);
	}

	public void checkChangePasswordFor(String fieldName) throws Exception {
		super.checkChangePasswordFor(fieldName);
	}

	@com.thoughtworks.gauge.Step("Click save - Already on Subversion Material Add Popup")
	public void clickSave() throws Exception {
		browser.submit("primary finish submit MB_focusable").click();
		currentPageState
				.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE);

	}

	private ElementStub elementShouldCheckExternals() {
		return browser.checkbox("material[checkExternals]");
	}

	@com.thoughtworks.gauge.Step("Make checkexternals to be <shouldCheck>")
	public void makeCheckexternalsToBe(Boolean shouldCheck) throws Exception {
		ElementStub shouldAutoUpdate = elementShouldCheckExternals();
		if (shouldCheck(shouldCheck, shouldAutoUpdate)
				|| shouldUncheck(shouldCheck, shouldAutoUpdate)) {
			shouldAutoUpdate.click();
		}
	}
		
	
	

	@com.thoughtworks.gauge.Step("Verify username is <userName> - Already on Subversion Material Add Popup")
	public void verifyUsernameIs(String userName) throws Exception {
		assertThat(elementUsername().getText(), is(userName));
	}

	public void verifyPasswordIs(String password) throws Exception {
		assertThat(elementPassword().getText(), is(password));
	}

	@com.thoughtworks.gauge.Step("Verify checkexternals is <selection>")
	public void verifyCheckexternalsIs(Boolean selection) throws Exception {
		assertThat(elementShouldCheckExternals().checked(), is(selection));
	}

	@com.thoughtworks.gauge.Step("Enter material name <materialName>")
	public void enterMaterialName(String materialName) throws Exception {
		super.enterMaterialName(materialName);
	}

	@com.thoughtworks.gauge.Step("Enter url <url>")
	public void enterUrl(String url) throws Exception {
		super.enterUrl(url);
	}

	@com.thoughtworks.gauge.Step("Enter destination directory <dest>")
	public void enterDestinationDirectory(String dest) throws Exception {
		super.enterDestinationDirectory(dest);
	}

	@com.thoughtworks.gauge.Step("Make autoupdate to be <shouldCheck> - Already on Subversion Material Add Popup")
	public void makeAutoupdateToBe(Boolean shouldCheck) throws Exception {
		super.makeAutoupdateToBe(shouldCheck);
	}

	@com.thoughtworks.gauge.Step("Enter black list <blackList> - Already on Subversion Material Add Popup")
	public void enterBlackList(String blackList) throws Exception {
		super.enterBlackList(blackList);
	}

	@com.thoughtworks.gauge.Step("Verify material name is <materialName> - Already on Subversion Material Add Popup")
	public void verifyMaterialNameIs(String materialName) throws Exception {
		super.verifyMaterialNameIs(materialName);
	}

	@com.thoughtworks.gauge.Step("Verify url is <url> - Already on Subversion Material Add Popup")
	public void verifyUrlIs(String url) throws Exception {
		super.verifyUrlIs(url);
	}

	@com.thoughtworks.gauge.Step("Verify destination directory is <dest> - Already on Subversion Material Add Popup")
	public void verifyDestinationDirectoryIs(String dest) throws Exception {
		super.verifyDestinationDirectoryIs(dest);
	}

	@com.thoughtworks.gauge.Step("Verify autoupdate is <selection> - Already on Subversion Material Add Popup")
	public void verifyAutoupdateIs(Boolean selection) throws Exception {
		super.verifyAutoupdateIs(selection);
	}

	@com.thoughtworks.gauge.Step("Verify black list is <blackList> - Already on Subversion Material Add Popup")
	public void verifyBlackListIs(String blackList) throws Exception {
		super.verifyBlackListIs(blackList);
	}
	
	private ElementStub elementShouldPollForChanges()
	{
		return browser.checkbox("material[autoUpdate]");
	}
	
	@com.thoughtworks.gauge.Step("Set poll for changes as <check>")
	public void setPollForChangesAs(Boolean check) throws Exception {
	ElementStub shouldPollForChanges = elementShouldPollForChanges();
		if (shouldCheck(check, shouldPollForChanges)
				|| shouldUncheck(check, shouldPollForChanges)) {
			shouldPollForChanges.click();
		}
	}	

}
