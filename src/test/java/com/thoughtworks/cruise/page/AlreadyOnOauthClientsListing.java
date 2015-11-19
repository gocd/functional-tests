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

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.page.admin.PageElementIdentifiers;
import com.thoughtworks.cruise.page.admin.Predicate;
import com.thoughtworks.cruise.state.OauthState;
import com.thoughtworks.cruise.state.OauthState.OauthClient;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class AlreadyOnOauthClientsListing {

	private static final String CLIENT_ID = "Client ID";
	private static final String CLIENT_SECRET = "Client Secret";
	private static final String REDIRECT_URI = "[1]";
	private Browser browser;
	private final OauthState oauthState;

	public AlreadyOnOauthClientsListing(Browser browser, OauthState outhState) {
		this.browser = browser;
		this.oauthState = outhState;
	}
	
    @com.thoughtworks.gauge.Step("Edit client named <clientName>")
	public void editClientNamed(String clientName) throws Exception {
        browser.link(clientName).click();
    }

	@com.thoughtworks.gauge.Step("Delete all client entries")
	public void deleteAllClientEntries() throws Exception {
		ElementStub destroyLink = linkForDeletion();
		while(destroyLink.exists() && destroyLink.isVisible()) {
			deleteClient(destroyLink.parentNode("tr"));
			destroyLink = linkForDeletion();
		}
	}

	private ElementStub linkForDeletion() {
		Predicate predicate = (Predicate) PageElementIdentifiers.get(PageElementIdentifiers.OAUTH_DELETE_BUTTON);
		return (ElementStub) predicate.invoke(browser);
	}

	private void deleteClient(ElementStub row) {
		linkForDeletion().in(row).click();
	}

	@com.thoughtworks.gauge.Step("Navigate to new client form")
	public void navigateToNewClientForm() throws Exception {
		browser.link("/Add client/i").click();
	}

	@com.thoughtworks.gauge.Step("Remember client entries for <clientNames>")
	public void rememberClientEntriesFor(String clientNames) throws Exception {
		String[] clientNameList = clientNames.split(", ");
		for (String clientName : clientNameList) {
			ElementStub clientRow = elementOuthClient(clientName).parentNode("tr");
			String clientId = clientString(clientRow, CLIENT_ID);
			String clientSecret = clientString(clientRow, CLIENT_SECRET);
			String redirectURI = browser.cell(REDIRECT_URI).in(clientRow).getText();
			oauthState.saveOauthClient(clientName, new OauthClient(clientId, clientSecret, redirectURI));
		}
	}

    private String clientString(ElementStub clientRow, String fieldName) {        
        return browser.byXPath(String.format("//b[.='%s']", fieldName)).in(clientRow).parentNode().getText().split(":")[1].trim();
    }

	@com.thoughtworks.gauge.Step("Verify table shows <expectedUri> as redirect uri for <clientName>")
	public void verifyTableShowsAsRedirectUriFor(String expectedUri, String clientName) throws Exception {
		ElementStub row = elementOuthClient(clientName).parentNode("tr");
		String redirectUri = browser.cell(REDIRECT_URI).in(row).getText();
		Assert.assertThat(redirectUri, Is.is(Urls.urlFor(expectedUri)));
	}

	@com.thoughtworks.gauge.Step("Delete <clientName> entry")
	public void deleteEntry(String clientName) throws Exception {
		ElementStub cell = elementOuthClient(clientName).in(browser.row(1));
		deleteClient(cell.parentNode());
	}

	private ElementStub elementOuthClient(String clientName) {
		return browser.cell(clientName);
	}

	@com.thoughtworks.gauge.Step("Verify <clientName> does not show")
	public void verifyDoesNotShow(String clientName) throws Exception {
		Assert.assertThat(elementOuthClient(clientName).isVisible(), Is.is(false));
	}
}
