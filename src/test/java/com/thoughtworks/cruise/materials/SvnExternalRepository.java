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

package com.thoughtworks.cruise.materials;

import com.thoughtworks.cruise.client.TalkToCruise;
import org.dom4j.Element;

import java.io.File;
import java.util.List;

public class SvnExternalRepository implements Repository {

	private SvnRepository parentRepo;
	private SvnRepository externalRepo;

	public SvnExternalRepository(Element material, String parentBundlePath,String externalBundlePath) {
		parentRepo = new SvnRepository(material, parentBundlePath);
		externalRepo = new SvnRepository(material, externalBundlePath);
		parentRepo.addExternal(externalRepo);
	}

	@Override
	public void checkin(String message) {
		parentRepo.checkin(message);
	}

	@Override
	public void checkin(String username, String message) {
		parentRepo.checkin(username, message);
	}

    @Override
    public String head() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
	public void cleanup() {
		parentRepo.cleanup();
		externalRepo.cleanup();		
	}

	@Override
	public void createNewFile(String file, String userName) {
		parentRepo.createNewFile(file, userName);		
	}

	@Override
	public String getRevisionFromComment(String checkinComment) {
		return parentRepo.getRevisionFromComment(checkinComment);
	}

	@Override
	public String getUrl() {
		return parentRepo.getUrl();
	}

	@Override
	public String getDestinationFolder() { 
		return parentRepo.getDestinationFolder();
	}

	public String getMaterialName() {
		return parentRepo.getMaterialName();
	}

    @Override
    public List<String> recentCommits() {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
	public boolean belongsTo(String runtimePipeline){
		return parentRepo.belongsTo(runtimePipeline);
	}
	
	@Override
	public boolean isForMaterial(Element material){
		return false;
	}

	@Override
	public Repository externalRepo() {
		return externalRepo;
	}

	@Override
	public void addFile(File file) {
		throw new UnsupportedOperationException("The material SvnExternalRepository is currently unable to add a file.");
	}
	
	@Override
	public void addFile(File file, String destFolder) {
		throw new UnsupportedOperationException("The material SvnExternalRepository is currently unable to add a file.");
	}
	
	@Override
	public Revision latestRevision() {
		return parentRepo.latestRevision();
	}

	@Override 
    public void setUrl(Element element){
    	if (element.attribute("url") == null)
            return;
        element.attribute("url").setValue(getUrl());
    }

	@Override
	public void deleteFile(String fileName, String userName) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public String latestRevisionString() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public void modifyFile(String fileName, String userName) {
		throw new UnsupportedOperationException("not implemented yet");
	}

    @Override
    public void initatePostCommitHook(TalkToCruise talkToCruise) {
        throw new UnsupportedOperationException();
    }
}
