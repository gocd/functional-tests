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

import java.util.LinkedHashMap;

import com.thoughtworks.twist.driver.sahi.browsersettings.DefaultBrowserSettings;
import com.thoughtworks.twist.driver.sahi.browsersettings.IBrowserSettings;
import com.thoughtworks.twist.driver.sahi.browsersettings.win32.ChromeWinSettings;
import com.thoughtworks.twist.driver.sahi.browsersettings.win32.FirefoxWinSettings;
import com.thoughtworks.twist.driver.sahi.browsersettings.win32.IE7WinSettings;
import com.thoughtworks.twist.driver.sahi.browsersettings.win32.IE8WinSettings;
import com.thoughtworks.twist.driver.sahi.browsersettings.win32.OperaWinSettings;

public class SahiWin32BrowserSettings extends SahiBrowserSettings {
	
	public SahiWin32BrowserSettings() {
		super();
        supportedBrowsers = new LinkedHashMap<String, IBrowserSettings>();
        supportedBrowsers.put("IE 8", new IE8WinSettings());
        supportedBrowsers.put("IE 7", new IE7WinSettings());
        supportedBrowsers.put("Firefox", new FirefoxWinSettings());
        supportedBrowsers.put("Chrome", new ChromeWinSettings());
        supportedBrowsers.put("Opera", new OperaWinSettings());
        supportedBrowsers.put("Other", new DefaultBrowserSettings());
        defaultBrowserSettings = new IE8WinSettings();
    }

}
