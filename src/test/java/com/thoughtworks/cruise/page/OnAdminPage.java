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
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.page.OnServerDetailPage.License;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import junit.framework.Assert;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OnAdminPage extends CruisePage {
    private final CurrentPageState currentPageState;

    public OnAdminPage(ScenarioState state, Browser browser, CurrentPageState currentPageState) {
        super(state, browser);
        this.currentPageState = currentPageState;
    }
    
    @Override
    protected String url() {
        return Urls.urlFor("/admin/pipelines");
    }

    public void verifyLicenseWasSavedSuccessfully(String licenseType) throws Exception {
        browser.byId("tab-link-of-source-xml").click();
        final License license = OnServerDetailPage.licenses.get(licenseType);
        Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {
            public boolean call() throws Exception {
                String cruiseConfigXml = browser.byId("content_container").getText().replaceAll("\n", "");
                return cruiseConfigXml.contains(String.format("<license user=\"%s\">%s</license>", license.user, license.key));
            }
        });
    }

    @Override
    public void verifyCruiseFooter() throws Exception {
        super.verifyCruiseFooter();
    }

    @com.thoughtworks.gauge.Step("On Admin page")
    public void goToAdminPage() throws Exception {
        navigateToURL();
    }

    @com.thoughtworks.gauge.Step("Navigate to Admin page")
    public void navigateToAdminPage() throws Exception {
        browser.navigateTo(Urls.localhostSslUrlFor("/admin/pipelines"));
    }

    @com.thoughtworks.gauge.Step("Verify source xml is not visible")
	public void verifySourceXmlIsNotVisible() throws Exception {
        verifyTabIsNotVisible("source-xml-tab-button");
    }

    @com.thoughtworks.gauge.Step("Verify source xml is visible")
	public void verifySourceXmlIsVisible() throws Exception {
        verifyTabIsVisible("source-xml-tab-button");
    }

    @com.thoughtworks.gauge.Step("Verify server configuration is not visible")
	public void verifyServerConfigurationIsNotVisible() throws Exception {
        verifyTabIsNotVisible("server-configuration-tab-button");
    }

    @com.thoughtworks.gauge.Step("Verify server configuration is visible")
	public void verifyServerConfigurationIsVisible() throws Exception {
        verifyTabIsVisible("server-configuration-tab-button");
    }

    @com.thoughtworks.gauge.Step("Verify user summary is not visible")
	public void verifyUserSummaryIsNotVisible() throws Exception {
        verifyTabIsNotVisible("user-summary-tab-button");
    }

    @com.thoughtworks.gauge.Step("Verify user summary is visible")
	public void verifyUserSummaryIsVisible() throws Exception {
        verifyTabIsVisible("user-summary-tab-button");
    }

    private void verifyTabIsNotVisible(String tabName) throws Exception {
        verifyTabVisibity(tabName, false);
    }

    private void verifyTabIsVisible(String tabName) throws Exception {
        verifyTabVisibity(tabName, true);
    }

    private void verifyTabVisibity(String tabName, boolean isVisible) {
        assertThat(String.format("Visibility of tab '%s' should be '%s'", tabName, isVisible), browser.listItem(tabName).exists(),
                is(isVisible));
    }

    public void verifyPipelineGroupsAreNotVisible(String groupsString) throws Exception {
        List<String> groups = asList(groupsString.split(","));
        for (String group : groups) {
            assertThat(elementPipelineGroup(group).exists(), is(false));
        }
    }

    private ElementStub elementPipelineGroup(String group) {
        return browser.listItem("group-" + group).in(browser.div("pipelines-configuration"));
    }

    private List<String> asList(String[] strings) {
        List<String> result = new ArrayList<String>();
        for (String string : strings) {
            result.add(string.trim());
        }
        return result;
    }

    @com.thoughtworks.gauge.Step("Verify templates are not visible")
	public void verifyTemplatesAreNotVisible() throws Exception {
        assertThat(browser.link("Templates").exists(), is(false));
    }

    @com.thoughtworks.gauge.Step("Verify templates are visible")
	public void verifyTemplatesAreVisible() throws Exception {
        verifyTabIsVisible("templates-tab-button");
    }

    public void verifyPipelineGroupsAreVisible(String groupsString) throws Exception {
        List<String> groups = asList(groupsString.split(","));
        for (String group : groups) {
            assertThat(elementPipelineGroup(group).exists(), is(true));
        }
    }

    public void verifyPipelinesAreVisible(String pipelinesString) throws Exception {
        List<String> pipelines = asList(pipelinesString.split(","));
        for (String pipeline : pipelines) {
            assertThat(elementPipelineInAccordion(scenarioState.pipelineNamed(pipeline)).exists(), is(true));
        }
    }

    private ElementStub elementPipelineInAccordion(String pipeline) {
        return browser.listItem("pipeline-link-" + pipeline).in(browser.div("pipelines-configuration"));
    }

    private ElementStub elementPipeline(String pipeline) {
        return browser.listItem("pipeline-" + pipeline).in(browser.div("pipelines-configuration"));
    }

    public String selectPipeline(String pipeline) {
        scenarioState.usingPipeline(pipeline);
        String runtimeName = scenarioState.pipelineNamed(pipeline);
        elementPipelineInAccordion(runtimeName).click();
        return runtimeName;
    }

    public void renameJobTo(String newJobName) throws Exception {
        ElementStub editArea = elementEditArea();
        String currentContent = editArea.getText();
        String newContent = currentContent.replaceAll("<job name=\"defaultJob\">", String.format("<job name=\"%s\">", newJobName));
        editArea.setValue(newContent.replaceAll("\"", "'").replaceAll("\\n", ""));
    }

    private ElementStub elementEditArea() {
        return browser.textarea(0).in(
                browser.div(Regex.wholeWord("edit-container")).in(elementPipeline(scenarioState.currentRuntimePipelineName())));
    }

    public void save() throws Exception {
        ElementStub link = browser.link("SAVE");
        link.click();
        browser.span("SAVE").in(link).click();
    }

    public void verifyTheSaveWasSuccessful() throws Exception {
        assertThat(browser.byId("success-box").getText().trim(), is("Configuration updated successfully!"));
    }
    
    @com.thoughtworks.gauge.Step("Open config tab as group admin")
	public void openConfigTabAsGroupAdmin(){
        browser.link("Config XML").in(browser.div(Regex.wholeWord("sub_tabs_container"))).click();
        currentPageState.currentPageIs(CurrentPageState.Page.EDIT_PIPELINE_GROUP_XML_TAB);
        
    }
    
    @com.thoughtworks.gauge.Step("Open <tabName> tab")
	public void openTab(String tabName) throws Exception {

        browser.link(tabName).in(browser.div(Regex.wholeWord("sub_tabs_container"))).click();
        if (tabName.equals("OAuth Clients")) {
            currentPageState.currentPageIs(CurrentPageState.Page.ADMIN_OAUTH_TAB);
        }
        if (tabName.equals("User Summary")) {
            currentPageState.currentPageIs(CurrentPageState.Page.USERS_SUMMARY_PAGE);
        }
        if (tabName.equals("Server Configuration")) {
            currentPageState.currentPageIs(CurrentPageState.Page.SERVER_CONFIGURATION_TAB);
        }
        if (tabName.equals("Pipelines")) {
            currentPageState.currentPageIs(CurrentPageState.Page.EDIT_PIPELINE_GROUPS_TAB);
        }
        if (tabName.equals("Templates")) {
        	currentPageState.currentPageIs(CurrentPageState.Page.EDIT_TEMPLATES_TAB);
        }
        if (tabName.equals("Backup")) {
            currentPageState.currentPageIs(CurrentPageState.Page.BACKUP_SERVER_TAB);
        }
        if (tabName.equals("Config XML")) {
            currentPageState.currentPageIs(CurrentPageState.Page.SOURCE_XML_TAB);
        }
        if (tabName.equals("User Roles")) {
            currentPageState.currentPageIs(CurrentPageState.Page.USER_ROLES_PAGE);
        }
        if (tabName.equals("Plugins")) {
        	currentPageState.currentPageIs(CurrentPageState.Page.ADMIN_PLUGINS_PAGE);
        }
        if (tabName.equals("Package Repositories")) {
        	currentPageState.currentPageIs(CurrentPageState.Page.PACKAGE_REPOSITORIES_PAGE);
        }
    }

    public void verifyStageHasAttributeSetTo(String stageName, String attributeName, String value) throws Exception {
        ElementStub attribute = browser.span(String.format("/%s/", attributeName)).in(stageScopeElement(stageName));
        String text = attribute.getText();
        assertTrue(text.contains(value));
    }

    private ElementStub stageScopeElement(String stageName) {
        return browser.div("/stages-h3-wrapper/").in(
                browser.listItem(String.format("stage-%s-%s", scenarioState.currentRuntimePipelineName(), stageName)));
    }

    public void verifyStageJobHasText(String stageName, String jobName, String textToVerify) throws Exception {
        ElementStub jobElement = browser.listItem(String.format("job-%s-%s-%s", scenarioState.currentRuntimePipelineName(), stageName,
                jobName));
        String text = jobElement.getText();
        assertTrue(text.contains(textToVerify));
    }

    public void verifyPipelineHasAttributeWithText(String header, String textToVerify) {
        ElementStub cellToVerify = browser.cell(0).near(browser.tableHeader(header));
        assertTrue(cellToVerify.getText().contains(textToVerify));
    }

    @com.thoughtworks.gauge.Step("Verify error message <message> - On Admin Page")
	public void verifyErrorMessage(String message) throws Exception {
        String notificationContent = browser.div(Regex.wholeWord("information")).getText();
        Assert.assertTrue(String.format("Expecting a message %s as notification", message), notificationContent.contains(message));
    }

    public void verifyTemplatesTabIsNotVisible() throws Exception {
        verifyTabIsNotVisible("templates-tab-button");
    }

    @com.thoughtworks.gauge.Step("Force navigate to add new pipeline")
	public void forceNavigateToAddNewPipeline() throws Exception {
        browser.navigateTo(Urls.urlFor("/go/admin/pipeline/new"));
        currentPageState.currentPageIs(Page.ADD_NEW_PIPELINE_WIZARD);
    }

	@com.thoughtworks.gauge.Step("Force navigate to add new pipeline with pipeline group <groupName>")
	public void forceNavigateToAddNewPipelineWithPipelineGroup(String groupName)
			throws Exception {
		browser.navigateTo(Urls.urlFor("/go/admin/pipeline/new?group="+ groupName));
		currentPageState.currentPageIs(Page.ADD_NEW_PIPELINE_WIZARD);		
	
	}
}
