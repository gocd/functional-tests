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

package com.thoughtworks.twist.driver.sahi.browsersettings.win32;

import com.thoughtworks.twist.driver.sahi.browsersettings.IBrowserSettings;


public class WinXBrowserSettings implements IBrowserSettings {

	private String name = "Other";
	private String options = "";
	private String terminationString = "";
	private String defaultPath = "";
	private final String installRoot32 = "C:\\Program Files\\";
	private final String installRoot64 = "C:\\Program Files (x86)\\";

	public WinXBrowserSettings() {
		super();
	}

	public WinXBrowserSettings(String name, String options, String terminationString) {
		this.name = name;
		this.options = options;
		this.terminationString = terminationString;
	}

	public String getName() {
		return name;
	}

	public String getBrowserOptions(String projectPath) {
		return options;
	}

	public String getTerminationString() {
		return terminationString;
	}

	public String getDefaultBrowserPath() {
		return defaultPath;
	}
	
	public String getInstallationRoot() {
		return System.getProperty("os.arch").endsWith("64") ? installRoot64 : installRoot32;
	}

}