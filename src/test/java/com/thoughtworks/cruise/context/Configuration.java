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

import com.thoughtworks.cruise.ConfigureCruiseUsingApi;
import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.CurrentUsernameProvider;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.FileUtil;
import com.thoughtworks.cruise.util.XmlUtil;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Node;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Configuration {

    private static final String CRUISE_XML_PATH = RuntimePath.getServerConfigPath() + "/cruise-config.xml";
	private final ScenarioState scenarioState;
    private HashMap<String, String> aliases = new HashMap<String, String>();
    public static final String X_CRUISE_CONFIG_MD5 = "X-CRUISE-CONFIG-MD5";

    public Configuration(ScenarioState scenarioState) {
		this.scenarioState = scenarioState;
	}
	
    public CruiseConfigDom provideDom() {
        GetConfigUsingAsCurrentUser func = new GetConfigUsingAsCurrentUser();
        return getDom(func);
    }

    public String provideCurrentConfigmd5(){
        return currentConfigState().md5;
    }

    public CruiseConfigDom provideDomAsAdmin() {
        return getDom(new GetConfigUsingAsAdmin());
    }

    private CruiseConfigDom getDom(Function<CruiseResponse> func) {
        CruiseResponse response = Assertions.waitFor(Timeout.THIRTY_SECONDS, func);
        String content = response.getBody();
        try {
            return new CruiseConfigDom(content);
        } catch (Exception e) {
            throw new RuntimeException("Error loading content from the : " + content, e);
        }
    }

    public void setDom(final CruiseConfigDom dom) {
    	CurrentConfigState currentConfigState = currentConfigState();
    	dom.replaceServerId(currentConfigState.serverId);
    	dom.replaceTokenGenerationKey(currentConfigState.tokenGenerationKey);
        String url = ConfigureCruiseUsingApi.GO_CONFIG_API_URL;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new NameValuePair("xmlFile", dom.asXml()));
		nameValuePairs.add(new NameValuePair("md5", currentConfigState.md5));
        CruiseResponse response = new TalkToCruise(scenarioState).post(url, nameValuePairs);
        if (!response.isSuccess()) {
            throw new RuntimeException("Error while posting config.\nThe response code was: " + response.getStatus() + ".\nThe response body was: " + response.getBody());
        }
        System.out.println("Finished updating config with response: " + response.getBody());
    }

    private static final class CurrentConfigState {
        final String md5;
        final String serverId;
        final String tokenGenerationKey;

        private CurrentConfigState(String md5, String serverId, String tokenGenerationKey) {
            this.md5 = md5;
            this.serverId = serverId;
            this.tokenGenerationKey = tokenGenerationKey;
        }
    }

    private CurrentConfigState currentConfigState() {
        CruiseResponse configContent = getConfigContent();
        String serverId = null;
        String tokenGenerationKey = null;
        try {
            Document dom = XmlUtil.parse(configContent.getBody());
            Node node = dom.selectSingleNode("//server/@serverId");
            serverId = node.getText();
            tokenGenerationKey = dom.selectSingleNode("//server/@tokenGenerationKey").getText();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new CurrentConfigState(configContent.getResponseHeader(X_CRUISE_CONFIG_MD5), serverId, tokenGenerationKey);
    }

    private CruiseResponse getConfigContent() {
        return new TalkToCruise(scenarioState).get(ConfigureCruiseUsingApi.GO_CONFIG_API_URL);
    }

    private CruiseResponse getConfigContentAsAdmin() {
        return new TalkToCruise(new CurrentUsernameProvider() {
            
            @Override
            public String loggedInUser() {
                return scenarioState.loggedInUser() != null ? "admin" : null;
            }
        }).get(ConfigureCruiseUsingApi.GO_CONFIG_API_URL);
    }

    public File copyPasswordFile(URL url) {
		try {
			File configDir = new File(RuntimePath.getServerConfigPath());
			FileUtils.copyFileToDirectory(new File(url.getPath()), configDir);
			return new File(configDir, url.getPath().substring(url.getPath().lastIndexOf("/") + 1));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setDomOnFileSystem(final CruiseConfigDom dom) {
		setConfigOnFileSystem(dom.asXml());
	}

	public void setConfigOnFileSystem(final String cruiseConfigContents) {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			public boolean call() throws Exception {
				FileUtil.writeContentToFile(cruiseConfigContents, new File(CRUISE_XML_PATH));
				return true;
			}
		});
	}

    public String getRememberedValue(String alias) {
        return aliases.get(alias);
    }

    public void rememberValueAs(String xpathLocator, String alias) {
        CruiseConfigDom dom = provideDom();
        Node node = dom.getNode(xpathLocator);
        aliases.put(alias, node.getText());
    }

    public final class GetConfigUsingAsCurrentUser implements Function<CruiseResponse> {
        @Override
        public CruiseResponse call() {
            CruiseResponse response = getConfigContent();
            if (!response.isSuccess()) {
                throw new RuntimeException("Cannot fetch the config content from the server. Got response: " + response.getBody());
            }
            return response;
        }
    }
    
    private final class GetConfigUsingAsAdmin implements Function<CruiseResponse> {
        @Override
        public CruiseResponse call() {
            CruiseResponse response = getConfigContentAsAdmin();
            if (!response.isSuccess()) {
                throw new RuntimeException("Cannot fetch the config content from the server. Got response: " + response.getBody());
            }
            return response;
        }
    }
}