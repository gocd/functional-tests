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

import com.thoughtworks.cruise.preconditions.ServerIsRunning;

import java.io.File;

public class SetupServer{

    private final ServerIsRunning serverIsRunning;

    public SetupServer(ServerIsRunning serverIsRunning){
        this.serverIsRunning = serverIsRunning;
        
    }
    
    @com.thoughtworks.gauge.Step("Stop server")
	public void stopServer() throws Exception {
        serverIsRunning.stop();
    }
    
    @com.thoughtworks.gauge.Step("Start server with fanin turned off")
	public void startServerWithFaninTurnedOff() throws Exception {
        serverIsRunning.setFanInOff(true);
        serverIsRunning.start();
    }
    
	@com.thoughtworks.gauge.Step("Create a directory <dirName> without permissions in the command _ repository location <commandRepoLocation>")
	public void createADirectoryWithoutPermissionsInTheCommand_RepositoryLocation(
			String dirName, String commandRepoLocation) throws Exception {
		String dirWithoutPermissionLocation = String.format("/db/command_repository/%s/%s", commandRepoLocation, dirName);
		File dirWithoutPermissions = new File(RuntimePath.getServerRoot()+ dirWithoutPermissionLocation);
    	dirWithoutPermissions.mkdir();
    	dirWithoutPermissions.setReadable(false);
	}

	@com.thoughtworks.gauge.Step("Cleanup directory <dirName> in the command _ repository location <commandRepoLocation>")
	public void cleanupDirectoryInTheCommand_RepositoryLocation(String dirName,
			String commandRepoLocation) throws Exception {
		String dirLocation = String.format("/db/command_repository/%s/%s", commandRepoLocation, dirName);
		File dir = new File(RuntimePath.getServerRoot()+ dirLocation);
		dir.delete();
	}

	@com.thoughtworks.gauge.Step("Start server")
	public void startServer() throws Exception {
		serverIsRunning.start();
	}
}
