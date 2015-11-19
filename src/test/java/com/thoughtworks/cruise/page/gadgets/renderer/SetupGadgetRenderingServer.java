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

package com.thoughtworks.cruise.page.gadgets.renderer;

// JUnit Assert framework can be used for verification

import net.sf.sahi.client.Browser;

public class SetupGadgetRenderingServer {

	private Browser browser;

	public SetupGadgetRenderingServer(Browser browser) {
		this.browser = browser;
	}

	@com.thoughtworks.gauge.Step("By creating user <username> with password <password>")
	public void byCreatingUserWithPassword(String username, String password) throws Exception {
		browser.navigateTo("http://localhost:3000");
		browser.link("Sign up").click();
		browser.textbox("user[email]").setValue(username);
		browser.password("user[password]").setValue(password);
		browser.submit("Create").click();
	}
}
