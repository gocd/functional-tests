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
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;

import static org.junit.Assert.assertThat;

public class OnLoginPage extends CruisePage {
	private final ScenarioHelper scenarioHelper;

	public OnLoginPage(ScenarioState scenarioState, ScenarioHelper scenarioHelper, Browser browser) {
        super(scenarioState, browser);
		this.scenarioHelper = scenarioHelper;
    }

	@com.thoughtworks.gauge.Step("Login as <username>")
	public void loginAs(final String username) {
 		login(username);
 		verifyLoggedInUser(username);
		scenarioState.loggedInAs(username);
	}

	private void verifyLoggedInUser(final String username) {
		String current_url = browser.fetch("window.location.href");
		if (current_url.startsWith("https")) {
			browser.navigateTo(Urls.localhostSslUrlFor("/dashboard"), false);
		} else{
			browser.navigateTo(Urls.urlFor("/dashboard"), false);
		}
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {

			@Override
			public boolean call() throws Exception {
				ElementStub currentUser = browser.link(username);
				boolean result = currentUser.exists();
				if (!result) reloadPage();
				return result;
			}
		});
	}

	private void login(final String username) {
			loginWithUsernameAndPassword(username, "badger");
	}

    protected String url() {
        return Urls.urlFor("");
    }

	@com.thoughtworks.gauge.Step("Login as <username> should fail with message <msg>")
	public void loginAsShouldFailWithMessage(String username, String msg) throws Exception {
		login(username);
		assertLoginError(msg);
	}


	@com.thoughtworks.gauge.Step("On Login Page")
	public void goToLoginPage() throws Exception {
		navigateToURL();

	}

	@com.thoughtworks.gauge.Step("Assert login error <msg>")
	public void assertLoginError(String msg) {
		assertLoginErrorDisplayed(browser, msg);
	}

	public static void assertLoginErrorDisplayed(Browser browser, String msg) {
		assertThat(browser.div("error-box").getText(), Is.is(msg));
	}

	@com.thoughtworks.gauge.Step("Login with username <username> and password <password>")
	public void loginWithUsernameAndPassword(final String username, final String password) {
		Assertions.waitUntil(Timeout.TEN_SECONDS, new Assertions.Predicate() {
			public boolean call() throws Exception {
				new OnAnyPage(scenarioState, scenarioHelper, browser).logout();
				reloadPage();
				browser.textbox("user_login").setValue(username);
				browser.password("user_password").setValue(password);
				return true;
			}
		});
		browser.submit("signin2").click();
	
	}

	@com.thoughtworks.gauge.Step("User should get logged in as <username>")
	public void userShouldGetLoggedInAs(final String username) throws Exception {
 		verifyLoggedInUser(username);
	}
}
