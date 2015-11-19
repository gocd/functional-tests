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

package com.thoughtworks.cruise.api.feeds;

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.materials.Repository;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.CommaSeparatedParams;
import com.thoughtworks.cruise.utils.RuntimeVariableSubstituter;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Node;
import org.jaxen.JaxenException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class StagesFeedXml extends FeedXml{

	private final RepositoryState repoState;

	protected StagesFeedXml(ScenarioState state, RepositoryState repoState) {
		super(state);
		this.repoState = repoState;
	}

	@com.thoughtworks.gauge.Step("Verify stage link for current pipeline with counter <pipelineCounter> and stage <stageName> with stage counter <stageCounter> is approved by <approvedBy>")
	public void verifyStageLinkForCurrentPipelineWithCounterAndStageWithStageCounterIsApprovedBy(Integer pipelineCounter, String stageName,	Integer stageCounter, String approvedBy) throws Exception {
		String xpathPattern = titleXpath(pipelineCounter, stageName, stageCounter) + "/atom:link/@href";
		
		Attribute href = (Attribute) apiHelper.fetchNode(feed(), xpathPattern);
		assertThat(String.format("xpath %s not found in doc: %s", xpathPattern, feed().asXML()), href, is(not(nullValue())));
		
		Document stageDocument = apiHelper.loadXmlDocumentFromUrl(href.getValue());
		assertThat(stageDocument.selectSingleNode("/stage/@name").getStringValue(), is(stageName));
		
		assertThat(stageDocument.selectSingleNode("/stage/pipeline/@name").getStringValue(), is(state.currentRuntimePipelineName()));
		assertThat(stageDocument.selectSingleNode("/stage/pipeline/@counter").getStringValue(), is(pipelineCounter.toString()));
		
		
		assertThat(stageDocument.selectSingleNode("/stage/state").getStringValue(), is("Completed"));
		assertThat(stageDocument.selectSingleNode("/stage/result").getStringValue(), is("Passed"));
		assertThat(stageDocument.selectSingleNode("/stage/approvedBy").getStringValue(), is(approvedBy));		
		assertThat(stageDocument.selectNodes("/stage/jobs/job[contains(@href,'/go/api/jobs/')]").size(), is(greaterThan(0)));		
	}

    private String titleXpath(Integer pipelineCounter, String stageName, Integer stageCounter) {
        return entryTitleXpath(state.currentRuntimePipelineName(), pipelineCounter, stageName, stageCounter, "Passed");
    }

    public static String
    entryTitleXpath(final String pipelineName, Integer pipelineCounter, String stageName, Integer stageCounter, final String result) {
        String stageTitle = String.format("%s(%s) stage %s(%s) %s", pipelineName, pipelineCounter, stageName, stageCounter, result);
		return String.format( "/atom:feed/atom:entry[atom:title[.=\"%s\"]]", stageTitle);
    }

	@Override
	protected String feedUrl() {
		return Urls.stageFeedUrl(state.currentRuntimePipelineName());
	}

    @com.thoughtworks.gauge.Step("Verify the feed id is <idUrl>")
	public void verifyTheFeedIdIs(String idUrl) throws Exception {
        Document feed = feed();
        idUrl = new RuntimeVariableSubstituter(state).replaceRuntimeVariables(idUrl);
        assertThat(apiHelper.fetchNode(feed, "/atom:feed/atom:id").getText(), is(idUrl));
    }

    @com.thoughtworks.gauge.Step("For pipeline <pipelineName> - Stages Feed Xml")
	public void forPipeline(String pipelineName) throws Exception {
        state.usingPipeline(pipelineName);
    }

    @com.thoughtworks.gauge.Step("Verify the feed id matches <idUrlRegex>")
	public void verifyTheFeedIdMatches(String idUrlRegex) throws Exception {
        idUrlRegex = new RuntimeVariableSubstituter(state).replaceRuntimeVariables(idUrlRegex);
        assertThat(apiHelper.fetchNode(feed(), "/atom:feed/atom:id").getText().matches(idUrlRegex), is(true));
    }

    @com.thoughtworks.gauge.Step("Verify stage named <stageName> having pipeline counter <pipelineCounter> and stage counter <stageCounter> is triggered by <expectedApproverName>")
	public void verifyStageNamedHavingPipelineCounterAndStageCounterIsTriggeredBy(String stageName, Integer pipelineCounter, Integer stageCounter, String expectedApproverName) throws Exception {
        String approverName = fetchNode(stageName, pipelineCounter, stageCounter, "/go:author/go:name/.").getText();
        assertThat(approverName, is(expectedApproverName));
    }

    @com.thoughtworks.gauge.Step("Verify stage named <stageName> having pipeline counter <pipelineCounter> and stage counter <stageCounter> has commit author <expectedAuthorNames>")
	public void verifyStageNamedHavingPipelineCounterAndStageCounterHasCommitAuthor(String stageName, Integer pipelineCounter, Integer stageCounter, String expectedAuthorNames) throws Exception {        
        CommaSeparatedParams expectedNames = new CommaSeparatedParams(expectedAuthorNames);
        for(String expectedName : expectedNames) {
            Node nameNode = fetchNode(stageName, pipelineCounter, stageCounter, "/atom:author/atom:name[.='" + expectedName + "']");
            assertNotNull("Author node with name '" + expectedName + "' was not found.", nameNode);
        }
    }
    
    @com.thoughtworks.gauge.Step("Verify stage named <stageName> having pipeline counter <pipelineCounter> and stage counter <stageCounter> has commit author from latest commit of material with name <materialName>")
	public void verifyStageNamedHavingPipelineCounterAndStageCounterHasCommitAuthorFromLatestCommitOfMaterialWithName(
    		String stageName, Integer pipelineCounter, Integer stageCounter, String materialName) throws Exception {
    	Repository repo = repoState.getRepoByMaterialName(state.currentRuntimePipelineName(), materialName);
    	verifyStageNamedHavingPipelineCounterAndStageCounterHasCommitAuthor(stageName, pipelineCounter, stageCounter, repo.latestRevision().author());
	}

    private Node fetchNode(String stageName, Integer pipelineCounter, Integer stageCounter, String suffix) throws JaxenException {
        return apiHelper.fetchNode(feed(), titleXpath(pipelineCounter, stageName, stageCounter) + suffix);
    }

    @com.thoughtworks.gauge.Step("Verify stage named <stageName> having pipeline counter <pipelineCounter> and stage counter <stageCounter> having cards <cardNumbers> linked to <mingleUrl> project <projectName>")
	public void verifyStageNamedHavingPipelineCounterAndStageCounterHavingCardsLinkedToProject(String stageName, Integer pipelineCounter, Integer stageCounter, String cardNumbers, String mingleUrl, String projectName) throws Exception {
        CommaSeparatedParams expectedCards = new CommaSeparatedParams(cardNumbers);
        for(String expectedCard : expectedCards) {
            String expectedCardNumber = expectedCard.substring(1);//ignore #
            String apiLink = fetchNode(stageName, pipelineCounter, stageCounter, String.format("/atom:link[@title='%s'][@type='application/vnd.mingle+xml']/@href", expectedCard)).getText();
            assertThat(apiLink, is(String.format("%s/api/v2/projects/%s/cards/%s.xml", mingleUrl, projectName, expectedCardNumber)));

            String html = fetchNode(stageName, pipelineCounter, stageCounter, String.format("/atom:link[@title='%s'][@type='text/html']/@href", expectedCard)).getText();
            assertThat(html, is(String.format("%s/projects/%s/cards/%s", mingleUrl, projectName, expectedCardNumber)));
        }
    
    }

	
}
