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

public class SahiBrowserWrapper {
	
	private final Browser browser;

	public SahiBrowserWrapper(Browser browser) {
		this.browser = browser;
	}

	public List<ElementStub> collectIn(String type, String identifier, ElementStub inEl) {
		List<ElementStub> list = new ArrayList<ElementStub>();
		for (int i = 0; i < 100; i++) {
			ElementStub element = new ElementStub(type, browser, String.format("%s[%d]", identifier, i));
			if (inEl != null) element = element.in(inEl);
			if (!element.exists()) break;
			list.add(element);
		}
		return list;
	}
	
	public List<ElementStub> collectNear(String type, String identifier, ElementStub nearEl) {
		List<ElementStub> list = new ArrayList<ElementStub>();
		for (int i = 0; i < 100; i++) {
			ElementStub element = new ElementStub(type, browser, String.format("%s[%d]", identifier, i));
			if (nearEl != null) element = element.near(nearEl);
			if (!element.exists(true)) break;
			list.add(element);
		}
		return list;
	}

	public List<ElementStub> collect(String type, String identifier) {
		return collectIn(type, identifier, null);
	}

	public void reload() {
		browser.navigateTo(getCurrentUrl(), true);
	}

	public String getCurrentUrl() {
		return browser.fetch("top.location.href");
	}

	public boolean isEnabled(ElementStub element) {
		return element.exists() && element.fetch("disabled").equals("false");
	}

	public boolean isAutoRefresh() {
		return !getCurrentUrl().endsWith("autoRefresh=false");
	}
}
