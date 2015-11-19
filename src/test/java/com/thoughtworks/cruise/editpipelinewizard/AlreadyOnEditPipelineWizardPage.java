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

package com.thoughtworks.cruise.editpipelinewizard;

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.page.CruiseAdminPage;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThat;

public class AlreadyOnEditPipelineWizardPage extends CruiseAdminPage {

	protected final CurrentPageState currentPageState;

	public AlreadyOnEditPipelineWizardPage(CurrentPageState currentPageState,
			ScenarioState scenarioState, Browser browser) {
		super(scenarioState, true, browser);
		this.currentPageState = currentPageState;
	}

	@Override
	protected String url() {
		return null;
	}

	public void clickSave() throws Exception {
		Assertions.waitUntil(Timeout.TEN_SECONDS, new Predicate() {
			@Override
			public boolean call() throws Exception {
				browser.submit("SAVE").click();
				return !browser.button("reload_config").exists();
			}
		});
	}

	public void clickReset() throws Exception {
		browser.link(Regex.matches("RESET")).click();
	}

	public void verifyButtonIsEnabled(String buttonName) throws Exception {
		assertTrue(browser.button(buttonName).exists());
	}

	public void verifyFieldIsDisabled(String fieldName) throws Exception {
		assertTrue(browser.textbox(fieldName).exists());
	}

	public void verifyResetButtonExists() throws Exception {
		assertThat(browser.link("RESET").exists(), Is.is(true));
	}

	public void openParametersPage() throws Exception {
		browser.link("Parameters").click();
		currentPageState
				.currentPageIs(Page.EDIT_PIPELINE_WIZARD_PARAMETERS_PAGE);
	}

	public void goToMaterialsPage() throws Exception {
		openMaterialListingPage();
	}

	public void goToProjectManagementPage() throws Exception {
		browser.link("Project Management").click();
		currentPageState
				.currentPageIs(Page.EDIT_PIPELINE_WIZARD_PROJECT_MANAGEMENT_PAGE);
	}

	public void verifyMessageIsDisplayed(String message) throws Exception {
		assertThat(isMessagePresent(message), Is.is(true));
	}

	public void verifySavedSuccessfully() {
		assertThat(isSavedSuccessfully(), Is.is(true));
	}

	public void verifySaveFailed() {
		assertThat(isFailure(), Is.is(true));
	}

	private boolean isFailure() {
		return isMessagePresent("Save failed, see errors below");
	}

	public boolean isSavedSuccessfully() {
		return isMessagePresent("Saved successfully.");
	}

	public void goToEnvironmentVariablesPage() throws Exception {
		browser.link("Environment Variables").click();
		currentPageState
				.currentPageIs(Page.EDIT_PIPELINE_WIZARD_ENVIRONMENT_PAGE);
	}

	public void openGeneralOptionsPage() throws Exception {
		browser.link("General Options").click();
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_GENERAL_PAGE);
	}

	public void openStageListingPage() throws Exception {
		browser.link(Regex.startsWith("Stages")).click();
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_STAGES_PAGE);

	}

	public void openMaterialListingPage() throws Exception {
		browser.link(Regex.startsWith("Materials")).click();
		currentPageState
				.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE);

	}

	public boolean isMessagePresent(final String value) {
		try {
			Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {

				@Override
				public boolean call() throws Exception {
					return browser.div(value).exists();
				}
			});
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}

	public void cancelDirtyCheckPrompt() throws Exception {
		ElementStub cancelButton = browser.button("/Cancel/i");
		Assert.assertThat(cancelButton.exists(), Is.is(true));
		cancelButton.click();
	}

	@com.thoughtworks.gauge.Step("Verify pipeline <pipelineName> is paused with message <message>")
	public void verifyPipelineIsPausedWithMessage(String pipelineName,
			String message) throws Exception {
		ElementStub unPauseButton = unpauseButton(pipelineName);
		Assert.assertThat(unPauseButton.exists(), Is.is(true));
		ElementStub pauseContainer = browser.div("pause_info_and_controls");
		String actualMessage = browser
				.span(Regex.wholeWord("pause_description")).in(pauseContainer)
				.text();
		Assert.assertThat(actualMessage, Is.is(message));
	}

	@com.thoughtworks.gauge.Step("Unpause pipeline <pipelineName>")
	public void unpausePipeline(String pipelineName) {
		unpauseButton(pipelineName).click();
	}

	private ElementStub unpauseButton(String pipelineName) {
		return browser.byId(String.format("unpause-%s",
				scenarioState.pipelineNamed(pipelineName)));
	}

	public void verifyCreatedSuccessfully() {
		Assert.assertThat(isMessagePresent("Pipeline successfully created."),
				Is.is(true));

	}

}