/*
 * Copyright 2018 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thoughtworks.cruise.client;

import com.thoughtworks.cruise.state.CurrentUsernameProvider;
import com.thoughtworks.cruise.util.CruiseConstants;
import com.thoughtworks.cruise.util.URL;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class TalkToCruise {

    public final CurrentUsernameProvider currentUserNameProvider;
    private final List<String> apiV4Urls = Arrays.asList("/api/agents");
    private final List<String> apiV1Urls = Arrays.asList("/api/backups");
    private final List<String> apiV2Urls = Arrays.asList("/api/users");
    private final List<String> ancientUrls = Arrays.asList("/job_run_history");



    public TalkToCruise(CurrentUsernameProvider state) {
        this.currentUserNameProvider = state;
    }


    public CruiseResponse post(String url, List<NameValuePair> nameValuePairs){
        return post(url, toArray(nameValuePairs));
    }

    public CruiseResponse post(String url, NameValuePair... nameValuePairs) {
        HttpClient client = client();
        PostMethod post = new PostMethod(url);
        post.addParameters(nameValuePairs);
        if(supportApiV1(url)){
            post.setRequestHeader("Accept", CruiseConstants.apiV1);
            post.setRequestHeader("Content-Type", "application/json");
        }

        if(supportApiV2(url)){
            post.setRequestHeader("Accept", CruiseConstants.apiV2);
            post.setRequestHeader("Content-Type", "application/json");
        }
        post.setRequestHeader("CONFIRM","true");
        return execute(url, client, post);
    }

    public CruiseResponse post(String url, StringRequestEntity body, String CORSHeader, String version) {
        HttpClient client = client();
        PostMethod post = new PostMethod(url);
        post.setRequestEntity(body);
        post.setRequestHeader("Accept", version);
        post.setRequestHeader("Content-Type", "application/json");

        if (CORSHeader != "") {
            post.setRequestHeader(CORSHeader,"true");
        }

        return execute(url, client, post);
    }


    public CruiseResponse patch(String url, String body, NameValuePair... nameValuePairs) {

        HttpClient client = client();
        PostMethod post = createPatch(url + "?_HttpMethod=PATCH");
        post.addParameters(nameValuePairs);
        post.setRequestBody(body);
        return execute(url, client, post);
    }

    private PostMethod createPatch(String uri) {
        PostMethod post = new PostMethod(uri){
            @Override public String getName() { return "PATCH"; }
        };
        post.setRequestHeader("Accept", CruiseConstants.apiV4);
        post.setRequestHeader("Content-Type", "application/json");
        return post;
    }


    private CruiseResponse execute(String url, HttpClient client, EntityEnclosingMethod post) {
        try {
            post.setRequestHeader("CONFIRM","true");
            client.executeMethod(post);
            CruiseResponse response = new CruiseResponse(post);
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Error while trying to POST to url " + url + ".", e);
        }
    }

    public CruiseResponse post(String url, File file, String partName) throws IOException {
        HttpClient client = client();
        PostMethod filePost = new PostMethod(url);
        filePost.setRequestEntity(new MultipartRequestEntity(new Part[]{new FilePart(partName, file)}, filePost.getParams()));
        return execute(url, client, filePost);
    }

    public CruiseResponse put(String url, String text, String partName) {
        HttpClient client = client();
        PutMethod put = new PutMethod(url);
        put.setRequestEntity(new MultipartRequestEntity(new Part[]{new StringPart(partName, text)}, put.getParams()));
        return execute(url, client, put);
    }

    public CruiseResponse put(String url, RequestEntity reqEntity) {
        HttpClient client = client();
        PutMethod put = new PutMethod(url);
        put.setRequestEntity(reqEntity);
        return execute(url, client, put);
    }

    public CruiseResponse get(String url, List<NameValuePair> nameValuePairs) {
        return get(url, toArray(nameValuePairs));
    }

    public CruiseResponse get(String url, NameValuePair... nameValuePairs) {
        return get(url, true, nameValuePairs);
    }

    public CruiseResponse get(String url, boolean shouldFollowRedirect, NameValuePair... nameValuePairs) {
        URL uri = new URL(url);
        for (NameValuePair nameValuePair : nameValuePairs) {
            uri.addParameter(nameValuePair.getName(), nameValuePair.getValue());
        }
        GetMethod get = new GetMethod(uri.toString());
        if(supportApiV4(url) && !isAncient(url))
            get.setRequestHeader("Accept", CruiseConstants.apiV4);
        get.setFollowRedirects(shouldFollowRedirect);
        return execute(url, get, true);
    }

    public CruiseResponse delete(String url) {
        return delete(url, false, "");
    }

    public CruiseResponse delete(String url, boolean newApi, String accept) {
        DeleteMethod del = new DeleteMethod(url);
        if (newApi) {
            del.addRequestHeader("Accept", accept);
        }
        return execute(url, del, true);
    }

    public CruiseResponse getWithBasicAuth(String url, String headerName, String headerValue, boolean withBasicAuth) {
        GetMethod get = new GetMethod(url);
        get.addRequestHeader(headerName, headerValue);
        return execute(url, get, withBasicAuth);
    }

    private CruiseResponse execute(String url, HttpMethodBase method, boolean useBasicAuth) {
        try {
            client(useBasicAuth).executeMethod(method);
            return new CruiseResponse(method);
        } catch (IOException e) {
            throw new RuntimeException("Error while trying to GET url " + url + ".", e);
        }
    }

    public static class CruiseResponse {
        private final int status;
        private final String body;
        private URI uri;
        private final HttpMethodBase method;

        public CruiseResponse(HttpMethodBase method) {
            this.method = method;
            this.status = method.getStatusCode();
            try {
                this.body = method.getResponseBodyAsString();
                this.uri = method.getURI();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public int getStatus() {
            return status;
        }

        public String getBody() {
            return body;
        }

        public boolean isSuccess() {
            return (status >= 200 && status < 400);
        }

        public boolean isForbidden() {
            return status == HttpStatus.SC_FORBIDDEN;
        }

        public boolean isUnAuthorized() {
            return status == HttpStatus.SC_UNAUTHORIZED;
        }

        @Override
        public String toString() {
            return String.format("Response-> status: %s | body: %s ------ Request-> url: %s ", status, body, uri);
        }

        public String getResponseHeader(String header) {
            Header responseHeader = method.getResponseHeader(header);
            return responseHeader == null ? null : responseHeader.getValue();
        }
    }

    private NameValuePair[] toArray(List<NameValuePair> nameValuePairs) {
        return nameValuePairs.toArray(new NameValuePair[nameValuePairs.size()]);
    }

    private HttpClient client() {
        return client(true);
    }

    private HttpClient client(boolean shouldUseBasicAuth) {
        HttpClient httpClient = new HttpClient();
        String userName = currentUserNameProvider.loggedInUser();
        if (userName != null && shouldUseBasicAuth) {
            httpClient.getParams().setAuthenticationPreemptive(true);
            httpClient.getState().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, "badger"));
        }
        return httpClient;
    }

    private boolean supportApiV1(String url){
        for(String subUrl: apiV1Urls){
            return url.contains(subUrl);
        }
        return false;
    }

    private boolean supportApiV2(String url){
        for(String subUrl: apiV2Urls){
            return url.contains(subUrl);
        }
        return false;
    }

    private boolean supportApiV4(String url){
        for(String subUrl: apiV4Urls){
            return url.contains(subUrl);
        }
        return false;
    }

    private boolean isAncient(String url){
        for(String subUrl: ancientUrls){
            return url.contains(subUrl);
        }
        return false;
    }

}
