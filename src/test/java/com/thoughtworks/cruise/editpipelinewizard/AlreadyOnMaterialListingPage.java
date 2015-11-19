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

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.state.ConfigState;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import com.thoughtworks.cruise.utils.configfile.CruiseConfigDom;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AlreadyOnMaterialListingPage extends AlreadyOnEditPipelineWizardPage {

	private final Configuration configuration;

    public AlreadyOnMaterialListingPage(CurrentPageState currentPageState, ScenarioState scenarioState, Browser browser, Configuration configuration) {
		super(currentPageState, scenarioState, browser);
        this.configuration = configuration;
		currentPageState.assertCurrentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_LISTING_PAGE);

	}

	@com.thoughtworks.gauge.Step("Open new subversion material creation popup")
	public AlreadyOnEditMaterialPopup openNewSubversionMaterialCreationPopup() throws Exception {
	    openMaterialPopup("Subversion");
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);	
		return new AlreadyOnSubversionMaterialAddPopup(currentPageState, scenarioState, browser);
	}
	
	@com.thoughtworks.gauge.Step("Open new git material creation popup")
	public AlreadyOnGitMaterialCreationPopup openNewGitMaterialCreationPopup() throws Exception {
		openMaterialPopup("Git");
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);	
		return new AlreadyOnGitMaterialCreationPopup(currentPageState, scenarioState, browser);
	}
	

	@com.thoughtworks.gauge.Step("Verify that material saved successfully")
	public void verifyThatMaterialSavedSuccessfully() throws Exception {
			super.verifySavedSuccessfully();
	}

	@com.thoughtworks.gauge.Step("Open new mercurial material creation popup")
	public AlreadyOnMercurialMaterialCreationPopup openNewMercurialMaterialCreationPopup() throws Exception {
		openMaterialPopup("Mercurial");
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
		return new AlreadyOnMercurialMaterialCreationPopup(currentPageState, scenarioState, browser);
	}

	@com.thoughtworks.gauge.Step("Open new perforce material creation popup")
	public AlreadyOnPerforceMaterialCreationPopup openNewPerforceMaterialCreationPopup() throws Exception {
		openMaterialPopup("Perforce");
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
		return new AlreadyOnPerforceMaterialCreationPopup(currentPageState, scenarioState, browser);
	}

	@com.thoughtworks.gauge.Step("Open new pipeline material creation popup")
	public void openNewPipelineMaterialCreationPopup() throws Exception {
		openMaterialPopup("Pipeline");
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
	}
	
	@com.thoughtworks.gauge.Step("Open new package material creation popup")
	public AlreadyOnPackageMaterialCreationPopup openNewPackageMaterialCreationPopup() throws Exception {
		openMaterialPopup("Package");
		currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
		return new AlreadyOnPackageMaterialCreationPopup(currentPageState, scenarioState, browser);
	}
	
	@com.thoughtworks.gauge.Step("Verify <linkId> link is present")
	public void verifyLinkIsPresent(String linkId) throws Exception {
		assertTrue(browser.link(linkId).exists());		
	}
	
	@com.thoughtworks.gauge.Step("Assert mD5 - Already on material listing page")
	public void assertMD5() throws Exception {
    	String md5value = scenarioState.getValueFromStore(ConfigState.md5key);
    	assertEquals(browser.hidden("config_md5").getValue(), md5value);
    }

	public void openMaterialPopup(String materialType) {
		browser.span("Add Material").click();
		openMaterial(materialType);
	}
	
	private void waitForPopupToLoad() {
	    Assertions.waitUntil(Timeout.TEN_SECONDS, new Predicate() {
            public boolean call() throws Exception {
                ElementStub button = browser.submit("SAVE");
                if (!button.exists()) {
                    return false;
                }
                return true;
            }
        });
	}

    @com.thoughtworks.gauge.Step("Verify that <material> with name <materialName> is added with attributes <attributeNameValues>")
	public void verifyThatWithNameIsAddedWithAttributes(final String material, final String materialName, final String attributeNameValues) throws Exception {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            
            @Override
            public boolean call() throws Exception {
                String[] nameValues = attributeNameValues.split(",");
                CruiseConfigDom dom = configuration.provideDomAsAdmin();
                dom.hasMaterialWithAttribs(scenarioState.currentRuntimePipelineName(), material, materialName, nameValues);
                return true;
            }
        });
    }

    @com.thoughtworks.gauge.Step("Edit material <materialName>")
	public void editMaterial(String materialName) throws Exception {
        openMaterial(materialName);
        currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);
    }

    private void openMaterial(String materialName) {
        browser.link(materialName).click();
        waitForPopupToLoad();
    }

    @com.thoughtworks.gauge.Step("Create new <materialType> material with data <attributes>")
	public void createNewMaterialWithData(String materialType, String attributes) throws Exception {
        AlreadyOnEditMaterialPopup popup;
        if ("hg".equals(materialType)) {
            popup = openNewMercurialMaterialCreationPopup();
        } else if ("git".equals(materialType)) {
            popup = openNewGitMaterialCreationPopup();
        } else if ("p4".equals(materialType)) {
            popup = openNewPerforceMaterialCreationPopup();
        } else {
            popup = openNewSubversionMaterialCreationPopup();
        }
        popup.usingData(attributes);
        popup.clickSave();
        verifyThatMaterialSavedSuccessfully();
    }

    @com.thoughtworks.gauge.Step("Open new tfs material creation popup")
	public AlreadyOnTfsMaterialCreationPopup openNewTfsMaterialCreationPopup() throws Exception {
        openMaterialPopup("Team Foundation Server");
        currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_MATERIAL_POPUP);   
        return new AlreadyOnTfsMaterialCreationPopup(currentPageState, scenarioState, browser);
    }

	@com.thoughtworks.gauge.Step("Delete material with name <materialName>")
	public void deleteMaterialWithName(String materialName) throws Exception {
		ElementStub materialRow = getMaterialRowForMaterialName(materialName);
		browser.span(Regex.matches("icon_remove")).in(materialRow).click();
		proceedWithConfirmPrompt();
	}

	@com.thoughtworks.gauge.Step("Verify that material with name <materialName> does not exist")
	public void verifyThatMaterialWithNameDoesNotExist(String materialName) throws Exception {
		ElementStub materialRow = getMaterialRowForMaterialName(materialName);
		Assert.assertThat(materialRow.exists(), Is.is(false));
	}
	
	private ElementStub getMaterialRowForMaterialName(String materialName) {
		ElementStub listingTable = browser.table("list_table");
		ElementStub materialRow = browser.link(materialName).in(listingTable).parentNode().parentNode();
		return materialRow;
	}

    @com.thoughtworks.gauge.Step("Verify that material <materialName> cannot be deleted because of reason <message>")
	public void verifyThatMaterialCannotBeDeletedBecauseOfReason(String materialName, String message) throws Exception {
        ElementStub deleteIcon = deleteIconWithClass(materialName, "delete_icon_disabled");
        Assert.assertThat(deleteIcon.exists(), Is.is(true));
        Assert.assertThat(deleteIcon.fetch("title"), Is.is(message));
    }


    @com.thoughtworks.gauge.Step("Verify that material <materialName> can be deleted")
	public void verifyThatMaterialCanBeDeleted(String materialName) throws Exception {
        ElementStub deleteIcon = deleteIconWithClass(materialName, "icon_remove");
        Assert.assertThat(deleteIcon.exists(), Is.is(true));
        Assert.assertThat(deleteIcon.fetch("title"), Is.is("Remove this material"));
    }
    

	@com.thoughtworks.gauge.Step("Open stage listing page - Already on material listing page")
	public void openStageListingPage() throws Exception {
		super.openStageListingPage();
	}
	
	@com.thoughtworks.gauge.Step("Go to environment variables page - Already on material listing page")
	@Override
	public void goToEnvironmentVariablesPage() throws Exception {
		super.goToEnvironmentVariablesPage();
	}
	
	@com.thoughtworks.gauge.Step("Open general options page - Already on material listing page")
	@Override
	public void openGeneralOptionsPage() throws Exception {
		super.openGeneralOptionsPage();
	}
    
    private ElementStub deleteIconWithClass(String materialName, String deleteIconClassName) {
        List<ElementStub> materialNamelinks = browserWrapper.collect("link", "material_name");
        ElementStub deleteIcon = null;
        for(int i=0 ; i < materialNamelinks.size(); i++) {
            if(materialNamelinks.get(i).getText().equals(scenarioState.expand(materialName))){
                ElementStub materialRow = browser.row(i+1).in(browser.table("list_table"));
                deleteIcon = browser.span(Regex.wholeWord(deleteIconClassName)).in(materialRow);
                break;
            }
        }
        return deleteIcon;
    }

	@com.thoughtworks.gauge.Step("Verify that package with name <materialName> is added with url <url>")
	public void verifyThatPackageWithNameIsAddedWithUrl(String materialName, String url) throws Exception {
		ElementStub materialLink = browser.link(materialName);
		ElementStub materialRow = materialLink.parentNode().parentNode();
		ElementStub materialTypeCell = browser.cell("Package").in(materialRow);

		Assert.assertThat(materialLink.exists(), Is.is(true));
		Assert.assertThat(materialRow.containsText(scenarioState.expand(url)), Is.is(true));		
		Assert.assertThat(materialTypeCell.exists(), Is.is(true));
	}
	
	@com.thoughtworks.gauge.Step("Verify pipeline name is <pipelineName>")
	public void verifyPipelineNameIs(String pipelineName) throws Exception
	{
		Assert.assertThat(browser.link("selected").in(browser.listItem("collapsable lastCollapsable")).getText(),Is.is(scenarioState.pipelineNamed(pipelineName)));
	}
}
