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

package com.thoughtworks.cruise.client;

// JUnit Assert framework can be used for verification


import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.OauthState;
import com.thoughtworks.cruise.state.OauthState.OauthClient;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.hamcrest.core.Is;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

public class AsUserOnOauthClient {

	private Browser browser;
	private final OauthState oauthState;
	private final TalkToCruise talkToCruise;
	private String currentClient;
	private final ScenarioState state;
	private String username;

	public AsUserOnOauthClient(Browser browser, OauthState oauthState, TalkToCruise talkToCruise, ScenarioState state) {
		this.browser = browser;
		this.oauthState = oauthState;
		this.talkToCruise = talkToCruise;
		this.state = state;
	}

	@com.thoughtworks.gauge.Step("Request approval")
	public void requestApproval() throws Exception {
		OauthClient client = currentClient();
		browser.navigateTo(Urls.oauthAuthorize(client.getId(), client.getRedirectURI()));
	}

	@com.thoughtworks.gauge.Step("Approve pending request")
	public void approvePendingRequest() throws Exception {
		browser.link("authorize-yes").in(browser.byId("oauth_authorize_form")).click();
	}

	@com.thoughtworks.gauge.Step("Save authorization code")
	public void saveAuthorizationCode() throws Exception {
		String url = browser.fetch("window.location.href");
		String authCode = queryParam(url, "code");
		oauthState.getOauthClientNamed(currentClient).setAuthCode(authCode);
	}

	private String queryParam(String url, String param) throws URIException {
		URI uri = new URI(url, true);
		String[] queryParts = uri.getQuery().split("&");
		for (String queryPart : queryParts) {
			if (queryPart.startsWith(param + "=")) {
				return queryPart.substring(queryPart.indexOf("=")+1);
			}
		}
		throw new IllegalArgumentException("param not found");
	}

	@com.thoughtworks.gauge.Step("Gadget page for pipeline <pipelineName> should contain <content>")
	public void gadgetPageForPipelineShouldContain(String pipelineName, String content) throws Exception {
		verifyCanAccessUrlUsingAccessToken(Urls.gadgetUrlForPipeline(state.pipelineNamed(pipelineName)), content, true);
	}

	private void verifyCanAccessUrlUsingAccessToken(String url, String pageName, boolean canView) {
		CruiseResponse response = getUsingAccessToken(url);
		Assert.assertThat(response.getStatus(), Is.is(200));
		Assert.assertThat(response.getBody().contains(pageName), Is.is(canView));
	}

	private CruiseResponse getUsingAccessToken(String url) {
		OauthClient client = currentClient();
		String headervalue = String.format("Token token=\"%s\"", client.getAccessToken(username == null ? state.loggedInUser() : username));
		String headerName = "Authorization";
		//TODO: Clean this up a bit
		CruiseResponse response = talkToCruise.getWithBasicAuth(Urls.urlFor(url), headerName, headervalue, false);
		return response;
	}

	@com.thoughtworks.gauge.Step("Obtain access token")
	public void obtainAccessToken() throws Exception {
		OauthClient client = currentClient();
		CruiseResponse response = httpAccessToken(client);
		client.setAccessTokenForUser(state.loggedInUser(), accessToken(response));
	}

	private OauthClient currentClient() {
		return oauthState.getOauthClientNamed(currentClient);
	}

	private CruiseResponse httpAccessToken(OauthClient client) {
		return talkToCruise.post(Urls.sslUrlFor("/oauth/token"), requestParamsForAccessToken(client));
	}

	private NameValuePair[] requestParamsForAccessToken(OauthClient client) {
		NameValuePair authCode = new NameValuePair("code", client.getAuthCode());
		NameValuePair grantType = new NameValuePair("grant_type", "authorization-code");
		NameValuePair clientId = new NameValuePair("client_id", client.getId());
		NameValuePair clientSecret = new NameValuePair("client_secret", client.getSecret());
		NameValuePair redirectUri = new NameValuePair("redirect_uri", client.getRedirectURI());
		return new NameValuePair[] {authCode, grantType, clientId, clientSecret, redirectUri};
	}

	private String accessToken(CruiseResponse response) throws JSONException {
		return new JSONObject(response.getBody()).getString("access_token");
	}

	@com.thoughtworks.gauge.Step("For client <clientName>")
	public void forClient(String clientName) throws Exception {
		this.currentClient = clientName;
	}

	@com.thoughtworks.gauge.Step("Verify requesting approval redirects to <path> and returns with error <error>")
	public void verifyRequestingApprovalRedirectsToAndReturnsWithError(String path, String error) throws Exception {
		OauthClient client = currentClient();
		CruiseResponse response = talkToCruise.get(Urls.oauthAuthorize(client.getId(), client.getRedirectURI()), false);
		String location = response.getResponseHeader("Location");
		URI uri = new URI(location, true);
		Assert.assertThat(uri.getQuery().contains(error), Is.is(true));
		Assert.assertThat(uri.getPath().contains(path), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Verify obtaining access token returns error message <message> and status <returnCode>")
	public void verifyObtainingAccessTokenReturnsErrorMessageAndStatus(String message, int returnCode) throws Exception {
		CruiseResponse response = httpAccessToken(currentClient());
		Assert.assertThat(new JSONObject(response.getBody()).get("error").toString(), Is.is(message));
		Assert.assertThat(response.getStatus(), Is.is(returnCode));
	}

	@com.thoughtworks.gauge.Step("For user <username>")
	public void forUser(String username) throws Exception {
		this.username = username;
	}

	@com.thoughtworks.gauge.Step("<path> Should return code <returnCode>")
	public void shouldReturnCode(String path, int returnCode) throws Exception {
		Assert.assertThat(getUsingAccessToken(path).getStatus(), Is.is(returnCode));
	}
}
