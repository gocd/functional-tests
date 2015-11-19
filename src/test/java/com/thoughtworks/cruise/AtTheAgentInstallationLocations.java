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

package com.thoughtworks.cruise;

import com.thoughtworks.cruise.state.ScenarioState;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AtTheAgentInstallationLocations {

    private String currentAgent;
    private String currentPipeline;
    private final ScenarioState scenarioState;
    private final CruiseAgents createdAgents;

    public AtTheAgentInstallationLocations(CruiseAgents createdAgents, ScenarioState scenarioState) {
        this.createdAgents = createdAgents;
        this.scenarioState = scenarioState;
    }
    
    @com.thoughtworks.gauge.Step("Looking at first agent for pipeline <pipelineName>")
	public void lookingAtFirstAgentForPipeline(String pipelineName) throws Exception {
        this.currentPipeline = pipelineName;
        currentAgent = createdAgents.get(0).getWorkingDirectory();
    }

    @com.thoughtworks.gauge.Step("Verify <materialFolderName> is checked out under the pipelines folder")
	public void verifyIsCheckedOutUnderThePipelinesFolder(String materialFolderName) throws Exception {
        assertThat(new File(codeFolder(), materialFolderName).exists(), is(true));
    }

    private String codeFolder() {
        return currentAgent + "/pipelines/" + currentPipeline() ;
    }

    private String currentPipeline() {
        return currentPipeline == null ? scenarioState.currentRuntimePipelineName() : scenarioState.pipelineNamed(currentPipeline);
    }

    @com.thoughtworks.gauge.Step("Verify code for <materialFolderName> material is removed")
	public void verifyCodeForMaterialIsRemoved(String materialFolderName) throws Exception {
        assertThat(new File(codeFolder(), materialFolderName).exists(), is(false));
    }
}
