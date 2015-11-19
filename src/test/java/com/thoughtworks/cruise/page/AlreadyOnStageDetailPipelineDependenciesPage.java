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
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AlreadyOnStageDetailPipelineDependenciesPage extends CruisePage {

	private final CurrentPageState currentPageState;
	private PipelinePartial pipelinePartial;
	private StageHeaderPartial stageHeaderPartial;

	public AlreadyOnStageDetailPipelineDependenciesPage(CurrentPageState currentPageState, ScenarioState scenarioState, Browser browser) {
		super(scenarioState, true, browser);
		this.currentPageState = currentPageState;
		currentPageState.assertCurrentPageIs(Page.STAGE_DETAILS);
		this.pipelinePartial = new PipelinePartial(scenarioState, this, browser);
		this.stageHeaderPartial = new StageHeaderPartial(scenarioState, this, browser);
	}

	@com.thoughtworks.gauge.Step("Verify pipeline has label <label>")
	public void verifyPipelineHasLabel(String label) throws Exception {
		assertThat(browser.link(0).in(browser.div("/content/").in(currentPipeline(scenarioState.currentRuntimePipelineName()))).getText().trim(), is(label));		
	}

	public void verifyDetailsAreFor(String pipelineName) throws Exception {
		assertThat(currentPipeline(scenarioState.pipelineNamed(pipelineName)), is(not(nullValue())));
	}

	private ElementStub currentPipeline(String pipelineName) {
		return browser.div(String.format("/current_%s/", pipelineName));
	}

	@com.thoughtworks.gauge.Step("Verify pipeline depends on <materialNameStrings>")
	public void verifyPipelineDependsOn(String materialNameStrings) throws Exception {
		for (String materialName : materialNameStrings.split(",")) {
			assertThat(browser.div(String.format("/%s/", materialName.trim())).in(browser.div("upstream")).exists(), is(true));
		}
	}

	private ElementStub downstreamPipeline(String pipeline) {
		return browser.div("downstream_" + pipeline);
	}

	@com.thoughtworks.gauge.Step("Current pipeline being <currentPipeline>")
	public void currentPipelineBeing(String currentPipeline) throws Exception {
		scenarioState.usingPipeline(currentPipeline);
	}

	@com.thoughtworks.gauge.Step("Verfiy pipeline has dependencies <pipelineNames>")
	public void verfiyPipelineHasDependencies(String pipelineNames) throws Exception {
		String[] pipelines = pipelineNames.split(",");
		for (String pipeline : pipelines) {
			assertThat(downstreamPipeline(scenarioState.pipelineNamed(pipeline.trim())), is(not(nullValue())));
		}
	}

	@com.thoughtworks.gauge.Step("Verify dependency <dependentPipeline> has runs <countersAsString>")
	public void verifyDependencyHasRuns(String dependentPipeline, String countersAsString) throws Exception {
		String[] counters = countersAsString.split(",");		
		for (String counter : counters) {
			assertThat(linkToDownstreamPipeline(scenarioState.pipelineNamed(dependentPipeline), counter), is(not(nullValue())));
		}
	}

	private ElementStub linkToDownstreamPipeline(String dependentPipeline, String counter) {
		return browser.link(counter).in(downstreamPipeline(dependentPipeline));
	}

	@com.thoughtworks.gauge.Step("Verify dependency <dependentPipeline> has no runs")
	public void verifyDependencyHasNoRuns(String dependentPipeline) throws Exception {
		String actualPipelineName = scenarioState.pipelineNamed(dependentPipeline);
		assertThat(browser.div(Regex.wholeWord("Unknown")).in(downstreamPipeline(actualPipelineName)).fetch("title"), containsString("(Unknown)"));
	}

	public void navigateToRunOfPipeline(String counter, String pipelineName) throws Exception {
		linkToDownstreamPipeline(scenarioState.pipelineNamed(pipelineName), counter).click();
		currentPageState.currentPageIs(Page.PIPELINE_DETAILS);
	}

	private ElementStub pipelineInStatusBar() {
		return browser.div("/pipeline/").in(browser.div("pipeline_status_bar"));
	}

	public void verifyPipelineLabelOnHeaderIs(String label) throws Exception {
		assertThat(browser.link(0).in(browser.div("/label/").in(pipelineInStatusBar())).getText().trim(), is(label));
	}

	public void verifyPipelineWasTriggeredBy(String userName) throws Exception {
		assertThat(browser.span("/who/").in(browser.div("/schedule_info/").in(browser.div("page_status_bar"))).getText().trim(), is(userName));
	}

	public void verifyPipelineIs(String lockedStatus) throws Exception {
		assertThat(browser.span(0).in(browser.div("/locked_instance/").in(pipelineInStatusBar())).getText().trim(), is(lockedStatus));
	}

	@Override
	protected String url() {
		return browserWrapper.getCurrentUrl();
	}

	@com.thoughtworks.gauge.Step("Verify cannot trigger <pipeline>")
	public void verifyCannotTrigger(String pipeline) throws Exception {
		scenarioState.usingPipeline(pipeline); 
		pipelinePartial.verifyCannotTriggerWithOptions();
	}

	@com.thoughtworks.gauge.Step("Open trigger lightbox for <pipeline>")
	public void openTriggerLightboxFor(String pipeline) throws Exception {
		scenarioState.usingPipeline(pipeline);
		pipelinePartial.openTriggerWithOptions();
	}

	@com.thoughtworks.gauge.Step("Trigger stage <stageName> - Already On Stage Detail Pipeline Dependencies Page")
	public void triggerStage(String stageName) throws Exception {
		stageHeaderPartial.triggerStage(stageName);
	}

	@com.thoughtworks.gauge.Step("Verify stage <stage> does not have actions link - Already On Stage Detail Pipeline Dependencies Page")
	public void verifyStageDoesNotHaveActionsLink(String stage) throws Exception {
		stageHeaderPartial.verifyStageDoesNotHaveAnyAction(stage);
	}

	@com.thoughtworks.gauge.Step("Cancel <stage> - Already On Stage Detail Pipeline Dependencies Page")
	public void cancel(String stage) throws Exception {
		stageHeaderPartial.cancel(stage);
	}

	@com.thoughtworks.gauge.Step("Verify stage <stage> has action <actionName> - Already On Stage Detail Pipeline Dependencies Page")
	public void verifyStageHasAction(String stage, String actionName) throws Exception {
		stageHeaderPartial.verifyStageHasAction(stage, actionName);
	}	
	
	@com.thoughtworks.gauge.Step("Verify cruise footer - Already On Stage Detail Pipeline Dependencies Page")
	@Override
	public void verifyCruiseFooter() throws Exception {
		super.verifyCruiseFooter();
	}

    @com.thoughtworks.gauge.Step("Navigate to pipeline dependencies for <pipelineName> <pipelineCounter> <stageName> <stageCounter> - Already On Stage Detail Pipeline Dependencies Page")
	public void navigateToPipelineDependenciesFor(String pipelineName, Integer pipelineCounter, String stageName, Integer stageCounter) throws Exception {
    	browser.navigateTo(stageDetailsUrl(pipelineName, pipelineCounter.toString(), stageName, stageCounter.toString()) + "/pipeline");
        currentPageState.currentPageIs(CurrentPageState.Page.STAGE_DETAILS);
    }



	
    @com.thoughtworks.gauge.Step("Click label <upstreamPipelineLabel> for upstream pipeline <upstreamPipelineName>")
	public void clickLabelForUpstreamPipeline(String upstreamPipelineLabel, String upstreamPipelineName) throws Exception {
    	ElementStub topmostParentNode = browser.link(scenarioState.pipelineNamed(upstreamPipelineName)).parentNode().parentNode().parentNode();
    	browser.link(upstreamPipelineLabel).in(topmostParentNode).click();
	}

	@com.thoughtworks.gauge.Step("Verify that unauthorized access message is shown - Already On Stage Detail Pipeline Dependencies Page")
	public void verifyThatUnauthorizedAccessMessageIsShown() throws Exception {
		super.verifyThatUnauthorizedAccessMessageIsShown();
	}
}
