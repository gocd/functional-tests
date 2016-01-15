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

package com.thoughtworks.cruise.context.configuration;

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.SystemUtil;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import net.sf.sahi.client.Browser;
import org.apache.commons.lang3.StringUtils;

public class ValidLdapConfiguration extends AbstractConfiguration{
	public ValidLdapConfiguration(Configuration config, ScenarioState state, RepositoryState repositoryState, ScenarioHelper scenarioHelper, Browser browser) throws Exception {
		super("/config/valid-ldap-server-cruise-config.xml",config,state,repositoryState, scenarioHelper, browser);
	}

	@com.thoughtworks.gauge.Step("Valid ldap configuration - setup")
	public void setUp() throws Exception {
		super.setUp();
	}

	protected void postProcess(CruiseConfigDom dom) throws Exception {
		String ldapServerIp = System.getenv("LDAP_SERVER_IP");
		if (StringUtils.isBlank(ldapServerIp)) throw new RuntimeException(String.format("%s is not set", ldapServerIp));
		dom.getLdap().attribute("uri").setValue("ldap://" + ldapServerIp);
	}


	@com.thoughtworks.gauge.Step("Valid ldap configuration - teardown")
	public void tearDown() throws Exception {
		super.tearDown();
	}

}
