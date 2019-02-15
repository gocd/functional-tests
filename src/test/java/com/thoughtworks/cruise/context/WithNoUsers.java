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

package com.thoughtworks.cruise.context;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import org.bouncycastle.util.encoders.Base64;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WithNoUsers {

    private final TalkToCruise talkToCruise;
	private String auth = "Basic "+new String(Base64.encode("admin:badger".getBytes()));
	private String apiVersion = "application/vnd.go.cd.v3+json";
	private String contentType = "application/json";

    public WithNoUsers(TalkToCruise talkToCruise) {
        this.talkToCruise = talkToCruise;
	}

	@com.thoughtworks.gauge.Step("With no users - setup")
	public void setUp() throws Exception {
		List<String> allUsers = getAllUsers();
		if( !allUsers.isEmpty()){
			disableUsers(allUsers).then().statusCode(200);
			deleteUsers(allUsers).then().statusCode(200);
		}
	}

	@com.thoughtworks.gauge.Step("With no users - teardown")
	public void tearDown() throws Exception {
	}


	private List<String> getAllUsers() throws Exception {

		Response response = RestAssured.given().
				headers(getHeaders()).
				when().get(Urls.urlFor("/go/api/users"));
		if (response.getStatusCode() != 200){
			System.out.println("No Users setup on server, moving on...");
			return Collections.emptyList();
		}
		JsonPath jsonPathEvaluator = response.jsonPath();

		List<String> allUsers = jsonPathEvaluator.getList("_embedded.users.login_name");
		allUsers.remove("admin");

		return allUsers;

	}


	private Response disableUsers(List<String> users){

		Response response = RestAssured.given().
				headers(getHeaders()).
				body(String.format("{ \"users\": %s, \"operations\": { \"enable\": false } }", users)).
				patch(Urls.urlFor("/go/api/users/operations/state"));
		return response;
	}

	private Response deleteUsers(List<String> users){

		Response response = RestAssured.given().
				headers(getHeaders()).
				body(String.format("{ \"users\": %s}", users)).
				delete(Urls.urlFor("/go/api/users"));

		return response;
	}

	private HashMap<String, String> getHeaders(){

		HashMap<String, String> headers = new HashMap<String, String>();

		headers.put("Authorization", auth);
		headers.put("Accept", apiVersion);
		headers.put("Content-Type", "application/json");

		return headers;

	}
}
