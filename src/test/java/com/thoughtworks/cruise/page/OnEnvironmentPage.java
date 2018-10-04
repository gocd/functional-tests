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

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.materials.Repository;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.EnvironmentState;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.hamcrest.text.StringContains;
import org.junit.Assert;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

public class OnEnvironmentPage extends CruisePage {
    private String environmentName;
    private boolean autoRefresh = false;
    private final RepositoryState repoState;
    private final EnvironmentState envState;
	private final ScenarioHelper scenarioHelper;
    private final CurrentPageState currentPageState;

    public OnEnvironmentPage(ScenarioState scenarioState, CurrentPageState currentPageState, RepositoryState repoState, EnvironmentState envState, 
    		ScenarioHelper scenarioHelper, boolean alreadyOn, Browser browser) {
        super(scenarioState, alreadyOn, browser);
        this.repoState = repoState;
        this.envState = envState;
		this.scenarioHelper = scenarioHelper;
		this.currentPageState = currentPageState;
    }
    
    public OnEnvironmentPage(ScenarioState scenarioState, CurrentPageState currentPageState, RepositoryState repoState, EnvironmentState envState, 
    		ScenarioHelper scenarioHelper, Browser browser) {
        this(scenarioState, currentPageState, repoState, envState, scenarioHelper, false, browser);        
    }

    @Override
    protected String url() {
        return Urls.urlFor("/admin/environments?autoRefresh=" + autoRefresh);
    }

    public void turnOnAutoRefresh() throws Exception {
        autoRefresh = true;
        open();
    }

    public void reloadPage() {
        if (!autoRefresh) {
            String url = browserWrapper.getCurrentUrl();
            browser.navigateTo(url, true);
        }
    }

    @com.thoughtworks.gauge.Step("Looking at <environmentName> environment")
	public void lookingAtEnvironment(String environmentName) throws Exception {
        this.environmentName = environmentName;

    }


    @com.thoughtworks.gauge.Step("On Environments Page")
    public void goToEnvironmentPage() throws Exception {
        navigateToURL();

    }

    @com.thoughtworks.gauge.Step("Verify pipeline is visible <pipelineName>")
	public void verifyPipelineIsVisible(String pipelineName) throws Exception {
        findPipelineTitleLink(pipelineName);
    }

    @com.thoughtworks.gauge.Step("Verify pipeline is not visible <pipelineName>")
	public void verifyPipelineIsNotVisible(final String pipelineName) throws Exception {
		final boolean[] sawPipeline = new boolean[1];
		sawPipeline[0] = false;
    	Assertions.waitUntil(Timeout.ONE_MINUTE, new ReloaderPredicate() {
			public String toString(){
				try {
					return "Should not contain pipeline " + pipelineName + ", but contained: " + pipeline(pipelineName);
				} catch (Exception e) {
					return "Should not contain pipeline " + pipelineName;
				}
			}

			@Override
			boolean callAfterReload() throws Exception {	
				sawPipeline[0] = pipeline(pipelineName).isVisible();
				return true;
			}
		});
        
    	assertThat(sawPipeline[0], Is.is(false));
    }

    @com.thoughtworks.gauge.Step("Verify <pipelineName> status is <value>")
	public void verifyStatusIs(String pipelineName, String value) throws Exception {
        Assert.assertThat(browser.span("message").in(browser.div("/status/").in(pipeline(pipelineName))).getText(), Is.is(value));
    }

    private ElementStub pipeline(String pipelineName) {
    	return browser.div("environment_pipeline_" + scenarioState.pipelineNamed(pipelineName) + "_panel"); //environment_basic-environment-pipelinea597d5da_panel
    }

    @com.thoughtworks.gauge.Step("Click on pipeline <pipelineName>")
	public void clickOnPipeline(String pipelineName) throws Exception {
    	browser.link(0).in(browser.heading3("/title/").in(findPipelineTitleLink(pipelineName))).click();
    }

    private ElementStub findPipelineTitleLink(final String pipelineName) {
        ElementStub found = Assertions.waitFor(Timeout.THIRTY_SECONDS, new Function<ElementStub>() {
            public ElementStub call() {
                reloadPage();
                ElementStub pipeline = pipeline(pipelineName);
				if (!pipeline.exists()) throw new RuntimeException("Element does not exist"); // TODO: anything simpler?
                return pipeline;
            }
        });
        Assert.assertThat("Pipeline " + pipelineName + " was not found in environment " + environmentName, found, Is.is(IsNot.not(IsNull
                .nullValue())));
        return found;
    }

    @com.thoughtworks.gauge.Step("Click on stage <stageName> of pipeline <pipelineName>")
	public void clickOnStageOfPipeline(String stageName, String pipelineName) throws Exception {
    	ElementStub link = null;
    	List<ElementStub> list = browserWrapper.collectIn("div", "/stage_bar/", pipeline(pipelineName));
    	for (ElementStub stageBar : list) {
			if (stageBar.fetch("title").contains(stageName)) 
				link = stageBar;
		}
		link.click();
    }

    @com.thoughtworks.gauge.Step("Verify on <pageName> page for <pipelineName>")
	public void verifyOnPageFor(String pageName, String pipelineName) throws Exception {
        String expectedTitle = scenarioState.pipelineNamed(pipelineName);
        verifyPageTitleIs(expectedTitle);
    }
    
	@com.thoughtworks.gauge.Step("Verify on stage details page for <pipelineName> stage <stageName>")
	public void verifyOnStageDetailsPageForStage(String pipelineName, String stageName) throws Exception {
		Assert.assertThat(browser.title(), Matchers.containsString("Stage Detail"));
		
		String foundPipelineName = browser.link(0).in(browser.listItem("name").in(browser.div("pipeline_header"))).getText();
		Assert.assertThat(foundPipelineName, Is.is(scenarioState.pipelineNamed(pipelineName)));

		String foundStageName = browser.link(0).in(browser.div("row1")).in(browser.listItem("stage selected")).getText();
		Assert.assertThat(foundStageName, Matchers.containsString(stageName));
	}

    private ElementStub elementPipeline() {
        return pipeline(scenarioState.currentPipeline());
    }

    private ElementStub elementStatus() {
        return browser.div("/status/").in(elementPipeline());
    }

    @com.thoughtworks.gauge.Step("Looking at pipeline <pipelineName> - On Environment Page")
	public void lookingAtPipeline(String pipelineName) throws Exception {
        scenarioState.usingPipeline(pipelineName);
        verifyPipelineIsVisible(pipelineName);
    }

    @com.thoughtworks.gauge.Step("Verify the pipeline has label <label>")
	public void verifyThePipelineHasLabel(String label) throws Exception {
        Assert.assertThat(pipelineLabel(), Is.is(label));
    }

    private String pipelineLabel() {
        String[] instance = browser.span("pipeline_run_label").in(elementStatus()).getText().split(":");
        return instance[1].trim();
    }

    private String elementStatusMessageText() {
        return browser.span("message").in(elementStatus()).getText().trim();
    }

    @com.thoughtworks.gauge.Step("Verify stage status <message> shows up for the pipeline")
	public void verifyStageStatusShowsUpForThePipeline(String message) throws Exception {
        Assert.assertThat(latestStageMessage(), Is.is(message));
    }

    private String latestStageMessage() {
        return browser.div("/latest_stage/").in(elementPipeline()).getText();
    }

    @com.thoughtworks.gauge.Step("Verify nothing is currently deployed")
	public void verifyNothingIsCurrentlyDeployed() throws Exception {
        Assert.assertThat(elementStatusMessageText(), Is.is("No historical data"));
    }

    @com.thoughtworks.gauge.Step("Deploy latest")
	public void deployLatest() throws Exception {
    	Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {
    		public boolean call() {
    			ElementStub deployButton = browser.byId(String.format("deploy-%s", scenarioState.currentRuntimePipelineName()));
    			if("false".equals(deployButton.fetch("disabled"))) deployButton.click();
    			return true;
    		}
    	});
    	
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
            public boolean call() {
    			ElementStub deployButton = browser.byId(String.format("deploy-%s", scenarioState.currentRuntimePipelineName()));
    			return "true".equals(deployButton.fetch("disabled"));
            }
        });

    }

    @com.thoughtworks.gauge.Step("Wait for the pipeline to have label <label>")
	public void waitForThePipelineToHaveLabel(final String label) throws Exception {
        Assertions.waitUntil(Timeout.FIVE_MINUTES, new ReloaderPredicate() {
            @Override
            public boolean callAfterReload() {
                return label.equals(pipelineLabel());
            }

            @Override
            public String toString() {
                return "pipelineLabel=" + label;
            }
        });
    }

    @com.thoughtworks.gauge.Step("Wait for status <stageStatus> to show up for the pipeline")
	public void waitForStatusToShowUpForThePipeline(final String stageStatus) throws Exception {
        Assertions.waitUntil(Timeout.FIVE_MINUTES, new ReloaderPredicate() {
            @Override
            public boolean callAfterReload() {
                return latestStageMessage().contains(stageStatus);
            }

            @Override
            public String toString() {
                return "stageStatus=" + stageStatus;
            }
        });
    }

    //we need this method here as it needed on environments page and we don't want to navigate away from environments page.
    @com.thoughtworks.gauge.Step("Stop <numberOfJobs> jobs waiting for file to exist")
	public void stopJobsWaitingForFileToExist(int numberOfJobs) throws Exception {
        scenarioHelper.stopJobsThatAreWaitingForFileToExist(numberOfJobs);
    }

    private ElementStub elementWarnMessage() {
        return browser.span("/warn_message/").in(elementPipeline());
    }

    @com.thoughtworks.gauge.Step("Verify status is <status>")
	public void verifyStatusIs(String status) throws Exception {
        reloadPage();
        verifyStatusIs(scenarioState.currentPipeline(), status);
    }

    @com.thoughtworks.gauge.Step("Verify has <numberOfMaterials> materials")
	public void verifyHasMaterials(final int numberOfMaterials) throws Exception {
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {

            @Override
            public boolean call() throws Exception {
                reloadPage();
                return elementMaterialsLink().getText().equals(numberOfMaterials + " Material(s):");
            }
        });
    }

    private ElementStub elementMaterialsLink() {
    	return browser.link(0).in(browser.div("/deployed_revisions/")).in(elementPipeline());
    }

    @com.thoughtworks.gauge.Step("Expand materials")
	public void expandMaterials() throws Exception {
    	verifyMaterialsAreClosed();
        elementMaterialsLink().click();
        verifyMaterialsAreExpanded();
    }

    private void verifyMaterialsAreClosed() {
        assertThat(elementPipeline().fetch("className"), StringContains.containsString("hidereveal_collapsed"));
    }

    private void verifyMaterialsAreExpanded() {
    	assertThat(elementPipeline().fetch("className"), IsNot.not(StringContains.containsString("hidereveal_collapsed")));
    }

    public void verifyMaterialHasRevision(final int materialIndex, final String revision) throws Exception {
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
            public boolean call() throws Exception {
                ElementStub revisionElement = browser.cell("revision_number").in(browser.row(materialIndex).in(browser.table("/materials/").in(elementPipeline())));
                return revisionElement.getText().trim().equals(revision);
            }
        });
    }
    
    @com.thoughtworks.gauge.Step("Verify material <materialIndex> with name <materialName> has latest revision")
	public void verifyMaterialWithNameHasLatestRevision(final int materialIndex, final String materialName) throws Exception {
    	Repository repo = repoState.getRepoByMaterialName(scenarioState.currentRuntimePipelineName(), materialName);
    	final String revision = repo.latestRevision().revisionNumber();
    	 Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
             public boolean call() throws Exception {
                 ElementStub revisionElement = browser.cell("revision_number wrapped_word").in(browser.row(materialIndex).in(browser.table("/materials/").in(elementPipeline())));
                 return revisionElement.fetch("title").equals(revision);
                // return revisionElement.getText().trim().equals(revision);
             }
         });
	}

    public void verifyMaterialsRemainExpanded() throws Exception {
        Assertions.sleepMillis(11000);
        verifyMaterialsAreExpanded();
    }

    public void collapseMaterials() throws Exception {
        verifyMaterialsAreExpanded();
        elementMaterialsLink().click();
        verifyMaterialsAreClosed();
    }

    public void verifyMaterialsRemainCollapsed() throws Exception {
        Assertions.sleepMillis(11000);
        verifyMaterialsAreClosed();
    }
    
    @com.thoughtworks.gauge.Step("Verify has new revisions")
	public void verifyHasNewRevisions() throws Exception {
        verifyMaterialHasNewRevisions();
    }

    @com.thoughtworks.gauge.Step("Verify does not have new revisions")
	public void verifyDoesNotHaveNewRevisions() throws Exception {
        Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
            @Override
            public boolean call() throws Exception {
            	reloadPage();
            	return !browser.div("/has_new_materials/").in(elementPipeline()).exists();
            }
        });
    }


    public void verifyMaterialHasNewRevisions() throws Exception {
    	waitForPipelineElement(Timeout.TWO_MINUTES, browser.div("/has_new_materials/"));
    }

    @com.thoughtworks.gauge.Step("Verify material <materialName> has new revisions")
	public void verifyMaterialHasNewRevisions(String materialName) throws Exception {
        waitForPipelineElement(Timeout.TWO_MINUTES, browser.image("has_new_material_revisions").near(browser.cell(materialName)));
    }

	public void waitForPipelineElement(Timeout timeout, final ElementStub expected) {
		Assertions.waitUntil(timeout, new Predicate() {
			@Override
			public boolean call() throws Exception {
				reloadPage();
				return expected.in(elementPipeline()).exists();
			}

			public String toString() {
				return "Element " + expected + " did not appear.";
			}
		});
	}

    @com.thoughtworks.gauge.Step("Change materials")
	public void changeMaterials() throws Exception {
        browser.submit("/change_revision/").click();
    }

    @com.thoughtworks.gauge.Step("Looking at pipeline <pipline> of <environment> environment")
	public void lookingAtPipelineOfEnvironment(String pipline, String environment) throws Exception {
        lookingAtEnvironment(environment);
        lookingAtPipeline(pipline);
    }

    public void verifyLatestOfMaterialIsCurrentlyDeployed(String materialName) throws Exception {
        Repository repo = repoState.getRepoByMaterialName(scenarioState.currentRuntimePipelineName(), materialName);
        assertThat(repo.latestRevision().hasRevisionNumber(deployedRevision(materialName)), Is.is(true));
    }

    private String deployedRevision(String materialName) {
    	ElementStub material = elementMaterialExpanded(materialName);
		return browser.cell("revision_number").near(material).getText();
    }

    private ElementStub elementMaterialExpanded(String materialName) {
    	return browser.cell(materialName).in(browser.table("/materials/").in(elementPipeline()));
    }

    public void rememberVersionOfCurrentlyDeployed(String materialName) throws Exception {
        envState.setCurrentlyDeployed(scenarioState.currentPipeline(), materialName, deployedRevision(materialName));
    }

	@com.thoughtworks.gauge.Step("Verify stage bar is not visible for pipeline <pipelineName>")
	public void verifyStageBarIsNotVisibleForPipeline(String pipelineName) throws Exception {
		assertThat(browser.div("stage").in(pipeline(pipelineName)).exists(), IsNot.not(true));
	}

	@com.thoughtworks.gauge.Step("Verify material number <materialNumber> has name <materialName>")
	public void verifyMaterialNumberHasName(Integer materialNumber, String materialName) throws Exception {
	   String actualMaterialName = materialName(materialNumber);
	   assertThat(actualMaterialName, Matchers.is(materialName));
	}
	
	private String materialName(Integer materialNumber) {
		return browser.cell(String.format("material_name wrapped_word[%d]", materialNumber-1)).in(elementPipeline()).getText();
	}

	private String materialRevision(Integer materialNumber) {
		return browser.cell(String.format("revision_number wrapped_word[%d]", materialNumber - 1)).in(elementPipeline()).fetch("title");
	}
	
	@com.thoughtworks.gauge.Step("Verify material number <materialNumber> has revision <revisionAlias> deployed")
	public void verifyMaterialNumberHasRevisionDeployed(Integer materialNumber, String revisionAlias) throws Exception {
		String actualRevision = materialRevision(materialNumber);
		String expectedRevision = repoState.getRevisionFromAlias(revisionAlias);
		assertThat(actualRevision, Matchers.is(expectedRevision));
	}

	@com.thoughtworks.gauge.Step("Verify material number <materialNumber> has a url for a name")
	public void verifyMaterialNumberHasAUrlForAName(Integer materialNumber) throws Exception {
	   String actualMaterialName = materialName(materialNumber);
	   assertThat(actualMaterialName, Matchers.containsString("/"));
	}

	@com.thoughtworks.gauge.Step("Verify cruise footer - On Environment Page")
	@Override
	public void verifyCruiseFooter() throws Exception {
		super.verifyCruiseFooter();
	}

	@com.thoughtworks.gauge.Step("Click add new environment link")
	public void clickAddNewEnvironmentLink() throws Exception {
		elementAddNewEnvironmentLink().click();
	}

	private ElementStub elementAddNewEnvironmentLink() {
		return browser.link(0).in(browser.div("add_new_environment"));
	}

	public void verifyMessageIsPresent(String message) {
		assertThat(browser.div("message_pane").in(browser.div("messaging_wrapper")).getText(), StringContains.containsString(message));
	}

	@com.thoughtworks.gauge.Step("Verify environment has <message> in it")
	public void verifyEnvironmentHasInIt(String message) {
		assertEquals(message, browser.span(0).in(elementEnvironment()).text());
	}

	private ElementStub elementEnvironment() {
		return browser.div("environment_" + environmentName + "_panel");
	}

	@com.thoughtworks.gauge.Step("Verify add new environment link is not visible")
	public void verifyAddNewEnvironmentLinkIsNotVisible() throws Exception {
		assertThat(elementAddNewEnvironmentLink().exists(), Is.is(false));
	}

	@com.thoughtworks.gauge.Step("Verify on environments page")
	public void verifyOnEnvironmentsPage() throws Exception {
		assertThat(browser.heading1("entity_title").text(), Is.is("Environments"));
	}

	@com.thoughtworks.gauge.Step("Click edit environment link for <environmentName>")
	public void clickEditEnvironmentLinkFor(String environmentName) throws Exception {
		browser.link("Environments").click();
        String environmentDiv = String.format("environment_%s_panel", environmentName);
        browser.link("icon16 setting").in(browser.div(environmentDiv)).click();
	}
    
    @com.thoughtworks.gauge.Step("Click compare link - On Environment Page")
	public void clickCompareLink() throws Exception {
        ElementStub link = browser.link("Compare").in(browser.div("environment_pipeline_" + scenarioState.currentRuntimePipelineName() + "_panel"));
        link.click();
        currentPageState.currentPageIs(Page.COMPARE_PAGE);
    }

	
}
