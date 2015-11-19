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

import net.sf.sahi.client.Browser;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CommandRepository {

	private final Browser browser;

	public CommandRepository(Browser browser) {
		this.browser = browser;
	}

	public void verifySnippetDetailsAreShownWithNameDescriptionAuthorWithAuthorlinkAndMoreInfo(String baseXPath, String name, String description, String author, String authorLink, String moreInfoLink) throws Exception {
		String xPathForSnippetDetailsSection = "//div[contains(@class, 'snippet_details')]";
		assertEquals(name, browser.byXPath(baseXPath + xPathForSnippetDetailsSection + "//div[@class='name']/span[@class='value']").getText());
		assertEquals(description, browser.byXPath(baseXPath + xPathForSnippetDetailsSection + "//div[@class='description']/span[@class='value']").getText());
		assertEquals(author, browser.byXPath(baseXPath + xPathForSnippetDetailsSection + "//div[@class='author']/span[@class='value-with-link']/a").getText());
		assertEquals(authorLink, browser.byXPath(baseXPath + xPathForSnippetDetailsSection + "//div[@class='author']/span[@class='value-with-link']/a").fetch("href"));
		assertEquals(moreInfoLink, browser.byXPath(baseXPath + xPathForSnippetDetailsSection + "//div[@class='more-info']/span[@class='value-with-link']/a").fetch("href"));
	}
	
	public void verifySnippetDetailsAreShownWithNameOnly(String baseXPath, String name) throws Exception {
		String xPathForSnippetDetailsSection = "//div[contains(@class, 'snippet_details')]";
		assertEquals(name, browser.byXPath(baseXPath + xPathForSnippetDetailsSection + "//div[@class='name']/span[@class='value']").getText());
		assertEquals("No description available.", browser.byXPath(baseXPath + xPathForSnippetDetailsSection + "//div[@class='description']/span[@class='value']").getText());
        assertTrue("Author is visible!", !browser.byXPath(baseXPath + xPathForSnippetDetailsSection + "//div[@class='author']").isVisible());
        assertTrue("More info is visible!", !browser.byXPath(baseXPath + xPathForSnippetDetailsSection + "//div[@class='more-info']/span[@class='value-with-link']/a").isVisible());
	}

}
