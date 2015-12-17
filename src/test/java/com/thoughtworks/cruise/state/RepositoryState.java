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

package com.thoughtworks.cruise.state;

import com.thoughtworks.cruise.materials.Repository;
import com.thoughtworks.cruise.materials.RepositoryFactory;
import com.thoughtworks.cruise.util.ExceptionUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryState {

    private List<Repository> repos = new ArrayList<Repository>();
    private final Map<String, String> revisionAliases = new HashMap<String, String>();
    private final Map<String, String> stageUrlKeyMap = new HashMap<String, String>();
    private Repository currentRepo;

    public void cleanupRepos() {
        for (Repository repo : repos) {            
            try {
                repo.cleanup();
            } catch (Exception e) {
                System.err.println("Ignoring error when tearing down "  + " - " + repo.getUrl() + " : " + e.getMessage());
            }
        }
    }

    public List<Repository> allRepos() {
        return repos;
    }

  /*  public boolean containsRepo(String type) {
    	for (Repository repo : repos) {
    		if(type.equals(repo.getType())) return true;
		}
        return false;
    }*/

    public void createMaterial(Element element) {
        if (!RepositoryFactory.isRepository(element.getName()))
            return;
        Repository repository = getOrCreateRepository(element);
        repository.setUrl(element);
        repository.setOtherAttributes(element);
    }

    private Repository getOrCreateRepository(Element material) {
    	for (Repository repo : repos) {			
    		if(repo.isForMaterial(material)) return repo;
		}	
        Repository repo = RepositoryFactory.create(material);
        repos.add(repo);
		return repo;
    }

    public String commitRevision(String which, String materialName, String pipelineRuntimeName) {
        int revisionOffset = Integer.parseInt(which.substring(0, which.length() - 2));
        Repository repository = getRepoByMaterialName(pipelineRuntimeName, materialName);
        List<String> recentCommits = repository.recentCommits();
        return recentCommits.get(recentCommits.size() - revisionOffset);
    }

    public Repository getRepositoryByDestinationFolder(String dest) {
        for (Repository repo : allRepos()) {
            if (repo.getDestinationFolder().equals(dest)) {
                return repo;
            }
        }
        throw ExceptionUtils.bomb("no material with dest folder: " + dest);
    }   
  
    public void setCurrentRepository(String materialName, String pipelineRuntimeName) {
    	Repository repo = getRepoByMaterialName(pipelineRuntimeName, materialName);
    	if(repo==null) {
    	    throw ExceptionUtils.bomb("no material with name: " + materialName + " in pipeline " + pipelineRuntimeName);
    	}
    	currentRepo =repo;
    	
    }

    public Repository getRepoByMaterialName(String pipelineRuntimeName, String materialName) {
        for (Repository repo : allRepos()) {
            if (materialName.equals(repo.getMaterialName()) && repo.belongsTo(pipelineRuntimeName)) {
                
                return repo;
            }
        }
        return null;
    }

    public Repository currentRepository() {
    	if (currentRepo == null) { currentRepo = onlyRepository(); }
		if (currentRepo == null) { throw new IllegalStateException("Must first set the current repository"); }
        return currentRepo;
    }

	public Repository onlyRepository() {
		if (repos.size() != 1) { return null; }
		return repos.get(0);
	}
	
	public void setCurrentRepositoryWithExternal(String materialName, String pipelineName) {
    	for (Repository repo : allRepos()) {
            if (repo.getMaterialName().equals(materialName) && repo.belongsTo(pipelineName)) {
                currentRepo = repo.externalRepo();
                return;
            }
        }
    	throw ExceptionUtils.bomb("no material with name: " + materialName);
	}

	public void rememberCurrentVersionAs(String revisionAlias) {
		revisionAliases.put(revisionAlias, currentRepository().latestRevisionString());
	}

	public String getRevisionFromAlias(String revisionAlias) {
		String cachedRev = revisionAliases.get(revisionAlias);
		return cachedRev == null ? revisionAlias : cachedRev;
	}

    public void rememberVersionAs(String revision, String alias) {
        revisionAliases.put(alias, revision);
    }

	public void rememberStageIdForKey(String stageIdKey, String xmlVersionUrl) {
		stageUrlKeyMap.put(stageIdKey, xmlVersionUrl);
	}
	
	public String getStageIdForKey(String stageIdKey) {
		return stageUrlKeyMap.get(stageIdKey);
	}
}
