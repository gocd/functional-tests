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
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AlreadyOnPipelineGroupEditPage extends CruisePage {

	@Override
	protected String url() {
		throw new RuntimeException("Should not be called");
	}

	public AlreadyOnPipelineGroupEditPage(ScenarioState scenarioState, Browser browser, CurrentPageState currentPageState) {
		super(scenarioState, true, browser);
		this.browser = browser;
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_GROUP);	
	}

	@com.thoughtworks.gauge.Step("Enter user <zeroBasedUserIndex> as <userName> with permissions <permissions>")
	public void enterUserAsWithPermissions(int zeroBasedUserIndex, String userName, String permissions) throws Exception {
		ElementStub userScope = scope("user");
		if (!getUserNameTextbox(userScope, zeroBasedUserIndex).exists()) {
			addNewRow("USER");
		}
		ElementStub userNameTextbox = getUserNameTextbox(userScope, zeroBasedUserIndex);
		userNameTextbox.setValue(userName);
		setPermissions(zeroBasedUserIndex, permissions, userScope);
	}

	@com.thoughtworks.gauge.Step("Enter role <zeroBasedRoleIndex> as <roleName> with permissions <permissions>")
	public void enterRoleAsWithPermissions(int zeroBasedRoleIndex, String roleName, String permissions) throws Exception {
		ElementStub roleScope = scope("role");
		if (!getUserNameTextbox(roleScope, zeroBasedRoleIndex).exists()) {
			addNewRow("ROLE");
		}
		ElementStub userNameTextbox = getUserNameTextbox(roleScope, zeroBasedRoleIndex);
		userNameTextbox.setValue(roleName);
		setPermissions(zeroBasedRoleIndex, permissions, roleScope);
	}

	@com.thoughtworks.gauge.Step("Verify shows error <message> on role <zeroBasedRoleIndex>")
	public void verifyShowsErrorOnRole(String message, int zeroBasedRoleIndex) throws Exception {
		ElementStub userNameTextbox = getUserNameTextbox(scope("role"), zeroBasedRoleIndex);
		ElementStub errorMessage = browser.div(message).in(userNameTextbox.parentNode().parentNode());
		assertThat(errorMessage.exists(), is(true));
	}

	@com.thoughtworks.gauge.Step("Click save - Already On Pipeline Group Edit Page")
	public void clickSave() throws Exception {
		browser.submit("SAVE").click();
	}
	
	private ElementStub scope(String type) {
		ElementStub userScope = browser.div(Regex.wholeWord(type + "_permissions_for_group"));
		return userScope;
	}
	
	private void setPermissions(int zeroBasedUserIndex, String permissions, ElementStub userScope) {
		String[] perms = permissions.split(",");
		for (String perm : perms) {
			String checkBoxId = zeroBasedUserIndex == 0 ? perm.trim() : String
					.format("%s[%s]", perm.trim(), zeroBasedUserIndex);
			ElementStub checkbox = browser.checkbox(checkBoxId).in(userScope);
			if (!checkbox.checked()) {
				checkbox.click();
			}
		}
	}

	private ElementStub getUserNameTextbox(ElementStub scope, int index) {
		String idx = index == 0 ? "" : String.format("[%s]", index); 
		return browser.textbox(String.format("group[authorization][][name]%s", idx)).in(scope);
	}
	
	private void addNewRow(String type) {
		browser.link(type + "_add_users_and_roles").click();
	}

	@com.thoughtworks.gauge.Step("Verify that the message <message> shows up")
	public void verifyThatTheMessageShowsUp(String message) throws Exception {
		 assertThat(browser.div(message).exists(), is(true));
	}

	@com.thoughtworks.gauge.Step("Delete user <userName>")
	public void deleteUser(String userName) throws Exception {
		deleteRow("USER", userName);
	}

	@com.thoughtworks.gauge.Step("Delete role <roleName>")
	public void deleteRole(String roleName) throws Exception {
		deleteRow("ROLE", roleName);
	}
	
	private void deleteRow(String type, String userName) {
		ElementStub row = browser.row(String.format("%s_%s", type, userName));
		ElementStub deleteLink = browser.span(Regex.wholeWord("delete_parent")).in(row);
		deleteLink.click();
	}

    @com.thoughtworks.gauge.Step("Enter <groupName> as group name")
	public void enterAsGroupName(String groupName) throws Exception {
        ElementStub groupNameTextBox = browser.textbox("group[group]");
        groupNameTextBox.setValue(groupName);
    }

    @com.thoughtworks.gauge.Step("Assert mD5 - Already On Pipeline Group Edit Page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }

	
}
