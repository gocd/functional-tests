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

public class OnAdministrationPage extends CruisePage {
	public OnAdministrationPage(ScenarioState state, Browser browser) {
		super(state, browser);
	}

	@com.thoughtworks.gauge.Step("Verify page title is <pageTitle> - On Administration Page")
	public void verifyPageTitleIs(String pageTitle) {
		super.verifyPageTitleIs(pageTitle);
	}

	@Override
	protected String url() {
		return Urls.urlFor("/admin/config_xml");
	}

}
