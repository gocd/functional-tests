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

import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.context.WithNoUsers;
import com.thoughtworks.cruise.page.OnAnyPage;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.FileUtil;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import net.sf.sahi.client.Browser;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractConfiguration {

	private final String configFilePath;
	private final ScenarioState state;
	private final Configuration config;
    private final RepositoryState repositoryState;
	private final ScenarioHelper scenarioHelper;
	private final Browser browser;


	AbstractConfiguration(String configFilePath, Configuration config, ScenarioState state, RepositoryState repositoryState, ScenarioHelper scenarioHelper, Browser browser) {
		this.configFilePath = configFilePath;
		this.config = config;
		this.state = state;
		this.repositoryState = repositoryState;
		this.scenarioHelper = scenarioHelper;
		this.browser = browser;
	}

	public void setUp() throws Exception {
	    if (deleteAllUsersOnSetup()) {
			new OnAnyPage(state, scenarioHelper, browser).logout();
	        new WithNoUsers(new TalkToCruise(this.state)).setUp();
	    }
		loadDom(configFilePath);
	}

	protected boolean deleteAllUsersOnSetup() {
        return true;
    }

    public void tearDown() throws Exception {
		repositoryState.cleanupRepos();
	}

	private void loadDom(String path) throws Exception {
		String configXmlContents = FileUtil.readToEnd(getClass().getResourceAsStream(path));
		CruiseConfigDom dom = new CruiseConfigDom(configXmlContents);
		replacePipelineNames(dom);
		
		postProcess(dom);
		config.setDom(dom);
	}

	protected void postProcess(CruiseConfigDom dom) throws Exception {
	}

	private void replacePipelineNames(CruiseConfigDom dom) throws DocumentException, SAXException, URISyntaxException {
		Map<String, String> pipelineNames = dom.replacePipelineNames();
		for (Entry<String, String> pipelineName : pipelineNames.entrySet()) {
			state.pushPipeline(pipelineName.getKey(), pipelineName.getValue());
		}
	}

}