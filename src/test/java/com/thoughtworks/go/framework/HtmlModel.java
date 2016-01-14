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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HtmlModel {
    protected WebDriver driver;
    protected WebElement rootElement;

    public HtmlModel(WebDriver driver, WebElement rootElement) {
        this.driver = driver;
        this.rootElement = rootElement;
    }

    public WebElement child(String cssSelector) {
        return this.rootElement.findElement(By.cssSelector(cssSelector));
    }

    public List<WebElement> children(String cssSelector) {
        return this.rootElement.findElements(By.cssSelector(cssSelector));
    }
}
