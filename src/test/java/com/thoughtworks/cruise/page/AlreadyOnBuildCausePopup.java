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

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.SahiBrowserWrapper;
import com.thoughtworks.cruise.materials.Repository;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AlreadyOnBuildCausePopup {
    private final ScenarioState scenarioState;
    private String materialName;
    private String pipelineCounter;
    private Browser browser;
    private String materialType;
    private final RepositoryState repoState;
    private final SahiBrowserWrapper sahiBrowserWrapper;

    public AlreadyOnBuildCausePopup(ScenarioState scenarioState, RepositoryState repoState, Browser browser) {
        this.scenarioState = scenarioState;
        this.repoState = repoState;
        this.browser = browser;
        this.sahiBrowserWrapper = new SahiBrowserWrapper(browser);
    }

    @com.thoughtworks.gauge.Step("Verify modification <modificationOffset> has revision <revision>")
	public void verifyModificationHasRevision(String modificationOffset, String revision) throws Exception {
        ElementStub revisionElement = browser.cell("revision").in(elementModificationAt(modificationOffset));
        if (materialType.equals("Pipeline"))
            assertThat(revisionElement.getText().trim(), containsString(scenarioState.expand(revision)));
        else
            assertThat(revisionElement.getText().trim(), containsString(repoState.getRevisionFromAlias(revision)));
    }
    
	@com.thoughtworks.gauge.Step("Verify has only <numberOfModificationsExpected> modifications")
	public void verifyHasOnlyModifications(Integer numberOfModificationsExpected) throws Exception {
		String messageDidNotFindLastModification = "Failed to find modification numbered: " + (numberOfModificationsExpected - 1) +
				". This suggests that there are lesser modifications than expected (" + numberOfModificationsExpected + ").";
		assertThat(messageDidNotFindLastModification, elementModificationAt(String.valueOf(numberOfModificationsExpected - 1)).exists(true), is(true));

		String messageFoundExtraModification = "Found unexpected modification numbered: " + numberOfModificationsExpected +
				". This suggests that there are more modifications than expected (" + numberOfModificationsExpected + ").";
		assertThat(messageFoundExtraModification, elementModificationAt(String.valueOf(numberOfModificationsExpected)).exists(true), is(false)); 
	}

	@com.thoughtworks.gauge.Step("Verify modification <modificationOffset> has latest revision - Already On Build Cause Popup")
	public void verifyModificationHasLatestRevision(String modificationOffset) throws Exception {
		Repository repo = repoState.getRepoByMaterialName(scenarioState.currentRuntimePipelineName(), this.materialName);
		verifyModificationHasRevision(modificationOffset, repo.latestRevision().revisionNumber());
	}

    private ElementStub elementModificationAt(String offset) {
        return browser.byId(materialId() + "_" + offset);
    }

    private String materialId() {
        return scenarioState.expand(format("material_%s_%s_%s", scenarioState.currentRuntimePipelineName(), pipelineCounter, materialName));
    }

    @com.thoughtworks.gauge.Step("Verify material has changed")
	public void verifyMaterialHasChanged() throws Exception {
        assertThat(materialChangedElement().exists(), is(true));
    }

    @com.thoughtworks.gauge.Step("Verify material has not changed")
	public void verifyMaterialHasNotChanged() throws Exception {
        assertThat(materialChangedElement().exists(), is(false));
    }

    private ElementStub materialChangedElement() {
        return browser.cell(Regex.wholeWord("changed")).in(browser.byId(materialId()));
    }

    @com.thoughtworks.gauge.Step("Looking at material of type <materialType> named <materialName> for pipeline <pipeline> with counter <counter>")
	public void lookingAtMaterialOfTypeNamedForPipelineWithCounter(String materialType, String materialName, String pipeline, String counter)
            throws Exception {
        this.materialName = "Pipeline".equals(materialType) ? scenarioState.expand(materialName) : materialName;
        this.materialType = materialType;
        scenarioState.usingPipeline(pipeline);
        this.pipelineCounter = counter;
    }

    @com.thoughtworks.gauge.Step("Verify revision <revision> exists")
	public void verifyRevisionExists(String revision) throws Exception {
    	String useNewRails = System.getenv("USE_NEW_RAILS");
    	String revisionString = (useNewRails != null && useNewRails.equals("N")) ? revision : revision + " - VSM";

		ElementStub revisionElement = browser.byText(revisionString, "TD");
        assertThat(revisionElement.getText().trim(), containsString(revision));
    }
    
	@com.thoughtworks.gauge.Step("Verify latest revision exists")
	public void verifyLatestRevisionExists() throws Exception {
    	Repository repo = repoState.getRepoByMaterialName(scenarioState.currentRuntimePipelineName(), this.materialName);
		String revision = repo.latestRevision().revisionNumber();
		ElementStub revisionElement = browser.byText(revision, "TD");
        assertThat(revisionElement.getText().trim(), containsString(revision));
	}

    @com.thoughtworks.gauge.Step("Verify modification <modificationNumber> has comment containing <comment>")
	public void verifyModificationHasCommentContaining(Integer modificationNumber, String comment) throws Exception {
        ElementStub commentElement = commentForModificationAt(modificationNumber);
        assertThat(commentElement.getText().trim(), containsString(comment));
    }

	@com.thoughtworks.gauge.Step("Verify modification <modificationNumber> does not have comment containing <comment>")
	public void verifyModificationDoesNotHaveCommentContaining(Integer modificationNumber, String comment) throws Exception {	
        ElementStub commentElement = commentForModificationAt(modificationNumber);
        assertThat(commentElement.getText().trim(), not(containsString(comment)));
	}

    @com.thoughtworks.gauge.Step("Verify modification <modificationNumber> has multi line comment with paragraph content <content>")
	public void verifyModificationHasMultiLineCommentWithParagraphContent(Integer modificationNumber, String content) throws Exception {
        ElementStub commentContainer = commentForModificationAt(modificationNumber);
        assertThat(commentContainer.containsHTML("<p>"+ content + "</p>"), Is.is(true));
    }
    
    private ElementStub commentForModificationAt(Integer modificationNumber) {
        ElementStub mod = elementModificationAt(String.valueOf(modificationNumber));
        ElementStub commentElement = browser.cell(Regex.wholeWord("comment")).in(mod);
        return commentElement;
    }
  
    @com.thoughtworks.gauge.Step("Verify modification <modificationNumber> has modified by containing <modifiedBy>")
	public void verifyModificationHasModifiedByContaining(Integer modificationNumber, String modifiedBy) throws Exception {
    	ElementStub modifiedbyContainer = modifiedbyForModificationAt(modificationNumber);
    	assertThat(modifiedbyContainer.getText().trim(), containsString(modifiedBy));
    }
    
    private ElementStub modifiedbyForModificationAt(Integer modificationNumber) {
        ElementStub mod = elementModificationAt(String.valueOf(modificationNumber));
        ElementStub modifiedByElement = browser.cell(Regex.wholeWord("modified_by")).in(mod);
        return modifiedByElement;
    }
}
