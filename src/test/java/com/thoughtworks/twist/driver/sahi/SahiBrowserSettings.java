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

package com.thoughtworks.twist.driver.sahi;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.twist.driver.sahi.browsersettings.DefaultBrowserSettings;
import com.thoughtworks.twist.driver.sahi.browsersettings.IBrowserSettings;

public class SahiBrowserSettings implements SahiOSSpecificBrowserSettings {

	protected Map<String, IBrowserSettings> supportedBrowsers;
	protected IBrowserSettings defaultBrowserSettings;

	public String getDefaultBrowserPath(String browser) {
		return findBrowserSettings(browser).getDefaultBrowserPath();
	}

	private IBrowserSettings findBrowserSettings(String browser) {
		if (supportedBrowsers.containsKey(browser)) {
			return supportedBrowsers.get(browser);
		}
		return new DefaultBrowserSettings();
	}

	public String getBrowserTerminationStringFor(String browser) {
		return findBrowserSettings(browser).getTerminationString();
	}

	public String getProfileInformationFor(String browser, String projectPath) {
		return findBrowserSettings(browser).getBrowserOptions("");
	}

	public List<String> getSupportedBrowsers() {
	    return Arrays.asList(supportedBrowsers.keySet().toArray(new String[0]));
	}

	public String getDefaultBrowser() {
		return defaultBrowserSettings.getName();
	}

    public String determineBrowser(String browserPath, String browserExecutable, String browserOptions) {
    	for (Entry<String, IBrowserSettings> entry : supportedBrowsers.entrySet()) {
    		IBrowserSettings browserSettings = entry.getValue();
			if ((browserSettings.getDefaultBrowserPath().equals(browserPath) ||
				browserSettings.getTerminationString().equals(browserExecutable)) && 
				browserSettings.getBrowserOptions("").equals(browserOptions)) {
				return browserSettings.getName();
			}
		}
    	return defaultBrowserSettings.getName();
    }

}
