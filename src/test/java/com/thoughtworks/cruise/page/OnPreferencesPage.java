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

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;

public class OnPreferencesPage extends CruisePage {

	public OnPreferencesPage(ScenarioState scenarioState, Browser browser) {
		super(scenarioState, browser);
	}

	protected String url() {
		return Urls.urlFor("/tab/mycruise/user");
	}
	
	@com.thoughtworks.gauge.Step("Verify page title is <expectedTitle>")
	public void verifyPageTitleIs(String expectedTitle) {
		super.verifyPageTitleIs(expectedTitle);
	}


	@com.thoughtworks.gauge.Step("On Preferences page")
	public void goToPreferencePage() {
		navigateToURL();
	}
	
	@com.thoughtworks.gauge.Step("Verify cruise footer - On preferences page")
	@Override
	public void verifyCruiseFooter() throws Exception {
		super.verifyCruiseFooter();
	}
	
    
    public void changeEmailToAndAliasTo(String email, String alias) throws Exception {
        browser.byId("edit-matchers").click();
        browser.byId("email").setValue(email);
        browser.byId("matchers").setValue(alias);        
        browser.byId("save-matchers").click();
    }    
    

}
