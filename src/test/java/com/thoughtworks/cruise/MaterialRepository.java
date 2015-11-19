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

import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.context.Configuration;
import com.thoughtworks.cruise.materials.GitSubmoduleRepository;
import com.thoughtworks.cruise.materials.Repository;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import org.dom4j.Element;

import java.io.File;
import java.util.List;

public class MaterialRepository {
	
    private final RepositoryState repositoryState;
    private final ScenarioState scenarioState;
    private final Configuration configuration;
    private final TalkToCruise talkToCruise;
    

	public MaterialRepository(RepositoryState repoState, ScenarioState scenarioState, Configuration configuration, TalkToCruise talkToCruise) {
		this.repositoryState = repoState;
        this.scenarioState = scenarioState;
        this.configuration = configuration;
        this.talkToCruise = talkToCruise;
	}
	
	@com.thoughtworks.gauge.Step("With material <materialName> for pipeline <pipeline>")
	public void withMaterialForPipeline(String materialName, String pipeline) throws Exception {
		repositoryState.setCurrentRepository(materialName, scenarioState.pipelineNamed(pipeline));
	}

	@com.thoughtworks.gauge.Step("Create file <file> as <userName>")
	public void createFileAs(String file,String userName) throws Exception {
		repositoryState.currentRepository().createNewFile(file, userName);
	}

	@com.thoughtworks.gauge.Step("Checkin as <username> with message <message>")
	public void checkinAsWithMessage(String username, String message) throws Exception {
		repositoryState.currentRepository().checkin(username, message);
	}

    @com.thoughtworks.gauge.Step("Checkin file <fileName> as user <username> with message <message>")
	public void checkinFileAsUserWithMessage(String fileName, String username, String message) throws Exception {
        createFileAs(fileName,username);
        checkinAsWithMessage(username, message);
    }

    @com.thoughtworks.gauge.Step("With material named <materialName> in pipeline <pipeline>")
	public void withMaterialNamedInPipeline(String materialName, String pipeline) throws Exception {
        if (!containsMaterial(materialName, pipeline)) {
            throw new IllegalArgumentException("Material " + materialName + " not found");
        }
        this.repositoryState.setCurrentRepository(materialName,scenarioState.pipelineNamed(pipeline));
    }
    

    private boolean containsMaterial(String materialName, String pipeline) {
        List<Element> materials = configuration.provideDom().materialsForPipeline(scenarioState.pipelineNamed(pipeline));
        for (Element material : materials) {
        	if(material.attribute("materialName") == null){
        		continue;
        	}
            if (material.attribute("materialName").getText().equals(materialName)) {
                return true;
            }
        }
        return false;
    }

	@com.thoughtworks.gauge.Step("With external material of <materialName> for pipeline <pipelineName>")
	public void withExternalMaterialOfForPipeline(String materialName, String pipelineName) throws Exception {
        if (!containsMaterial(materialName, pipelineName)) {
            throw new IllegalArgumentException("Material " + materialName + " not found");
        }
        this.repositoryState.setCurrentRepositoryWithExternal(materialName, scenarioState.pipelineNamed(pipelineName));
	}

	@com.thoughtworks.gauge.Step("Commit file <fileBaseName>")
	public void commitFile(String fileBaseName) throws Exception {
		Repository currentRepository = currentRepository();
		currentRepository.addFile(new File(RuntimePath.pathFor("material-files"), fileBaseName));
		currentRepository.checkin(String.format("File %s", fileBaseName));
	}

	@com.thoughtworks.gauge.Step("Commit file <fileBaseName> to directory <folderToCopyTo>")
	public void commitFileToDirectory(String fileBaseName, String folderToCopyTo) throws Exception {
		Repository currentRepository = currentRepository();
		currentRepository.addFile(new File(RuntimePath.pathFor("material-files"), fileBaseName), folderToCopyTo);
		currentRepository.checkin(String.format("File %s/%s", folderToCopyTo, fileBaseName));
	}
	
	@com.thoughtworks.gauge.Step("Commit file <fileBaseName> to directory <folderToCopyTo> as user <username>")
	public void commitFileToDirectoryAsUser(String fileBaseName, String folderToCopyTo, String username) throws Exception {
		Repository currentRepository = currentRepository();
		currentRepository.addFile(new File(RuntimePath.pathFor("material-files"), fileBaseName), folderToCopyTo);
		currentRepository.checkin(username, String.format("File %s/%s", folderToCopyTo, fileBaseName));
	}

	@com.thoughtworks.gauge.Step("Checkin git submodule reference")
	public void checkinGitSubmoduleReference() throws Exception {
		Repository currentRepository = currentRepository();
		if (!(currentRepository instanceof GitSubmoduleRepository)) {
			throw new IllegalArgumentException("Current repository is not a git submodule repository");
		}
		GitSubmoduleRepository gitSubmoduleRepository = (GitSubmoduleRepository) currentRepository;
		gitSubmoduleRepository.updateAndCommitSubmoduleReference();
	}

	@com.thoughtworks.gauge.Step("Remember current version as <revisionAlias>")
	public void rememberCurrentVersionAs(String revisionAlias) throws Exception {
		repositoryState.rememberCurrentVersionAs(revisionAlias);
	}

	public void waitForMaterialCheckToComplete() throws Exception {
	    Thread.sleep(2000);
	}

    @com.thoughtworks.gauge.Step("Remember revision <revision> as <alias>")
	public void rememberRevisionAs(String revision, String alias) throws Exception {
        repositoryState.rememberVersionAs(scenarioState.expand(revision), alias);
    }

	@com.thoughtworks.gauge.Step("Modify file <fileName> as <userName>")
	public void modifyFileAs(String fileName, String userName) throws Exception {
		Repository currentRepository = currentRepository();
		currentRepository.modifyFile(fileName, userName);
	}

	@com.thoughtworks.gauge.Step("Delete file <fileName> as <userName>")
	public void deleteFileAs(String fileName, String userName) throws Exception {
		Repository currentRepository = currentRepository();
		currentRepository.deleteFile(fileName, userName);
	}

    private Repository currentRepository() {
        Repository currentRepository = repositoryState.currentRepository();
        return currentRepository;
    }

    @com.thoughtworks.gauge.Step("Initiate post commit hook")
	public void initiatePostCommitHook() throws Exception {
        currentRepository().initatePostCommitHook(talkToCruise);
    }

}
