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

package com.thoughtworks.cruise.page;

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.SahiBrowserWrapper;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.ScenarioHelper;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.springframework.beans.factory.DisposableBean;

import java.util.List;

public class AlreadyOnMessagesPopup implements DisposableBean {

	private final Browser browser;
	private final ScenarioState state;
	private final ScenarioHelper scenarioHelper;

	public AlreadyOnMessagesPopup(ScenarioState state, ScenarioHelper scenarioHelper, Browser browser) {
		this.state = state;
		this.scenarioHelper = scenarioHelper;
		this.browser = browser;
	}
	
	@com.thoughtworks.gauge.Step("Verify error <location> contains <message>")
	public void verifyErrorContains(final String location, final String message) throws Exception {
		assertMessage(location, message, "error");
	}

	@com.thoughtworks.gauge.Step("Verify warning <location> contains <message>")
	public void verifyWarningContains(final String location, String message) throws Exception {
		assertMessage(location, message, "warning");
	}

	private void assertMessage(final String location, final String message, final String status) {
		Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {			
			public boolean call() throws Exception {
				ElementStub content = browser.div(Regex.matches(message));
				if (content.exists() && content.fetch("className").contains(location) && content.parentNode().fetch("className").contains(status)) {
					return true;
				} else {
					browser.link("MB_close").click();
					reloadPage();
					openErrorAndWarningMessagesPopup();
					return false;
				}
			}
		});
	}
	
	private void reloadPage(){
		new SahiBrowserWrapper(browser).reload();        
	}
	
	private void openErrorAndWarningMessagesPopup() throws Exception {
		new OnAnyPage(state, scenarioHelper, browser).openErrorAndWarningMessagesPopup();
	}	

	@com.thoughtworks.gauge.Step("Verify error <location> does not contain <message>")
	public void verifyErrorDoesNotContain(String location, String message) throws Exception {
		assertMessageDoesNotExist(location, message, "error");
	}
	
	@com.thoughtworks.gauge.Step("Verify warning <location> do not contain <message>")
	public void verifyWarningDoNotContain(String location, String message) throws Exception {
		assertMessageDoesNotExist(location, message, "warning");
	}

	private void assertMessageDoesNotExist(final String location, final String message, final String status) {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
		
		public boolean call() throws Exception {
			ElementStub content = browser.div(Regex.matches(message));
			if (content.exists() && content.fetch("className").contains(location) && content.parentNode().fetch("className").contains(status)) {
				browser.link("MB_close").click();
				reloadPage();
				openErrorAndWarningMessagesPopup();
				return false;
			} else {
				return true;
			}
		}
	});
	}

	@Override
	public void destroy() throws Exception {
		close();
	}
	
    @com.thoughtworks.gauge.Step("Close")
	public void close() {
    	browser.byId("MB_close").click();         
    }

	@com.thoughtworks.gauge.Step("Verify error message <message> and description <description>")
	public void verifyErrorMessageAndDescription(final String message, final String description)
			throws Exception {
		final String expected = state.expand(description).trim();
		final String expectedMessage = state.expand(message).trim();
		Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {
			public boolean call() throws Exception {
				List<ElementStub> messageTags = browser.div("message").collectSimilar();
				ElementStub descriptionTag = null;
		
					for (ElementStub elementStub : messageTags) {
						if(elementStub.containsText(expectedMessage))
						{
							descriptionTag = browser.div("description").in(elementStub.parentNode());
							break;
						}
					}
					if ( !(descriptionTag == null))
					{
						return(expected.contains(descriptionTag.getText().trim()));
					}
					else
					{
						browser.link("MB_close").click();
						reloadPage();
						openErrorAndWarningMessagesPopup();
						return false;
					}
				}
			});
		}
}
