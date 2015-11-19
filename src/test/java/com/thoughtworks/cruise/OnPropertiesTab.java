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

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

import java.util.ArrayList;
import java.util.List;

public class OnPropertiesTab {

	private final List<String> properties = new ArrayList<String>();
	
	private String propertyName;

	private Browser browser;

	public OnPropertiesTab(Browser browser) {
		this.browser = browser;
	}
	
	public String exists() throws Exception {
		return new Boolean(properties.contains(propertyName)).toString();
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setUp() throws Exception {
		if (properties.isEmpty()) {
			List<ElementStub> columns = new SahiBrowserWrapper(browser).collectIn("cell", "first", browser.table("build-peroperties-table"));
			for (ElementStub webElement : columns) {
				properties.add(webElement.getText().trim());
			}
		}
		System.err.println("The properties are: " + properties);
	}

	public void tearDown() throws Exception {
	}

	@com.thoughtworks.gauge.Step("OnPropertiesTab <table>")
	public void brtMethod(com.thoughtworks.gauge.Table table) throws Throwable {
		com.thoughtworks.twist.migration.brt.BRTMigrator brtMigrator = new com.thoughtworks.twist.migration.brt.BRTMigrator();
		try {
			brtMigrator.BRTExecutor(table, this);
		} catch (Exception e) {
			throw e.getCause();
		}
	}

}
