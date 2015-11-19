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


import com.thoughtworks.cruise.materials.Repository;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AlreadyOnGitMaterialCreationPopup extends AlreadyOnEditMaterialPopup {

    
    private RepositoryState repositoryState;

    
    
    public AlreadyOnGitMaterialCreationPopup(CurrentPageState currentPageState,
            ScenarioState scenarioState, Browser browser) {
        super(currentPageState, scenarioState, browser);
        currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
    }
    
	public AlreadyOnGitMaterialCreationPopup(CurrentPageState currentPageState,
			ScenarioState scenarioState, Browser browser, RepositoryState repositoryState) {
		super(currentPageState, scenarioState, browser);
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
		this.repositoryState = repositoryState;
		
	}

	@com.thoughtworks.gauge.Step("Enter material name <materialName> - Already on git material creation popup")
	public void enterMaterialName(String materialName) throws Exception {
		super.enterMaterialName(materialName);
	}
	
	@com.thoughtworks.gauge.Step("Copy over material url from material named <materialName> in pipeline <pipelineName> to current pipeline")
	public void copyOverMaterialUrlFromMaterialNamedInPipelineToCurrentPipeline(String materialName, String pipelineName) throws Exception {
	    enterMaterialName(materialName);
	    String pipelineRuntimeName = scenarioState.expand(pipelineName);
	    Repository repo = repositoryState.getRepoByMaterialName(pipelineRuntimeName, materialName);
	    
	    enterUrl(repo.getUrl());
    }


	@com.thoughtworks.gauge.Step("Enter url <url> - Already on git material creation popup")
	public void enterUrl(String url) throws Exception {
	    super.enterUrl(url);
	}

	@com.thoughtworks.gauge.Step("Enter branch <branch>")
	public void enterBranch(String branch) throws Exception {
		elementBranch().setValue(branch);	
	}

    private ElementStub elementBranch() {
        return browser.textbox("material[branch]");
    }

	@com.thoughtworks.gauge.Step("Enter destination directory <destination> - Already on git material creation popup")
	public void enterDestinationDirectory(String destination) throws Exception {
		super.enterDestinationDirectory(destination);
	}

	@com.thoughtworks.gauge.Step("Enter black list <blackList>")
	public void enterBlackList(String blackList) throws Exception {
		super.enterBlackList(blackList);
	}

	@com.thoughtworks.gauge.Step("Click save - Already on git material creation popup")
	public void clickSave() throws Exception {
	    super.clickSave();
        currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE);
	}
	

    @com.thoughtworks.gauge.Step("Make autoupdate to be <shouldCheck>")
	public void makeAutoupdateToBe(Boolean shouldCheck) throws Exception {
        super.makeAutoupdateToBe(shouldCheck);
    }

    @com.thoughtworks.gauge.Step("Verify material name is <materialName>")
	public void verifyMaterialNameIs(String materialName) throws Exception {
        super.verifyMaterialNameIs(materialName);
    }

    @com.thoughtworks.gauge.Step("Verify url is <url>")
	public void verifyUrlIs(String url) throws Exception {
        super.verifyUrlIs(url);
    }

    @com.thoughtworks.gauge.Step("Verify autoupdate is <selection>")
	public void verifyAutoupdateIs(Boolean selection) throws Exception {
        super.verifyAutoupdateIs(selection);
    }

    @com.thoughtworks.gauge.Step("Verify branch is <branch>")
	public void verifyBranchIs(String branch) throws Exception {
        assertThat(elementBranch().getText(), is(branch));
    }

    @com.thoughtworks.gauge.Step("Verify destination directory is <dest>")
	public void verifyDestinationDirectoryIs(String dest) throws Exception {
        super.verifyDestinationDirectoryIs(dest);
    }

    @com.thoughtworks.gauge.Step("Verify black list is <blackList>")
	public void verifyBlackListIs(String blackList) throws Exception {
        super.verifyBlackListIs(blackList);
    }

	private ElementStub elementShouldPollForChanges()
	{
		return browser.checkbox("material[autoUpdate]");
	}
	
	@com.thoughtworks.gauge.Step("Set poll for changes as <check> - Already on git material creation popup")
	public void setPollForChangesAs(Boolean check) throws Exception {
	ElementStub shouldPollForChanges = elementShouldPollForChanges();
		if (shouldCheck(check, shouldPollForChanges)
				|| shouldUncheck(check, shouldPollForChanges)) {
			shouldPollForChanges.click();
		}
	}

    @com.thoughtworks.gauge.Step("Check connectivity should be successful - Already on Git Material Creation Popup")
	public void checkConnectivityShouldBeSuccessful() {
        checkConnectivityWithMessage("ok_message");
    }
    
    void checkConnectivityWithMessage(final String className) {
        browser.button("check_connection_git").click();
        Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
            
            @Override
            public boolean call() throws Exception {
                return browser.byId("vcsconnection-message_git").fetch("className").contains(className);
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify saved successfully - Already on Git Material Creation Popup")
	@Override
    public void verifySavedSuccessfully() {
        super.verifySavedSuccessfully();
    }
    
    
}
