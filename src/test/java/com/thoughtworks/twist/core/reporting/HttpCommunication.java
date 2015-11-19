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

package com.thoughtworks.twist.core.reporting;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.io.InputStream;

public class HttpCommunication {

    private HttpClient client;
    private final String hostname;
    private final String password;
    private final int port;
    private final String username;

    public HttpCommunication(String hostname, int port, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
        this.client = createClient();
    }

    private HttpClient createClient() {
        HttpClient client = new HttpClient();
        HostConfiguration hostConfiguration = new HostConfiguration();
        hostConfiguration.setHost(hostname, port);
        client.setHostConfiguration(hostConfiguration);

        if (!"".equals(username)) {
            client.getParams().setAuthenticationPreemptive(true);
            client.getState().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        }

        return client;
    }

    public InputStream getMethodBodyAsStream(String url) {
        GetMethod get = new GetMethod(url);
        try {
            int returnCode = client.executeMethod(get);
            while (!isReponseNotOK(returnCode) && returnCode != 200) {
                returnCode = client.executeMethod(get);
            }
            if (isReponseNotOK(returnCode)) {
                throw new RuntimeException("Dint find what you are looking for. Instead got " + returnCode + ". The URL was " + url);
            }
            return get.getResponseBodyAsStream();
        } catch (IOException e) {
            throw new RuntimeException("Damn. Something went hiediously wrong. The URL was " + url, e);
        }
    }

    private boolean isReponseNotOK(int returnCode) {
        return returnCode < 200 || returnCode > 202;
    }

    public String getMethodBodyAsString(String url) {
        GetMethod get = new GetMethod(url);
        try {
            int returnCode = client.executeMethod(get);
            if (isReponseNotOK(returnCode)) {
                throw new RuntimeException("Dint find what you are looking for. Instead got " + returnCode);
            }
            return get.getResponseBodyAsString();
        } catch (IOException e) {
            throw new RuntimeException("Damn. Something went hiediously wrong.", e);
        }
    }
}