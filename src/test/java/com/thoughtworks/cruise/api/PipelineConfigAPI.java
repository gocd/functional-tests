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
import org.bouncycastle.util.encoders.Base64;
import org.hamcrest.Matchers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.StringContains.containsString;

public class PipelineConfigAPI {

    private boolean useConfigAPI = false;
    private String auth = "Basic "+new String(Base64.encode("admin:badger".getBytes())); //"Basic YWRtaW46YmFkZ2Vy";
    private String apiv3 = "application/vnd.go.cd.v3+json";
    private String contentType = "application/json";

    @com.thoughtworks.gauge.Step("Using Pipeline Config API")
    public void useConfigAPI() throws IOException {
        useConfigAPI = true;

    }

    @com.thoughtworks.gauge.Step("Create pipeline <pipelineName> from <requestFile>")
    public void createPipeline(String pipeline, String file) throws IOException {

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", auth);
        headers.put("Accept", apiv3);
        headers.put("Content-Type", contentType);

        RestAssured.given().
                headers(headers).
                body(getFileAsString(getClass().getResourceAsStream("/config/config_json/" + file))).
                when().post(Urls.urlFor("/go/api/admin/pipelines")).then().statusCode(200);


    }


    @com.thoughtworks.gauge.Step("Update pipeline <pipeline> with <file>")
    public void updatePipelineNoError(String pipeline, String file) throws Exception {

        updatePipeline(pipeline,file)
                .then().statusCode(200);

    }


    @com.thoughtworks.gauge.Step("Verify pipeline <pipeline> has <element> has items <value>")
    public void verifyPipelinehasItems(String pipeline, String element, String value) throws Exception {

        getPipeline(pipeline).then().statusCode(200)
                .and().body(element,Matchers.hasItems(value));


    }

    @com.thoughtworks.gauge.Step("Verify pipeline <pipeline> has <element> has item <value>")
    public void verifyPipelinehasItem(String pipeline, String element, String value) throws Exception {

        getPipeline(pipeline).then().statusCode(200)
                .and().body(element,Matchers.hasItem(value));


    }


    @com.thoughtworks.gauge.Step("Verify pipeline <pipeline> has <element> contains <value>")
    public void verifyPipelinehasChange(String pipeline, String element, String value) throws Exception {

        getPipeline(pipeline).then().statusCode(200)
                .and().body(element,Matchers.containsString(value));

    }


    @com.thoughtworks.gauge.Step("Update pipeline <pipeline> with <file> should return error <error>")
    public void updatePipelineWithError(String pipeline, String file, String error) throws Exception {

        Response response = updatePipeline(pipeline,file);
        response.then().statusCode(422);
        assertThat(response.body().asString(), containsString(error) );

    }

    @com.thoughtworks.gauge.Step("Delete pipeline <pipeline> as <user> user should return access denied error")
    public void deletePipelineAsViewUser(String pipeline, String user) throws Exception {

        Response response = deletePipeline(pipeline,"view");
        response.then().statusCode(403);

    }

    @com.thoughtworks.gauge.Step("Delete pipeline <pipeline> as <user> user should have <element> as <value>")
    public void deletePipelineAsAdminUser(String pipeline, String user, String element, String value) throws Exception {

        Response response = deletePipeline(pipeline,"admin");
        response.then().statusCode(200).and().body(element,Matchers.containsString(value));

    }


    @com.thoughtworks.gauge.Step("Get pipeline <pipelineName>")
    public Response getPipeline(String pipeline) throws IOException {

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", auth);
        headers.put("Accept", apiv3);
        headers.put("Content-Type", contentType);

        Response response = RestAssured.given().
                headers(headers).
                when().get(Urls.urlFor("/go/api/admin/pipelines/" + pipeline));

        response.then().statusCode(200).and().body("name", equalTo(pipeline));

        return response;
    }

    private Response updatePipeline(String pipeline, String file) throws Exception {

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put("Authorization", auth);
        headers.put("Accept", apiv3);
        headers.put("Content-Type", contentType);
        headers.put("If-Match", getPipeline(pipeline).getHeader("Etag"));

        return RestAssured.given().
                headers(headers).
                body(getFileAsString(getClass().getResourceAsStream("/config/config_json/" + file))).
                when().put(Urls.urlFor("/go/api/admin/pipelines/"+pipeline));
    }

    private Response deletePipeline(String pipeline, String user) throws Exception {

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put("Authorization", "Basic "+new String(Base64.encode((user+":badger").getBytes())));
        headers.put("Accept", apiv3);
        headers.put("Content-Type", contentType);

        return RestAssured.given().
                headers(headers).
                when().delete(Urls.urlFor("/go/api/admin/pipelines/"+pipeline));
    }

    private String getFileAsString(InputStream in) throws IOException {

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String read;

        while ((read = br.readLine()) != null) {
            sb.append(read);
        }

        return sb.toString();
    }

}