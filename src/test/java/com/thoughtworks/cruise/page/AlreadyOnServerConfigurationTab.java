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

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.SahiBrowserWrapper;
import com.thoughtworks.cruise.page.OnServerDetailPage.License;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.util.SystemUtil;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.core.Is;
import org.junit.Assert;

import java.io.File;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AlreadyOnServerConfigurationTab {

	private Browser browser;
	private String licenseKey;
	private String licenseUser;
	private final CurrentPageState currentPageState;
	private SahiBrowserWrapper browserWrapper;
	private String currentTab;
	private String md5value;

	public AlreadyOnServerConfigurationTab(Browser browser, CurrentPageState currentPageState) {
		this.browser = browser;
		this.currentPageState = currentPageState;
		this.browserWrapper = new SahiBrowserWrapper(browser);
		currentPageState.assertCurrentPageIs(Page.SERVER_CONFIGURATION_TAB);
	}

	public void usingLicense(String licenseType) throws Exception {
		License license = OnServerDetailPage.licenses.get(licenseType);
		this.licenseKey = license.key;
		this.licenseUser = license.user;
	}

	@com.thoughtworks.gauge.Step("Save configuration")
	public void saveConfiguration() throws Exception {
		browser.submit("submit_form").click();
	}

	@com.thoughtworks.gauge.Step("Verify message <message> shows up")
	public void verifyMessageShowsUp(String message) throws Exception {
		assertThat(message(), is(message));		
	}
	
	@com.thoughtworks.gauge.Step("Verify message contains <message>")
	public void verifyMessageContains(String message) throws Exception {
	    assertThat(message(), containsString(message));
    }
	
	public String message() {
		return Assertions.waitFor(Timeout.TWENTY_SECONDS, new Function<String>() {
			public String call() {				
				String message = browser.byId("message_pane").getText().trim();
				if (StringUtils.isBlank(message)) {
					return null;
				}
				return message;
			}
		});
	}

	public void usingAsLicenseUser(String user) throws Exception {
		this.licenseUser = user;
	}

	public void usingAsLicenseKey(String key) throws Exception {
		this.licenseKey = key;
	}

	public void navigateToLicenseDetails() throws Exception {
		browser.link("License Details").click();
		currentPageState.currentPageIs(Page.SERVER_DETAILS_PAGE);
	}

	@com.thoughtworks.gauge.Step("For field <fieldName> <value>")
	public void forField(String fieldName, String value) {
		if ("true".equalsIgnoreCase(value.trim())) {
			browser.checkbox("server_configuration_form_" + fieldName).check();
			return;
		} 
		if ("false".equalsIgnoreCase(value.trim())) {
			browser.checkbox("server_configuration_form_" + fieldName).uncheck();
			return;
		}
		if (fieldName.contains("password")) {
			browser.password("server_configuration_form_" + fieldName).setValue(value);
			return;
		}
		browser.textbox("server_configuration_form_" + fieldName).setValue(value);
	}

	@com.thoughtworks.gauge.Step("For text area field <fieldName> <value>")
	public void forTextAreaField(String fieldName, String value) {
		browser.textarea("server_configuration_form_" + fieldName).setValue(value);
	}

	@com.thoughtworks.gauge.Step("Validate ldap should return <msg>")
	public void validateLdapShouldReturn(String msg) throws Exception {
		ElementStub checkLdapButton = browser.button("testLDAPSettings");
		assertTrue(checkLdapButton.exists());
		checkLdapButton.click();
		assertEquals(msg, browser.span("ldap_settings_validation").text());
	}

	@com.thoughtworks.gauge.Step("Set field <fieldname> to <uri>")
	public void setFieldTo(String fieldname, String uri) throws Exception {
		uri = uri.replaceAll("localhost", SystemUtil.getLocalhostName());
		String field = String.format("server_configuration_form[%s]", fieldname);
        browser.textbox(field).setValue(uri);
	}

	@com.thoughtworks.gauge.Step("Set password file path to only admin password properties")
	public void setPasswordFilePathToOnlyAdminPasswordProperties() throws Exception {
		String currentPath = browser.textbox("server_configuration_form[password_file_path]").text();
		String newPath = currentPath.replace("password.properties", "only-admin-password.properties");
		browser.textbox("server_configuration_form[password_file_path]").setValue(newPath);
	}

	@com.thoughtworks.gauge.Step("Verify <fieldName> password field is <state> and contains <value>")
	public void verifyPasswordFieldIsAndContains(String fieldName, String state, String value) throws Exception {
		ElementStub passwordField = browser.password("server_configuration_form_" + fieldName);
        assertThat(passwordField.getText(), is(value));
		assertThat(browserWrapper.isEnabled(passwordField), is(!state.equals("disabled")));
	}

    @com.thoughtworks.gauge.Step("Verify text field <fieldName> has value <value>")
	public void verifyTextFieldHasValue(String fieldName, String value) {
        ElementStub textField = browser.textbox("server_configuration_form_" + fieldName);
        textField.getText();
        assertThat(textField.getText(), is(value));
    }
  
	@com.thoughtworks.gauge.Step("Check change password for <fieldName>")
	public void checkChangePasswordFor(String fieldName) throws Exception {
		browser.check(browser.checkbox("server_configuration_form_" + fieldName + "_changed"));
	}
	
	@com.thoughtworks.gauge.Step("Set cancel job after <timeout> minutes")
	public void setCancelJobAfterMinutes(String timeout) throws Exception {
		browser.radio("hungjobs_overrideTimeout").click();
		browser.textbox("server_configuration_form_jobTimeout").setValue(timeout);
	}

    public void verifyThatAutoDeleteArtifactIsNever() throws Exception {
        assertThat(browser.radio("never_delete_artifacts").checked(), is(true));
    }

    public void configureDeleteArtifactIfSpaceIsLessThanGBUntilThereIsGBLeft(double startPurgingAt, double stopPurgingWhenReached) throws Exception {
        browser.radio("select_artifact_purge_size").click();
        elementStartPurgeAt().setValue(String.valueOf(startPurgingAt));
        elementStopPurgeAfter().setValue(String.valueOf(stopPurgingWhenReached));
    }

    public ElementStub elementStopPurgeAfter() {
        return browser.textbox("server_configuration_form_purgeUpto");
    }

    public ElementStub elementStartPurgeAt() {
        return browser.textbox("server_configuration_form_purgeStart");
    }

    public void verifyThatTheDeleteArtifactSpaceIsConfiguredWithAnd(final double startPurgingAt, final double stopPurgingWhenReached) throws Exception {
        Assertions.waitUntil(Timeout.TEN_SECONDS, new Predicate() {
            
            @Override
            public boolean call() throws Exception {
                return elementStartPurgeAt().getText().equals(String.valueOf(startPurgingAt));
            }
            
            @Override
            public String toString() {
                return String.format("Expected start purging to be: '%s'. It was: '%s'", startPurgingAt, elementStartPurgeAt().getText());
            }
        });
        Assertions.waitUntil(Timeout.TEN_SECONDS, new Predicate() {
            
            @Override
            public boolean call() throws Exception {
                return elementStopPurgeAfter().getText().equals(String.valueOf(stopPurgingWhenReached));
            }
            
            @Override
            public String toString() {
                return String.format("Expected start purging to be: '%s'. It was: '%s'", stopPurgingWhenReached, elementStopPurgeAfter().getText());
            }
        });
    }

	@com.thoughtworks.gauge.Step("Using <commandRepositoryLocation> as command repository location")
	public void usingAsCommandRepositoryLocation(String commandRepositoryLocation) throws Exception {
		browser.textbox("server_configuration_form_commandRepositoryLocation").setValue(commandRepositoryLocation);	
	}

	@com.thoughtworks.gauge.Step("Verify command repository location is set to <expectedCommandRepositoryLocation>")
	public void verifyCommandRepositoryLocationIsSetTo(String expectedCommandRepositoryLocation) throws Exception {
        String configuredTaskRepoLocation = browser.textbox("server_configuration_form_commandRepositoryLocation").getText();        
        assertThat(configuredTaskRepoLocation, is(expectedCommandRepositoryLocation));	
	}

	@com.thoughtworks.gauge.Step("Add valid snippet file with name <snippetName> into command _ repository location <commandRepositoryLocation>")
	public void addValidSnippetFileWithNameIntoCommand_RepositoryLocation(String snippetName, String commandRepositoryLocation) throws Exception {
		String prefix = RuntimePath.getServerRoot()+ "/db/command_repository/";
		String snippetWithoutNameInComment = prefix + "custom/random-snippet-without-name-in-comment.xml";
		File destination = new File(prefix + commandRepositoryLocation, snippetName + ".xml");

		FileUtils.copyFile(new File(snippetWithoutNameInComment), destination);
	}

	@com.thoughtworks.gauge.Step("Reload command snippet cache")
	public void reloadCommandSnippetCache() throws Exception {
		browser.button("reloadCommandRepoCache").click();

		Assertions.waitUntil(Timeout.FIVE_SECONDS, new Predicate() {
			@Override
			public boolean call() throws Exception {
				return "Done!".equals(browser.span("command_repo_reload_result").getText());
			}
		});
	}

	@com.thoughtworks.gauge.Step("Make snippet with name <snippetName> in command _ repository location <commandRepositoryLocation> unreadable")
	public void makeSnippetWithNameInCommand_RepositoryLocationUnreadable(String snippetName, String commandRepositoryLocation) throws Exception {
		String prefix = RuntimePath.getServerRoot()+ "/db/command_repository/";
		File file = new File(prefix + commandRepositoryLocation, snippetName + ".xml");
		file.setReadable(false);
	}
	
	@com.thoughtworks.gauge.Step("Remember current tab - Already on Server Configuration Tab")
	public void rememberCurrentTab() throws Exception {
	        this.currentTab = currentTab();
	}

	private String currentTab() {
	        return browser.listItem(Regex.matches("current_tab")).fetch("id");
	}
	
	@com.thoughtworks.gauge.Step("Remember md5 - Already on Server Configuration Tab")
	public void rememberMd5() throws Exception {
	       md5value = browser.getValue(browser.hidden("cruise_config_md5"));
	}

		public void verifyMd5IsSame() throws Exception {
			assertEquals(browser.hidden("cruise_config_md5").getValue(), md5value);
	}
		
		public void verifyMd5IsNotSame() throws Exception {
			String md5check = browser.hidden("cruise_config_md5").getValue();
			Assert.assertThat(md5check.equalsIgnoreCase(md5value), Is.is(false));
	}
		
	@com.thoughtworks.gauge.Step("Reload page - Already on Server Configuration Tab")
	public void reloadPage() {
			browserWrapper.reload();
	}
}
