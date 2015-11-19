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

import com.thoughtworks.cruise.page.OnAnyPage;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import net.sf.sahi.client.Browser;

public class LogoutOnExit {
	private final ScenarioState state;
	private ScenarioHelper scenarioHelper;
	private final Browser browser;

	public LogoutOnExit(ScenarioState state, ScenarioHelper scenarioHelper, Browser browser) {
		this.state = state;
		this.scenarioHelper = scenarioHelper;
		this.browser = browser;
	}
	
	@com.thoughtworks.gauge.Step("Logout on exit - setup")
	public void setUp() throws Exception {
	}

	@com.thoughtworks.gauge.Step("Logout on exit - teardown")
	public void tearDown() throws Exception {
		new OnAnyPage(state, scenarioHelper, browser).logout();
		state.logOut();
	}

}
