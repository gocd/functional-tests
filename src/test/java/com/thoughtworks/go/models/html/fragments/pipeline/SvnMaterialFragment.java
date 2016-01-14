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

import com.thoughtworks.go.models.data.Material;
import com.thoughtworks.go.models.data.SvnMaterial;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SvnMaterialFragment extends MaterialFragment {
    public SvnMaterialFragment(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getUrl() {
        return child(("input[data-prop-name=url]")).getAttribute("value");
    }

    public String getUsername() {
        return child(("input[data-prop-name=username]")).getAttribute("value");
    }

    public String getPassword() {
        return child(("input[data-prop-name=passwordValue]")).getAttribute("value");
    }

    public boolean shouldCheckExternals() {
        return child(("input[data-prop-name=checkExternals]")).isSelected();
    }

    @Override
    public Material materialInstance() {
        return new SvnMaterial(getName(), getDestination(), getIgnoreFields(), isAutoUpdate(), getUrl(), getUsername(), getPassword(), shouldCheckExternals());
    }
}