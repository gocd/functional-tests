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

package com.thoughtworks.go.steps.pipeline;

import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.go.framework.Steps;
import com.thoughtworks.go.models.data.GitMaterial;
import com.thoughtworks.go.models.data.HgMaterial;
import com.thoughtworks.go.models.data.SvnMaterial;
import com.thoughtworks.go.models.html.fragments.pipeline.GitMaterialFragment;
import com.thoughtworks.go.models.html.fragments.pipeline.HgMaterialFragment;
import com.thoughtworks.go.models.html.fragments.pipeline.SvnMaterialFragment;
import com.thoughtworks.go.models.html.pages.PipelineEditPage;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MaterialsSteps extends Steps {
    private ScenarioState scenarioState;

    public MaterialsSteps(WebDriver driver, ScenarioState scenarioState) {
        super(driver);
        this.scenarioState = scenarioState;
    }

    @Step("View materials")
    public void viewMaterials() {
        On(PipelineEditPage.class).viewMaterials();
    }

    @Step("Test connection for Git Material")
    public void testGitConnection() {
        On(PipelineEditPage.class).materialsFragment().getGitMaterialFragment().testConnection();
    }

    @Step("Verify Git test connection failed")
    public void verifyTestConnectionFailed() {
        assertTrue(On(PipelineEditPage.class).materialsFragment().getGitMaterialFragment().testConnectionFailed());
    }

    @Step("Verify materials section has Git Material with data <table>")
    public void verifyGitMaterial(Table table) {
        GitMaterialFragment fragment = On(PipelineEditPage.class).materialsFragment().getGitMaterialFragment();

        fragment.show();
        GitMaterial gitMaterial = (GitMaterial) fragment.materialInstance();

        assertThat(gitMaterial.getName(), is(table.getColumnValues("Name").get(0)));
        assertThat(gitMaterial.getDestination(), is(table.getColumnValues("Destination").get(0)));
        assertThat(gitMaterial.getIgnoreFields(), is(table.getColumnValues("IgnoreFields").get(0)));
        assertThat(gitMaterial.isAutoUpdate(), is(Boolean.parseBoolean(table.getColumnValues("AutoUpdate").get(0))));
        assertThat(gitMaterial.getBranch(), is(table.getColumnValues("Branch").get(0)));
    }

    @Step("Verify materials section has Svn Material with data <table>")
    public void verifySvnMaterial(Table table) {
        SvnMaterialFragment fragment = On(PipelineEditPage.class).materialsFragment().getSvnMaterialFragment();

        fragment.show();
        SvnMaterial material = (SvnMaterial) fragment.materialInstance();

        assertThat(material.getName(), is(table.getColumnValues("Name").get(0)));
        assertThat(material.getDestination(), is(table.getColumnValues("Destination").get(0)));
        assertThat(material.getIgnoreFields(), is(table.getColumnValues("IgnoreFields").get(0)));
        assertThat(material.isAutoUpdate(), is(Boolean.parseBoolean(table.getColumnValues("AutoUpdate").get(0))));
        assertThat(material.isCheckExternals(), is(Boolean.parseBoolean(table.getColumnValues("CheckExternals").get(0))));
    }

    @Step("Verify materials section has Hg Material with data <table>")
    public void verifyHgMaterial(Table table) {
        HgMaterialFragment fragment = On(PipelineEditPage.class).materialsFragment().getHgMaterialFragment();

        fragment.show();
        HgMaterial material = (HgMaterial) fragment.materialInstance();

        assertThat(material.getName(), is(table.getColumnValues("Name").get(0)));
        assertThat(material.getDestination(), is(table.getColumnValues("Destination").get(0)));
        assertThat(material.getIgnoreFields(), is(table.getColumnValues("IgnoreFields").get(0)));
        assertThat(material.isAutoUpdate(), is(Boolean.parseBoolean(table.getColumnValues("AutoUpdate").get(0))));
        assertThat(material.getBranch(), is(table.getColumnValues("Branch").get(0)));
    }
}
