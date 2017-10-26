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

import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class AlreadyOnStageDetailMaterialsTab extends CruisePage {
	private final RepositoryState repositoryState;
	private String materialName;
	private String materialType;
	private String revision;

	public AlreadyOnStageDetailMaterialsTab(CurrentPageState currentPageState, ScenarioState scenarioState, RepositoryState repositoryState, Browser browser) {
		super(scenarioState, true, browser);
		this.repositoryState = repositoryState;
		currentPageState.assertCurrentPageIs(CurrentPageState.Page.STAGE_DETAIL_MATERIALS_TAB);
	}
	
	@Override
	protected String url() {
		return browserWrapper.getCurrentUrl();
	}

	@com.thoughtworks.gauge.Step("Verify commit <revisionAlias> is shown with user <userName> and comment <comment> for material <materialName> of type <materialType> - Already On Stage Detail Materials Tab")
	public void verifyCommitIsShownWithUserAndCommentForMaterialOfType(String revisionAlias, String userName, String comment, String materialName, String materialType) throws Exception {
		ElementStub materialSection = browser.div(AlreadyOnStageDetailMaterialsTab.materialMessage(materialName, materialType)).parentNode();
		assertThat(materialSection.exists(), Is.is(true));
		String rev = repositoryState.getRevisionFromAlias(revisionAlias);
		String text = materialSection.getText();
		assertThat(text, containsString(rev));
		assertThat(text, containsString(userName));
		assertThat(text, containsString(comment));
	}
	
	//Temporary solution until package is being implemented as full fledged material
	
	@com.thoughtworks.gauge.Step("Verify commit <revision> is shown with user <userName> and comment <comment> for package material <materialName> - Already on Stage Detail Materials Tab")
	public void verifyCommitIsShownWithUserAndCommentForPackageMaterial(String revision, String userName, String comment, String materialName) throws Exception {
		ElementStub materialSection = browser.div(AlreadyOnStageDetailMaterialsTab.materialMessage(materialName, "Package")).parentNode();
		assertThat(materialSection.exists(), Is.is(true));
		String text = materialSection.getText();
		assertThat(text, containsString(revision));
		assertThat(text, containsString(userName));
		assertThat(text, containsString(comment));
	}

	public void goToTab(String tabName) {
		browser.link(tabName).in(browser.div("sub_tabs_container")).click();
	}

	@com.thoughtworks.gauge.Step("Looking at <materialName> of type <materialType>")
	public void lookingAtOfType(String materialName, String materialType) throws Exception {
		this.materialName = scenarioState.expand(materialName);
		this.materialType = materialType;
	}

	@com.thoughtworks.gauge.Step("Looking at revision alias <revision>")
	public void lookingAtRevisionAlias(String revision) throws Exception {
		this.revision = repositoryState.getRevisionFromAlias(revision);
	}
	
	@com.thoughtworks.gauge.Step("Looking at revision <revision>")
	public void lookingAtRevision(String revision) throws Exception {
		this.revision = scenarioState.expand(revision);
	}
	
	@com.thoughtworks.gauge.Step("Click on revision <revision> - Already On Stage Detail Materials Tab")
	public void clickOnRevision(String revision) throws Exception {
		browser.link(scenarioState.expand(revision)).click();
	}
	

	private ElementStub getCurrentRevisionSection() {
		ElementStub materialSection = materialSection();
		ElementStub revisionSection = browser.div(revisionMessage(revision)).in(materialSection).parentNode();
		return revisionSection;
	}

	public void verifyHasComment(String comment) throws Exception {
		getVisibleSection(commentMessage(comment));
	}

	private ElementStub getVisibleSection(String text) {
		ElementStub textSection = browser.div(text).in(getCurrentRevisionSection());
		assertThat(textSection.isVisible(), Is.is(true));
		return textSection;
	}

	public void verifyHasUser(String userName) throws Exception {
		getVisibleSection(modificationMessage(userName));
	}

	@com.thoughtworks.gauge.Step("Verify has <filePath> marked as <action>")
	public void verifyHasMarkedAs(String filePath, String action) throws Exception {
		ElementStub materialSection = browser.div(AlreadyOnStageDetailMaterialsTab.materialMessage(materialName, materialType)).parentNode();
		assertThat(materialSection.exists(), Is.is(true));
		ElementStub modifiedFilesSection = browser.div("modified_files").in(materialSection);
		ElementStub file = browser.div(filePath).in(modifiedFilesSection);
		assertThat(file.exists(), Is.is(true));
	}

	private ElementStub materialSection() {
		return browser.div(materialMessage(materialName, materialType)).parentNode();
	}

	public static String materialMessage(String materialName, String materialType) {
		return materialType + " - " + materialName;
	}

	public static String revisionMessage(String rev) {
		return String.format("Revision: %s", rev);
	}

	public static String modificationMessage(String userName) {
		return String.format("/Modified by: %s/", userName);
	}

	public static String commentMessage(String comment) {
		return String.format("Comment: %s", comment);
	}

	@com.thoughtworks.gauge.Step("Verify has label <label>")
	public void verifyHasLabel(String label) throws Exception {
		getVisibleSection(dependencyRevisionLabelMessage(label));
	}

	public static String dependencyRevisionLabelMessage(String label) {
		return String.format("Instance: %s", label);
	}

	@com.thoughtworks.gauge.Step("Verify shows completed at")
	public void verifyShowsCompletedAt() throws Exception {
		ElementStub materialSection = materialSection();
		ElementStub completedAtSection = browser.div("completed_at").in(materialSection);
		assertThat(completedAtSection.getText().contains("Completed at:"), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Verify that unauthorized access message is shown - Already On Stage Detail Materials Tab")
	public void verifyThatUnauthorizedAccessMessageIsShown() throws Exception {
		super.verifyThatUnauthorizedAccessMessageIsShown();
	}

	@com.thoughtworks.gauge.Step("Verify revision <revision> having label <label> is shown for material <pipelineName> - Already On Stage Detail Materials Tab")
	public void verifyRevisionHavingLabelIsShownForMaterial(String revision, String label, String pipelineName) throws Exception {
		ElementStub materialSection = browser.div(AlreadyOnStageDetailMaterialsTab.materialMessage(scenarioState.expand(pipelineName), "Pipeline")).parentNode();
		assertThat(materialSection.exists(), Is.is(true));
		String text = materialSection.getText();
		assertThat(text.contains(scenarioState.expand(revision)), Is.is(true));
		assertThat(text.contains(label), Is.is(true));
	}	
}
