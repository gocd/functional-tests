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
import com.thoughtworks.cruise.Urls;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class FeatureToggleAPI {


    @com.thoughtworks.gauge.Step("Enable feature <feature>")
    public void enableFeature(String feature){
        toggle(feature, "on");
    }

    @com.thoughtworks.gauge.Step("Disable feature <feature>")
    public void disableFeature(String feature){
        toggle(feature, "off");
    }

    private void toggle(String feature, String value){

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");

        JSONObject body = new JSONObject();
        body.put("toggle_value",value);

        RestAssured.given().
                headers(headers).
                body(body.toString()).
                when().post(Urls.urlFor("/go/api/admin/feature_toggles/"+feature)).then().statusCode(200);
    }
}
