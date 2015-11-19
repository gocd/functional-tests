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
import com.thoughtworks.cruise.page.OnLoginPage;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import net.sf.sahi.client.Browser;

public class LoginAs {
	private final ScenarioState state;
	private ScenarioHelper scenarioHelper;
	private final Browser browser;

	public LoginAs(ScenarioState state, ScenarioHelper scenarioHelper, Browser browser) {
		this.state = state;
		this.scenarioHelper = scenarioHelper;
		this.browser = browser;
	}
	
	@com.thoughtworks.gauge.Step("Login as <userName> - setup")
	public void setUp(String userName) throws Exception {
		//Logout is done explicitly here to avoid failures if teardown fails to run. This happens when there is a failure in setup
		new OnAnyPage(state, scenarioHelper, browser).logout();
		new OnLoginPage(state, scenarioHelper, browser).loginAs(userName);
		state.loggedInAs(userName);
	}

	@com.thoughtworks.gauge.Step("Login as <ignore> - teardown")
	public void tearDown(String ignore) throws Exception {
		new OnAnyPage(state, scenarioHelper, browser).logout();
		state.logOut();
	}

}
