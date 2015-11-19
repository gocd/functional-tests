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

package com.thoughtworks.cruise.editpipelinewizard;

import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

public class AlreadyOnMercurialMaterialCreationPopup extends AlreadyOnEditMaterialPopup {

	public AlreadyOnMercurialMaterialCreationPopup(
			CurrentPageState currentPageState, ScenarioState scenarioState,
			Browser browser) {
		super(currentPageState, scenarioState, browser);
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
	}

	@com.thoughtworks.gauge.Step("Enter material name <materialName> - Already on Mercurial Material Creation Popup")
	public void enterMaterialName(String materialName) throws Exception {
		browser.textbox("material[materialName]").setValue(materialName);
	}

	@com.thoughtworks.gauge.Step("Click save - Already on Mercurial Material Creation Popup")
	public void clickSave() throws Exception {
		browser.submit("primary finish submit MB_focusable").click();
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE);
	}

	@com.thoughtworks.gauge.Step("Close - Already on Mercurial Material Creation Popup")
	public void close() {
		super.close();
	}
	
    @com.thoughtworks.gauge.Step("Enter url <url> - Already on Mercurial Material Creation Popup")
	public void enterUrl(String url) throws Exception {
        super.enterUrl(url);
    }

    @com.thoughtworks.gauge.Step("Enter destination directory <dest> - Already on Mercurial Material Creation Popup")
	public void enterDestinationDirectory(String dest) throws Exception {
        super.enterDestinationDirectory(dest);
    }

    @com.thoughtworks.gauge.Step("Make autoupdate to be <shouldCheck> - Already on Mercurial Material Creation Popup")
	public void makeAutoupdateToBe(Boolean shouldCheck) throws Exception {
        super.makeAutoupdateToBe(shouldCheck);
    }

    @com.thoughtworks.gauge.Step("Enter black list <blackList> - Already on Mercurial Material Creation Popup")
	public void enterBlackList(String blackList) throws Exception {
        super.enterBlackList(blackList);
    }

    public void verifyMaterialNameIs(String materialName) throws Exception {
        super.verifyMaterialNameIs(materialName);
    }

    public void verifyUrlIs(String url) throws Exception {
        super.verifyUrlIs(url);
    }

    public void verifyDestinationDirectoryIs(String dest) throws Exception {
        super.verifyDestinationDirectoryIs(dest);
    }

    public void verifyAutoupdateIs(Boolean selection) throws Exception {
        super.verifyAutoupdateIs(selection);
    }

    public void verifyBlackListIs(String blackList) throws Exception {  
        super.verifyBlackListIs(blackList);
    }

    public void usingData(String materialName, String url, String dest) throws Exception {
        enterMaterialName(materialName);
        enterUrl(url);
        enterDestinationDirectory(dest);
    }

	@com.thoughtworks.gauge.Step("Check connectivity should be successful - Already on Mercurial Material Creation Popup")
	public void checkConnectivityShouldBeSuccessful() {
		super.checkConnectivityShouldBeSuccessful();
	}

	@com.thoughtworks.gauge.Step("Check connectivity should fail")
	public void checkConnectivityShouldFail() {
		super.checkConnectivityShouldFail();
	}

	private ElementStub elementShouldPollForChanges()
	{
		return browser.checkbox("material[autoUpdate]");
	}
	
	@com.thoughtworks.gauge.Step("Set poll for changes as <check> - Already on Mercurial Material Creation Popup")
	public void setPollForChangesAs(Boolean check) throws Exception {
	ElementStub shouldPollForChanges = elementShouldPollForChanges();
		if (shouldCheck(check, shouldPollForChanges)
				|| shouldUncheck(check, shouldPollForChanges)) {
			shouldPollForChanges.click();
		}
	}	

}
