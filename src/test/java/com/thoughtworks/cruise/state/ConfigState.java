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

package com.thoughtworks.cruise.state;

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigState {

	private static final String CONFIG_MD5_HEADER = "X-CRUISE-CONFIG-MD5";
	public static final String LATEST_VERSION = "current";
	
	private Map<String, String> versionAliasMap = new ConcurrentHashMap<String, String>();
	private String lastAliased;
	
	private final TalkToCruise talkToCruise;
	private ScenarioState scenarioState;
	public static String md5key= "md5_form";
	
	public ConfigState(TalkToCruise talkToCruise, ScenarioState scenarioState) {
		this.talkToCruise = talkToCruise;
		this.scenarioState = scenarioState;
		versionAliasMap.put(LATEST_VERSION, LATEST_VERSION);
	}
	
	public void aliasCurrentAs(String alias) {
		CruiseResponse cruiseResponse = talkToCruise.get(Urls.urlFor("/api/admin/config.xml"));
		String configMd5 = cruiseResponse.getResponseHeader(CONFIG_MD5_HEADER);
		versionAliasMap.put(alias, configMd5);
		lastAliased = cruiseResponse.getBody();
	}
	
	public void captureCurrentMd5() {
		CruiseResponse cruiseResponse = talkToCruise.get(Urls.urlFor("/api/admin/config.xml"));
		String currentMd5 = cruiseResponse.getResponseHeader(CONFIG_MD5_HEADER);
		scenarioState.putValueToStore(md5key,currentMd5);
		Assert.assertNotNull(currentMd5);	
	}

	public String getConfig(String alias) {
		CruiseResponse cruiseResponse = talkToCruise.get(Urls.urlFor(String.format("/api/admin/config/%s.xml", versionAliasMap.get(alias))));
		Assert.assertThat(alias.equals(LATEST_VERSION) || cruiseResponse.getResponseHeader(CONFIG_MD5_HEADER).equals(versionAliasMap.get(alias)), Is.is(true));
		return cruiseResponse.getBody();
	}

	public String lastAliased() {
		return lastAliased;
	}
}