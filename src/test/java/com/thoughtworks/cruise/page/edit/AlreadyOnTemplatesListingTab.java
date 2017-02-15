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

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.page.CruisePage;
import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.CommaSeparatedParams;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.hamcrest.text.StringContains;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class AlreadyOnTemplatesListingTab extends CruisePage {

    private Browser browser;
    private CurrentPageState currentPageState;

    public AlreadyOnTemplatesListingTab(Browser browser, ScenarioState state, CurrentPageState pageState) {
        super(state, true, browser);
        this.browser = browser;
        this.currentPageState = pageState;
        pageState.assertCurrentPageIs(Page.EDIT_TEMPLATES_TAB);
    }

    @com.thoughtworks.gauge.Step("Verify that templates <csTemplates> are present")
	public void verifyThatTemplatesArePresent(String csTemplates) {
        CommaSeparatedParams templates = new CommaSeparatedParams(csTemplates);
        for (String template : templates) {
            assertThat(String.format("Template must be present '%s'", template), elementTemplateName(template).exists(), Is.is(true));
        }
    }

    private ElementStub elementTemplateName(String template) {
        return browser.heading2(template);
    }
    
    private ElementStub elementTemplateContainer(String template) {
        return browser.div(String.format("template_container_%s", template));
    }

    @com.thoughtworks.gauge.Step("Verify that template <templateName> is used by pipelines <csPipelines>")
	public void verifyThatTemplateIsUsedByPipelines(String templateName, String csPipelines) throws Exception {
        CommaSeparatedParams pipelines = new CommaSeparatedParams(csPipelines);
        for (String pipeline : pipelines) {
            String actualName = scenarioState.pipelineNamed(pipeline);
            ElementStub pipelineUnderTemplate = browser.link(actualName).in(elementTemplateContainer(templateName));
            assertThat(String.format("Pipeline '%s' must be present under '%s'", actualName, templateName), pipelineUnderTemplate.exists(), Is.is(true));
        }
    }

    @com.thoughtworks.gauge.Step("Verify that template <templateName> has message <message>")
	public void verifyThatTemplateHasMessage(String templateName, String message) throws Exception {
        ElementStub informationField = browser.div("information").in(elementTemplateContainer(templateName));
        assertThat(informationField.getText(), Is.is(message));
    }

    @com.thoughtworks.gauge.Step("Verify that edit pipeline <pipelineName> lands on pipeline edit page")
	public void verifyThatEditPipelineLandsOnPipelineEditPage(String pipelineName) throws Exception {
        String actualName = scenarioState.pipelineNamed(pipelineName);
        ElementStub pipelineLink = browser.link(actualName);
        String href = pipelineLink.fetch("href");
        assertThat(String.format("Edit pipeline must have URL: '%s'", href), href, StringContains.containsString(String.format("/admin/pipelines/%s", actualName)));
    }

    @com.thoughtworks.gauge.Step("Verify cannot delete templates <csTemplates>")
	public void verifyCannotDeleteTemplates(String csTemplates) throws Exception {
        CommaSeparatedParams templates = new CommaSeparatedParams(csTemplates);
        for (String template : templates) {
            assertThat(String.format("Template delete must be disabled for '%s'", template), browser.span(Regex.wholeWord("delete_icon_disabled")).in(elementTemplateContainer(template)).exists(), is(true));
        }
    }

    @com.thoughtworks.gauge.Step("Verify can delete templates <csTemplates>")
	public void verifyCanDeleteTemplates(String csTemplates) throws Exception {
        CommaSeparatedParams templates = new CommaSeparatedParams(csTemplates);
        for (String template : templates) {
            assertThat(String.format("Template delete must be enabled for '%s'", template), elementDeleteTemplate(template).exists(), is(true));
        }
    }

    private ElementStub elementDeleteTemplate(String template) {
        return browser.byId(String.format("trigger_delete_%s", template));
    }

    @com.thoughtworks.gauge.Step("Delete template <templateName>")
	public void deleteTemplate(String templateName) throws Exception {
        elementDeleteTemplate(templateName).click();
        proceedWithConfirmPrompt();
        isMessagePresent("Saved successfully.");
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

    @com.thoughtworks.gauge.Step("Verify that templates <csTemplates> are not present")
	public void verifyThatTemplatesAreNotPresent(String csTemplates) throws Exception {
        CommaSeparatedParams templates = new CommaSeparatedParams(csTemplates);
        for (String template : templates) {
            assertThat(elementTemplateName(template).exists(), is(false));
        }
    }

    @Override
    protected String url() {
        return null;
    }

    @com.thoughtworks.gauge.Step("Add new template")
	public void addNewTemplate() throws Exception {
        browser.link("Add New Template").click();
        currentPageState.currentPageIs(Page.NEW_TEMPLATE_POPUP);
    }

	@com.thoughtworks.gauge.Step("Edit template <templateName>")
	public void editTemplate(String templateName) throws Exception {
		ElementStub templateHeadingParent = browser.heading2(templateName).parentNode();
		ElementStub editLink = browser.link(Regex.wholeWord("edit_icon")).in(templateHeadingParent);
		editLink.click();
		currentPageState.currentPageIs(Page.EDIT_TEMPLATE_PAGE);
	}

	@com.thoughtworks.gauge.Step("Delete template with confirm prompt <templateName>")
	public void deleteTemplateWithConfirmPrompt(String templateName) throws Exception {
		elementDeleteTemplate(templateName).click();
	}
	
	@com.thoughtworks.gauge.Step("Assert mD5 - Already On Templates Listing tab")
	public void assertMD5() throws Exception {
	    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
	    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
	}
	
	@com.thoughtworks.gauge.Step("Verify cannot add new template for template admin")
	public void verifyCannotAddNewTemplateForTemplateAdmin() {
		ElementStub templateContainer = browser.div("templates"); 
		ElementStub secondaryTemplateContainer = browser.span("title_secondary_info").in(templateContainer); 
		assertThat("Add new template must be disabled for template admin", 
				browser.span("add_icon_disabled").in(secondaryTemplateContainer).exists(), is(true));
	}

	@com.thoughtworks.gauge.Step("Verify that <templateName> template has permissions link enabled and click on it")
	public void verifyThatTemplateHasPermissionsLinkEnabledAndClickOnIt(String templateName) {
		ElementStub templateDiv = browser.heading2(templateName).parentNode();
		ElementStub permissionsLink = browser.link("Permissions").in(templateDiv);
		assertTrue(permissionsLink.exists());
		permissionsLink.click();
		currentPageState.currentPageIs(Page.PERMISSIONS_PAGE_FOR_TEMPLATE);
	}

	@com.thoughtworks.gauge.Step("Verify that template <templateName> is present with disabled <link> link")
	public void verifyThatTemplateIsPresentWithDisabledLink(String templateName, String link) {
		verifyThatTemplatesArePresent(templateName);
		ElementStub templateDiv = browser.heading2(templateName).parentNode();
		ElementStub permissionsLink = browser.link(link).in(templateDiv);
		assertThat(permissionsLink.exists(), is(false));
	}

    @com.thoughtworks.gauge.Step("Verify that template <templateName> is present with enabled <link> link")
    public void verifyThatTemplateIsPresentWithEnabledLink(String templateName, String link) {
        verifyThatTemplatesArePresent(templateName);
        ElementStub templateDiv = browser.heading2(templateName).parentNode();
        ElementStub permissionsLink = browser.link(link).in(templateDiv);
        assertThat(permissionsLink.exists(), is(true));
    }

    @com.thoughtworks.gauge.Step("Verify <count> pipelines are associated with template <templateName>")
    public void verifyAssociatedPipelinesCountForTemplate(String count, String templateName) throws Exception {

        ElementStub templateDiv = browser.div("template_container_"+templateName);
        ElementStub pipelineTable = browser.table("list_table").in(templateDiv);
        ElementStub pipelines = browser.row("").in(pipelineTable);
        assertEquals(pipelines.countSimilar()-1,Integer.parseInt(count));


    }


	@com.thoughtworks.gauge.Step("Verify message <message> for template <templateName>")
	public void verifyMessageForTemplate(String message, String templateName) throws Exception {
		
		ElementStub templateDiv = browser.div("template_container_"+templateName);
		ElementStub pipelineTable = browser.table("list_table").in(templateDiv);
		ElementStub spanMessage = browser.span("").in(pipelineTable);
		assertEquals(spanMessage.getText().trim(),message+".");
		
	
	}
}
