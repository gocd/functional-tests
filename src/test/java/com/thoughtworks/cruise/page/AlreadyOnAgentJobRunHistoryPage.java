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

import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class AlreadyOnAgentJobRunHistoryPage extends CruisePage {

	private final CurrentPageState currentPageState;

	public AlreadyOnAgentJobRunHistoryPage(ScenarioState scenarioState, CurrentPageState currentPageState, Browser browser) {
		super(scenarioState, true, browser);
		this.currentPageState = currentPageState;
		currentPageState.assertCurrentPageIs(Page.AGENT_JOB_RUN_HISTORY);
	}
	
	@Override
	protected String url() {
		return null;
	}

	@com.thoughtworks.gauge.Step("Verify presence of sortable columns <sortableColumns>")
	public void verifyPresenceOfSortableColumns(String sortableColumns) throws Exception {
		ElementStub header = sortableTableHeader();
		String[] columns = splitMe(sortableColumns);
		for(String column : columns) {
			Assert.assertThat(browser.span(column.trim()).in(header).exists(), Is.is(true));
		}
	}

	@com.thoughtworks.gauge.Step("Verify presence of unsortable columns <plainColumns>")
	public void verifyPresenceOfUnsortableColumns(String plainColumns) throws Exception {
		ElementStub header = sortableTableHeader();
		for(String column : splitMe(plainColumns)) {
			Assert.assertThat(browser.tableHeader(column.trim()).in(header).exists(), Is.is(true));
		}
	}
	
	public void verifyRowHasPipelineStageJobResult(Integer oneBasedRowNumber, String pipelineName, String stageName, String jobName, String result) throws Exception {
		ElementStub tableContainer = tableContainer();
		ElementStub table = browser.table("jobs list_table sortable_table").in(tableContainer);
		ElementStub row = browser.row(oneBasedRowNumber).in(table);
		Assert.assertThat(browser.cell("pipeline").in(row).getText(), Is.is(scenarioState.expand(pipelineName)));
		Assert.assertThat(browser.cell("stage").in(row).getText(), Is.is(stageName));
		Assert.assertThat(browser.cell("job").in(row).getText(), Is.is(jobName));
		Assert.assertThat(browser.cell("result").in(row).getText(), Is.is(result));
	}
	
	private ElementStub sortableTableHeader() {
		ElementStub tableContainer = tableContainer();
		ElementStub header = browser.row("header").in(tableContainer);
		return header;
	}

	private ElementStub tableContainer() {
		ElementStub tableContainer = browser.div("job_history_table");
		return tableContainer;
	}

	@com.thoughtworks.gauge.Step("Sort by <columnName>")
	public void sortBy(String columnName) throws Exception {
		ElementStub header = sortableTableHeader();
		ElementStub columnSortLink = browser.span(columnName.trim()).in(header).parentNode();
		columnSortLink.click();
	}

	@com.thoughtworks.gauge.Step("Verify row <oneBasedRowNumber> has pipeline <pipelineName> stage <stageName> job <jobName> for pipeline counter <pipelineCounter> and stage counter <stageCounter> with result <result>")
	public void verifyRowHasPipelineStageJobForPipelineCounterAndStageCounterWithResult(
			Integer oneBasedRowNumber, String pipelineName, String stageName, 
			String jobName, String pipelineCounter, String stageCounter, String result) throws Exception {
		ElementStub tableContainer = tableContainer();
		ElementStub table = browser.table("jobs list_table sortable_table").in(tableContainer);
		ElementStub row = browser.row(oneBasedRowNumber).in(table);
		String expandedPipelineName = scenarioState.expand(pipelineName);
		Assert.assertThat(browser.cell("pipeline").in(row).getText(), Is.is(expandedPipelineName));
		Assert.assertThat(browser.cell("stage").in(row).getText(), Is.is(stageName));
		Assert.assertThat(browser.cell("job").in(row).getText(), Is.is(jobName));
		Assert.assertThat(browser.cell("result").in(row).getText(), Is.is(result));
		String expectedPath = String.format("/go/tab/build/detail/%s/%s/%s/%s/%s", expandedPipelineName, pipelineCounter, stageName, stageCounter, jobName);
		String actualLink = browser.link(jobName).in(row).fetch("href");
		Assert.assertThat(actualLink.endsWith(expectedPath), Is.is(true));
	}
}
