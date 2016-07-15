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
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.editpipelinewizard.AutoCompleteSuggestions;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.CruiseConstants;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.hamcrest.text.StringContains;
import org.junit.Assert;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public abstract class CruisePage {

    protected final ScenarioState scenarioState;
    protected final boolean alreadyOn;
    protected Browser browser;
    protected SahiBrowserWrapper browserWrapper;

    public CruisePage(ScenarioState scenarioState, Browser browser) {
        this(scenarioState, false, browser);
    }

    public CruisePage(ScenarioState scenarioState, boolean alreadyOn, Browser browser) {
        if (!alreadyOn)
            ensureIsNotAnAlreadyOnPageClass();
        this.scenarioState = scenarioState;
        this.alreadyOn = alreadyOn;
        this.browser = browser;
        this.browserWrapper = new SahiBrowserWrapper(browser);
        open();
    }

    private void ensureIsNotAnAlreadyOnPageClass() {
        if (getClass().getSimpleName().startsWith("AlreadyOn")) {
            throw new IllegalStateException(String.format(
                    "Page class %s is using the wrong constructor, use the one with alreadyOn set to true", getClass().getSimpleName()));
        }
    }

    public void open() {
        if (alreadyOn)
            return;
        navigateToURL();
    }

	protected void navigateToURL() {
		browser.navigateTo(url(), true);
	}

    protected abstract String url();

    public void verifyPageTitleIs(String expectedTitle) {
        String pageTitle = findPageTitle();
        Assert.assertThat(pageTitle, Is.is(expectedTitle));
    }

    protected String findPageTitle() {
        return browser.byId("page-title").getText();
    }

    public void verifyRedirectedToServerDetailPage() throws Exception {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            public boolean call() throws Exception {
                return browserWrapper.getCurrentUrl().contains(OnServerDetailPage.URL);
            }
        });
    }

    public void verifyNotRedirected() throws Exception {
        Assert.assertThat(browserWrapper.getCurrentUrl(), Matchers.not(StringContains.containsString(OnServerDetailPage.URL)));
    }

    protected void reloadPage() {
        browserWrapper.reload();
    }

    public void verifyCruiseFooter() throws Exception {
        ElementStub footerText = browser.paragraph("copyright");
		assertTrue(footerText.isVisible());

        ElementStub elementTWLink = browser.link("/ThoughtWorks/").in(footerText);
        assertTrue(elementTWLink.exists());
        assertThat(elementTWLink.fetch("href"), StringContains.containsString("https://www.thoughtworks.com/products"));

        ElementStub licenseLink = browser.link("/Apache License, Version 2.0/").in(footerText);
        assertTrue(licenseLink.exists());
        assertThat(licenseLink.fetch("href"), StringContains.containsString("https://www.apache.org/licenses/LICENSE-2.0"));

        //ElementStub elementVersion = browser.listItem("last").in(browser.div("footer"));
        assertThat(footerText.getText(), Matchers.containsString("Go Version: " + CruiseConstants.CURRENT_REVISION));

        ElementStub elementCCTrayFeed = browser.link("cctray");
        assertThat(elementCCTrayFeed.fetch("href"), StringContains.containsString("/go/cctray.xml"));

        ElementStub elementAPIs = browser.link("api");
        assertThat(elementAPIs.fetch("href"), StringContains.containsString("https://api.go.cd/current"));

        ElementStub elementPlugins = browser.link("plugins");
        assertThat(elementPlugins.fetch("href"), StringContains.containsString("https://www.go.cd/community/plugins.html"));

        ElementStub elementServerDetails = browser.link("server-details");
        assertThat(elementServerDetails.fetch("href"), StringContains.containsString("/go/about"));

        ElementStub elementDoc = browser.link("documentation");
        assertThat(elementDoc.fetch("href"), StringContains.containsString("https://docs.go.cd/current"));

        ElementStub elementForum = browser.link("forums");
        assertThat(elementForum.fetch("href"), StringContains.containsString("https://groups.google.com/d/forum/go-cd"));

        ElementStub elementGit = browser.link("github");
        assertThat(elementGit.fetch("href"), StringContains.containsString("https://github.com/gocd/gocd"));

        ElementStub elementTwitter = browser.link("twitter");
        assertThat(elementTwitter.fetch("href"), StringContains.containsString("https://twitter.com/goforcd"));


    }

    protected String stageDetailsUrl(String pipelineName, String pipelineCounter, String stageName, String stageCounter) {
        return Urls.stageDetailUrlfor(String.format("%s/%s/%s/%s", scenarioState.pipelineNamed(pipelineName), pipelineCounter, stageName,
                stageCounter));
    }

    public void logout() {
    	browser.execute("_sahi._deleteCookie('JSESSIONID')");
    	browser.execute("document.cookie='JSESSIONID=;expires=Thu, 01 Jan 1970 00:00:01 GMT;'");
		ElementStub userLink = browser.listItem("current_user icon");
    	if (!browser.title().contains("Login") && userLink.exists() && browser.link("nav-logout").exists()) {
        	userLink.click();
            ElementStub logoutLink = browser.link("nav-logout");
            assertThat(logoutLink.exists(), Is.is(true));
            logoutLink.click();
        }
        scenarioState.logOut();
    }

    public void verifyInformationMessage(String expectedMessage) throws Exception {
        Assert.assertThat(browser.div("notification").getText(), Is.is(expectedMessage));
    }

    protected void enterInTextBox(final String fieldName, final String value) {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            public boolean call() throws Exception {
                ElementStub textbox = browser.textbox(fieldName);
                if (!textbox.exists()) {
                    return false;
                }
                textbox.setValue(value);
                return true;
            }
        });
    }

    public void proceedWithDirtyCheckPrompt(){
        ElementStub proceedButton = browser.button("Proceed");
        Assert.assertThat(proceedButton.exists(), Is.is(true));
        proceedButton.click();
    }

    public void proceedWithConfirmPrompt(){
        ElementStub proceedButton = browser.span("PROCEED");
        Assert.assertThat(proceedButton.exists(), Is.is(true));
        proceedButton.click();
    }

    public String[] splitMe(String tabs) {
		String[] allTabs = tabs.split(",");
		return allTabs;
	}

    protected abstract class ReloaderPredicate implements Predicate {
        @Override
        public boolean call() throws Exception {
            reloadPage();
            return callAfterReload();
        }

        abstract boolean callAfterReload() throws Exception;
    }

    public void verifyThatTableHasHeaders(String tableName, String allHeaders) throws Exception {
        ElementStub stagesOfTemplatedPipelineTable = browser.table(tableName);
        assertThat(stagesOfTemplatedPipelineTable.exists(), Is.is(true));

        String[] individualHeaders = allHeaders.split(",");
        for (String currentHeader : individualHeaders) {
            ElementStub tableHeader = browser.tableHeader(currentHeader.trim()).in(stagesOfTemplatedPipelineTable);
            assertThat(tableHeader.exists(), Is.is(true));
        }
    }

    public void verifyThatUnauthorizedAccessMessageIsShown() throws Exception {
		ElementStub notification = browser.div(Regex.matches("notification"));
		ElementStub notificationIcon = browser.div("biggest").in(notification);
		ElementStub notificationText = browser.heading3("Not authorized to view pipeline { Not authorized to view pipeline } ").in(notification);
		assertThat(browser.title().contains("HTTP ERROR 401"), Is.is(true));
		assertThat(notificationIcon.exists(), Is.is(true));
		assertThat(notificationText.exists(), Is.is(true));
	}

    public void assertThatTextBoxHasValue(String identifier, String value) {
		assertEquals(browser.textbox(identifier).getValue(), value);
	}

	public void assertThatTextAreaHasValue(String identifier, String value) {
		assertEquals(browser.textarea(identifier).getValue(), value);
	}

	public void sleepFor(int millis) throws InterruptedException{
		Thread.sleep(millis);
	}

	public void autoCompleteShouldShowSuggestions(String expectedSuggestions) throws Exception {
        AutoCompleteSuggestions suggestions = new AutoCompleteSuggestions(browser, browserWrapper);
        String[] expected = expectedSuggestions.split(",");
        for (String suggestion : expected){
        	suggestions.autoCompletesShouldShowSuggestion(suggestion.trim());
        }
        assertEquals("Found: " + suggestions.allSuggestionTexts(0), expected.length, suggestions.allSuggestion(0).size());
	}

	public void selectOptionFromCommandLookupDropdown(Integer oneBasedOptionIndex) throws Exception {
        AutoCompleteSuggestions suggestions = new AutoCompleteSuggestions(browser, browserWrapper);
        suggestions.selectOption(oneBasedOptionIndex - 1, 0);
	}
}
