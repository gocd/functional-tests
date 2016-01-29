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
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import net.sf.sahi.client.Browser;
import org.apache.commons.lang3.StringUtils;

public class TfsConfiguration extends AbstractConfiguration {

	public TfsConfiguration(Configuration config, ScenarioState state, RepositoryState repositoryState, ScenarioHelper scenarioHelper, Browser browser) throws Exception {
		super("/config/tfs-cruise-config.xml", config, state, repositoryState, scenarioHelper, browser);
	}

	// setUp and tearDown is needed because Twist scenarios need their contexts to support these otherwise they show compile errors!
	@com.thoughtworks.gauge.Step("Tfs configuration - setup")
	public void setUp() throws Exception {
		super.setUp();
	}

	protected void postProcess(CruiseConfigDom dom) throws Exception {
		String tfsPassword = System.getenv("TFS_SERVER_PASSWORD");
		if (StringUtils.isBlank(tfsPassword)) throw new RuntimeException(String.format("%s is not set", tfsPassword));
		for(String pipeline : dom.getPipelineNames()){
			dom.getMaterial(pipeline,"tfs_mat").attribute("password").setValue(tfsPassword);
		}
	}

	@com.thoughtworks.gauge.Step("Tfs configuration - teardown")
	public void tearDown() throws Exception {
		super.tearDown();
	}
}