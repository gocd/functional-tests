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
import com.thoughtworks.twist.driver.sahi.browsersettings.linux.ChromeLinuxSettings;
import com.thoughtworks.twist.driver.sahi.browsersettings.linux.FirefoxLinuxSettings;
import com.thoughtworks.twist.driver.sahi.browsersettings.linux.OperaLinuxSettings;

public class SahiLinuxBrowserSettings extends SahiBrowserSettings {
    
    public SahiLinuxBrowserSettings() {
    	super();
        supportedBrowsers = new LinkedHashMap<String, IBrowserSettings>();
        supportedBrowsers.put("Firefox", new FirefoxLinuxSettings());
        supportedBrowsers.put("Chrome", new ChromeLinuxSettings());
        supportedBrowsers.put("Opera", new OperaLinuxSettings());
        supportedBrowsers.put("Other", new DefaultBrowserSettings());
        defaultBrowserSettings = new FirefoxLinuxSettings();
    }

}
