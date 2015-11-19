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

import com.thoughtworks.cruise.preconditions.AgentLauncher;
import com.thoughtworks.cruise.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class PreInstalledAgents implements Agents {

	private void ignoringMessage(String message) {
		System.err.println("IGNORING(running in preinstalled agents mode) -> " + message);
	}

	@Override
	public void addAgent(AgentLauncher workingDirectory) {
		ignoringMessage("adding agents to working dir " + workingDirectory);
	}

	@Override
	public int count() {
		ignoringMessage("count of agents, returning 0");
		return 0;
	}

	@Override
	public void deleteStopjobFileOnAllAgents() {
		throw new UnsupportedOperationException("delete stop job file not implemented for pre installed agent.");
	}

    public void createStopJobFileForAllAgents(boolean shouldPass) {
        String dir = RuntimePath.getTwistAgentPath();
		try {
			String content = "";
			if (!shouldPass) {
				content += "\nstopjob.fail=ANYTHING\n";
			}
			FileUtil.writeContentToFile(content, new File(dir, STOP_JOB_FILE));
		} catch (IOException e) {
			throw new RuntimeException("Could not create file " + STOP_JOB_FILE, e);
		}
    }

    @Override
	public AgentLauncher get(int index) {
		ignoringMessage("getting agent by index " + index + ", returning null");
		return null;
	}
    
    @Override
    public AgentLauncher get(String uuid) {
        ignoringMessage("getting agent by uuid " + uuid + ", returning null");
        return null;
    }

	@Override
	public void stopRunTillFileExistsJobs(int numberOfJobs, boolean shouldPass) {
		if (numberOfJobs > 1) {
            throw new IllegalArgumentException("Running against installed agent must have maximum less than or equal to one(1) job waiting for file to exist.");
        }
        createStopJobFileForAllAgents(shouldPass);
	}

    @Override
    public void removeAgentFromList(AgentLauncher agent) {
        ignoringMessage("removing agent " + agent.getUuid());
    }

    
}
