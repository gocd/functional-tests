/*************************GO-LICENSE-START*********************************
 * Copyright 2016 ThoughtWorks, Inc.
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

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.state.ScenarioState;
import org.bouncycastle.util.encoders.Base64;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

public class PluginInfoAPI {

    private String apiv3 = "application/vnd.go.cd.v3+json";
    private final ScenarioState state;

    public PluginInfoAPI(ScenarioState state) {
        this.state = state;
    }


    @com.thoughtworks.gauge.Step("Verify plugin with id <pluginId> is active")
    public void getPluginInfo(String pluginId) throws IOException {

        HashMap<String, String> headers = new HashMap<String, String>();
        if(state.loggedInUser() != null){
            headers.put("Authorization", "Basic "+new String(Base64.encode("admin:badger".getBytes())));
        }
        headers.put("Accept", apiv3);

        Response response = RestAssured.given().
                headers(headers).
                when().get(Urls.urlFor("/go/api/admin/plugin_info/" + pluginId));

        response.then().statusCode(200).and().body("id", equalTo(pluginId));
        response.then().body("status.state", equalTo("active"));


    }



}