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

package com.thoughtworks.cruise.api;

// JUnit Assert framework can be used for verification

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.thoughtworks.cruise.ConfigureCruiseUsingApi;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import net.sf.sahi.client.Browser;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.bouncycastle.util.encoders.Base64;
import org.codehaus.jettison.json.JSONObject;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.io.IOException;
import java.util.HashMap;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class UsingEnvironmentApi {

	private Browser browser;
	private TalkToCruise talkToCruise;
	private String environmentUpdateUrl;
	private String envName;
	private String auth = "Basic "+new String(Base64.encode("admin:badger".getBytes()));
	private String apiv2 = "application/vnd.go.cd.v2+json";
	private String contentType = "application/json";

	public UsingEnvironmentApi(Browser browser, TalkToCruise talkToCruise) {
		this.browser = browser;
		this.talkToCruise = talkToCruise;
	}

	@com.thoughtworks.gauge.Step("Change environment name to <newEnvName>")
	public void changeEnvironmentNameTo(String newEnvName) throws Exception {
		Response response = getEnvironment(this.envName);
		HashMap<String, String> headers = new HashMap<String, String>();

		headers.put("Authorization", auth);
		headers.put("Accept", apiv2);
		headers.put("Content-Type", contentType);
		headers.put("If-Match", response.getHeader("Etag"));

		JSONObject jsonObj = new JSONObject(response.body().asString());
		String requestBody = jsonObj.putOpt("name",newEnvName).toString();

		Response updateResponse =  RestAssured.given().
				headers(headers).
				body(requestBody).
				when().put(environmentUpdateUrl);

		updateResponse.then().statusCode(200).and().body("name",equalTo(newEnvName));
	}

	public Response getEnvironment(String envName) throws IOException {

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", auth);
		headers.put("Accept", apiv2);
		headers.put("Content-Type", contentType);

		Response response = given().
				headers(headers).
				when().get(environmentUpdateUrl);

		response.then().statusCode(200).and().body("name", equalTo(envName));

		return response;
	}
	
	@com.thoughtworks.gauge.Step("For environment named <envName>")
	public void forEnvironmentNamed(String envName) throws Exception {
		this.environmentUpdateUrl = Urls.urlFor(String.format("/go/api/admin/environments/%s",envName));
		this.envName = envName;
	}
}
