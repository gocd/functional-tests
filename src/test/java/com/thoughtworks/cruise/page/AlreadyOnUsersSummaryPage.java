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

package com.thoughtworks.cruise.page;

import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.TriStateScope;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.junit.Assert;
import org.junit.matchers.StringContains;

import static com.thoughtworks.cruise.Regex.wholeWord;
import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

// JUnit Assert framework can be used for verification

public class AlreadyOnUsersSummaryPage extends CruisePage{

    private static final String ROLES_PANEL = "roles_panel";
    private String addedUser;
    private ElementStub parentTable;

    public AlreadyOnUsersSummaryPage(ScenarioState scenarioState, Browser browser) {
        super(scenarioState,true, browser);
    }

    @Override
    protected String url() {
        return browserWrapper.getCurrentUrl();
    }
    
    @com.thoughtworks.gauge.Step("Add and verify <username>")
	public void addAndVerify(String username) throws Exception {
    	gotoAddUsers();
    	searchForUser(username);
    	waitForSearchToReturnResults();
    	addFirstUserInResults();
    	verifyAddedUserSuccessfully();
    }

    @com.thoughtworks.gauge.Step("Sort column <column> - Already On Users Summary page")
	public void sortColumn(String column) throws Exception {
        browser.link(wholeWord(column)).click();
    }

    @com.thoughtworks.gauge.Step("Verify <enabled> enabled and <disabled> disabled users")
	public void verifyEnabledAndDisabledUsers(String enabled, String disabled) throws Exception {
        assertThat(browser.listItem(wholeWord("enabled")).text(),containsString(enabled));
        assertThat(browser.listItem(wholeWord("disabled")).text(),containsString(disabled));
    }

    @com.thoughtworks.gauge.Step("Goto add users")
	public void gotoAddUsers() throws Exception {
        ElementStub addUserLink = browser.link("/Add User/i");
        assertThat("Could not find the ADD USER link", addUserLink.exists(), is(true));
		addUserLink.click();
    }

    @com.thoughtworks.gauge.Step("Search for user <searchText>")
	public void searchForUser(final String searchText) throws Exception {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            public boolean call() throws Exception {
                ElementStub textbox = browser.textbox("search_text");
                if (!textbox.exists()) {
                    return false;
                }
                textbox.setValue(searchText);
                return true;
            }
        });
        browser.submit("SEARCH").click();
    }

    @com.thoughtworks.gauge.Step("Wait for search to return results")
	public void waitForSearchToReturnResults() throws Exception {
        Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
            @Override
            public boolean call() throws Exception {
                ElementStub div = browser.div("throbber");
                return !browser.isVisible(div);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Add first user in results")
	public void addFirstUserInResults() throws Exception {
     addUserWithIndex(1);   
    }  
    

    @com.thoughtworks.gauge.Step("Verify added user successfully")
	public void verifyAddedUserSuccessfully() throws Exception {
        assertThat(browser.div("Added user successfully").isVisible(), is(true));
        assertThat(browser.cell(addedUser).isVisible(), is(true));
    }

    @com.thoughtworks.gauge.Step("Verify user addition failed")
	public void verifyUserAdditionFailed() throws Exception {
        verifyMessageOnSearchPopupIs(String.format("Failed to add user. The user '%s' already exists.", addedUser));
    }

    private void verifySearchPopupIsOpen() {
        assertThat(browser.submit("SEARCH").isVisible(), is(true));
    }

    @com.thoughtworks.gauge.Step("Verify message on search popup is <message>")
	public void verifyMessageOnSearchPopupIs(String message) throws Exception {
        verifySearchPopupIsOpen();
        assertThat(browser.span(String.format(message)).isVisible(), is(true));        
    }

    @com.thoughtworks.gauge.Step("Verify <sourceName> results are displayed")
	public void verifyResultsAreDisplayed(String sourceName) throws Exception {
        assertThat(browser.cell(sourceName).under(browser.tableHeader("Source")).isVisible(), is(true));
    }
    
    @com.thoughtworks.gauge.Step("Verify column <column> has <valueList> in order")
	public void verifyColumnHasInOrder(String column, String valueList) throws Exception {
        String [] values = spiltToArray(valueList);
        for (int i = 0; i < values.length; i++) {
            ElementStub userCell = browser.cell(values[i]).under(browser.tableHeader(wholeWord(column)));
            assertThat(format("%s '%s' was not present",column, values[i]),userCell.text(),is(values[i]));        
        }
    }

    @com.thoughtworks.gauge.Step("Search for <userName> and add user <searchIndex> in the search result")
	public void searchForAndAddUserInTheSearchResult(String userName, Integer searchIndex) throws Exception {
        gotoAddUsers();
        searchForUser(userName);
        waitForSearchToReturnResults();
        addUserWithIndex(searchIndex);
        verifyAddedUserSuccessfully();
    }

    private void addUserWithIndex(Integer searchIndex) {
        ElementStub userSelect = browser.radio(searchIndex - 1).in(browser.div("search_users_table"));
        if (!userSelect.checked()) {
            userSelect.click();
        }
        addedUser = browser.cell(0).under(browser.tableHeader("User Name")).getText();
        browser.submit("ADD USER").click();      
    }

    @com.thoughtworks.gauge.Step("Verify users <userNamesList> are enabled")
	public void verifyUsersAreEnabled(String userNamesList) throws Exception {
        verifyEnabledTextForUserNamesAs(userNamesList, "Yes"); 
    }

    @com.thoughtworks.gauge.Step("Verify users <userNamesList> are disabled")
	public void verifyUsersAreDisabled(String userNamesList) throws Exception {
        verifyEnabledTextForUserNamesAs(userNamesList, "No"); 
    }

    @com.thoughtworks.gauge.Step("Disable users <userNamesList>")
	public void disableUsers(String userNamesList) throws Exception {
        toggleSelectionOfUsers(userNamesList);
        browser.submit("DISABLE").click();    
    }

    @com.thoughtworks.gauge.Step("Enable users <userNamesList>")
	public void enableUsers(String userNamesList) throws Exception {
        toggleSelectionOfUsers(userNamesList);
        browser.submit("ENABLE").click();  
    }

    @com.thoughtworks.gauge.Step("Toggle selection of users <userNamesList>")
	public void toggleSelectionOfUsers(String userNamesList) {
        String [] userNames = spiltToArray(userNamesList);
        for (int i = 0; i < userNames.length; i++) {
            browser.checkbox(userNames[i]).click();
        }
    }
     
	private void verifyEnabledTextForUserNamesAs(String userNamesList, String enabledText) {
        String [] userNames = spiltToArray(userNamesList);
        parentTable = browser.parentTable(browser.cell(userNames[0]));
        for (int i = 0; i < userNames.length; i++) {
            ElementStub userCell = browser.cell(parentTable, userNames[i], "Enabled");
            assertThat(format("%s '%s' was not present","Enabled", userNames[i]),userCell.text(),is(enabledText));        
        }
    }

    @com.thoughtworks.gauge.Step("Verify error message <errorText> is displayed")
	public void verifyErrorMessageIsDisplayed(String errorText) throws Exception {
        Assert.assertThat(flashMessage(), StringContains.containsString(errorText));
    }

	private String flashMessage() {
		return browser.div("message_pane").text();
	}

    @com.thoughtworks.gauge.Step("Select role states <roleNamesState>")
	public void selectRoleStates(String roleNamesState) throws Exception {
        openRolesPopup();
        getRolesTristateScope().set(roleNamesState);
    }
   
    @com.thoughtworks.gauge.Step("Apply changes to roles and verify message <message>")
	public void applyChangesToRolesAndVerifyMessage(final String message) throws Exception {
        browser.submit("role_operation").click();
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			
			@Override
			public boolean call() throws Exception {
				String flashMessage = flashMessage();
				return flashMessage.contains(message);
			}
		});
    }

    private String[] spiltToArray(String userNamesList) {
        return userNamesList.split("\\s*,\\s*");
    }

    @com.thoughtworks.gauge.Step("Add role <roleName>")
	public void addRole(String roleName) throws Exception {
        openRolesPopup();
        browser.textbox("new_role").setValue(roleName);
    }

    private void openRolesPopup() {
        browser.button("ROLES").click();
    }

    @com.thoughtworks.gauge.Step("Verify users <userNamesList> does not have the role <rolesList>")
	public void verifyUsersDoesNotHaveTheRole(String userNamesList, String rolesList) throws Exception {
        verifyUsersHaveRoles(userNamesList, rolesList, false);
    }
    
    @com.thoughtworks.gauge.Step("Verify users <userNamesList> are assigned role <rolesList>")
	public void verifyUsersAreAssignedRole(String userNamesList, String rolesList){
        verifyUsersHaveRoles(userNamesList, rolesList, true);
    }

    private void verifyUsersHaveRoles(String userNamesList, String rolesList, boolean toAssert) {
        String[] userNames = spiltToArray(userNamesList);
        String[] roleNames = spiltToArray(rolesList);
        parentTable = browser.parentTable(browser.cell(userNames[0]));
        for (int i = 0; i < userNames.length; i++) {
            ElementStub rolesCell = browser.cell(parentTable, userNames[i], "Roles");
            for (int j = 0; j < roleNames.length; j++)
                assertThat(format("%s '%s' was present", "Roles", userNames[i]), rolesCell.text().contains(roleNames[j]), is(toAssert));
        }
    }

    @com.thoughtworks.gauge.Step("Verify users <userNames> are administrators")
	public void verifyUsersAreAdministrators(String userNames) throws Exception {
        assertAreAdmins(userNames, true);
    }

    @com.thoughtworks.gauge.Step("Verify role state <tryStateName> is disabled with message <message>")
	public void verifyRoleStateIsDisabledWithMessage(String tryStateName, String message) throws Exception {
        openRolesPopup();
        getRolesTristateScope().assertDisabledWithMessage(tryStateName, message);
    }

    private TriStateScope getRolesTristateScope() {
        return new TriStateScope(browser, ROLES_PANEL, "admin");
    }

    @com.thoughtworks.gauge.Step("Verify role state <triStateName> is enabled")
	public void verifyRoleStateIsEnabled(String triStateName) throws Exception {
        openRolesPopup();
        getRolesTristateScope().assertEnabled(triStateName);
    }

    @com.thoughtworks.gauge.Step("Verify users <userNames> are not administrators")
	public void verifyUsersAreNotAdministrators(String userNames) throws Exception {
        assertAreAdmins(userNames, false);
    }

    private void assertAreAdmins(String userNames, boolean isAdmin) {
        for(String userName : userNames.split(",")) {
            ElementStub row = browser.cell(userName).parentNode();
            ElementStub adminPermissionCell = browser.cell(isAdmin ? "Yes" : "No").in(row).under(browser.tableHeader("Admin"));
            assertThat(adminPermissionCell.exists(), is(true));
        }
    }

    public void closePopup() throws Exception {
        browser.button("CLOSE").click();
    }
}

