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

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.state.CurrentPageState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import org.junit.Assert;

public class AlreadyInPluginsListingPage {

	private Browser browser;

	public AlreadyInPluginsListingPage(Browser browser, CurrentPageState currentPageState) {
		this.browser = browser;
		currentPageState.assertCurrentPageIs(CurrentPageState.Page.ADMIN_PLUGINS_PAGE);
	}

	@com.thoughtworks.gauge.Step("Verify plugin with id <pluginId> is loaded")
	public void verifyPluginWithIdIsLoaded(String pluginId) throws Exception {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		Assert.assertThat(pluginBlock.exists(), Is.is(true));
	}

	private ElementStub getPluginListingContainer() {
		ElementStub container = browser.byId("plugins-listing");
		Assert.assertThat(container.exists(), Is.is(true));
		return container;
	}
	
	private ElementStub getPluginBlock(String pluginId){
		ElementStub pluginListingContainer = getPluginListingContainer();
		ElementStub pluginBlock = browser.byId(pluginId).in(pluginListingContainer);
		return pluginBlock;
	}

	public void verifyVersionForPluginWithIdIs(String pluginId, String version) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		Assert.assertThat(browser.span("smaller version").in(pluginBlock).text(), Is.is(version));
	}

	public void verifyNameForPluginWithIdIs(String pluginId, String name) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		Assert.assertThat(browser.span("name").in(pluginBlock).text(), Is.is(name));
	}

	public void verifyDescriptionForPluginWithIdIs(String pluginId, String description) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		Assert.assertThat(browser.div("description").in(pluginBlock).text(), Is.is(description));
	}

	public void clickOnMoreInformationForPluginWithId(String pluginId) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		browser.link("show-info").in(pluginBlock).click();
		Assert.assertThat(browser.listItem("plugin-location").in(pluginBlock).isVisible(), Is.is(true));
	}
	
	public void closeMoreInformationForPluginWithId(String pluginId) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		browser.link("show-info").in(pluginBlock).click();
		Assert.assertThat(browser.listItem("plugin-location").in(pluginBlock).isVisible(), Is.is(false));
	}

	public void verifyAuthorForPluginWithIdIsWithEnabledLinkTo(String pluginId, String authorName, String linkUrl) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		ElementStub pluginAuthor = browser.span("plugin-author smaller").in(pluginBlock);
		Assert.assertThat(browser.span("key").in(pluginAuthor).in(pluginBlock).text(), Is.is("Author"));
		Assert.assertThat(browser.span("value").in(pluginAuthor).in(pluginBlock).text(), Is.is(authorName));
		Assert.assertThat(browser.link(authorName).in(pluginAuthor).in(pluginBlock).fetch(), Is.is(linkUrl));
	}

	public void verifyPathForPluginWithIdContains(String pluginId, String path) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		ElementStub pluginLocation = browser.listItem("plugin-location").in(pluginBlock);
		Assert.assertThat(browser.span("key").in(pluginLocation).in(pluginBlock).text(), Is.is("Loaded from:"));
		Assert.assertThat(browser.span("value").in(pluginLocation).in(pluginBlock).text().contains(path), Is.is(true));
	}

	public void verifyTargetOsForPluginWithIdIs(String pluginId, String targetOs) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		ElementStub pluginLocation = browser.listItem("plugin-target-oses").in(pluginBlock);
		Assert.assertThat(browser.span("key").in(pluginLocation).in(pluginBlock).text(), Is.is("Target operating systems:"));
		Assert.assertThat(browser.span("value").in(pluginLocation).in(pluginBlock).text(), Is.is(targetOs));
	}

	public void verifyTargetGoVersionForPluginWithIdIs(String pluginId, String targetGoVersion) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		ElementStub pluginLocation = browser.listItem("plugin-target-go-version").in(pluginBlock);
		Assert.assertThat(browser.span("key").in(pluginLocation).in(pluginBlock).text(), Is.is("Target Go Version:"));
		Assert.assertThat(browser.span("value").in(pluginLocation).in(pluginBlock).text(), Is.is(targetGoVersion));
	}

	public void verifyBundledStatusForPluginWithIdIs(String pluginId, String bundledStatus) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		ElementStub pluginLocation = browser.listItem("plugin-bundled-status").in(pluginBlock);
		Assert.assertThat(browser.span("key").in(pluginLocation).in(pluginBlock).text(), Is.is("Bundled:"));
		Assert.assertThat(browser.span("value").in(pluginLocation).in(pluginBlock).text(), Is.is(bundledStatus));
	}

	public void verifyAuthorForPluginWithIdIsWithDisabledLink(String pluginId, String authorName) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		ElementStub pluginAuthor = browser.span("plugin-author smaller").in(pluginBlock);
		Assert.assertThat(browser.span("key").in(pluginAuthor).in(pluginBlock).text(), Is.is("Author"));
		Assert.assertThat(browser.span("value").in(pluginAuthor).in(pluginBlock).text(), Is.is(authorName));
		Assert.assertThat(browser.link(authorName).in(pluginAuthor).in(pluginBlock).exists(), Is.is(false));
	}

	@com.thoughtworks.gauge.Step("Verify plugin with id <pluginId> has version <version> name <name> description <description> author <authorNmae> with enabled link to <linkUrl>")
	public void verifyPluginWithIdHasVersionNameDescriptionAuthorWithEnabledLinkTo(String pluginId, String version, 
			String name, String description, String authorNmae, String linkUrl) {
		verifyVersionForPluginWithIdIs(pluginId, version);
		verifyNameForPluginWithIdIs(pluginId, name);
		verifyDescriptionForPluginWithIdIs(pluginId, description);
		verifyAuthorForPluginWithIdIsWithEnabledLinkTo(pluginId, authorNmae, linkUrl);
	}
	
	@com.thoughtworks.gauge.Step("Verify plugin with id <pluginId> has version <version> name <name> description <description> author <authorNmae> with disabled link")
	public void verifyPluginWithIdHasVersionNameDescriptionAuthorWithDisabledLink(String pluginId, String version,
			String name, String description, String authorNmae) {
		verifyVersionForPluginWithIdIs(pluginId, version);
		verifyNameForPluginWithIdIs(pluginId, name);
		verifyDescriptionForPluginWithIdIs(pluginId, description);
		verifyAuthorForPluginWithIdIsWithDisabledLink(pluginId, authorNmae);
	}

	@com.thoughtworks.gauge.Step("Verify more information for plugin with id <pluginId> has path <path> target os <targetOs> target go version <targetGoVersion> and bundled status as <bundledStatus>")
	public void verifyMoreInformationForPluginWithIdHasPathTargetOsTargetGoVersionAndBundledStatusAs(String pluginId, 
			String path, String targetOs, String targetGoVersion, String bundledStatus) {
		clickOnMoreInformationForPluginWithId(pluginId);
		verifyPathForPluginWithIdContains(pluginId, path);
		verifyTargetOsForPluginWithIdIs(pluginId, targetOs);
		verifyTargetGoVersionForPluginWithIdIs(pluginId, targetGoVersion);
		verifyBundledStatusForPluginWithIdIs(pluginId, bundledStatus);
		closeMoreInformationForPluginWithId(pluginId);
	}

	@com.thoughtworks.gauge.Step("Verify plugin named <name> with id <pluginId> is marked as invalid with message <errorMessage>")
	public void verifyPluginNamedWithIdIsMarkedAsInvalidWithMessage(String name, String pluginId, String errorMessage) {
		ElementStub pluginMessages = verifyInvalidPluginDetailsAndGiveBackPluginMessages(name, pluginId);
		Assert.assertThat(pluginMessages.containsText(errorMessage), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Verify plugin named <name> with id <pluginId> is marked as invalid with message which is either <errorMessage> or <alternateErrorMessage>")
	public void verifyPluginNamedWithIdIsMarkedAsInvalidWithMessageWhichIsEitherOr(String name, String pluginId, String errorMessage, String alternateErrorMessage)
	{
		ElementStub pluginMessages = verifyInvalidPluginDetailsAndGiveBackPluginMessages(name, pluginId);
		Assert.assertThat(pluginMessages.containsText(errorMessage) || pluginMessages.containsText(alternateErrorMessage), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Verify plugin named <name> with id <pluginId> is valid")
	public void verifyPluginNamedWithIdIsValid(String name, String pluginId) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		Assert.assertThat(pluginBlock.exists(), Is.is(true));
		Assert.assertThat(pluginBlock.fetch("className"), Is.is("plugin enabled"));
		ElementStub pluginData = browser.div("plugin-data").in(pluginBlock);
		ElementStub pluginDetails = browser.div("plugin-details").in(pluginData);
		Assert.assertThat(browser.span("name").in(pluginDetails).text(), equalToIgnoringCase(name));
		Assert.assertThat(browser.span(" smaller descriptor-id").in(pluginDetails).text(), equalToIgnoringCase(String.format("[%s]", pluginId)));
	}

	private ElementStub verifyInvalidPluginDetailsAndGiveBackPluginMessages(String name, String pluginId) {
		ElementStub pluginBlock = getPluginBlock(pluginId);
		Assert.assertThat(pluginBlock.exists(), Is.is(true));
		Assert.assertThat(pluginBlock.fetch("className"), Is.is("plugin disabled"));
		ElementStub pluginData = browser.div("plugin-data").in(pluginBlock);
		ElementStub pluginDetails = browser.div("plugin-details").in(pluginData);
		Assert.assertThat(browser.span("name").in(pluginDetails).text(), equalToIgnoringCase(name));
		Assert.assertThat(browser.span(" smaller descriptor-id").in(pluginDetails).text(), equalToIgnoringCase(String.format("[%s]", pluginId)));
		ElementStub pluginMessages = browser.div("plugin-messages").in(pluginData);
		return pluginMessages;
	}
}
