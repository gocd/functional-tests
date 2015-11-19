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
import com.thoughtworks.cruise.SahiBrowserWrapper;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AlreadyOnPackageDetailsPage {

	private Browser browser;
	private final CurrentPageState currentPageState;
	private SahiBrowserWrapper browserWrapper;
	
	

	public AlreadyOnPackageDetailsPage(Browser browser,
			CurrentPageState currentPageState) {
		this.browser = browser;
		this.currentPageState = currentPageState;
		this.browserWrapper = new SahiBrowserWrapper(browser);
		currentPageState.assertCurrentPageIs(Page.PACKAGE_DETAILS_PAGE);
	}

	@com.thoughtworks.gauge.Step("Verify package with name <packageName> and spec <packageSpec> is shown")
	public void verifyPackageWithNameAndSpecIsShown(String packageName, String packageSpec) {
		assertThat(browser.textbox("package_name_value").getValue(), is(packageName));
		assertThat(browser.textbox("package_PACKAGE_SPEC_value").getValue(), is(packageSpec));
	}

	@com.thoughtworks.gauge.Step("Verify delete button is <state>")
	public void verifyDeleteButtonIs(String state) {
		boolean deleteButtonState = false;
		if(state.equals("enabled")){
			deleteButtonState = true;
		}
		assertThat(browserWrapper.isEnabled(browser.button("delete_package")), is(deleteButtonState));
	}

	@com.thoughtworks.gauge.Step("Click on <pipelineUsedInLink> link")
	public void clickOnLink(String pipelineUsedInLink) {
		ElementStub link = browser.link("show_pipelines_used_in");
		assertThat(link.text(), is(pipelineUsedInLink));
		link.click();
	}

	@com.thoughtworks.gauge.Step("Verify pipeline <pipelineName> as link with group <groupName> on row <rowNumber>")
	public void verifyPipelineAsLinkWithGroupOnRow(String pipelineName, String groupName, Integer rowNumber ){
		ElementStub pipelinesTable = browser.table("list_table");
		ElementStub row = browser.row(rowNumber).in(pipelinesTable);
		ElementStub pipelineCell = browser.cell(0).in(row);
		ElementStub groupCell = browser.cell(1).in(row);
		assertThat(browser.link(Regex.startsWith(pipelineName)).in(pipelineCell).exists(),is(true));
		assertThat(groupCell.text(),is(groupName));
	}

	@com.thoughtworks.gauge.Step("Click on pipeline <pipelineName> on row <rowNumber>")
	public void clickOnPipelineOnRow(String pipelineName, Integer rowNumber){
		ElementStub pipelinesTable = browser.table("list_table");
		ElementStub row = browser.row(rowNumber).in(pipelinesTable);
		ElementStub pipelineCell = browser.cell(0).in(row);
		browser.link(Regex.startsWith(pipelineName)).in(pipelineCell).click();
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE);
	}

	@com.thoughtworks.gauge.Step("Delete package <packagName>")
	public void deletePackage(String packagName) throws Exception {
		browser.button(Regex.startsWith("delete_button_")).in(browser.div("form_buttons")).click();
		assertThat(browser.div("warning_prompt").isVisible(), is(true));
		assertThat(browser.div("ui-dialog-content").text(), is("You are about to delete package " + packagName));
		browser.span("PROCEED").click();
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate(){
			@Override
			public boolean call() throws Exception {
				return browser.div("messaging_wrapper").text().equals("Saved successfully.");
			}			
		});	
	}

	@com.thoughtworks.gauge.Step("Verify message <message> is shown in the heading")
	public void verifyMessageIsShownInTheHeading(String message) throws Exception {
		assertThat(browser.span(message).in(browser.div("form")).exists(), is(true));
	}
	
	@com.thoughtworks.gauge.Step("Verify message <message> is shown")
	public void verifyMessageIsShown(String message) throws Exception {
		assertThat(browser.div("information").containsText(message), is(true));

	}

	@com.thoughtworks.gauge.Step("Verify pipeline <pipelineName> with group <groupName> on row <rowNumber>")
	public void verifyPipelineWithGroupOnRow(String pipelineName, String groupName, Integer rowNumber) {
		ElementStub pipelinesTable = browser.table("list_table");
		ElementStub row = browser.row(rowNumber).in(pipelinesTable);
		ElementStub pipelineCell = browser.cell(0).in(row);
		ElementStub groupCell = browser.cell(1).in(row);
		assertThat(browser.link(Regex.startsWith(pipelineName)).in(pipelineCell).exists(),is(false));
		assertThat(pipelineCell.text().startsWith(pipelineName), is(true));
		assertThat(groupCell.text(),is(groupName));
	}
	
	@com.thoughtworks.gauge.Step("Verify tooltip message <message> when package cannot be deleted")
	public void verifyTooltipMessageWhenPackageCannotBeDeleted(String message){
		assertThat(browser.byId("delete_package").fetch("title").contains(message),is(true));		
	}
	
	@com.thoughtworks.gauge.Step("Verify tooltip message <message> when package can be deleted")
	public void verifyTooltipMessageWhenPackageCanBeDeleted(String message){
		String actionAttributeDetails[]=browser.byId("delete_package_form").fetch("action").split("/");
		assertThat(browser.byId("delete_button_"+actionAttributeDetails[(actionAttributeDetails.length-1)]).in(browser.div("form_buttons")).fetch("title").contains(message),is(true));		
	}
}
