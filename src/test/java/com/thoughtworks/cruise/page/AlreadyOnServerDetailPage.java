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

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.CruiseAgents;
import com.thoughtworks.cruise.api.UsingAgentsApi;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;

public class AlreadyOnServerDetailPage extends OnServerDetailPage {

	public AlreadyOnServerDetailPage(ScenarioState state, UsingAgentsApi agentsApi, CruiseAgents createdAgents, Browser browser, CurrentPageState currentPageState) {
		super(state, agentsApi, createdAgents, browser, true);
		currentPageState.assertCurrentPageIs(Page.SERVER_DETAILS_PAGE);
	}

	public void verifyOnServerDetailsPage() throws Exception {
		super.verifyRedirectedToServerDetailPage();
	}
}
