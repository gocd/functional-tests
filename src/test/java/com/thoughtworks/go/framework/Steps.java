/*
 * Copyright 2016 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thoughtworks.go.framework;

import com.thoughtworks.cruise.Urls;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class Steps {
    private WebDriver driver;
    private HashMap<String, Page> cache;

    public Steps(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        this.cache = new HashMap<>();
    }

    public void navigateTo(String url) {
        driver.get(Urls.urlFor(url));
    }

    public <T extends Page> T On(Class<T> clazz) {
        Page page = this.cache.get(clazz.getName());

        if(page == null){
            try {
                page = clazz.getConstructor(WebDriver.class).newInstance(driver);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.cache.put(clazz.getName(), page);
        }
        return (T) page;
    }
}
