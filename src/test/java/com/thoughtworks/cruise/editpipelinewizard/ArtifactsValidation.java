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

package com.thoughtworks.cruise.editpipelinewizard;


import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;

public class ArtifactsValidation {

	
	
	private String source;

	private String destination;

	private String messageForSource;

	private String messageForDestination;

	private String saveStatus;

	private boolean expected;
	
	private final String escapedCharacter = "|";
	
	private final String escapeCharacter = ":";

	private AlreadyOnArtifactsListingPage artifactsPage;

	public ArtifactsValidation(AlreadyOnArtifactsListingPage alreadyOnArtifactsListingPage)
	{
		this.artifactsPage = alreadyOnArtifactsListingPage;
		
	}
	
	
	public void setDestination(final String destination) throws Exception {
		this.destination = destination;		
			
	}

	public void setExpected(final String expected) throws Exception {
		this.expected = Boolean.valueOf(expected);
		
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {

			public boolean call() throws Exception {
				
				artifactsPage.enterArtifactDetail(1, source, destination, "Build");
				artifactsPage.clickSave();
					
				return (( artifactsPage.isMessagePresent(messageForDestination) && artifactsPage.isMessagePresent(messageForSource) && artifactsPage.isMessagePresent(saveStatus)) == Boolean.valueOf(expected));
				
						
			}
		});
	}

	public void setMessageForDestination(final String messageForDestination)
			throws Exception {
		this.messageForDestination = messageForDestination.replaceAll(escapeCharacter, escapedCharacter);
		System.out.println(this.messageForDestination);
	}

	public void setMessageForSource(final String messageForSource) throws Exception {
		this.messageForSource = messageForSource.replaceAll(escapeCharacter, escapedCharacter);
		System.out.println(this.messageForSource);
	}

	public void setSaveStatus(final String saveStatus) throws Exception {
		this.saveStatus = saveStatus;
	}

	public void setSource(final String source) throws Exception {
		this.source = source;
	}


	@com.thoughtworks.gauge.Step("ArtifactsValidation <table>")
	public void brtMethod(com.thoughtworks.gauge.Table table) throws Throwable {
		com.thoughtworks.twist.migration.brt.BRTMigrator brtMigrator = new com.thoughtworks.twist.migration.brt.BRTMigrator();
		try {
			brtMigrator.BRTExecutor(table, this);
		} catch (Exception e) {
			throw e.getCause();
		}
	}

	
}
