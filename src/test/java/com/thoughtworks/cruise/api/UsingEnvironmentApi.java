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

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import net.sf.sahi.client.Browser;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class UsingEnvironmentApi {

	private Browser browser;
	private TalkToCruise talkToCruise;
	private String environmentUpdateUrl;

	public UsingEnvironmentApi(Browser browser, TalkToCruise talkToCruise) {
		this.browser = browser;
		this.talkToCruise = talkToCruise;
	}

	@com.thoughtworks.gauge.Step("Change environment name to <newEnvName>")
	public void changeEnvironmentNameTo(String newEnvName) throws Exception {
	    StringRequestEntity reqEntity = new StringRequestEntity(String.format("cruise_config_md5=%s&environment[name]=%s", getMD5(), newEnvName), "application/x-www-form-urlencoded", "UTF-8");
		CruiseResponse response = talkToCruise.put(environmentUpdateUrl, reqEntity);
		Assert.assertThat(response.getStatus(), Is.is(200));
	}
	
	private String getMD5() {
	    String url = Urls.urlFor("/admin/configuration/file.xml");
	    CruiseResponse response = talkToCruise.get(url);
	    return response.getResponseHeader("X-CRUISE-CONFIG-MD5");
	}
	
	@com.thoughtworks.gauge.Step("For environment named <envName>")
	public void forEnvironmentNamed(String envName) throws Exception {
		this.environmentUpdateUrl = Urls.urlFor(String.format("/environments/%s",envName));
	}
}
