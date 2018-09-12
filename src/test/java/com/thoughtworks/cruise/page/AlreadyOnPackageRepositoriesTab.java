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

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.SahiBrowserWrapper;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.core.Is;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AlreadyOnPackageRepositoriesTab extends CruiseAdminPage {
	private static final String PACKAGE_REPOSITORY_YUM_REPO_URL_ID = "package_repository_configuration_0_configurationValue_value";
	private static final String PACKAGE_REPOSITORY_PLUGIN_CONFIGURATION_ID = "package_repository_pluginConfiguration_id";
	private static final String PACKAGE_REPOSITORY_NAME_ID = "package_repository_name";
	private Browser browser;
	private String licenseKey;
	private String licenseUser;
	private final CurrentPageState currentPageState;
	private SahiBrowserWrapper browserWrapper;
	private String currentTab;
	private String md5value;

	public AlreadyOnPackageRepositoriesTab(Browser browser,
			CurrentPageState currentPageState, ScenarioState state) {
		super(state, true, browser);
		this.browser = browser;
		this.currentPageState = currentPageState;
		this.browserWrapper = new SahiBrowserWrapper(browser);
		currentPageState.assertCurrentPageIs(Page.PACKAGE_REPOSITORIES_PAGE);
	}

	public void reloadPage() {
		browserWrapper.reload();
	}

	@com.thoughtworks.gauge.Step("Enter repository name as <repoName>")
	public void enterRepositoryNameAs(String repoName) throws Exception {
		browser.textbox(PACKAGE_REPOSITORY_NAME_ID).setValue(repoName);
	}

	@com.thoughtworks.gauge.Step("Enter repo url as <repoUrl>")
	public void enterRepoUrlAs(String repoUrl) throws Exception {
		browser.textbox(PACKAGE_REPOSITORY_YUM_REPO_URL_ID).setValue(scenarioState.expand(repoUrl));
	}

	@com.thoughtworks.gauge.Step("Enter username as <username>")
	public void enterUsernameAs(String username) throws Exception {
		browser.textbox("package_repository_configuration_1_configurationValue_value").setValue(username);
	}

	@com.thoughtworks.gauge.Step("Enter password as <password>")
	public void enterPasswordAs(String password) throws Exception {
		ElementStub checkbox = browser.checkbox("checkbox_field_2");
		if(checkbox.exists()){
			checkbox.click();
		}
		browser.password("secure_field_2").setValue(password);
	}

	@com.thoughtworks.gauge.Step("Select <plugin> plugin")
	public void selectPlugin(String plugin) throws Exception {
		browser.select(PACKAGE_REPOSITORY_PLUGIN_CONFIGURATION_ID).choose(plugin);
	}

	@com.thoughtworks.gauge.Step("Click save - Already on Package Repositories tab")
	public void clickSave() throws Exception {
		browser.submit("SAVE").click();
	}
	
	@com.thoughtworks.gauge.Step("Click reset - Already on Package Repositories tab")
	public void clickReset() throws Exception {
		browser.reset("RESET").click();
	}

	@com.thoughtworks.gauge.Step("Verify message shows up <message>")
	public void verifyMessageShowsUp(String message) throws Exception {
		assertThat(message(), is(message));		
	}
	
	public void verifyMessageContains(String message) throws Exception {
	    assertThat(message(), containsString(message));
    }
	
	public String message() {
		return Assertions.waitFor(Timeout.TWENTY_SECONDS, new Function<String>() {
			public String call() {				
				String message = browser.byId("message_pane").getText().trim();
				if (StringUtils.isBlank(message)) {
					return null;
				}
				return message;
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify error message <errorMessage> shows up against field repository name")
	public void verifyErrorMessageShowsUpAgainstFieldRepositoryName(String errorMessage) throws Exception {
		verifyErrorMessageOnField(browser.textbox(PACKAGE_REPOSITORY_NAME_ID), errorMessage);
	}


	@com.thoughtworks.gauge.Step("Verify error message <errorMessage> shows up against field plugin selection")
	public void verifyErrorMessageShowsUpAgainstFieldPluginSelection(String errorMessage) throws Exception {
		verifyErrorMessageOnField(browser.select(PACKAGE_REPOSITORY_PLUGIN_CONFIGURATION_ID), errorMessage);
	}

	@com.thoughtworks.gauge.Step("Verify error message <errorMessage> shows up against field repository url")
	public void verifyErrorMessageShowsUpAgainstFieldRepositoryUrl(String errorMessage)	throws Exception {
		verifyErrorMessageOnField(browser.textbox(PACKAGE_REPOSITORY_YUM_REPO_URL_ID), errorMessage);
	}

	private void verifyErrorMessageOnField(ElementStub field, String errorMessage) {
		ElementStub parentNode = field.parentNode();
		assertThat(parentNode.fetch("className"), containsString("error"));
		assertThat(browser.span("error").in(parentNode).text(), is(errorMessage));
	}

	@com.thoughtworks.gauge.Step("Verify repository listing contains <repoName>")
	public void verifyRepositoryListingContains(String repoName) throws Exception {
		ElementStub list = browser.list(Regex.wholeWord("repositories"));
		ElementStub repoNameListItem = browser.link(repoName).in(list);
		assertThat(repoNameListItem.exists(), is(true));
	}

	@com.thoughtworks.gauge.Step("Click on <repoName> in repository listing")
	public void clickOnInRepositoryListing(String repoName) throws Exception {
		browser.link(repoName).in(browser.list(Regex.wholeWord("repositories"))).click();
	}
	
	@com.thoughtworks.gauge.Step("Expand repository listing for <repoName>")
	public void expandRepositoryListingFor(String repoName) throws Exception {
		ElementStub repoLink = browser.link(repoName).in(browser.list(Regex.wholeWord("repositories")));
		browser.span("handle").in(repoLink.parentNode().parentNode()).click();
	}


	@com.thoughtworks.gauge.Step("Verify add new repository form is shown")
	public void verifyAddNewRepositoryFormIsShown() throws Exception {
		assertThat(browser.heading2(Regex.startsWith("Add Package Repository")).exists(), is(true));
	}

	@com.thoughtworks.gauge.Step("Verify repo details are filled with name <repoName> plugin <plugin> and non secure configuration <configuration>")
	public void verifyRepoDetailsAreFilledWithNamePluginAndNonSecureConfiguration(String repoName, String plugin, String configuration) throws Exception {
		assertThat(browser.textbox(PACKAGE_REPOSITORY_NAME_ID).getValue(), is(repoName));
		assertThat(browser.select(PACKAGE_REPOSITORY_PLUGIN_CONFIGURATION_ID).selectedText(), is(plugin));
		String[] configurationValues = scenarioState.expand(configuration).split(",");
		for(int i=0; i< configurationValues.length; i++){
			assertThat(browser.textbox(String.format("package_repository_configuration_%s_configurationValue_value", i)).getValue(), is(configurationValues[i].trim()));
		}
	}

	@com.thoughtworks.gauge.Step("Verify password is encrypted value")
	public void verifyPasswordIsEncryptedValue() throws Exception {
		String value = browser.hidden("hidden_field_2").getValue();
		assertThat(browser.password("secure_field_2").getValue(), is(value));
	}

	@com.thoughtworks.gauge.Step("Verify check connection gives message containing <message>")
	public void verifyCheckConnectionGivesMessageContaining(final String message)throws Exception {
		browser.button("check_connection").click();
		Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate(){
			@Override
			public boolean call() throws Exception {
				String actualMessage = browser.byId("repository_connection_message").getText();
				System.out.println("This is the message from check connection : " + actualMessage);
				return actualMessage != null && actualMessage.contains(message);
			}			
		});			
	}

	@com.thoughtworks.gauge.Step("Click on package <packageName> in repository listing")
	public void clickOnPackageInRepositoryListing(String packageName) throws Exception {
		browser.link(packageName).in(browser.list(Regex.wholeWord("repositories"))).click();
		currentPageState.currentPageIs(Page.PACKAGE_DETAILS_PAGE);
	}

	@com.thoughtworks.gauge.Step("Verify package <packageName> is shown with delete icon disabled")
	public void verifyPackageIsShownWithDeleteIconDisabled(String packageName){
		ElementStub packageLink = browser.link(packageName).in(browser.list(Regex.wholeWord("repositories")));
        assertThat(packageLink.exists(),is(true));
        assertThat(browserWrapper.isEnabled(browser.button(Regex.startsWith("delete_button_")).in(packageLink.parentNode())), is(false));
	}
	
	@com.thoughtworks.gauge.Step("Verify package <packageName> is shown with delete icon enabled")
	public void verifyPackageIsShownWithDeleteIconEnabled(String packageName){
		assertThat(browserWrapper.isEnabled(deletePackageButton(packageName)), is(true));        
	}

	
	@com.thoughtworks.gauge.Step("Verify package <packageName> is not shown in the tree")
	public void verifyPackageIsNotShownInTheTree(String packageName){
		assertThat(browser.link(packageName).in(browser.list("repositories")).exists(),is(false));
	}

	@com.thoughtworks.gauge.Step("Delete package <packageName> from tree")
	public void deletePackageFromTree(String packageName) throws Exception {
		deletePackageButton(packageName).click();
		assertThat(browser.div("warning_prompt").isVisible(), is(true));
		assertThat(browser.div("ui-dialog-content").text(), is(String.format("You are about to delete package %s", packageName)));
		browser.span("PROCEED").click();
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate(){
			@Override
			public boolean call() throws Exception {
				return browser.div("messaging_wrapper").text().equals("Saved successfully.");
			}			
		});		
	}
	
	@com.thoughtworks.gauge.Step("Verify tooltip message <message> when package <packageName> cannot be deleted from tree view")
	public void verifyTooltipMessageWhenPackageCannotBeDeletedFromTreeView(String message,String packageName){
		assertThat(browser.byXPath("//ul[@class='packages']/li[@title='"+packageName+"']/div[@class='entity']/form/button").fetch("title").contains(message),is(true));
	}
	
	@com.thoughtworks.gauge.Step("Verify tooltip message <message> when package <packageName> can be deleted from tree view")
	public void verifyTooltipMessageWhenPackageCanBeDeletedFromTreeView(String message,String packageName){
	  String hrefAttributeDetails[]=browser.byXPath("//ul[@class='packages']/li[@title='"+packageName+"']/div[@class='entity']/a").fetch("href").split("/");
	  assertThat(browser.byId("delete_button_from_tree_"+hrefAttributeDetails[(hrefAttributeDetails.length-2)]).fetch("title").contains(message),is(true));		
	}

	@com.thoughtworks.gauge.Step("Verify repository <repoName> is shown with delete icon disabled")
	public void verifyRepositoryIsShownWithDeleteIconDisabled(String repoName)
			throws Exception {
		ElementStub repositoryLink = browser.link(repoName).in(browser.list(Regex.wholeWord("repositories")));
        assertThat(repositoryLink.exists(),is(true));
        assertThat(browserWrapper.isEnabled(browser.button(Regex.startsWith("delete_repository_button_")).in(repositoryLink.parentNode())), is(false));
	}

	@com.thoughtworks.gauge.Step("Verify repository <repoName> is shown with delete icon enabled")
	public void verifyRepositoryIsShownWithDeleteIconEnabled(String repoName)
			throws Exception {
		assertThat(browserWrapper.isEnabled(deleteRepositoryButton(repoName)), is(true));
		
	}

	@com.thoughtworks.gauge.Step("Delete repository <repoName> from tree")
	public void deleteRepositoryFromTree(String repoName) throws Exception {
		deleteRepositoryButton(repoName).click();
		assertThat(browser.div("warning_prompt").isVisible(), is(true));
		assertThat(browser.div("ui-dialog-content").text(), is(String.format("You are about to delete repository %s",repoName)));
		browser.span("PROCEED").click();
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate(){
			@Override
			public boolean call() throws Exception {
				return browser.div("messaging_wrapper").text().equals("Saved successfully.");
			}			
		});		
	}
		
	private ElementStub deleteRepositoryButton(String repoName) {
		ElementStub repositoryLink = browser.link(repoName).in(browser.list(Regex.wholeWord("repositories")));
        assertThat(repositoryLink.exists(),is(true));
        ElementStub repoDeleteButton = browser.button(Regex.startsWith("button_for_repository_delete_from_tree_")).in(repositoryLink.parentNode());
		return repoDeleteButton;
	}

	private ElementStub deletePackageButton(String packageName) {
		ElementStub packageLink = browser.link(packageName).in(browser.list(Regex.wholeWord("repositories")));
        assertThat(packageLink.exists(),is(true));
        ElementStub button = browser.button(Regex.startsWith("delete_button_from_tree_")).in(packageLink.parentNode());
		return button;
	}
	
	@com.thoughtworks.gauge.Step("Verify repository <repoName> is not shown in the tree")
	public void verifyRepositoryIsNotShownInTheTree(String repoName) {
		assertThat(browser.link(repoName).in(browser.list(Regex.wholeWord("repositories"))).exists(),is(false));
	}

	@com.thoughtworks.gauge.Step("Verify that search box is not shown")
	public void verifyThatSearchBoxIsNotShown() {
		assertThat(browser.textbox("search").in(browser.div("navigation")).isVisible(), is(false));
	}

	@com.thoughtworks.gauge.Step("Verify that search box is shown")
	public void verifyThatSearchBoxIsShown() {
		assertThat(browser.textbox("search").in(browser.div("navigation")).isVisible(), is(true));
	}

	@com.thoughtworks.gauge.Step("Click on add new repository")
	public void clickOnAddNewRepository() throws Exception {
		browser.link("Add New Repository").in(browser.div("navigation")).click();
		assertThat(browser.textbox("package_repository_name").in(browser.byId("package_repositories_edit_form")).text(), is(""));
	}

	@com.thoughtworks.gauge.Step("Type <searchString> in search box and verify repository <listedRepository> is shown while repository <repositoryNotListed> is not")
	public void typeInSearchBoxAndVerifyRepositoryIsShownWhileRepositoryIsNot(String searchString, 
			String listedRepository, String repositoryNotListed) {
		browser.textbox("search").in(browser.div("navigation")).setValue(searchString);
		assertThat(browser.link(listedRepository).in(browser.list("repositories treenav accordion")).in(browser.div("navigation")).isVisible(), Is.is(true));
		assertThat(browser.link(repositoryNotListed).in(browser.list("repositories treenav accordion")).in(browser.div("navigation")).isVisible(), Is.is(false));
	}

	@Override
	protected String url() {
		return null;
	}

	@com.thoughtworks.gauge.Step("Assert mD5 - Already on package repositories tab")
	public void assertMD5() throws Exception {
		super.assertMD5();	
	}	
}
