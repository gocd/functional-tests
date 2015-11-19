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

package com.thoughtworks.cruise.page.gadgets.renderer;

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.Matchers;

import static org.junit.Assert.assertThat;

public class OnGadgetRenderingPage {

	private Browser browser;
	private final ScenarioState scenarioState;

	public OnGadgetRenderingPage(Browser browser, ScenarioState scenarioState) {
		this.browser = browser;
		this.scenarioState = scenarioState;
		browser.navigateTo("http://localhost:3000");
	}


	@com.thoughtworks.gauge.Step("On Gadget Rendering Page")
	public void goToGadgetRenderingPage() throws Exception {
		this.browser.navigateTo("http://localhost:3000");
	}

	@com.thoughtworks.gauge.Step("Add pipeline status gadget for <pipelineName>")
	public void addPipelineStatusGadgetFor(String pipelineName) throws Exception {
		if (scenarioState.hasPipeline(pipelineName)) { 
			pipelineName = scenarioState.pipelineNamed(pipelineName); 
		} 
		browser.textbox("up_pipelineName").setValue(pipelineName);
		browser.submit("Add").click();
	}

	@com.thoughtworks.gauge.Step("Authorize gadget to access data using user <username>")
	public void authorizeGadgetToAccessDataUsingUser(String username) throws Exception {
		browser.link("Authorize").click();
		Browser popup = browser.popup("/.*Go.*/");
		loginIfRequired(popup, username);
		popup.link("authorize-yes").in(browser.byId("oauth_authorize_form")).click();
	}

	@com.thoughtworks.gauge.Step("Verify authorize link is present")
	public void verifyAuthorizeLinkIsPresent() throws Exception {
		assertThat(browser.link("Authorize").exists(), Matchers.is(true));
	}

	private void loginIfRequired(final Browser popup, final String username) {
		if (!popup.textbox("user_login").exists()) {
			return;
		}
 		Assertions.waitUntil(Timeout.TEN_SECONDS, new Assertions.Predicate() {
			public boolean call() throws Exception {
				popup.textbox("user_login").setValue(username);
				popup.password("user_password").setValue("badger");
				return true;
			}
		});
		popup.submit("signin2").click();
	}

	@com.thoughtworks.gauge.Step("Verify pipeline <pipelineName> is at label <label>")
	public void verifyPipelineIsAtLabel(final String pipelineName, final int label) throws Exception {
		Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
			public boolean call() throws Exception {
				return browser.div("Label: " + label).near(browser.link(scenarioState.pipelineNamed(pipelineName))).exists();
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify pipeline <pipelineName> has no history")
	public void verifyPipelineHasNoHistory(final String pipelineName) throws Exception {
		Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
			public boolean call() throws Exception {
				return browser.span("No historical data").near(browser.link(scenarioState.pipelineNamed(pipelineName))).exists();
			}
		});
	}

	@com.thoughtworks.gauge.Step("Log in as <username> using password <password>")
	public void logInAsUsingPassword(String username, String password) throws Exception {
		browser.textbox("email").setValue(username);
		browser.textbox("password").setValue(password);
		browser.submit("Submit").click();
	}

	@com.thoughtworks.gauge.Step("Logout - On Gadget rendering page")
	public void logout() throws Exception {
		browser.link("logout").click();
	}

	@com.thoughtworks.gauge.Step("Verify message contains <message> - On Gadget rendering page")
	public void verifyMessageContains(String message) throws Exception {
		final String runtimeMessage = scenarioState.expand(message);
		Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
			public boolean call() throws Exception {
				ElementStub gadget = browser.div("gadget_content");
				return gadget.getText().equals(runtimeMessage);
			}
		});
	}

}
