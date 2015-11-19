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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AlreadyOnPerforceMaterialCreationPopup extends
		AlreadyOnEditMaterialPopup {

	public AlreadyOnPerforceMaterialCreationPopup(
			CurrentPageState currentPageState, ScenarioState scenarioState,
			Browser browser) {
		super(currentPageState, scenarioState, browser);
		currentPageState
				.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
	}

	@com.thoughtworks.gauge.Step("Enter material name <materialName> - Already On Perforce Material Creation Popup")
	public void enterMaterialName(String materialName) throws Exception {
		super.enterMaterialName(materialName);
	}

	@com.thoughtworks.gauge.Step("Enter server and port <serverAndPort>")
	public void enterServerAndPort(String serverAndPort) throws Exception {
		elementServerAndPort().setValue(serverAndPort);
	}

	private ElementStub elementServerAndPort() {
		return browser.textbox("material[serverAndPort]");
	}

	@com.thoughtworks.gauge.Step("Enter username <userName> - Already On Perforce Material Creation Popup")
	public void enterUsername(String userName) throws Exception {
		elementUsername().setValue(userName);
	}

	private ElementStub elementUsername() {
		return browser.textbox("material[userName]");
	}

	@com.thoughtworks.gauge.Step("Enter password <perforcePassword> - Already On Perforce Material Creation Popup")
	public void enterPassword(String perforcePassword) throws Exception {
		elementPassword().setValue(perforcePassword);
	}

	private ElementStub elementPassword() {
		return browser.password("material[password]");
	}

	@com.thoughtworks.gauge.Step("Verify <fieldName> field is <state> and contains <value>")
	public void verifyFieldIsAndContains(String fieldName, String state,
			String value) throws Exception {
		super.verifyFieldIsAndContains(fieldName, state, value);
	}

	public void checkChangePasswordFor(String fieldName) throws Exception {
		super.checkChangePasswordFor(fieldName);
	}

	@com.thoughtworks.gauge.Step("Enter view details <view>")
	public void enterViewDetails(String view) throws Exception {
		elementViewDetails().setValue(view);
	}

	private ElementStub elementViewDetails() {
		return browser.textarea("material[view]");
	}

	@com.thoughtworks.gauge.Step("Set use tickets flag <useTickets>")
	public void setUseTicketsFlag(Boolean useTickets) throws Exception {
		elementShouldUseTickets().click();
	}

	private ElementStub elementShouldUseTickets() {
		return browser.checkbox("material[useTickets]");
	}

	@com.thoughtworks.gauge.Step("Enter destination directory <destinationDirectory> - Already On Perforce Material Creation Popup")
	public void enterDestinationDirectory(String destinationDirectory)
			throws Exception {
		super.enterDestinationDirectory(destinationDirectory);
	}

	@com.thoughtworks.gauge.Step("Enter black list details <blackList>")
	public void enterBlackListDetails(String blackList) throws Exception {
		super.enterBlackList(blackList);
	}

	@com.thoughtworks.gauge.Step("Click save - Already On Perforce Material Creation Popup")
	public void clickSave() throws Exception {
		browser.submit("primary finish submit MB_focusable").click();
		currentPageState
				.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE);
	}

	@com.thoughtworks.gauge.Step("Make autoupdate to be <shouldCheck> - Already On Perforce Material Creation Popup")
	public void makeAutoupdateToBe(Boolean shouldCheck) throws Exception {
		super.makeAutoupdateToBe(shouldCheck);
	}

	@com.thoughtworks.gauge.Step("Verify material name is <materialName> - Already On Perforce Material Creation Popup")
	public void verifyMaterialNameIs(String materialName) throws Exception {
		super.verifyMaterialNameIs(materialName);
	}

	@com.thoughtworks.gauge.Step("Verify server and port is <serverAndPort>")
	public void verifyServerAndPortIs(String serverAndPort) throws Exception {
		assertThat(elementServerAndPort().getText(), is(serverAndPort));
	}

	@com.thoughtworks.gauge.Step("Verify autoupdate is <selection> - Already On Perforce Material Creation Popup")
	public void verifyAutoupdateIs(Boolean selection) throws Exception {
		super.verifyAutoupdateIs(selection);
	}

	@com.thoughtworks.gauge.Step("Verify username is <username>")
	public void verifyUsernameIs(String username) throws Exception {
		assertThat(elementUsername().getText(), is(username));
	}

	public void verifyPasswordIs(String password) throws Exception {
		assertThat(elementPassword().getText(), is(password));
	}

	@com.thoughtworks.gauge.Step("Verify view details is <viewDetails>")
	public void verifyViewDetailsIs(String viewDetails) throws Exception {
		assertThat(elementViewDetails().getText(), is(viewDetails));
	}

	@com.thoughtworks.gauge.Step("Verify use tickets is <isChecked>")
	public void verifyUseTicketsIs(Boolean isChecked) throws Exception {
		assertThat(elementShouldUseTickets().checked(), is(isChecked));
	}

	@com.thoughtworks.gauge.Step("Verify destination directory is <dest> - Already On Perforce Material Creation Popup")
	public void verifyDestinationDirectoryIs(String dest) throws Exception {
		super.verifyDestinationDirectoryIs(dest);
	}

	@com.thoughtworks.gauge.Step("Verify black list details is <blackList>")
	public void verifyBlackListDetailsIs(String blackList) throws Exception {
		super.verifyBlackListIs(blackList);
	}

	@Override
	public void setValueOf(String name, String value) {
		if ("material[view]".equals(name)) {
			browser.textarea(name).setValue(value);
		} else {
			super.setValueOf(name, value);
		}
	}
	
	@com.thoughtworks.gauge.Step("Check connectivity should be successful")
	@Override
	public void checkConnectivityShouldBeSuccessful() {
		checkConnectivityWithMessage("ok_message");
	}

	@Override
	void checkConnectivityWithMessage(final String className) {
		browser.button("check_connection_p4").click();
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			
			@Override
			public boolean call() throws Exception {
				return browser.byId("vcsconnection-message_p4").fetch("className").contains(className);
			}
		});
	}
}
