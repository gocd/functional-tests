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
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;

public class AlreadyOnPipelineGroupXml extends CruisePage {

	private final SahiBrowserWrapper browserWrapper;
	private String currentTab;

	public AlreadyOnPipelineGroupXml(ScenarioState state, Browser browser, CurrentPageState currentPageState) {
		super(state,true, browser);
		this.browserWrapper = new SahiBrowserWrapper(browser);		 
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_GROUP_XML_TAB);
		
	}

	@com.thoughtworks.gauge.Step("Verify group list has <groups>")
	public void verifyGroupListHas(String groups) throws Exception {
		List<String> groupNames = Arrays.asList(groups.split(","));		
		List<ElementStub> groupLinks = browserWrapper.collect("link", "modifiable_group_link");
		assertThat(groupNames.size(), Is.is(groupLinks.size()));
		for(ElementStub link: groupLinks){			
			assertThat(groupNames.contains(link.text()), Is.is(true));
			assertThat(link.fetch("href").contains("/pipelines/snippet/"+link.text()), Is.is(true));		
		}
	}

	@com.thoughtworks.gauge.Step("Verify selected group is <groupName>")
	public void verifySelectedGroupIs(String groupName) throws Exception {
		ElementStub selectedGroup = browser.listItem("selected");
		assertThat(selectedGroup.text(), Is.is(groupName));
	}

	@com.thoughtworks.gauge.Step("Click link <text>")
	public void clickLink(String text) throws Exception {
		browser.link("/" + text + "/i").click();
	}
	@com.thoughtworks.gauge.Step("Save changes")
	public void saveChanges() throws Exception {
		browser.submit("SAVE").click();
	}

	@Override
	protected String url() {
		// TODO Auto-generated method stub
		return null;
	}

	@com.thoughtworks.gauge.Step("Change group name of <fromGroupName> to <newGroupName>")
	public void changeGroupNameOfTo(String fromGroupName, String newGroupName) throws Exception {
		ElementStub editContainer = browser.textarea("content_container_for_edit");
		String groupXml = editContainer.text();
		String updatedXml = groupXml.replaceAll(groupNameNode(fromGroupName), groupNameNode(newGroupName));
		editContainer.setValue(updatedXml);		
	}

	private String groupNameNode(String fromGroupName) {
		return String.format("pipelines group=\"%s\"", fromGroupName);
	}

	@com.thoughtworks.gauge.Step("Verify that xml saved successfully")
	public void verifyThatXmlSavedSuccessfully() throws Exception {
		assertThat(browser.div("Saved configuration successfully.").exists(), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Verify that xml snippet contains <content>")
	public void verifyThatXmlSnippetContains(String content) throws Exception {
		ElementStub viewXmlContainer = browser.preformatted("content_container");
		assertThat(viewXmlContainer.text().contains(content), Is.is(true));
		
	}

	@com.thoughtworks.gauge.Step("Verify error message <message> - Already On Pipeline Group Xml")
	public void verifyErrorMessage(String message) throws Exception {
		ElementStub errorPane = browser.div("message_pane");
		assertThat(errorPane.text().contains(message), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Remember current tab - Already on Pipeline Group Xml")
	public void rememberCurrentTab() throws Exception {
        this.currentTab = currentTab();	
	}

	private String currentTab() {
		return browser.listItem(Regex.matches("current_tab")).fetch("id");
	}

	private String pipelineNameNode(String name) {
		return String.format("pipeline name=\"%s\"", name);
	}
	
	@com.thoughtworks.gauge.Step("Rename pipeline <originalName> to <newName> - Already On Pipeline Group Xml")
	public void renamePipelineTo(String originalName, String newName) throws Exception {
		ElementStub editContainer = browser.textarea("content_container_for_edit");
		String groupXml = editContainer.text();
		String updatedXml = groupXml.replaceAll(pipelineNameNode(scenarioState.pipelineNamed(originalName)), pipelineNameNode(newName));
		editContainer.setValue(updatedXml);	
	}

	@com.thoughtworks.gauge.Step("Verify merge conflict error message shows up")
	public void verifyMergeConflictErrorMessageShowsUp() {
		Assert.assertThat((browser.div("message_pane")).exists(), Is.is(true));
		Assert.assertThat(browser.getText(browser.div("flash")), Is.is("Someone has modified the configuration and your changes are in conflict. Please review, amend and retry."));
		Assert.assertThat(browser.getText(browser.div("form_submit_errors")), Is.is("The following error(s) need to be resolved in order to perform this action: Configuration file has been modified by someone else."));
	}

	@com.thoughtworks.gauge.Step("Verify post validation error occurs with the message <errorMessage>")
	public void verifyPostValidationErrorOccursWithTheMessage(String errorMessage) {
		Assert.assertThat((browser.div("message_pane")).exists(), Is.is(true));
		Assert.assertThat(browser.getText(browser.div("flash")), Is.is("Someone has modified the configuration and your changes are in conflict. Please review, amend and retry."));
		Assert.assertThat(browser.getText(browser.div("form_submit_errors")), Is.is("The following error(s) need to be resolved in order to perform this action: " + errorMessage));
	
	}
	
	@com.thoughtworks.gauge.Step("Verify successful merge message")
	public void verifySuccessfulMergeMessage() {
		Assert.assertThat((browser.div("message_pane")).exists(), Is.is(true));
		Assert.assertThat(browser.div("flash").getText(), Is.is("Saved configuration successfully. The configuration was modified by someone else, but your changes were merged successfully."));
		Assert.assertThat(browser.div("form_submit_errors").exists(), Is.is(false));
	}

}