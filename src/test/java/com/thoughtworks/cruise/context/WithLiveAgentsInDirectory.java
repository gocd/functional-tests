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

package com.thoughtworks.cruise.context;

import com.thoughtworks.cruise.CruiseAgents;
import com.thoughtworks.cruise.SahiBrowserWrapper;
import com.thoughtworks.cruise.api.UsingAgentsApi;
import com.thoughtworks.cruise.api.response.AgentInformation;
import com.thoughtworks.cruise.page.OnAgentsPage;
import com.thoughtworks.cruise.preconditions.AgentLauncher;
import com.thoughtworks.cruise.util.ProcessUtils;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Assertions.ReattemptFailureException;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WithLiveAgentsInDirectory {
    private List<AgentLauncher> agents = new ArrayList<AgentLauncher>();
    protected final UsingAgentsApi agentApi;
    protected final OnAgentsPage agentsPage;
    private static int scenarioNumber = 0;
    private final CruiseAgents createdAgents;
    private final Browser browser;

    // The dependency on Configuration enforces that this be added after a configuration context
    public WithLiveAgentsInDirectory(Configuration configuration, UsingAgentsApi agentApi, OnAgentsPage agentsPage, CruiseAgents createdAgents, Browser browser) {
        this.agentApi = agentApi;
        this.agentsPage = agentsPage;
        this.createdAgents = createdAgents;
        this.browser = browser;
    }

    @com.thoughtworks.gauge.Step("With <numberOfAgents> live agents in directory <directory> - setup")
	public void setUp(int numberOfAgents, String directory) throws Exception {
        if (System.getenv("DONT_LAUNCH_AGENTS") == null) {
            stopAnyOldAgentsLeftBehind();
            createAgents(numberOfAgents, scenarioDirectoryName(directory));
            waitForEnabledAgents(numberOfAgents);
        } else {
            waitForEnabledAgents(1);
        }
    }

    private void waitForEnabledAgents(final int numberOfLiveAgents) {
        agentsPage.open();
        try {
            Assertions.waitUntil(Timeout.FIVE_MINUTES, new Predicate() {
                public boolean call() {
                    boolean success = agentApi.getLiveAgentsCount() == numberOfLiveAgents;
                    try {
                        if (!success) {
                            System.err.println(toString() + " (as shown below)");
                            AgentInformation[] agents = agentApi.listInformationOfAllAgents();
                            for(AgentInformation agent: agents){
                                agents.toString();
                            }
                            System.err.println();
                        }
                    } catch (Exception e){e.printStackTrace();}
                    return success;
                }

                public String toString() {
                    String expectedStr = String.format("Expected live: [%s]", numberOfLiveAgents);
                    try {
                        return expectedStr
                                + String.format(" but Actual agents: [%s], Actual live [%s].", agentApi.getAgentsCount(), agentApi.getLiveAgentsCount(),
                                        new SahiBrowserWrapper(browser).getCurrentUrl());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return expectedStr + " Failed to retrive actuals";
                    }
                }
            });
        } catch (ReattemptFailureException e) {
            captureGoStateAndRethrowException(e);
        }
    }

    private void captureGoStateAndRethrowException(RuntimeException originalException) {
        try {
            new CaptureGoState(browser).generateReportWithAgentData("WITH_LIVE_AGENTS", "FAILED_TO_START");
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate report after catching original exception: " + ExceptionUtils.getFullStackTrace(e), e);
        }
        throw originalException;
    }

    @com.thoughtworks.gauge.Step("With <integer1> live agents in directory <string2> - teardown")
	public void tearDown(Integer integer1, String string2) throws Exception {
        if (System.getenv("DONT_LAUNCH_AGENTS") == null) {
            destroyAgents();
        }
    }

    private String scenarioDirectoryName(String directory) {
        return String.format("scn-%02d", (++scenarioNumber));
    }

    protected int numberOfMissingAgents() {
        return 1;
    }

    private void createAgents(int numberOfAgents, String directory) throws Exception {
        System.err.println("Creating " + numberOfAgents + " agents");
        for (int i = 0; i < numberOfAgents; i++) {
            AgentLauncher agent = AgentLauncher.startNewAgent(directory, null);
            agents.add(agent);
            createdAgents.addAgent(agent);
        }
    }

    private void destroyAgents() {
        for (AgentLauncher agent : agents) {
            try {
                agent.destroy();
            } catch (Exception e) {
                System.err.println("Ignoring error when destroying " + agent + " : " + e.getMessage());
            }
        }
        stopAnyOldAgentsLeftBehind();
    }

    private void stopAnyOldAgentsLeftBehind() {
        System.out.println(new DateTime() + " ==================================");
        System.out.println(new DateTime() + " List agents before sending a kill:");
        System.out.println(ProcessUtils.pgrep("gauge.agent"));

        ProcessUtils.pkill("gauge.agent");
        ProcessUtils.pkillForcibly("gauge.agent");

        System.out.println(new DateTime() + " List agents after sending a kill:");
        System.out.println(ProcessUtils.pgrep("gauge.agent"));
        System.out.println(new DateTime() + " ==================================");
    }

    public AgentLauncher getAgent(int i) {
        return agents.get(i);
    }
}
