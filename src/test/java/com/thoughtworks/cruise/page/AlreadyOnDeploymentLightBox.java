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

import com.thoughtworks.cruise.state.EnvironmentState;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.springframework.beans.factory.DisposableBean;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AlreadyOnDeploymentLightBox implements DisposableBean {
    private ScenarioState scenarioState;
    private EnvironmentState envState;
    private String materialName;
    private final RepositoryState repoState;
	private int materialIndex;
	private String selectedLongRevision;
	private String selectedShortRevision;
	private Browser browser;

	private class SuggestedRevVerifier implements Predicate {
		private final Action action;
		private final boolean shouldSuggestionBeShown;

		public SuggestedRevVerifier(Action action, boolean shouldSuggestionBeShown) {
			this.action = action;
			this.shouldSuggestionBeShown = shouldSuggestionBeShown;
		}

		public boolean call() throws Exception {
			action.performAction();
			Thread.sleep(10000);//tests doing this operation has turned flaky from 16.6 release, increasing the animation wait time to 10 secs from 150 mills initially
			ElementStub autoCompleteContent = browser.byId(autocompleteContentId());
			if (!autoCompleteContent.isVisible() && shouldBeShown()) {
				throw new RuntimeException("Material revision suggestion should have been shown");
			}
			return true;
		}

		public boolean shouldBeShown() {
			return shouldSuggestionBeShown;
		}
	}

	private interface Action {
		void performAction() throws Exception;
	}

    public AlreadyOnDeploymentLightBox(ScenarioState scenarioState, EnvironmentState envState, RepositoryState repoState, Browser browser) {
        this.scenarioState = scenarioState;
        this.envState = envState;
        this.repoState = repoState;
        this.browser = browser;
    }

    @com.thoughtworks.gauge.Step("Using material <materialName>")
	public void usingMaterial(final String materialName) {
        this.materialName = materialName;
        Action action = new Action() {
			public void performAction() {
				ElementStub elementMaterialSummary = browser.div(materialName).in(browser.div("/material_summaries/")).parentNode();
				elementMaterialSummary.click();
				String materialNumberId = browser.div("/material-number-/").in(elementMaterialSummary).fetch("id");
				materialIndex = Integer.parseInt(materialNumberId.substring("material-number-".length()));
			}
        };
		Assertions.waitUntil(Timeout.FIVE_SECONDS, new SuggestedRevVerifier(action, true));
    }

    public void usingPipelineMaterial(String pipelineName) throws Exception {
    	usingMaterial(scenarioState.expand(pipelineName));
	}

    private ElementStub elementMaterialSummary() {
        return browser.div("/material_summary/[" + materialIndex + "]");
    }

	public void verifyCurrentlyDeployedRevisionIsShown() throws Exception {
		verifyCurrentlyDeployedRevisionIs(envState.currentlyDeployed(scenarioState.currentPipeline(), materialName));
    }

	@com.thoughtworks.gauge.Step("Verify currently deployed revision is <currentlyDeployed>")
	public void verifyCurrentlyDeployedRevisionIs(String currentlyDeployed) {
		ElementStub element = browser.span(String.format("currently-deployed-%d", materialIndex));
		Assert.assertThat(scenarioState.expand(currentlyDeployed), Is.is(element.getText()));
	}

    public void selectLatestRevisionForDeploy() throws Exception {
    	searchForLatestRevisionNumber();
    }

    @com.thoughtworks.gauge.Step("Deploy")
	public void deploy() {
        submitButton().click();
    }

    @com.thoughtworks.gauge.Step("Close - Already on deployment light box")
	public void close() {
    	browser.byId("close").click();
    }

	private ElementStub submitButton() {
		return browser.submit("/MB_focusable/");
	}

	private ElementStub elementToDeployTextbox() {
		return browser.textbox(String.format("material-number-%s-autocomplete", materialIndex));
	}

    private ElementStub elementPeggedRevision() {
    	return browser.div(String.format("material-number-%s-pegged", materialIndex));
    }

	@com.thoughtworks.gauge.Step("Verify search box is displayed with <expectedMatches> matches")
	public void verifySearchBoxIsDisplayedWithMatches(final int expectedMatches) throws Exception {
		assertNumberOfSearchBoxMatches(expectedMatches, autocompleteContentId());
	}

	private String autocompleteContentId() {
		return "material-number-" + materialIndex + "-autocomplete-content";
	}

	private void assertNumberOfSearchBoxMatches(int expectedMatches, String autocompleteId) {
		int count = Integer.parseInt(browser.div(autocompleteId).fetch("getElementsByTagName('LI').length"));
		Assert.assertThat(count, Matchers.is(expectedMatches));
	}

	public void searchForPipelineInstance(String pipelineInstance) throws Exception {
		String[] parts = pipelineInstance.split("/");
		String searchStr = scenarioState.expand(parts[0]);
		for (int i = 1; i < parts.length; i++) {
			searchStr += "/" + parts[i];
		}
		searchFor(searchStr);
	}

	@com.thoughtworks.gauge.Step("Search for <msg>")
	public void searchFor(final String msg) throws Exception {
        searchFor(msg, true);
	}

	private void searchFor(final String msg, boolean shouldBeShown) {

		Action action = new Action() {
			public void performAction() throws Exception {
				ElementStub elementToDeployTextbox = elementToDeployTextbox();
				browser.execute("Sahi.BLUR_TIMEOUT = 30000");
				elementToDeployTextbox.setValue(msg);
				browser.execute("Sahi.BLUR_TIMEOUT = 30000");
			}
		};
		Assertions.waitUntil(Timeout.FIVE_SECONDS, new SuggestedRevVerifier(action, shouldBeShown));
	}

	public void searchForAndExpectMatches(final String msg, Integer count) throws Exception {
        searchFor(msg, count != 0);
		verifySearchBoxIsDisplayedWithMatches(count);
	}

	public void searchForLatestRevisionNumberAndExpectMatches(Integer count) throws Exception {
        searchFor(latestRevisionNumber(), count != 0);
        verifySearchBoxIsDisplayedWithMatches(count);
	}

	public void searchForLatestRevisionNumber() throws Exception {
        searchFor(latestRevisionNumber());
	}

	private String latestRevisionNumber() {
		return repoState.getRepoByMaterialName(scenarioState.currentRuntimePipelineName(), materialName).latestRevision().revisionNumber();
	}

	public void clearSearchBox() throws Exception {
		searchFor(" ");
	}

	@com.thoughtworks.gauge.Step("Select revision <oneBasedIndex> in search box")
	public void selectRevisionInSearchBox(Integer oneBasedIndex) throws Exception {
		ElementStub listItem = browser.listItem(oneBasedIndex-1).in(browser.div(autocompleteContentId()));
		ElementStub revisionElement = browser.div("/revision/").in(listItem);
		selectedLongRevision = revisionElement.fetch("title");
		if (selectedLongRevision == null) {
			Assert.fail("long revision not found");
		}
		selectedShortRevision = revisionElement.getText();
		if (selectedShortRevision == null) {
			Assert.fail("short revision not found");
		}
		listItem.click();
	}

    @com.thoughtworks.gauge.Step("Select revision <revision> for material <materialName>")
	public void selectRevisionForMaterial(final String revision, final String materialName) throws Exception {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            public boolean call() throws Exception {
                String expandedRevision = scenarioState.expand(revision);
                String expandedMaterialName = scenarioState.expand(materialName);
                usingMaterial(expandedMaterialName);
                for (ElementStub listItem : getRevisionList()) {
                    if (hasRevision(listItem, expandedRevision)) {
                        listItem.click();
                        return true;
                    }
                }
                browser.byId("MB_caption").click();
                return false;
            }
        });
    }

	public String getMaterialAutoCompleteId()
	{
		Integer index = materialIndex;
		return "material-number-" +  index.toString() + "-autocomplete-content";
	}

	private boolean hasRevision(ElementStub listItem, String revision) {
        return listItem.getText().contains(revision.trim());
	}

	private List<ElementStub> getRevisionList() {
		Integer i = 0;
		String revisionListId;
		List<ElementStub> revisionListItem = new ArrayList<ElementStub>();
		revisionListId = "matched-revision-" +  i.toString();

		while(browser.listItem(revisionListId ).near(browser.div(getMaterialAutoCompleteId())).exists())
		{
			revisionListId = "matched-revision-" +  i.toString();
			revisionListItem.add(browser.listItem(revisionListId).near(browser.div(getMaterialAutoCompleteId())));
			i++;
		}

		return revisionListItem;
	}

	@com.thoughtworks.gauge.Step("Verify material summary contains selected revision")
	public void verifyMaterialSummaryContainsSelectedRevision() throws Exception {
		if (selectedLongRevision == null || selectedShortRevision == null) {
			Assert.fail("No revision selected");
		}
		verifyMaterialSummaryContainsRevision(selectedShortRevision);
	}

	public void verifyMaterialSummaryContainsRevision(String revision) {
		revision = scenarioState.expand(revision);
		ElementStub actual = browser.div("material-number-" +  materialIndex);
		Assert.assertThat(actual.getText(), Matchers.is(revision));
		String longRevision = selectedLongRevision == null ? revision : selectedLongRevision;
		Assert.assertThat(actual.fetch("title"), Matchers.is(longRevision));
	}

	@com.thoughtworks.gauge.Step("Verify material summary is marked as modified")
	public void verifyMaterialSummaryIsMarkedAsModified() throws Exception {
		Assert.assertTrue("Expected to be marked as modified", isMaterialSummaryModified());
	}

	public void verifyMaterialSummaryIsNotMarkedAsModified() throws Exception {
		Assert.assertFalse("Expected to not be marked as modified", isMaterialSummaryModified());
	}

	private boolean isMaterialSummaryModified() throws Exception {
		ElementStub actual = browser.div("material-number-" + materialIndex);
		return actual.fetch("className").contains("updated");
	}

	@com.thoughtworks.gauge.Step("Using material number <oneBasedIndex>")
	public void usingMaterialNumber(final Integer oneBasedIndex) throws Exception {
		this.materialName = null;
		Action action = new Action() {
			public void performAction() {
				materialIndex = oneBasedIndex - 1;
				elementMaterialSummary().click();
			}
        };
		Assertions.waitUntil(Timeout.FIVE_SECONDS, new SuggestedRevVerifier(action, true));
	}

	@com.thoughtworks.gauge.Step("Verify last run revision is <revision>")
	public void verifyLastRunRevisionIs(String revision) throws Exception {
		ElementStub actualElement = browser.span("/currently-deployed/");
		String realRevision = repoState.getRevisionFromAlias(revision);
		Assert.assertThat(actualElement.fetch("title"), Is.is(realRevision));
	}

	@com.thoughtworks.gauge.Step("Trigger")
	public void trigger() throws Exception {
		deploy();
	}

	public void verifyThatTriggerButtonIsDisabled() throws Exception {
		Assertions.waitUntil(Timeout.FIVE_SECONDS, new Predicate() {

			@Override
			public boolean call() throws Exception {
				return submitButton().fetch("disabled").equals("true");
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify material is pegged with revision <revision>")
	public void verifyMaterialIsPeggedWithRevision(String revision) throws Exception {
		ElementStub searchBox = elementPeggedRevision();
		assertThat(searchBox.getText(), is(scenarioState.expand(revision)));
		verifyMaterialSummaryContainsRevision(revision);
	}

	@com.thoughtworks.gauge.Step("Change variable <name> to <value>")
	public void changeVariableTo(String name, String value) throws Exception {
		String identifier = String.format("variables[%s]", name);
		ElementStub textBox = browser.textbox(identifier);
		textBox.setValue(value);
	}

    @com.thoughtworks.gauge.Step("Select revision <revision> in search box for material number <materialNumber>")
	public void selectRevisionInSearchBoxForMaterialNumber(final Integer revision, final Integer materialNumber) throws Exception {
		Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
			@Override
			public boolean call() throws Exception {
				usingMaterialNumber(materialNumber);
				selectRevisionInSearchBox(revision);
				return true;
			}
		});
    }

	@com.thoughtworks.gauge.Step("Switch to <tabLabel> tab")
	public void switchToTab(String tabLabel) throws Exception {
		browser.link(tabLabel).in(browser.div("change_materials")).click();
	}

	@Override
	public void destroy() throws Exception {
		close();
	}

    @com.thoughtworks.gauge.Step("Override secure variable named <key> with value <value>")
	public void overrideSecureVariableNamedWithValue(String key, String value) throws Exception {
        ElementStub valueField = browser.password(String.format("secure_variables[%s]", key));
        ElementStub overrideLink = browser.link("Override").in(valueField.parentNode());
        overrideLink.click();
        valueField.setValue(value);
    }

    @com.thoughtworks.gauge.Step("Override variable named <key> with value <value>")
	public void overrideVariableNamedWithValue(String key, String value) throws Exception {
        ElementStub valueField = browser.textbox(String.format("variables[%s]", key));
        valueField.setValue(value);
    }
}
