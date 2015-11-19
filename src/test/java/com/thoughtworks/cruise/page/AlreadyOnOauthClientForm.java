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
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class AlreadyOnOauthClientForm {

	private Browser browser;
	private String clientName;
	private String redirectUrl;

	public AlreadyOnOauthClientForm(Browser browser) {
		this.browser = browser;
	}

	@com.thoughtworks.gauge.Step("Using client name <clientName>")
	public void usingClientName(String clientName) throws Exception {
		this.clientName = clientName;
	}

	@com.thoughtworks.gauge.Step("Using redirect url <redirectUrl>")
	public void usingRedirectUrl(String redirectUrl) throws Exception {
		this.redirectUrl = redirectUrl;
	}

	@com.thoughtworks.gauge.Step("Add new auth client")
	public void addNewAuthClient() throws Exception {
		browser.textbox(PageElementIdentifiers.get(PageElementIdentifiers.OAUTH_CLIENT_NAME)).setValue(clientName);
        browser.textbox(PageElementIdentifiers.get(PageElementIdentifiers.OAUTH_CLIENT_REDIRECT_URI)).setValue(redirectUrl);
        ElementStub submitLink = browser.link("Submit");
        Assert.assertThat(submitLink.exists(), Is.is(true));
		submitLink.click();
	}

    @com.thoughtworks.gauge.Step("Change redirect url to <newRedirectUrl>")
	public void changeRedirectUrlTo(String newRedirectUrl) throws Exception {
		browser.textbox(PageElementIdentifiers.get(PageElementIdentifiers.OAUTH_CLIENT_REDIRECT_URI)).setValue(Urls.urlFor(newRedirectUrl));
	}

	@com.thoughtworks.gauge.Step("Update this client")
	public void updateThisClient() throws Exception {
		browser.link("Submit").click();
	}

	public void navigateBackToListing() throws Exception {
		browser.link("Back").click();
	}
}
