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

import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.ListUtil;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;

import java.util.ArrayList;

public class UsingGroup {

	private CruiseConfigDom dom;
	private final Configuration configuration;
	private final UsingPipeline usingPipeline;
	private final ScenarioState scenarioState;

	public UsingGroup(Configuration configuration, UsingPipeline usingPipeline,ScenarioState scenarioState) {
		this.configuration = configuration;
		this.usingPipeline = usingPipeline;
		this.scenarioState = scenarioState;		
        this.dom = configuration.provideDom();
	}

	@com.thoughtworks.gauge.Step("Using group <joinedGroups> - setup")
	public void setUp(String joinedGroups) throws Exception {
		String[] groups = joinedGroups.split("\\s*,\\s*");
		waitUntilDOMHasGroups(groups);
		String pipelines = covertToLogical(configuration.provideDom().pipelinesFor(groups));
		usingPipeline.setUp(pipelines);
	}

	private String covertToLogical(ArrayList<String> pipelines) {
		ArrayList<String> logicalNames = new ArrayList<String>();
		for (String runtimename : pipelines) {
			logicalNames.add(scenarioState.logicalPipelineName(runtimename));
		}				
		return ListUtil.join(logicalNames,",");
	}

	private void waitUntilDOMHasGroups(final String[] groups) {
		Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {			
			public boolean call() throws Exception {				
				return configuration.provideDom().containsGroups(groups);
			}
		});
		this.dom = configuration.provideDom();
	}

	@com.thoughtworks.gauge.Step("Using group <string1> - teardown")
	public void tearDown(String string1) throws Exception {
	
	}	
}
