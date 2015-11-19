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

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import net.sf.sahi.client.Browser;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.xml.sax.SAXException;

import java.net.URISyntaxException;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class AlreadyOnSourceXmlTab {

	private Browser browser;
    private String currentTab;
	private final ScenarioState scenarioState;
	private final Configuration config;

	public AlreadyOnSourceXmlTab(Browser browser, CurrentPageState currentPageState, ScenarioState scenarioState, Configuration config) {
		this.browser = browser;
		currentPageState.assertCurrentPageIs(Page.SOURCE_XML_TAB);
		this.scenarioState = scenarioState;
		this.config = config;
	}

	@com.thoughtworks.gauge.Step("Config xml should have an environment <environmentName> with environment variable <name> <value>")
	public void configXmlShouldHaveAnEnvironmentWithEnvironmentVariable(String environmentName, String name, String value) throws Exception {
		CruiseConfigDom cruiseConfigDom = getCruiseConfigDom();
		Element environment = cruiseConfigDom.getEnvironment(environmentName);
		Assert.assertThat("Environment not found", environment, notNullValue());
		Node elementVar = environment.selectSingleNode(String.format("environmentvariables/variable[@name=\"%s\"]", name));
		Assert.assertThat("Environment variable not found", elementVar, notNullValue());
		Assert.assertThat("invalid environment variable value", elementVar.selectSingleNode("value").getText(), Matchers.is(value));
	}

    private CruiseConfigDom getCruiseConfigDom() throws DocumentException, SAXException, URISyntaxException {
        String text = browser.preformatted("content_container").getText();
		CruiseConfigDom cruiseConfigDom = new CruiseConfigDom(text);
        return cruiseConfigDom;
    }

    @com.thoughtworks.gauge.Step("Verify ldap manager password as <encryptedPassword>")
	public void verifyLdapManagerPasswordAs(String encryptedPassword) throws Exception {
        CruiseConfigDom cruiseConfigDom = getCruiseConfigDom();
        Element ldap = cruiseConfigDom.getLdap();
        Assert.assertThat("Ldap configuration not found", ldap, notNullValue());
        String encryptedManagerPassword = ldap.attributeValue("encryptedManagerPassword");
        Assert.assertThat(encryptedManagerPassword, Is.is(encryptedPassword));
        Assert.assertThat(ldap.attribute("managerPassword"), Is.is(nullValue()));
    }

    @com.thoughtworks.gauge.Step("Verify mail host password as <encryptedPassword>")
	public void verifyMailHostPasswordAs(String encryptedPassword) throws Exception {
        CruiseConfigDom cruiseConfigDom = getCruiseConfigDom();
        Element mailHost = cruiseConfigDom.getMailHost();
        Assert.assertThat("Mailhost configuration not found", mailHost, notNullValue());
        String encryptedManagerPassword = mailHost.attributeValue("encryptedPassword");
        Assert.assertThat(encryptedManagerPassword, Is.is(encryptedPassword));
        Assert.assertThat(mailHost.attribute("password"), Is.is(nullValue()));
    }

	@com.thoughtworks.gauge.Step("Remember current tab - Already on Source Xml Tab")
	public void rememberCurrentTab() throws Exception {
        this.currentTab = currentTab();	
	}

	private String currentTab() {
		return browser.listItem(Regex.matches("current_tab")).fetch("id");
	}

	@com.thoughtworks.gauge.Step("Click save - Already on Source Xml Tab")
	public void clickSave() throws Exception {
		browser.submit("SAVE").click();
	}
	
	@com.thoughtworks.gauge.Step("Click cancel")
	public void clickCancel() throws Exception {
		browser.link("cancel_edit").click();
	}

	@com.thoughtworks.gauge.Step("Click edit")
	public void clickEdit() throws Exception {
		browser.link("link_as_button primary").click();
	}

	@com.thoughtworks.gauge.Step("Change config to conflict")
	public void changeConfigToConflict() throws Exception {
		String oldConfig = browser.div("content_area").getText();
		String newConfig = oldConfig.replaceAll("replace-job", "replace-job-conflict");
		browser.textarea("go_config[content]").setValue(newConfig);
	}

	@com.thoughtworks.gauge.Step("Verify that split pane appears")
	public void verifyThatSplitPaneAppears() throws Exception {
		Assert.assertThat((browser.div("conflicted_content")).exists(), Is.is(true));
		Assert.assertThat((browser.div("current_content")).exists(), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Add downstream pipeline to create post validation conflict")
	public void addDownstreamPipelineToCreatePostValidationConflict() throws Exception {
		String oldConfig = browser.div("content_area").getText();
		String newConfig = oldConfig.replaceFirst("</pipelines>", "<pipeline name=\"downstream-pipeline\">"
		+ "<materials>"
		+ "<pipeline pipelineName=\"" + (scenarioState.pipelineNamed("upstream-pipeline")) + "\" stageName=\"defaultStage\" materialName=\"UP\" />"
        + "</materials>"
        + "<stage name=\"defaultStage\">"
        + "<approval type=\"manual\"/>"
        + "<jobs>"
        + "<job name=\"replace-job\">"
        + "<tasks>"
        + "<exec command=\"ls\"/>"
        + "</tasks>"
        + "</job>"
        + "</jobs>"
	  	+ "</stage>"
	    + "</pipeline>"
        + "</pipelines>");
		browser.textarea("go_config[content]").setValue(newConfig);
	}

	@com.thoughtworks.gauge.Step("Verify conflict error messages are shown")
	public void verifyConflictErrorMessagesAreShown() throws Exception {
		Assert.assertThat((browser.div("message_pane")).exists(), Is.is(true));
		Assert.assertThat(browser.getText(browser.div("flash")), Is.is("Someone has modified the configuration and your changes are in conflict. Please review, amend and retry. Help Topic: Configuration"));
		Assert.assertThat(browser.getText(browser.div("form_submit_errors")), Is.is("The following error(s) need to be resolved in order to perform this action: Configuration file has been modified by someone else."));
	}
	
	@com.thoughtworks.gauge.Step("Verify post validation error is shown with message <errorMessage>")
	public void verifyPostValidationErrorIsShownWithMessage(String errorMessage) throws Exception {
		Assert.assertThat((browser.div("message_pane")).exists(), Is.is(true));
		String splitErrorMessage[]=errorMessage.split("does");
		String pipelineName=splitErrorMessage[0].substring(9).trim();
		String newErrorMessage="Pipeline "+scenarioState.expand(pipelineName)+" does" +splitErrorMessage[1];
		Assert.assertThat(browser.getText(browser.div("flash")), Is.is("Someone has modified the configuration and your changes are in conflict. Please review, amend and retry. Help Topic: Configuration"));
		Assert.assertThat(browser.getText(browser.div("form_submit_errors")), Is.is("The following error(s) need to be resolved in order to perform this action: "+newErrorMessage));
	}
	
	@com.thoughtworks.gauge.Step("Assert mD5 - Already on Source Xml Tab")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("go_config_md5").getValue(), md5value);
    }
	
	@com.thoughtworks.gauge.Step("Verify config gets saved successfully")
	public void verifyConfigGetsSavedSuccessfully() throws Exception {
		Assert.assertThat((browser.div("message_pane")).exists(), Is.is(true));
		Assert.assertThat(browser.getText(browser.div("flash")), Is.is("Saved successfully."));
		Assert.assertThat(browser.div("form_submit_errors").exists(), Is.is(false));
	}
	
	@com.thoughtworks.gauge.Step("Verify config gets merged successfully")
	public void verifyConfigGetsMergedSuccessfully() throws Exception {
		Assert.assertThat((browser.div("message_pane")).exists(), Is.is(true));
		Assert.assertThat(browser.getText(browser.div("flash")), Is.is("Saved successfully. The configuration was modified by someone else, but your changes were merged successfully."));
		Assert.assertThat(browser.div("form_submit_errors").exists(), Is.is(false));
	}

	@com.thoughtworks.gauge.Step("Change repo config to have duplicate keys")
	public void changeRepoConfigToHaveDuplicateKeys() throws Exception {
		String oldConfig = browser.div("content_area").getText();
		String newConfig = oldConfig.replaceAll("REPO_URL_DUP", "REPO_URL");
		browser.textarea("go_config[content]").setValue(newConfig);
	}
	
	@com.thoughtworks.gauge.Step("Change package config to have duplicate keys")
	public void changePackageConfigToHaveDuplicateKeys() throws Exception {
		String oldConfig = browser.div("content_area").getText();
		String newConfig = oldConfig.replaceAll("PACKAGE_SPEC_DUP", "PACKAGE_SPEC");
		browser.textarea("go_config[content]").setValue(newConfig);
	}

	@com.thoughtworks.gauge.Step("Verify error message <message> is shown - Already on Source Xml Tab")
	public void verifyErrorMessageIsShown(String message) throws Exception {
		Assert.assertThat((browser.div("message_pane")).exists(), Is.is(true));
		Assert.assertThat(browser.getText(browser.div("form_submit_errors")), Is.is("The following error(s) need to be resolved in order to perform this action: "+message));
	}

	
}
