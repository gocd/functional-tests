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

import com.thoughtworks.cruise.materials.TfsServer;
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

public class AlreadyOnTfsMaterialCreationPopup extends AlreadyOnEditMaterialPopup {

	public AlreadyOnTfsMaterialCreationPopup(CurrentPageState currentPageState, ScenarioState scenarioState, Browser browser) {
		super(currentPageState, scenarioState, browser);
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
	}

	@com.thoughtworks.gauge.Step("Enter material name <materialName> - Already on Tfs Material Creation Popup")
	public void enterMaterialName(String materialName) throws Exception {
		super.enterMaterialName(materialName);
	}
	
	@com.thoughtworks.gauge.Step("Enter url <collection> - Already on tfs material creation popup")
	public void enterUrl(String collection) throws Exception {
        super.enterUrl(new TfsServer().getUrl() + collection);
    }

	@com.thoughtworks.gauge.Step("Enter username <userName> - Already on Tfs Material Creation Popup")
	public void enterUsername(String userName) throws Exception {
		elementUsername().setValue(userName);
	}
	
	@com.thoughtworks.gauge.Step("Enter domain <domain>")
	public void enterDomain(String domain) throws Exception {
		elementDomain().setValue(domain);
	}

	private ElementStub elementUsername() {
		return browser.textbox("material[username]");
	}

	@com.thoughtworks.gauge.Step("Enter password <password> - Already on Tfs Material Creation Popup")
	public void enterPassword(String password) throws Exception {
		elementPassword().setValue(password);
	}

	private ElementStub elementPassword() {
		return browser.password("material[password]");
	}
	
	private ElementStub elementDomain() {
		return browser.textbox("material[domain]");
	}

	public void verifyFieldIsAndContains(String fieldName, String state,
			String value) throws Exception {
		super.verifyFieldIsAndContains(fieldName, state, value);
	}

	public void checkChangePasswordFor(String fieldName) throws Exception {
		super.checkChangePasswordFor(fieldName);
	}

	@com.thoughtworks.gauge.Step("Enter destination directory <destinationDirectory> - Already on Tfs Material Creation Popup")
	public void enterDestinationDirectory(String destinationDirectory)
			throws Exception {
		super.enterDestinationDirectory(destinationDirectory);
	}

	public void enterBlackListDetails(String blackList) throws Exception {
		super.enterBlackList(blackList);
	}

	@com.thoughtworks.gauge.Step("Click save - Already on tfs material creation popup")
	public void clickSave() throws Exception {
		browser.submit("primary finish submit MB_focusable").click();
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE);
	}

	@com.thoughtworks.gauge.Step("Click check connection")
	public void clickCheckConnection() throws Exception {
		browser.byId("check_connection_tfs").click();
	}
	
	@com.thoughtworks.gauge.Step("Verify message <message>")
	public void verifyMessage(final String message) throws Exception {
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
			@Override
			public boolean call() throws Exception {
				return browser.div("vcsconnection-message_tfs").text().contains(message);
			}
		});
	}
	
	@com.thoughtworks.gauge.Step("Make autoupdate to be <shouldCheck> - Already on Tfs Material Creation Popup")
	public void makeAutoupdateToBe(Boolean shouldCheck) throws Exception {
		super.makeAutoupdateToBe(shouldCheck);
	}

	public void verifyMaterialNameIs(String materialName) throws Exception {
		super.verifyMaterialNameIs(materialName);
	}

	public void verifyAutoupdateIs(Boolean selection) throws Exception {
		super.verifyAutoupdateIs(selection);
	}

	public void verifyUsernameIs(String username) throws Exception {
		assertThat(elementUsername().getText(), is(username));
	}

	public void verifyPasswordIs(String password) throws Exception {
		assertThat(elementPassword().getText(), is(password));
	}

	public void verifyDestinationDirectoryIs(String dest) throws Exception {
		super.verifyDestinationDirectoryIs(dest);
	}

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

    @com.thoughtworks.gauge.Step("Enter project path as <projectPath>")
	public void enterProjectPathAs(String projectPath) throws Exception {
        browser.textbox("material[projectPath]").setValue(projectPath);
    }
    
    public void enterDomainAs(String domain) {
    	browser.textbox("material[domain]").setValue(domain);
    }
}
