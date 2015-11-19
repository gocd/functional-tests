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

import java.io.File;

import static com.thoughtworks.cruise.util.CruiseConstants.CURRENT_REVISION;

public class RuntimePath {
	private static final String SERVER_ROOT = "target/go-server-" + CURRENT_REVISION;
	private static final String SERVER_CONFIG_PATH = "target/go-server-" + CURRENT_REVISION + "/config"; //  use this path if you want to run tests against Development Twist Server "../../cruise/server/config";
	private static final String AGENT_ROOT = "target/go-agent-" + CURRENT_REVISION;

    public static String pathFor(String path) {
		return path;
	}

	public static String absolutePathFor(String path){
	    return new File(pathFor(path)).getAbsolutePath();
	}

	public static String getServerRoot() {
	    //System.getenv("TWIST_CRUISE_PORT") != null ? System.getenv("TWIST_CRUISE_PORT") : "8253";
	    String serverPath = System.getenv("TWIST_SERVER_PATH") != null ? System.getenv("TWIST_SERVER_PATH") : pathFor(SERVER_ROOT); 
	    return serverPath;
	}
	
	public static String getArtifactPath(String artifactsDir) {
	    return new File(RuntimePath.getServerRoot(), artifactsDir).getAbsolutePath();
	}
	
	public static String getServerConfigPath() {
		String serverConfigPath = System.getenv("TWIST_SERVER_CONFIG_PATH") != null ? System.getenv("TWIST_SERVER_CONFIG_PATH") : pathFor(SERVER_CONFIG_PATH); 
		return serverConfigPath;
	}
	
	public static String getAgentRoot() {
		String agentPath = System.getenv("TWIST_AGENT_PATH") != null ? System.getenv("TWIST_AGENT_PATH") : pathFor(AGENT_ROOT);
		return agentPath;
	}

	public static String getTwistAgentPath() {
		String agentPath = System.getenv("TWIST_AGENT_PATH") != null ? System.getenv("TWIST_AGENT_PATH") : pathFor("target/twist-agents");
		return agentPath;
	}

    public static String getOldAgentPath() {
        String agentPath = pathFor(SetupAgents.AGENT_2_4);
        return agentPath;
    }
}

