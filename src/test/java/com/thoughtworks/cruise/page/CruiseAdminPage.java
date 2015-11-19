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

package com.thoughtworks.cruise.page;

import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.containsString;
public abstract class CruiseAdminPage extends CruisePage{
	public CruiseAdminPage(ScenarioState scenarioState, boolean alreadyOn, Browser browser) {
		super(scenarioState, alreadyOn, browser);
	}
    
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }
	
	public void verifySuccessfulMergeMessageShowUp() throws Exception {
	    Assert.assertThat(message(), containsString("Saved configuration successfully. The configuration was modified by someone else, but your changes were merged successfully."));
	}
	
	protected String message() {
		return Assertions.waitFor(Timeout.TWENTY_SECONDS, new Function<String>() {
			public String call() {				
				String message = browser.byId("message_pane").getText().trim();
				if (StringUtils.isBlank(message)) {
					return null;
				}
				return message;
			}
		});
	}
}
