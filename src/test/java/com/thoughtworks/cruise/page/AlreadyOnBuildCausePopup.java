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

import com.thoughtworks.cruise.SahiBrowserWrapper;
import com.thoughtworks.cruise.materials.Repository;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;

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
        if (materialType.equals("Pipeline"))
            scenarioState.getBuildCauseResponse().then()
                    .body(String.format("material_revisions.find { it.material_name == '%s' }.modifications[%s].revision"
                            ,materialName, modificationOffset), containsString(scenarioState.expand(revision)));
        else
            scenarioState.getBuildCauseResponse().then()
                    .body(String.format("material_revisions.find { it.material_name == '%s' }.modifications[%s].revision"
                            ,materialName, modificationOffset), containsString(repoState.getRevisionFromAlias(revision)));
    }
    
	@com.thoughtworks.gauge.Step("Verify has only <numberOfModificationsExpected> modifications")
	public void verifyHasOnlyModifications(Integer numberOfModificationsExpected) throws Exception {
		String messageDidNotFindLastModification = "Failed to find modification numbered: " + (numberOfModificationsExpected - 1) +
				". This suggests that there are lesser modifications than expected (" + numberOfModificationsExpected + ").";
		assertThat(messageDidNotFindLastModification, matchesExpectedNumberOfModifications(Integer.valueOf(numberOfModificationsExpected)), is(true));
	}

	@com.thoughtworks.gauge.Step("Verify modification <modificationOffset> has latest revision - Already On Build Cause Popup")
	public void verifyModificationHasLatestRevision(String modificationOffset) throws Exception {
		Repository repo = repoState.getRepoByMaterialName(scenarioState.currentRuntimePipelineName(), this.materialName);
		verifyModificationHasRevision(modificationOffset, repo.latestRevision().revisionNumber());
	}

    private boolean matchesExpectedNumberOfModifications(int expected) {
        int actual = scenarioState.getBuildCauseResponse().then()
                .extract().path(String.format("material_revisions.find { it.material_name == '%s' }.modifications.size"
                        ,materialName));
        return actual == expected;
    }

    private String materialId() {
        return scenarioState.expand(format("material_%s_%s_%s", scenarioState.currentRuntimePipelineName(), pipelineCounter, materialName));
    }

    @com.thoughtworks.gauge.Step("Verify material has changed")
	public void verifyMaterialHasChanged() throws Exception {
        scenarioState.getBuildCauseResponse().then()
                .body(String.format("material_revisions.find { it.material_name == '%s' }.changed",materialName), is(true));
    }

    @com.thoughtworks.gauge.Step("Verify material has not changed")
	public void verifyMaterialHasNotChanged() throws Exception {
        scenarioState.getBuildCauseResponse().then()
                .body(String.format("material_revisions.find { it.material_name == '%s' }.changed",materialName), is(false));
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
        scenarioState.getBuildCauseResponse().then()
                .body(String.format("material_revisions.find { it.material_name == '%s' }.modifications[0].revision"
                        ,materialName), containsString(revision));
    }
    
	@com.thoughtworks.gauge.Step("Verify latest revision exists")
	public void verifyLatestRevisionExists() throws Exception {
    	Repository repo = repoState.getRepoByMaterialName(scenarioState.currentRuntimePipelineName(), this.materialName);
		String revision = repo.latestRevision().revisionNumber();
        verifyRevisionExists(revision);
	}

    @com.thoughtworks.gauge.Step("Verify modification <modificationNumber> has comment containing <comment>")
	public void verifyModificationHasCommentContaining(Integer modificationNumber, String comment) throws Exception {
        scenarioState.getBuildCauseResponse().then()
                .body(String.format("material_revisions.find { it.material_name == '%s' }.modifications[%s].comment"
                        ,materialName, modificationNumber), containsString(comment));
    }

	@com.thoughtworks.gauge.Step("Verify modification <modificationNumber> does not have comment containing <comment>")
	public void verifyModificationDoesNotHaveCommentContaining(Integer modificationNumber, String comment) throws Exception {
        scenarioState.getBuildCauseResponse().then()
                .body(String.format("material_revisions.find { it.material_name == '%s' }.modifications[%s].comment"
                        ,materialName, modificationNumber), not(containsString(comment)));
	}

    @com.thoughtworks.gauge.Step("Verify modification <modificationNumber> has multi line comment with paragraph content <content>")
	public void verifyModificationHasMultiLineCommentWithParagraphContent(Integer modificationNumber, String content) throws Exception {
//        ElementStub commentContainer = commentForModificationAt(modificationNumber);
//        assertThat(commentContainer.containsHTML("<p>"+ content + "</p>"), Is.is(true));
        throw new RuntimeException("This step not implemented");
    }

  
    @com.thoughtworks.gauge.Step("Verify modification <modificationNumber> has modified by containing <modifiedBy>")
	public void verifyModificationHasModifiedByContaining(Integer modificationNumber, String modifiedBy) throws Exception {
        scenarioState.getBuildCauseResponse().then()
                .body(String.format("material_revisions.find { it.material_name == '%s' }.modifications[%s].user_name"
                        ,materialName, modificationNumber), containsString(modifiedBy));
    }
    

}
