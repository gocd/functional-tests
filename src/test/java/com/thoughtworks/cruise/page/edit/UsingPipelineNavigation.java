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

package com.thoughtworks.cruise.page.edit;

import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

public class UsingPipelineNavigation {

    private Browser browser;
    private final CurrentPageState currentPageState;
    private final ScenarioState state;

    public UsingPipelineNavigation(Browser browser, CurrentPageState currentPageState, ScenarioState state) {
        this.browser = browser;
        this.currentPageState = currentPageState;
        this.state = state;
    }

    @com.thoughtworks.gauge.Step("Open stage <stageName> - Using Pipeline Navigation")
	public void openStage(String stageName) throws Exception {
        elementStageLink(stageName).click();
        currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_EDIT_STAGE_PAGE);
    }

    private ElementStub elementStageLink(String stageName) {
        return browser.link(stageName).in(browser.byId("pipeline_config_tree"));
    }

    @com.thoughtworks.gauge.Step("Open pipeline <pipelineName>")
	public void openPipeline(String pipelineName) throws Exception {
        browser.link(state.pipelineNamed(pipelineName)).in(browser.byId("pipeline_config_tree")).click();
        currentPageState.currentPageIs(Page.EDIT_PIPELINE_WIZARD_GENERAL_PAGE);
    }

    @com.thoughtworks.gauge.Step("Open job <job> of <stage>")
	public void openJobOf(String job, String stage) throws Exception {
        browser.link(job).in(elementStageLink(stage).parentNode("ul")).click();
        currentPageState.currentPageIs(Page.PIPELINE_WIZARD_TASK_LISTING_PAGE);
    }
}
