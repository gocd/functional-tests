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
import com.thoughtworks.go.framework.Steps;
import com.thoughtworks.go.models.html.pages.PipelineEditPage;
import org.openqa.selenium.WebDriver;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class GeneralSettingsSteps extends Steps {
    private final ScenarioState scenarioState;

    public GeneralSettingsSteps(WebDriver driver, ScenarioState scenarioState) {
        super(driver);
        this.scenarioState = scenarioState;
    }

    @Step("Go to <pipeline> edit page")
    public void editPipeLine(String pipeline) {
        navigateTo(String.format("/admin/pipelines/%s/edit", scenarioState.pipelineNamed(pipeline)));
    }

    @Step("Verify pipeline locking is disabled")
    public void verifyPipelineLockDisabled() throws Exception {
        assertFalse(On(PipelineEditPage.class).generalSettingsFragment().isPipelineLocked());
    }

    @Step("Verify label template")
    public void verifyLabelTemplate() throws Exception {
        assertThat(On(PipelineEditPage.class).generalSettingsFragment().labelTemplate(), is("${COUNT}"));
    }
}