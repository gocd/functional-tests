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

package com.thoughtworks.go.models.html.fragments.pipeline;

import com.thoughtworks.go.framework.Fragment;
import com.thoughtworks.go.models.data.Material;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

abstract class MaterialFragment extends Fragment {
    public MaterialFragment(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public void show() {
        child(".accordion-navigation>a").click();
    }

    public String getName() {
        return child(("input[data-prop-name=name]")).getAttribute("value");
    }

    public String getDestination() {
        return child(("input[data-prop-name=destination]")).getAttribute("value");
    }

    public String getIgnoreFields() {
        return child(("input[data-prop-name=ignore]")).getAttribute("value");
    }

    public boolean isAutoUpdate() {
        return child(("input[data-prop-name=autoUpdate]")).isSelected();
    }

    public abstract Material materialInstance();

    public void testConnection() {
        child("a.save-pipeline").click();
    }

    public boolean testConnectionFailed() {
        return !child(".alert-box").getText().isEmpty();
    }
}