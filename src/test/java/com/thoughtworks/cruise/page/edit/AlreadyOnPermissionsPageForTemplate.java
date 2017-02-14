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

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.page.CruisePage;
import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AlreadyOnPermissionsPageForTemplate extends CruisePage {

	private Browser browser;

	@Autowired
	private TwistScenarioDataStore scenarioStore;
	
	@Override
	protected String url() {
		throw new RuntimeException("Should not be called");
	}

	public AlreadyOnPermissionsPageForTemplate(ScenarioState scenarioState, Browser browser, CurrentPageState currentPageState) {
		super(scenarioState, true, browser);
		this.browser = browser;
		currentPageState.assertCurrentPageIs(Page.PERMISSIONS_PAGE_FOR_TEMPLATE);
	}
	
	@com.thoughtworks.gauge.Step("Enter user <zeroBasedUserIndex> as <userName>")
	public void enterUserAs(int zeroBasedUserIndex, String userName) {
		ElementStub userScope = scope("user");
		if (!getUserNameTextbox(userScope, zeroBasedUserIndex).exists()) {
			addNewRow("USER");
		}
		ElementStub userNameTextbox = getUserNameTextbox(userScope, zeroBasedUserIndex);
		userNameTextbox.setValue(userName);
	}

	private ElementStub scope(String type) {
		ElementStub userScope = browser.div(Regex.wholeWord(type + "_permissions_for_template"));
		return userScope;
	}
	
	private ElementStub getUserNameTextbox(ElementStub scope, int index) {
		String idx = index == 0 ? "" : String.format("[%s]", index); 
		return browser.textbox(String.format("template[authorization][][name]%s", idx)).in(scope);
	}
	
	private void addNewRow(String type) {
		browser.link(type + "_add_users_and_roles").click();
	}

	@com.thoughtworks.gauge.Step("Click save - Already On Permissions Page For Template")
	public void clickSave() {
		browser.submit("SAVE").click();
	}

	@com.thoughtworks.gauge.Step("Click reset - Already On Permissions Page For Template")
	public void clickReset() throws Exception {
		browser.link("reset_form").click();
	}

	@com.thoughtworks.gauge.Step("Verify that the message <message> shows up - Already On Permissions Page For Template")
	public void verifyThatTheMessageShowsUp(String message) {
		 assertThat(browser.div(message).exists(), is(true));
	}

	@com.thoughtworks.gauge.Step("Verify that user <userName> shows up in user permissions")
	public void verifyThatUserShowsUpInUserPermissions(String userName) {
		ElementStub userScope = scope("user");
		assertThat((browser.row(String.format("USER_%s", userName))).in(userScope).exists(), is(true));
	}

	@com.thoughtworks.gauge.Step("Verify that user <userName> does not show up in user permissions")
	public void verifyThatUserDoesNotShowUpInUserPermissions(String userName) {
		ElementStub userScope = scope("user");
		assertThat((browser.row(String.format("USER_%s", userName))).in(userScope).exists(), is(false));
	}
	
	@com.thoughtworks.gauge.Step("Delete user <userName> - Already On Permissions Page For Template")
	public void deleteUser(String userName) throws Exception {
		deleteRow("USER", userName);
	}
	
	private void deleteRow(String type, String userName) {
		ElementStub row = browser.row(String.format("%s_%s", type, userName));
		ElementStub deleteLink = browser.span(Regex.wholeWord("delete_parent")).in(row);
		deleteLink.click();
	}
	
	@com.thoughtworks.gauge.Step("Assert mD5 - Already On Permissions Page For Template")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }

	@com.thoughtworks.gauge.Step("Switch allow pipeline group admin view access to templates flag - Already On Permissions Page For Template")
	public void switchAllowViewAccessFlag() throws Exception {
		browser.checkbox(new Object[]{"template[allowGroupAdmins]"}).click();
	}
}
