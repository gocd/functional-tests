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

package com.thoughtworks.go.models.html.pages;

import com.thoughtworks.go.framework.Page;
import com.thoughtworks.go.models.html.fragments.pipeline.GeneralSettingsFragment;
import com.thoughtworks.go.models.html.fragments.pipeline.MaterialsFragment;
import org.openqa.selenium.WebDriver;

public class PipelineEditPage extends Page {
    public PipelineEditPage(WebDriver driver) {
        super(driver);
    }

    public GeneralSettingsFragment generalSettingsFragment() {
        return new GeneralSettingsFragment(driver, child(".pipeline>.row:nth-child(2)"));
    }

    public MaterialsFragment materialsFragment() {
        return new MaterialsFragment(driver, child("div.material-definitions"));
    }

    public void viewMaterials() {
        child(".pipeline-flow-boxes>.materials").click();
    }
}
