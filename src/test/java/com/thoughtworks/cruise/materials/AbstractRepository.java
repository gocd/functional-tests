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

import org.dom4j.Element;

import java.io.File;
import java.util.List;

public abstract class AbstractRepository implements Repository {

	private final Element material;
	private final String originalUrl;
	
	public AbstractRepository(Element material) {
		this.material = material;
		this.originalUrl = material.attributeValue("url");
	}

	public AbstractRepository(Element material, String originalUrl) {
		this.material = material;
		this.originalUrl = originalUrl;
	}

	public String getMaterialAttribute(String attribute){
	    return material.attributeValue(attribute);
	}
	
	@Override
	public String getDestinationFolder() {
		return getMaterialAttribute("dest");
	}
	
	@Override
	public boolean belongsTo(String runtimePipeline){
		return material.selectSingleNode("../..[@name='"+runtimePipeline+"']")!=null;
	}
	
	@Override
	public String getMaterialName() {
		return getMaterialAttribute("materialName");
	}

    @Override
    public List<String> recentCommits() {
        throw new RuntimeException("Not implemened yet");
    }

    @Override
	public boolean isForMaterial(Element material){
		if (!material.getName().equals(this.material.getName())) { return false; }
		String thatUrl = material.attributeValue("url");
		return (thatUrl == null) ? false : thatUrl.equals(originalUrl);
	}
	
	@Override
	public Repository externalRepo() {
		throw new UnsupportedOperationException("The material " + material + " does not have an external. Bu hao");
	}

	@Override
	public void addFile(File file) {
		throw new UnsupportedOperationException("The material " + material + " is currently unable to add a file.");
	}
	
	@Override
	public void addFile(File file, String destFolder) {
		throw new UnsupportedOperationException("The material " + material + " is currently unable to add a file.");
	}

    @Override
    public String head() {
        throw new RuntimeException("Not implemented yet");
    }
    
    @Override 
    public void setUrl(Element element){
    	if (element.attribute("url") == null)
            return;
        element.attribute("url").setValue(getUrl());
    }
    @Override
    public void setOtherAttributes(Element element){
    }
}
