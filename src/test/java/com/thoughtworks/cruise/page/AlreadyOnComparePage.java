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
import com.thoughtworks.cruise.materials.Repository;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.ExceptionUtils;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertThat;

public class AlreadyOnComparePage extends CruisePage  {
	
	private static final Pattern CARD_ACTIVITY_URL_PATTERN = Pattern.compile("^(.+)/\\d+/with/(\\d+)\\??.*$");
	private final CurrentPageState currentPageState;
	private final RepositoryState repositoryState;

	private enum BreadcrumbsPages {
		PIPELINES(CurrentPageState.Page.PIPELINE_DASHBOARD, "Pipelines"),
		COMPARE(CurrentPageState.Page.COMPARE_PAGE, "Compare"),
		PIPELINE_HISTORY(CurrentPageState.Page.PIPELINE_HISTORY) {
			public boolean matches(String linkText, ScenarioState scenarioState) {
				return scenarioState.getAllRuntimePipelineNames().contains(linkText);
			}
		};
		
		private final Page page;
		private final String matchText;

		BreadcrumbsPages(CurrentPageState.Page page, String matchText) {
			this.page = page;
			this.matchText = matchText;
		}
		
		BreadcrumbsPages(CurrentPageState.Page page) {
			this(page, null);
		}
		
		static CurrentPageState.Page getPageFor(String linkText, ScenarioState scenarioState) {
			for(BreadcrumbsPages page : values()) {
				if (page.matches(linkText, scenarioState)) return page.page;
			}
			throw ExceptionUtils.bomb("Don't know what page this one is");
		}

		boolean matches(String linkText, ScenarioState scenarioState) {
			return linkText.matches(matchText);
		}
	}

	public AlreadyOnComparePage(ScenarioState scenarioState, CurrentPageState currentPageState, RepositoryState repositoryState, Browser browser) {
		super(scenarioState, true, browser);
		this.currentPageState = currentPageState;
		this.repositoryState = repositoryState;
		currentPageState.assertCurrentPageIs(CurrentPageState.Page.COMPARE_PAGE);
	}

	@Override
	protected String url() {
		return "";
	}

	@com.thoughtworks.gauge.Step("Verify that page has gadget for url <url> project named <projectName> and criteria <mqlCriterion>")
	public void verifyThatPageHasGadgetForUrlProjectNamedAndCriteria(String url, String projectName, String mqlCriterion) throws Exception {
		String actualProjectName = browser.fetch("card_activity_gadget_options.up_project");
		String actualMqlCriterion = browser.fetch("card_activity_gadget_options.up_grouping_conditions");
		String actualUrl = browser.fetch("card_activity_gadget_url");
		
		Assert.assertThat(actualUrl, Is.is(url));
		Assert.assertThat(actualProjectName, Is.is(projectName));
		Assert.assertThat(actualMqlCriterion, Is.is(mqlCriterion));
	}

	@com.thoughtworks.gauge.Step("Verify that page displays stages <stagesWithStatus> in pipeline dropdown <pipelineComparatorType> with pipeline counter <pipelineCounter>")
	public void verifyThatPageDisplaysStagesInPipelineDropdownWithPipelineCounter(String stagesWithStatus, String pipelineComparatorType, String pipelineCounter) throws Exception {
		for (String stageWithStatus : stagesWithStatus.split(",")) {
			ElementStub stageBarInPipeline = stageBarInPipeline(pipelineComparatorType);
			verifyThatTextboxIsPopulatedWith(pipelineComparatorType, pipelineCounter);
            Assert.assertThat(stageBarInPipeline.containsHTML(stageWithStatus), Is.is(true));
		}
	}

	@com.thoughtworks.gauge.Step("Verify that <pipelineComparatorType> textbox is populated with <pipelineCounter>")
	public void verifyThatTextboxIsPopulatedWith(String pipelineComparatorType, String pipelineCounter) throws Exception {
	    String counter = getPipelineComparatorTextBox(pipelineComparatorType).value();
        Assert.assertThat(counter, Is.is(pipelineCounter));
    }

    private ElementStub getPipelineComparatorTextBox(String pipelineComparatorType) {
        return browser.byId(String.format("%s_pipeline", pipelineComparatorType));
    }
	
	@com.thoughtworks.gauge.Step("Click <linkText> in breadcrumbs")
	public void clickInBreadcrumbs(String linkText) throws Exception {
		linkText = scenarioState.expand(linkText);
		ElementStub breadcrumbsSection = browser.list("entity_title");
		browser.link(linkText).in(breadcrumbsSection).click();
		currentPageState.currentPageIs(BreadcrumbsPages.getPageFor(linkText, scenarioState));
	}

	public void verifyDisplayingComparisionOf(String urlFragment) throws Exception {
		Assert.assertThat(currentUrl(), Matchers.containsString(scenarioState.expand(urlFragment)));
	}

	

	@com.thoughtworks.gauge.Step("Select <tabName> tab")
	public void selectTab(String tabName) throws Exception {
		ElementStub link = browser.link(tabName);
		link.click();
	}

	public void verifyDisplaysRevisionHavingCommentUnderNamed(String revAlias, String comment, String materialType, String materialName) throws Exception {
		assertHasRevisionRow(repositoryState.getRevisionFromAlias(revAlias), comment, materialType + " - " + materialName);
	}
	
	@com.thoughtworks.gauge.Step("Verify displays revision <revAlias> having comment <comment> under <materialType> named <materialName> for pipeline <pipelineName>")
	public void verifyDisplaysRevisionHavingCommentUnderNamedForPipeline(String revAlias, String comment, String materialType, String materialName, String pipelineName) 
	       throws Exception {
	    Repository repoByMaterialName = repositoryState.getRepoByMaterialName(scenarioState.pipelineNamed(pipelineName), materialName);
	    String url = repoByMaterialName.getUrl();
	    String materialHeaderString = materialHeader(materialType, url);
		assertHasRevisionRow(repositoryState.getRevisionFromAlias(revAlias), comment, materialHeaderString);
    }
	
	private String materialHeader(String materialType, String url) throws Exception{
	    if(materialType.equalsIgnoreCase("mercurial"))
	    	return materialType + " - URL: " + url;
	    else if(materialType.equalsIgnoreCase("git"))
	    	return materialType + " - URL: " + url + ", Branch: master";
	    else throw new Exception("implement this for your fav material type :)");		
	}

	private void assertHasRevisionRow(String revision, String anotherColumnValue, String materialHeaderString) {
		ElementStub revisionRow = getRevisionRow(revision, materialHeaderString);
		Assert.assertThat(browser.cell(anotherColumnValue).in(revisionRow).exists(), Is.is(true));
	}

	
	private ElementStub getRevisionRow(String revision, String materialHeaderString){
		ElementStub materialHeader = browser.strong(materialHeaderString);
		ElementStub materialSection = materialHeader.parentNode().parentNode();
		
		return browser.cell(revision).in(materialSection).parentNode();

	} 
	private void assertHasRevisionRowPackage(String revision, String anotherColumnValue, String materialHeaderString) {
			ElementStub revisionRow = getRevisionRow(revision, materialHeaderString);
			Assert.assertThat(browser.cell("modified_by").in(revisionRow).text(), Matchers.containsString(anotherColumnValue));
		}

	
	@com.thoughtworks.gauge.Step("Diff with nth previous pipeline with n being <deltaWithPrevious>")
	public void diffWithNthPreviousPipelineWithNBeing(Integer deltaWithPrevious) throws Exception {
		Matcher matcher = CARD_ACTIVITY_URL_PATTERN.matcher(currentUrl());
		Assert.assertThat(matcher.matches(), Is.is(true));
		String higherCounterString = matcher.group(2);
		int higherCounter = Integer.parseInt(higherCounterString);
		String baseUrlFragment = matcher.group(1);
		browser.navigateTo(String.format("%s/%s/with/%s", baseUrlFragment, higherCounter - deltaWithPrevious, higherCounter));
	}

	@com.thoughtworks.gauge.Step("Verify displays revision <revision> having label <label> under pipeline named <pipelineName>")
	public void verifyDisplaysRevisionHavingLabelUnderPipelineNamed(String revision, String label, String pipelineName) throws Exception {
		assertHasRevisionRow(scenarioState.expand(revision), label, String.format("Pipeline - %s", scenarioState.expand(pipelineName)));
	}
	
	
    public void selectCounterOnDropdown(String counter, String pipelineComparatorType) throws Exception {
        getPipelineCounterLink(counter, pipelineComparatorType).click();
    }

    public void switchToPageOnDropdown(String pageNumber, String pipelineComparatorType) throws Exception {
        browser.byId(String.format("pipeline_history_%s_%s", pipelineComparatorType, pageNumber)).click();
    }

    public void verifyThatDropdownContainsPipelineCounter(String pipelineComparatorType, String counter) throws Exception {
        Assert.assertThat(getPipelineCounterLink(counter, pipelineComparatorType).exists(), Is.is(true));
    }
    
    @com.thoughtworks.gauge.Step("Search for <searchString> on <pipelineComparatorType> textbox")
	public void searchForOnTextbox(String searchString, String pipelineComparatorType) throws Exception {
        ElementStub textBox = getPipelineComparatorTextBox(pipelineComparatorType);
        textBox.setValue(searchString);
    }

    @com.thoughtworks.gauge.Step("Click on label <label> in the dropdown")
	public void clickOnLabelInTheDropdown(String label) throws Exception {
        labelFromDropdown(label).click();
    }
    
    @com.thoughtworks.gauge.Step("Verify dropdown has labels <items>")
	public void verifyDropdownHasLabels(String items) throws Exception {
        String[] labels = items.split(",");
        for (String label : labels) {
            assertLabelIsPresentInDropdown(label);
        }
    }
    
    @com.thoughtworks.gauge.Step("Search for revision <alias> on <pipelineComparatorType> textbox")
	public void searchForRevisionOnTextbox(String alias, String pipelineComparatorType) throws Exception {
        String revision = repositoryState.getRevisionFromAlias(alias);
        searchForOnTextbox(revision, pipelineComparatorType);
    }

    //PRIVATES!!!!
    
    private ElementStub getPipelineCounterLink(String counter, String pipelineComparatorType) {
        return browser.byId(String.format("stage_detail_%s_%s", pipelineComparatorType, counter));
    }
    
    private ElementStub stageBarInPipeline(String type) {
        String className = String.format("selected_pipeline_%s", type);
        return browser.div(Regex.matches(className));
    }
    
    private String currentUrl() {
        return browser.fetch("window.location.href");
    }

    private ElementStub labelFromDropdown(String label) {
        ElementStub dropdown = browser.div("ac_results");
        ElementStub labelFromDropDown = browser.heading3(label).in(dropdown);
        return labelFromDropDown;
    }
    
    private boolean assertLabelIsPresentInDropdown(final String label) {
        return Assertions.waitFor(Timeout.TWENTY_SECONDS, new Assertions.Function<Boolean>() {
            public Boolean call() {
                return labelFromDropdown(label).exists();
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify <materialType> material with name is not displayed")
	public void verifyMaterialWithNameIsNotDisplayed(String materialType) throws Exception {
        Assert.assertThat(browser.strong(Regex.startsWith(materialType + " - ")).exists(), Is.is(false));
    }

    @com.thoughtworks.gauge.Step("Verify that there are <materialCount> materials")
	public void verifyThatThereAreMaterials(Integer materialCount) throws Exception {
        Assert.assertThat(browserWrapper.collect("div", Regex.wholeWord("material_title")).size(), Is.is(materialCount));
    }

    @com.thoughtworks.gauge.Step("Click on <pipelineComparatorType> textbox")
	public void clickOnTextbox(String pipelineComparatorType) throws Exception {
        ElementStub textbox = getPipelineComparatorTextBox(pipelineComparatorType);
        textbox.click();        
    }

    @com.thoughtworks.gauge.Step("Verify <pipelineComparatorType> sugggestion box has <message>")
	public void verifySugggestionBoxHas(String pipelineComparatorType, String message) throws Exception {
        ElementStub container = browser.div("compare_search_instructions").in(browser.div(String.format("enhanced_dropdown %s", pipelineComparatorType)));
        Assert.assertThat(container.text(), Is.is(message));
    }

    @com.thoughtworks.gauge.Step("Click to browse the timeline on <pipelineComparatorType> suggestion box")
	public void clickToBrowseTheTimelineOnSuggestionBox(String pipelineComparatorType) throws Exception {
        browser.link(String.format("browse_timeline_link_%s",pipelineComparatorType)).click();
        currentPageState.currentPageIs(Page.COMPARE_PAGE_TIMELINE_POPUP);
    }

    @com.thoughtworks.gauge.Step("Verify that there is message <message>")
	public void verifyThatThereIsMessage(String message) throws Exception {
        ElementStub spanWithMessage = browser.span(message).in(browser.div("information"));
        assertThat(spanWithMessage.exists(), Is.is(true));
    }

    @com.thoughtworks.gauge.Step("Opt to see bisect diff")
	public void optToSeeBisectDiff() throws Exception {
        browser.link("Continue").click();
    }

    @com.thoughtworks.gauge.Step("Verify that there is a warning message <message>")
	public void verifyThatThereIsAWarningMessage(String message) throws Exception {
        ElementStub divWithMessage = browser.div(message).in(browser.div("information"));
        assertThat(divWithMessage.exists(), Is.is(true));
        assertThat(divWithMessage.fetch("className"), Is.is("warning"));
    }

	@com.thoughtworks.gauge.Step("Click on upstream pipeline revision <revision>")
	public void clickOnUpstreamPipelineRevision(String revision) throws Exception {
		browser.link(scenarioState.expand(revision)).click();
		currentPageState.currentPageIs(Page.STAGE_DETAILS);
	}

	@com.thoughtworks.gauge.Step("Verify that unauthorized access message is shown")
	public void verifyThatUnauthorizedAccessMessageIsShown() throws Exception {
		super.verifyThatUnauthorizedAccessMessageIsShown();
	}

	@com.thoughtworks.gauge.Step("Verify displays package with uri <uri> having spec <spec> with revision <revision> published by <user>")
	public void verifyDisplaysPackageWithUriHavingSpecWithRevisionPublishedBy(
			String uri, String spec, String revision, String user)
			throws Exception {
		
		assertHasRevisionRowPackage(scenarioState.expand(revision), user, String.format("Package - Repository: [repo_url=%s] - Package: [package_spec=%s]", scenarioState.expand(uri), spec));
		
	
	}	
}
