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

package com.thoughtworks.cruise.context.configuration;

import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import net.sf.sahi.client.Browser;

public class DeletePackageConfiguration extends AbstractConfiguration {

	private Configuration config;
	
	public DeletePackageConfiguration(Configuration config, ScenarioState state,
			RepositoryState repositoryState, ScenarioHelper scenarioHelper, Browser browser) throws Exception {
		super("/config/delete-package-cruise-config.xml", config, state,
				repositoryState, scenarioHelper, browser);
		this.config = config;
	}

	// setUp and tearDown is needed because Twist scenarios need their contexts
	// to support these otherwise they show compile errors!
	@com.thoughtworks.gauge.Step("Delete package configuration - setup")
	public void setUp() throws Exception {
		super.setUp();
	}
	

	@com.thoughtworks.gauge.Step("Delete package configuration - teardown")
	public void tearDown() throws Exception {
		super.tearDown();
	}
}