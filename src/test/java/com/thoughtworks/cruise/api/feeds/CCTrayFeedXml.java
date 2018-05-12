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
import com.thoughtworks.cruise.exceptions.CannotAccessUrlException;
import com.thoughtworks.cruise.materials.Repository;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

public class CCTrayFeedXml extends FeedXml{
	private final RepositoryState repositoryState;

	public CCTrayFeedXml(ScenarioState state, RepositoryState repositoryState) {
		super(state);
		this.repositoryState = repositoryState;	
	}
	
	@Override
	protected String feedUrl() {
		return Urls.ccTrayUrl();
	}
	
	@com.thoughtworks.gauge.Step("Looking at pipeline <pipelineName> - CC Tray Feed Xml")
	public void lookingAtPipeline(String pipelineName) throws Exception {        
        state.usingPipeline(pipelineName);
    }
	
	public Element getElementNode(String identifier)  throws Exception {
		Document jobDocument = apiHelper.loadXmlDocumentFromUrl(feedUrl());
		return (Element) jobDocument.selectSingleNode("/Projects/Project[@name='" + identifier+ "']");
	}
	
	@com.thoughtworks.gauge.Step("Verify cctray feed contains stage <stageName> job <jobName> with current activity <activity> with label <label> and last status <status>")
	public void verifyCctrayFeedContainsStageJobWithCurrentActivityWithLabelAndLastStatus(
			String stageName, String jobName, String activity, String label,
			String status) throws Exception {
		
		Element node = jobNode(stageName, jobName);
		
		assertThat(node.attribute("activity").getStringValue(), is(activity));
		assertThat(node.attribute("lastBuildStatus").getStringValue(), is(status));
		assertThat(node.attribute("lastBuildLabel").getStringValue(), is(label));
	}

    private Element jobNode(String stageName, String jobName) throws Exception {
        String jobValue = state.currentRuntimePipelineName() + " :: " + stageName  + " :: " + jobName; 
		Element node = getElementNode(jobValue);
        return node;
    }

	@com.thoughtworks.gauge.Step("Verify cctray feed contains stage <stageName> with current activity <activity> with label <label> and last status <status>")
	public void verifyCctrayFeedContainsStageWithCurrentActivityWithLabelAndLastStatus(
			String stageName, String activity, String label, String status)
			throws Exception {
	
		Element node = stageNode(stageName);
		
		assertThat(node.attribute("activity").getStringValue(), is(activity));
		assertThat(node.attribute("lastBuildStatus").getStringValue(), is(status));
		assertThat(node.attribute("lastBuildLabel").getStringValue(), is(label));
	}

    private Element stageNode(String stageName) throws Exception {
        String stageValue = state.currentRuntimePipelineName() + " :: " + stageName;				
		Element node = getElementNode(stageValue);
        return node;
    }	

	@com.thoughtworks.gauge.Step("Verify cctray feed contains stage <stageName> with relative weburl <webUrl>")
	public void verifyCctrayFeedContainsStageWithRelativeWeburl(String stageName,
			String webUrl) throws Exception {
		
		String expectedWebUrl = "/pipelines/" + state.currentRuntimePipelineName() + webUrl;
		
		Element node = stageNode(stageName);
		
		String actualWebUrl = node.attribute("webUrl").getStringValue();
		
		assertThat(actualWebUrl, containsString(expectedWebUrl));	
	}

	@com.thoughtworks.gauge.Step("Verify cctray feed contains stage <stageName> and job <jobName> with relative weburl <webUrl>")
	public void verifyCctrayFeedContainsStageAndJobWithRelativeWeburl(
			String stageName, String jobName, String webUrl) throws Exception {
		
		String expectedWebUrl = "/tab/build/detail/" + state.currentRuntimePipelineName() + webUrl;
		
		Element node = jobNode(stageName, jobName);
		
		String actualWebUrl = node.attribute("webUrl").getStringValue();
		
		assertThat(actualWebUrl, containsString(expectedWebUrl));		
	}	

	@com.thoughtworks.gauge.Step("Verify cctray does not contain this pipeline with stage <stageName>")
	public void verifyCctrayDoesNotContainThisPipelineWithStage(String stageName)
			throws Exception {		
	
		String identifier = state.currentRuntimePipelineName() + " :: " + stageName;
		Document jobDocument = apiHelper.loadXmlDocumentFromUrl(feedUrl());		
		Node node = jobDocument.selectSingleNode("/Projects/Project[@name='" + identifier+ "']");
		assertNull("Should not have found a node with that stage. But actually found: " + node, node);	
	}

	public void verifyCctrayFeedIsNotAccessible() throws Exception {
		try {
			apiHelper.loadXmlDocumentFromUrl(feedUrl());
			fail("Was expecting to get a 403. But seems to have passed while using the url: " + feedUrl());
		} catch (CannotAccessUrlException expected) {
			assertThat(expected.getCruiseResponse().getStatus(), is(403));
		}
	}

    public void verifyCctrayFeedContainsStageWithCurrentActivityWithLabelAndLastStatusWithBrokenByIncluding(
            String stageName, String activity, String label, String status, String breakers) throws Exception {
        verifyCctrayFeedContainsStageWithCurrentActivityWithLabelAndLastStatus(stageName, activity, label, status);
        Element stageNode = stageNode(stageName);
        assertBreakersAre(breakers, stageNode);
    }
    
    @com.thoughtworks.gauge.Step("Verify cctray feed contains stage <stageName> with current activity <activity> with label <label> and last status <status> with broken by authors of last commit for material with dest folder <materialDestFolder>")
	public void verifyCctrayFeedContainsStageWithCurrentActivityWithLabelAndLastStatusWithBrokenByAuthorsOfLastCommitForMaterialWithDestFolder(
    		String stageName, String activity, String label, String status, String materialDestFolder) throws Exception {
    	Repository repo = repositoryState.getRepositoryByDestinationFolder(materialDestFolder);
    	verifyCctrayFeedContainsStageWithCurrentActivityWithLabelAndLastStatusWithBrokenByIncluding(stageName, activity, label, status,repo.latestRevision().author());
	}

    private void assertBreakersAre(String breakers, Element projectNode) {
        String breakerNames = projectNode.selectSingleNode("./messages/message[@kind='Breakers']/@text").getText();
        assertThat(breakers(breakerNames), is(breakers(breakers)));
    }

    private Set<String> breakers(String breakerNamesStr) {
        Set<String> breakers = new HashSet<String>();
        for(String givenBreakerName : breakerNamesStr.split(",")) {
            breakers.add(givenBreakerName.trim());
        }
        return breakers;
    }

    public void verifyCctrayFeedContainsStageJobWithCurrentActivityWithLabelAndLastStatusWithBrokenByIncluding(String stageName, String jobName, String activity, String label,
            String status, String breakers) throws Exception {
        verifyCctrayFeedContainsStageJobWithCurrentActivityWithLabelAndLastStatus(stageName, jobName, activity, label, status);
        assertBreakersAre(breakers, jobNode(stageName, jobName));
    }

    @com.thoughtworks.gauge.Step("Verify cctray feed contains stage <stageName> job <jobName> with current activity <activity> with label <label> and last status <status> with broken by authors of last commit for material with dest folder <materialDestFolder>")
	public void verifyCctrayFeedContainsStageJobWithCurrentActivityWithLabelAndLastStatusWithBrokenByAuthorsOfLastCommitForMaterialWithDestFolder(
    		String stageName, String jobName, String activity, String label,String status, String materialDestFolder) throws Exception {
    	
    	Repository repo = repositoryState.getRepositoryByDestinationFolder(materialDestFolder);
    	verifyCctrayFeedContainsStageJobWithCurrentActivityWithLabelAndLastStatusWithBrokenByIncluding(stageName, jobName, activity, label, status, repo.latestRevision().author());
	}

		
}