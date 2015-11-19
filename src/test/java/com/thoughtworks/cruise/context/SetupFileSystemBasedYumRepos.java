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

import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.configfile.PackageRepository;
import com.thoughtworks.twist.core.execution.TwistScenarioDataStore;
import net.sf.sahi.client.Browser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

// JUnit Assert framework can be used for verification

class RepositoryInitialFiles
{
	
	
	public String [] getRepositoryFiles (String repoName)
	{
		
		if(repoName.equals("repo1"))
		{
			return "go-server-13.1.0-122.noarch.rpm,go-server-13.1.1-16714.noarch.rpm".split(",");
		}
		
		else if(repoName.equals("repo2"))
		{
			return "go-agent-13.1.0-5411.noarch.rpm,go-agent-13.1.0-112.noarch.rpm,go-agent-13.1.0-16714.noarch.rpm".split(",");
		}
		
		else if(repoName.equals("repo3"))
		{
			return "go-agent-13.1.0-112.noarch.rpm,go-server-12.4.0-1234.noarch.rpm".split(",");
		}
	    else {
	            throw new UnsupportedOperationException("Implement me");
	        }

	}
	
	
}

public class SetupFileSystemBasedYumRepos {

	File common_yum_repo_location = new File(PackageRepository.getPackageRepositoryLocation());

	private Browser browser;
	private final ScenarioState scenarioState;
	private RepositoryInitialFiles initialFiles;

	@Autowired
	private TwistScenarioDataStore scenarioStore;

	public SetupFileSystemBasedYumRepos(ScenarioState scenarioState,Browser browser) {
		this.browser = browser;
		this.scenarioState = scenarioState;
		initialFiles = new RepositoryInitialFiles();
	}

	
	
	@com.thoughtworks.gauge.Step("Setup file system based yum repos <joinedReposWithFiles> - setup")
	public void setUp(String joinedReposWithFiles) throws Exception {
		FileUtils.deleteQuietly(common_yum_repo_location);

		String [] reposString = joinedReposWithFiles.split(";");
		String [] fileNameList;
		String repository = "";		
		for (int i = 0; i < reposString.length; i++) {
			repository = hasFileNameList(reposString[i]) ? getRepositoryName(reposString[i]) : reposString[i]; 
			fileNameList = hasFileNameList(reposString[i]) ?  getFileNameListFromString(reposString[i]) : initialFiles.getRepositoryFiles(repository);
			copyAndPublishPackages(fileNameList, repository);
		}

	}

	private String getRepositoryName(String repoString)
	{
		
		return repoString.substring(0, repoString.indexOf(":"));
	}
	private String [] getFileNameListFromString(String fileNameString)
	{
		return fileNameString.substring(fileNameString.indexOf(":") + 1, fileNameString.length()).split(",");
		
	}
	
	private boolean hasFileNameList(String repositoryString) {
			return (repositoryString.split(":").length > 1);
		
	}



	private void execute(File workingDir, String... args) throws IOException,
	InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		processBuilder.directory(workingDir);
		Process process = processBuilder.start();
		process.waitFor();
	}

	private void copyFilesToDirectory(String [] fileNameList, String repoDirectory)
			throws IOException {

		for (int i = 0; i < fileNameList.length; i++) {
			File file = new File(PackageRepository.getYumRepoFilesLocation() + "/" +  fileNameList[i]);
			FileUtils.copyFileToDirectory(file, new File(repoDirectory));
		}


	}


	@com.thoughtworks.gauge.Step("Setup file system based yum repos <joinedRepos> - teardown")
	public void tearDown(String joinedRepos) throws Exception {
		FileUtils.deleteQuietly(common_yum_repo_location);

	}

	@com.thoughtworks.gauge.Step("Publish artifacts <joinedPackages> to <repository>")
	public void publishArtifactsTo(String joinedPackages, String repository)
			throws Exception {
		String [] packages = joinedPackages.split(",");
		copyAndPublishPackages(packages, repository);
	}

	public void copyAndPublishPackages(String [] packages, String repository)
			throws Exception
			{
		copyFilesToDirectory(packages, scenarioState.packageRepositoryNamed(repository.trim()) );
		execute(new File(scenarioState.packageRepositoryNamed(repository.trim()) ), "createrepo", ".");		

			}


}
