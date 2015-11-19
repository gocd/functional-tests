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
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;

public class OnAnyPageOverLocalhostSsl extends CruisePage {

	public OnAnyPageOverLocalhostSsl(ScenarioState scenarioState, Browser browser) {
		super(scenarioState, false, browser);
	}	
		
	public void lookingAtPipeline(String pipelineName) throws Exception {
		scenarioState.usingPipeline(pipelineName);
	}

	@Override
	protected String url() {
		return Urls.localhostSslUrlFor("/");
	}
	
	@com.thoughtworks.gauge.Step("Logout - On any page over localhost ssl")
	public void logout() {
		super.logout();
	}
}
