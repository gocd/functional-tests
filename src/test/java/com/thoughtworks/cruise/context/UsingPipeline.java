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

package com.thoughtworks.cruise.context;

import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import org.dom4j.Element;

public class UsingPipeline {

	private CruiseConfigDom dom;
	private final ScenarioState scenarioState;
    private final RepositoryState repositoryState;
	private final Configuration configuration;

	public UsingPipeline(Configuration configuration, ScenarioState scenarioState, RepositoryState repositoryState) {
		this.configuration = configuration;
		this.scenarioState = scenarioState;
        this.repositoryState = repositoryState;
        this.dom = configuration.provideDom();
	}

	@com.thoughtworks.gauge.Step("Using pipeline <joinedPipelines> - setup")
	public void setUp(String joinedPipelines) throws Exception {
		String[] pipelines = joinedPipelines.split("\\s*,\\s*");
		waitUntilDOMHasPipelines(pipelines);
		removeOtherPipelines(pipelines);
		for(String pipeline : pipelines) {
			setupPipeline(pipeline);
		}
	}

	private void waitUntilDOMHasPipelines(final String[] pipelines) {
		Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {
			
			public boolean call() throws Exception {
				String[] newPipelineNames = new String[pipelines.length];
				int i = 0;
				for (String pipeline : pipelines) {
					newPipelineNames[i++] = scenarioState.pipelineNamed(pipeline);
				}
				return configuration.provideDom().containsPipelines(newPipelineNames);
			}
		});
		this.dom = configuration.provideDom();
	}

	private void removeOtherPipelines(String... pipelines) throws Exception {
		String[] convertedPipelineNames = new String[pipelines.length];
		for (int i = 0; i < pipelines.length; i++) {
			convertedPipelineNames[i] = scenarioState.pipelineNamed(pipelines[i]);
		}
		dom.removePipelinesExcept(convertedPipelineNames);
		System.out.println("Removed pipelines");
		copyConfigToServer();
	}

	private void setupPipeline(String pipeline) throws Exception {
		verifyPipelineExists(pipeline);
		createMaterialsForPipeline(pipeline);
		System.out.println("Finished setting up the materials");
		copyConfigToServer();
		scenarioState.usingPipeline(pipeline);
	}

	private void verifyPipelineExists(String pipeline) {
		if (!scenarioState.hasPipeline(pipeline)) {
			throw new RuntimeException("Pipeline '" + pipeline + "' does not exist");
		}
	}

	@com.thoughtworks.gauge.Step("Using pipeline <string1> - teardown")
	public void tearDown(String string1) throws Exception {
	}
	
	// #2705 Doing a blind copy need to migrate
	private void copyConfigToServer() throws Exception {
		configuration.setDom(dom);
	}

	private void createMaterialsForPipeline(String pipelineName) throws Exception {
		for (Element materialElement : dom.materialsForPipeline(scenarioState.pipelineNamed(pipelineName))) {
			repositoryState.createMaterial(materialElement);
		}
	}
}
