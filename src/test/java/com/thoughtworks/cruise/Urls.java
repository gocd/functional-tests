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

package com.thoughtworks.cruise;

import org.apache.http.client.utils.URIBuilder;

import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class Urls {

	public static final String SERVER_PORT = System.getenv("TWIST_CRUISE_PORT") != null ? System.getenv("TWIST_CRUISE_PORT") : "8253";
	public static final String SSL_PORT = System.getenv("TWIST_CRUISE_SSL_PORT") != null ? System.getenv("TWIST_CRUISE_SSL_PORT") : "8254";
	private static final String SERVER_HOST = getHostName();
	private static final String URL_BASE = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	private static final String HTTPS_URL_BASE = "https://" + SERVER_HOST + ":" + SSL_PORT;
	private static final String LOCALHOST_SSL_URL_BASE = "https://localhost:" + SSL_PORT;
	private static final String LOCALHOST_URL_BASE = "http://localhost:" + SERVER_PORT;

	public static String urlFor(String url) {
		if (url.matches("^http[s]?://.*")) {
			return url;
		}
		url = pathFor(url);
		return URL_BASE + url;
	}

	public static String sslUrlFor(String url) {
		return HTTPS_URL_BASE + pathFor(url);
	}

	public static String localhostSslUrlFor(String url) {
		return LOCALHOST_SSL_URL_BASE + pathFor(url);
	}

	public static String localhostUrlFor(String url) {
		return LOCALHOST_URL_BASE + pathFor(url);
	}

	private static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "localhost";
		}
	}

	private static String pathFor(String url) {
		if (!url.startsWith("/go")) {
			url = "/go" + url;
		}
		return url;
	}

	public static String home() {
		return URL_BASE;
	}

	public static String jobFeedUrl() {
		return urlFor("/api/feeds/jobs.xml");
	}

	public static String stageFeedUrl(String pipelineName) {
		return urlFor(String.format("/api/pipelines/%s/stages.xml", pipelineName));
	}

	public static String logoutUrl() {
		return urlFor("/auth/logout");

	}

	public static String stageDetailUrlfor(String stageIdentifier) {
		return urlFor("/pipelines/" + stageIdentifier);
	}

	public static String ccTrayUrl() {
		return urlFor("/cctray.xml");
	}

	public static String adminGroup(String method) {
		return urlFor(String.format("/admin/restful/configuration/group/%s/xml", method));
	}

	public static String oauthAuthorize(String id, String redirectURI) throws URISyntaxException {
		String pathAndQuery = new URIBuilder("/oauth/authorize")
				.addParameter("redirect_uri", redirectURI)
				.addParameter("client_id", id)
				.addParameter("response_type", "code").toString();
		try {
			return localhostSslUrlFor(pathAndQuery);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String gadgetUrl(String gadgetName) {
		return "https://localhost:" + SSL_PORT + "/go/gadgets/" + gadgetName;
	}

	public static String gadgetUrlForPipeline(String pipelineName) {
		return sslUrlFor("/gadgets/pipeline/content?pipeline_name=" + pipelineName);
	}

	public static String deleteAllBackupExtries() {
		return urlFor("/add-on/test-addon/admin/backups/delete");
	}

    public static String svnPostCommitHook() {
        return urlFor("/go/api/material/svn/notify");
    }

    public static String gitPostCommitHook() {
        return urlFor("/go/api/material/git/notify");
    }

    public static String hgPostCommitHook() {
        return urlFor("/go/api/material/hg/notify");
    }

    public static String apiSupportURL() {
        return urlFor("/go/api/support");
    }
}
