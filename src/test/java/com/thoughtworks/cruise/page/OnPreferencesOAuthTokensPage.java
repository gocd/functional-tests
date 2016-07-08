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
import org.junit.Assert;

public class OnPreferencesOAuthTokensPage extends CruisePage {

	public OnPreferencesOAuthTokensPage(ScenarioState scenarioState, Browser browser) {
		super(scenarioState, browser);
	}

	@Override
	protected String url() {
		return Urls.localhostSslUrlFor("/oauth/user_tokens");
	}

	@com.thoughtworks.gauge.Step("Verify token <expectedToken> exists")
	public void verifyTokenExists(String expectedToken) {
		Assert.assertEquals("Expected to find OAuth token: " + expectedToken,
				browser.cell(expectedToken).in(browser.table("oauth_user_token_table")).exists(),
				true);
	}

	@com.thoughtworks.gauge.Step("On Preferences OAuth Tokens page")
	public void goToPreferencesOAuthPage() {
		navigateToURL();
	}
}
