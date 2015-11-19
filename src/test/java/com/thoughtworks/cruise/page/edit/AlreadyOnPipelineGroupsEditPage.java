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
import com.thoughtworks.cruise.util.CommaSeparatedParams;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AlreadyOnPipelineGroupsEditPage extends CruisePage {

    private Browser browser;
    private final CurrentPageState currentPageState;

    public AlreadyOnPipelineGroupsEditPage(ScenarioState scenarioState, Browser browser, CurrentPageState currentPageState) {
        super(scenarioState, true, browser);
        this.browser = browser;
        this.currentPageState = currentPageState;
    }

    @Override
    protected String url() {
        throw new RuntimeException("Should not be called");
    }

    @com.thoughtworks.gauge.Step("Verify groups <groupList> are visible")
	public void verifyGroupsAreVisible(String groupList) throws Exception {
        CommaSeparatedParams groups = new CommaSeparatedParams(groupList);
        for (String group : groups) {
            assertThat(elementGroup(group).exists(), is(true));
        }
    }

    @com.thoughtworks.gauge.Step("Verify <group> has pipelines <csPipelines>")
	public void verifyHasPipelines(String group, String csPipelines) throws Exception {
        verifyGroupContainsPipeline(group, csPipelines, true);
    }

    @com.thoughtworks.gauge.Step("Verify <group> does not have pipelines <csPipelines>")
	public void verifyDoesNotHavePipelines(String group, String csPipelines) throws Exception {
        verifyGroupContainsPipeline(group, csPipelines, false);
    }

    @com.thoughtworks.gauge.Step("Verify can delete <pipelineNames>")
	public void verifyCanDelete(String pipelineNames) throws Exception {
        CommaSeparatedParams pipelines = new CommaSeparatedParams(pipelineNames);
        for (String pipeline : pipelines) {
            assertThat(String.format("The pipeline '%s' should be deletable. But its not.", pipeline), elementDeletePipeline(pipeline)
                    .exists(), is(true));
        }
    }

    private void verifyGroupContainsPipeline(String group, String csPipelines, boolean hasPipeline) {
        CommaSeparatedParams pipelines = new CommaSeparatedParams(csPipelines);
        ElementStub groupContainer = elementGroupContainer(group);
        for (String pipeline : pipelines) {
            String actualPipeline = scenarioState.pipelineNamed(pipeline);
            assertThat(String.format("'%s' pipeline not found", actualPipeline), browser.link(actualPipeline).in(groupContainer).exists(),
                    is(hasPipeline));
        }
    }

    private ElementStub elementGroup(String group) {
        return browser.heading2(group);
    }

    private ElementStub elementGroupContainer(String group) {
        return elementGroup(group).parentNode();
    }

    private ElementStub elementDeletePipeline(String pipeline) {
        String actualPipelineName = scenarioState.pipelineNamed(pipeline);
        ElementStub pipelineRow = pipelineRow(actualPipelineName);
        final ElementStub deletePipelineLink = browser.link("Delete").in(pipelineRow);

        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {

            @Override
            public boolean call() throws Exception {
                return deletePipelineLink.exists();
            }

            @Override
            public String toString() {
                return String.format("Fetching delete link for pipeline using '%s' selector in ---%s---",
                        deletePipelineLink.fetch("innerHTML"));
            }
        });
        return deletePipelineLink;
    }

	private ElementStub pipelineRow(String actualPipelineName) {
		return browser.link(actualPipelineName).parentNode().parentNode();
	}

    @com.thoughtworks.gauge.Step("Verify cannot delete <pipelineNames>")
	public void verifyCannotDelete(String pipelineNames) throws Exception {
        CommaSeparatedParams pipelines = new CommaSeparatedParams(pipelineNames);
        for (String pipeline : pipelines) {
            String runtimePipelineName = scenarioState.pipelineNamed(pipeline);
            assertThat(String.format("Should not be able to delete pipeline '%s'. But looks like it can be.", runtimePipelineName),
                    deleteLinkForPipeline(runtimePipelineName).exists(), is(true));
        }
    }

    private ElementStub deleteLinkForPipeline(String runtimePipelineName) {
        ElementStub pipelineRow = pipelineRow(runtimePipelineName);
        return browser.span(Regex.wholeWord("delete_icon_disabled")).in(pipelineRow);
    }

    @com.thoughtworks.gauge.Step("Delete <pipelineName>")
	public void delete(String pipelineName) throws Exception {
        // HACK: reloadPage to remove any existing messages. This is so that we
        // can be
        // certain the message we are validating is from this delete.
        reloadPage();
        elementDeletePipeline(pipelineName).click();
        proceedWithConfirmPrompt();
        assertThat(isMessagePresent("Saved successfully."), is(true));
    }

    private boolean isMessagePresent(final String value) {
        return Assertions.waitFor(Timeout.TWENTY_SECONDS, new Assertions.Function<Boolean>() {
            public Boolean call() {
                ElementStub message = browser.div(value);
                return message.exists();
            }
        }, new Assertions.FailureHandler<Boolean>() {
            public Boolean invoke(Exception e, Timeout timeout, Assertions.Function<Boolean> func) {
                return false;
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify <groupName> has message <message>")
	public void verifyHasMessage(final String groupName, final String message) throws Exception {
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {

            @Override
            public boolean call() throws Exception {
                boolean exists = browser.div(message).in(elementGroupContainer(groupName)).exists();
                return exists;
            }

            @Override
            public String toString() {
                return String.format("Expecting to see message '%s' inside HTML--%s-- group container. ", message,
                        elementGroupContainer(groupName).fetch("innerHTML"));
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify groups <groupNames> are not visible")
	public void verifyGroupsAreNotVisible(String groupNames) throws Exception {
        CommaSeparatedParams groups = new CommaSeparatedParams(groupNames);
        for (String group : groups) {
            assertThat(elementGroup(group).exists(), is(false));
        }
    }

    @com.thoughtworks.gauge.Step("Click move button for pipeline <pipelineName>")
	public void clickMoveButtonForPipeline(String pipelineName) {
        ElementStub moveButton = moveToLinkFor(pipelineName);
        moveButton.click();
    }

    @com.thoughtworks.gauge.Step("Click clone button for pipeline <pipelineName>")
	public void clickCloneButtonForPipeline(String pipelineName) {
        ElementStub cloneButton = getCloneButtonFor(pipelineName);
        cloneButton.click();
        currentPageState.currentPageIs(Page.CLONE_PIPELINE_POPUP);
    }

    @com.thoughtworks.gauge.Step("Verify that <pipelineName> cannot be moved from group <fromGroup> to group <toGroup>")
	public void verifyThatCannotBeMovedFromGroupToGroup(String pipelineName, String fromGroup, String toGroup) throws Exception {
        ElementStub moveToLink = moveToLinkFor(pipelineName);
        if(!moveToLink.exists()) return;
        ElementStub groupListItem = toGroupInDropDownOfPipeline(pipelineName, fromGroup, toGroup);
        assertThat(groupListItem.exists(), is(false));
    }

    @com.thoughtworks.gauge.Step("Move pipeline <pipelineName> from group <fromGroup> to group <toGroup>")
	public void movePipelineFromGroupToGroup(String pipelineName, String fromGroup, String toGroup) throws Exception {
        ElementStub groupListItem = toGroupInDropDownOfPipeline(pipelineName, fromGroup, toGroup);
        groupListItem.click();
    }

    private ElementStub toGroupInDropDownOfPipeline(String pipelineName, String fromGroup, String toGroup) {
        ElementStub moveToHref = moveToLinkFor(pipelineName);
        moveToHref.click();
        ElementStub dropDown = browser.div("content_move_to_groups");
        ElementStub groupListItem = browser.listItem(toGroup).in(dropDown);
        return groupListItem;
    }

	private ElementStub moveToLinkFor(String pipelineName) {
		ElementStub moveToHref = browser.link("a_move_"+scenarioState.pipelineNamed(pipelineName));
		return moveToHref;
	}

    @com.thoughtworks.gauge.Step("Verify that the edit button for pipeline <pipelineName> is a link for edit pipeline")
	public void verifyThatTheEditButtonForPipelineIsALinkForEditPipeline(String pipelineName) throws Exception {
        ElementStub link = getPipelineEditLink(pipelineName);
        link.exists();
    }

    @com.thoughtworks.gauge.Step("Click to create a new pipeline to group <groupName>")
	public void clickToCreateANewPipelineToGroup(String groupName) throws Exception {
        ElementStub elementGroupContainer = elementGroupContainer(groupName);
        ElementStub createLink = browser.link("/add_pipeline_to_group/").in(elementGroupContainer);
        createLink.click();
        currentPageState.currentPageIs(Page.ADD_NEW_PIPELINE_WIZARD);
    }

    @com.thoughtworks.gauge.Step("Verify that move button is not present for <pipelineName>")
	public void verifyThatMoveButtonIsNotPresentFor(String pipelineName) throws Exception {
        ElementStub moveButton = moveToLinkFor(pipelineName);
        assertThat("Move button should be invisible if only one group exists.", moveButton.exists(), is(false));
    }

    public void editPipeline(String pipelineName) throws Exception {
        ElementStub pipelineLink = browser.link(scenarioState.pipelineNamed(pipelineName));
        pipelineLink.click();
        currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_GENERAL_PAGE);
    }

    private ElementStub getCloneButtonFor(String pipelineName) {
        ElementStub cloneButton = browser.link("Clone").in(pipelineRow(scenarioState.pipelineNamed(pipelineName)));
        return cloneButton;
    }

    private ElementStub getPipelineEditLink(String pipelineName) {
        String pipeline = scenarioState.pipelineNamed(pipelineName);
        String buttonClass = String.format("edit_for_pipeline_%s", pipeline);
        ElementStub editButton = browser.span(Regex.matches(buttonClass));
        ElementStub link = browser.link(String.format("/%s/general", pipeline)).in(editButton);
        return link;
    }

    @com.thoughtworks.gauge.Step("Click to edit pipeline group <groupName>")
	public void clickToEditPipelineGroup(String groupName) throws Exception {
        ElementStub nameHeader = browser.heading2(groupName);
        ElementStub editLink = browser.link(Regex.wholeWord("group_name_edit")).in(nameHeader.parentNode());
        editLink.click();
        currentPageState.currentPageIs(Page.EDIT_PIPELINE_GROUP);
    }

    @com.thoughtworks.gauge.Step("Verify delete link is disabled for <groupName>")
	public void verifyDeleteLinkIsDisabledFor(String groupName) throws Exception {
        ElementStub nameHeader = browser.heading2(groupName);
        ElementStub deleteLink = browser.span("Delete").in(nameHeader.parentNode());
        assertThat(deleteLink.fetch("className"), org.hamcrest.Matchers.containsString("delete_icon_disabled"));
    }

    @com.thoughtworks.gauge.Step("Delete group <groupName>")
	public void deleteGroup(String groupName) {
        // HACK: reloadPage to remove any existing messages. This is so that we
        // can be
        // certain the message we are validating is from this delete.
        reloadPage();
        deleteLink(groupName).click();
        proceedWithConfirmPrompt();
        assertThat(isMessagePresent("Saved successfully."), is(true));
    }
    
    @com.thoughtworks.gauge.Step("Delete group with confirm prompt <groupName>")
	public void deleteGroupWithConfirmPrompt(String groupName) {
        deleteLink(groupName).click();
    }
    

    private ElementStub deleteLink(final String groupName) {
        final ElementStub deleteGroupLink = browser.byId(String.format("trigger_delete_group_%s", groupName));
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {

            @Override
            public boolean call() throws Exception {
                return deleteGroupLink.exists();
            }

            @Override
            public String toString() {
                return String.format("Expecting delete link with id '%s'. Within %s", String.format("trigger_delete_group_%s", groupName),
                        elementGroupContainer(groupName).fetch("innerHTML"));
            }
        });
        return deleteGroupLink;
    }

    @com.thoughtworks.gauge.Step("Verify groups <groupName> is not visible")
	public void verifyGroupsIsNotVisible(String groupName) throws Exception {
        assertThat(elementGroup(groupName).exists(), is(false));
    }

    @com.thoughtworks.gauge.Step("Add new group")
	public void addNewGroup() throws Exception {
        browser.link("Add New Pipeline Group").click();
        currentPageState.currentPageIs(Page.NEW_PIPELINE_GROUP_POPUP);
    }
    
    @com.thoughtworks.gauge.Step("Assert mD5")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }
    
}
