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
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Timeout;

import java.util.ArrayList;
import java.util.List;

public class CreatedAgents implements Agents {
	private List<AgentLauncher> agents = new ArrayList<AgentLauncher>();

    public void addAgent(AgentLauncher workingDirectory) {
        this.agents.add(workingDirectory);
    }

    public AgentLauncher get(int index) {
        return agents.get(index);
    }
    
    @Override
    public AgentLauncher get(String uuid) {
        for(AgentLauncher agent: agents){
            if(agent.getUuid().equals(uuid)){
                return agent;
            }
        }
        return null;
    }
    
	public void stopRunTillFileExistsJobs(int numberOfJobs, boolean shouldPass) {
		stopAllAgents(numberOfJobs, shouldPass);
	}

	private void stopAllAgents(int numberOfJobs, boolean shouldPass) {
		createStopJobFileOnAllAgents(numberOfJobs, shouldPass);
	}

	private void createStopJobFileOnAllAgents(final int numberOfJobs, final boolean shouldPass) {
        final ArrayList<String> doneFor = new ArrayList<String>();
        Assertions.waitUntil(Timeout.FIVE_MINUTES, new Assertions.Predicate() {
            public boolean call() throws Exception {
                for (AgentLauncher agent : agents) {
                    if (agent.doesFileExist(WAITING_FOR_STOP_JOB_FILE) && !agent.doesFileExist(STOP_JOB_FILE)) {
                        agent.createFile(STOP_JOB_FILE, shouldPass);
                        doneFor.add(agent.getWorkingDirectory());
                    }
                }
                return doneFor.size() == numberOfJobs;
            }

            @Override
            public String toString() {
                System.out.println("Creating stop job file failed on one or more of the agents. Following is the evaluation of the booleans");
                for (AgentLauncher agent : agents) {
                    System.out.println(String.format("Agent at '%s'", agent.getWorkingDirectory()));
                    System.out.println("agent.doesFileExist(WAITING_FOR_STOP_JOB_FILE): " + agent.doesFileExist(WAITING_FOR_STOP_JOB_FILE));
                    System.out.println("!agent.doesFileExist(STOP_JOB_FILE): " + !agent.doesFileExist(STOP_JOB_FILE));
                }
                return String.format("Could not create stop job files for '%s' jobs.  Created only for: %s", numberOfJobs, doneFor);
            }
        });
        System.out.println("created stop job file for: " + doneFor);
	}

	public int count() {
		return agents.size();
	}

	public void deleteStopjobFileOnAllAgents() {
		for (AgentLauncher agent : agents) {
			try {
				System.out.println("deleting stopjob file on agent: " + agent.getWorkingDirectory());
				agent.deleteFile(STOP_JOB_FILE);
			} catch (Exception e) {
				System.err.println("ignoring exception when trying to delete stopjob file:  " + e.getMessage());
			}
		}
	}

    public void createStopJobFileForAllAgents(boolean shouldPass) {
        for (AgentLauncher agent : agents) {
            agent.createFile(STOP_JOB_FILE, shouldPass);
        }
        System.err.println("created stopjob file on all agents(you want to do this only in cleanup, as this leads to flakyness)");
    }

    @Override
    public void removeAgentFromList(AgentLauncher agent) {
        agents.remove(agent);        
    }
    
}
