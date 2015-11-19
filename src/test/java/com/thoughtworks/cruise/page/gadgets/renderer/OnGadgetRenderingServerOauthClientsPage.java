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

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.state.OauthState;
import com.thoughtworks.cruise.state.OauthState.OauthClient;
import net.sf.sahi.client.Browser;

public class OnGadgetRenderingServerOauthClientsPage {

	private Browser browser;
	private final OauthState oauthState;
	private OauthClient client;
	private String service;

	public OnGadgetRenderingServerOauthClientsPage(Browser browser, OauthState oauthState) {
		this.browser = browser;
		this.oauthState = oauthState;
		browser.navigateTo("http://localhost:3000/gadgets/oauth_clients");
	}

	@com.thoughtworks.gauge.Step("Using client <clientName>")
	public void usingClient(String clientName) throws Exception {
		client = oauthState.getOauthClientNamed(clientName);
	}

	@com.thoughtworks.gauge.Step("Using service <service>")
	public void usingService(String service) throws Exception {
		this.service = service;
	}

	@com.thoughtworks.gauge.Step("Add client for gadget <gadgetName>")
	public void addClientForGadget(String gadgetName) throws Exception {
		browser.link("New gadgets_oauth_client").click();
		browser.textbox("gadgets_oauth_client[gadget_url]").setValue(Urls.gadgetUrl(gadgetName));
		browser.textbox("gadgets_oauth_client[client_id]").setValue(client.getId());
		browser.textbox("gadgets_oauth_client[client_secret]").setValue(client.getSecret());
		browser.textbox("gadgets_oauth_client[service_name]").setValue(service);
		browser.textbox("gadgets_oauth_client[redirect_uri]").setValue(client.getRedirectURI());
		browser.submit("Create").click();
	}
}
