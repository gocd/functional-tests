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

import com.thoughtworks.cruise.page.CruisePage;
import com.thoughtworks.cruise.page.OnAnyPage;
import com.thoughtworks.cruise.page.OnLoginPage;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;


public class VerifyIfUserIsAdmin {

	private String loginAsUser;
	private final OnLoginPage onLoginPage;
	private final CruisePage onAnyPage;
	private Browser browser;

	public VerifyIfUserIsAdmin(OnLoginPage onLoginPage, OnAnyPage onAnyPage, Browser browser) {
		this.onLoginPage = onLoginPage;
		this.onAnyPage = onAnyPage;
		this.browser = browser;
	}

	public void setUp() throws Exception {
		onAnyPage.logout();
	}

	public void tearDown() throws Exception {
		onAnyPage.logout();
	}

	public void setAdmin(final String isAdmin) throws Exception {
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
			
			public boolean call() throws Exception {
				onAnyPage.logout();
				onLoginPage.loginAs(loginAsUser);
				ElementStub adminLink = browser.link("ADMIN");
				return adminLink.exists() == Boolean.valueOf(isAdmin);
			}
		});
	}

	public void setLoginAsUser(String loginAsUser) {
		this.loginAsUser = loginAsUser;
	}

	@com.thoughtworks.gauge.Step("VerifyIfUserIsAdmin <table>")
	public void brtMethod(com.thoughtworks.gauge.Table table) throws Throwable {
		com.thoughtworks.twist.migration.brt.BRTMigrator brtMigrator = new com.thoughtworks.twist.migration.brt.BRTMigrator();
		try {
			brtMigrator.BRTExecutor(table, this);
		} catch (Exception e) {
			throw e.getCause();
		}
	}
}