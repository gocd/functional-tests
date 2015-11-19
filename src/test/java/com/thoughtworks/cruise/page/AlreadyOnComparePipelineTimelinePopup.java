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

package com.thoughtworks.cruise.page;

import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

import static org.junit.Assert.assertThat;

public class AlreadyOnComparePipelineTimelinePopup extends CruisePage {

    private CurrentPageState currentPageState;
    
    public AlreadyOnComparePipelineTimelinePopup(ScenarioState scenarioState, CurrentPageState currentPageState, RepositoryState repositoryState, Browser browser) {
        super(scenarioState, true, browser);
        this.currentPageState = currentPageState;
        currentPageState.assertCurrentPageIs(CurrentPageState.Page.COMPARE_PAGE_TIMELINE_POPUP);
    }
    
    @Override
    protected String url() {
        // TODO Auto-generated method stub
        return null;
    }

    @com.thoughtworks.gauge.Step("Verify selected pipeline is <pipelineLabel>")
	public void verifySelectedPipelineIs(String pipelineLabel) throws Exception {
        foo(pipelineLabel);
    }

    public void foo(String pipelineLabel) {
        ElementStub labelContainer = browser.div("pipeline_label").in(browser.listItem(Regex.matches("pim_list[\\s\\w]+selected")));
        Assert.assertThat(labelContainer.exists(), Is.is(true));
        Assert.assertThat(labelContainer.getText(), Is.is(pipelineLabel));
    }

    @com.thoughtworks.gauge.Step("Choose pipeline with label <pipelineLabel>")
	public void choosePipelineWithLabel(String pipelineLabel) throws Exception {
        ElementStub pipelineInstance = browser.div(pipelineLabel).in(browser.div("pipeline_instance_list"));
        Assert.assertThat(String.format("Pipeline with label %s could not be found", pipelineLabel), pipelineInstance.exists(), Is.is(true));
        pipelineInstance.click();
        verifySelectedPipelineIs(pipelineLabel);
    }

    @com.thoughtworks.gauge.Step("Confirm selection")
	public void confirmSelection() throws Exception {
        browser.link("/Select this pipeline/i").click();
        currentPageState.currentPageIs(Page.COMPARE_PAGE);
    }
    
    // This method assumes that labels can be casted to integers and are sequential
    @com.thoughtworks.gauge.Step("Verify that page has pipeline range <high> to <low>")
	public void verifyThatPageHasPipelineRangeTo(String high, String low) throws Exception {
        ElementStub container = browser.div("pipeline_instance_list");
        for(int i=Integer.parseInt(high); i>Integer.parseInt(low) ;i--) {
            ElementStub labelContainer = browser.div(String.valueOf(i)).in(container);
            assertThat(String.format("Pipeline with label %s not present in the listing", i),labelContainer.exists(), Is.is(true));
        }
    }

    @com.thoughtworks.gauge.Step("Click on page <pageNumber>")
	public void clickOnPage(String pageNumber) throws Exception {
        ElementStub pageLink = browser.link(pageNumber).in(browser.div("pagination"));
        pageLink.click();
    }

}
