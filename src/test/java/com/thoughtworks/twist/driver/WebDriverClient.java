/*************************GO-LICENSE-START*********************************
 * Copyright 2016 ThoughtWorks, Inc.
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

package com.thoughtworks.twist.driver;


import com.thoughtworks.cruise.util.CruiseConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverClient {

    private WebDriver driver=null;

    public WebDriver getFirefoxDriver(String firefox_bin){
        if(driver == null) {
            if (CruiseConstants.WEBDRIVER_TESTS.equals("Y") ){
                System.setProperty("webdriver.firefox.bin", firefox_bin);
                driver = new FirefoxDriver();
            }
        }
        return driver;
    }
}
