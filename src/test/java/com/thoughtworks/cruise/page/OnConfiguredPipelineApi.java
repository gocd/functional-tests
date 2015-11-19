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
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.DomUtil;
import org.apache.commons.httpclient.HttpException;
import org.dom4j.Element;
import org.junit.Assert;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

// JUnit Assert framework can be used for verification

public class OnConfiguredPipelineApi {
	
	private final ScenarioState state;
	private final TalkToCruise talkToCruise;
	
	public OnConfiguredPipelineApi(ScenarioState state, TalkToCruise talkToCruise) {
		this.state = state;
		this.talkToCruise = talkToCruise;
	}

	@com.thoughtworks.gauge.Step("Verify <pipelineName> is shown")
	public void verifyIsShown(String pipelineName) throws Exception {
		Element pipelineElement = getConfiguredPipelinesPage(pipelineName);
		Assert.assertThat("Pipeline element should exist", pipelineElement, is(not(nullValue())));
	}

	private Element getConfiguredPipelinesPage(String pipelineName)	throws IOException, HttpException {
		CruiseResponse response = talkToCruise.get(Urls.urlFor("/api/pipelines.xml"));
		String pipelineXpath = String.format("//pipelines/pipeline[@href='%s']", Urls.urlFor(String.format("/api/pipelines/%s/stages.xml", state.pipelineNamed(pipelineName))));
		Assert.assertThat(response.getStatus(), is(200));
		Element pipelineElement = (Element) DomUtil.getDomFor(response.getBody()).selectSingleNode(pipelineXpath);
		return pipelineElement;
	}

	@com.thoughtworks.gauge.Step("Verify <pipelineName> is not shown")
	public void verifyIsNotShown(String pipelineName) throws Exception {
		Element pipelineElement = getConfiguredPipelinesPage(pipelineName);
		Assert.assertThat(pipelineElement, is(nullValue()));
	}

	

}
