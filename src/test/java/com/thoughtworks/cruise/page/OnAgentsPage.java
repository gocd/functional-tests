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

import com.thoughtworks.cruise.CruiseAgents;
import com.thoughtworks.cruise.Regex;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.api.UsingAgentsApi;
import com.thoughtworks.cruise.api.response.AgentInformation;
import com.thoughtworks.cruise.editpipelinewizard.AutoCompleteSuggestions;
import com.thoughtworks.cruise.preconditions.AgentLauncher;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.TriStateScope;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Assertion;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.hamcrest.core.StringContains;
import org.junit.Assert;

import java.net.InetAddress;
import java.util.*;
import java.util.regex.Pattern;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

@SuppressWarnings("serial")
public class OnAgentsPage extends CruisePage {

	private final class AgentRowFinderPredicate implements Predicate {
		private final String status;
		private final int idx;
		private String id;

		private AgentRowFinderPredicate(String status, int idx) {
			this.status = status;
			this.idx = idx;
		}

		@Override
		public boolean call() throws Exception {
			id = agentsByStatus(status).get(idx).fetch("id");
			return !StringUtils.isBlank(id);
		}
	}

	private static final String ENVIRONMENT_EDIT_BUTTON = "show_environments_panel";
	private static final String RESOURCES_EDIT_BUTTON = "show_resources_panel";
	private static final String SCOPE_RESOURCES_PANEL = "resources_panel";
	private static final String SCOPE_ENVIRONMENT_PANEL = "environments_panel";
	private static final List<String> AGENT_TABLE_HEADERS = Arrays.asList(
			"CHECKBOX", "AGENT NAME", "SANDBOX", "OS", "IP ADDRESS", "STATUS",
			"FREE SPACE", "RESOURCES", "ENVIRONMENTS");

	private final UsingAgentsApi agentsApi;
	private final CruiseAgents createdAgents;
	private final CurrentPageState currentPageState;
	private boolean autoRefresh = false;

	private Set<String> notEnabled = new HashSet<String>() {
		{
			add("pending");
			add("disabled");

		}
	};
	private Set<String> notAcceptedOrUnavailable = new HashSet<String>(
			notEnabled) {
		{
			add("missing");
			add("lost_contact");

		}
	};

	public OnAgentsPage(ScenarioState state, UsingAgentsApi agentsApi,
			CruiseAgents createdAgents, Browser browser,
			CurrentPageState currentPageState) {
		super(state, browser);
		this.agentsApi = agentsApi;
		this.createdAgents = createdAgents;
		this.currentPageState = currentPageState;
	}

	@Override
	protected String url() {
		return Urls.urlFor("/agents?autoRefresh=" + autoRefresh);
	}

	@com.thoughtworks.gauge.Step("Wait for agent to show status <status>")
	public void waitForAgentToShowStatus(String status) throws Exception {
		waitForStatusWithTimeout(status, Timeout.TWO_MINUTES);
		// Make sure we are REALLY building instead of just assigned (bug #3275)
		Thread.sleep(5000);
		waitForStatusWithTimeout(status, Timeout.ONE_MINUTE);
	}

	private ElementStub waitForStatusWithTimeout(final String status,
			final Timeout timeout) {
		return Assertions.waitFor(timeout, new Function<ElementStub>() {
			public ElementStub call() {
				reloadPage();
				return browser.cell(status);
			}
		});
	}

	@com.thoughtworks.gauge.Step("Verify clicking the link <linkText> navigates to the job detail page")
	public void verifyClickingTheLinkNavigatesToTheJobDetailPage(
			final String linkText) throws Exception {
		Assertions.waitUntil(Timeout.FIVE_MINUTES, new Predicate() {
			public boolean call() {
				reloadPage();
				browser.link(linkText).click();
				return true;
			}
		});

		Assertions.assertWillHappen(browser.fetch("top.location.href"),
				StringContains
						.containsString("go/tab/build/detail/basic-pipeline"));
	}

	public int numberOfEnabledAgents() {
		return agentsExcept(notEnabled);
	}

	public int numberOfAvailableAgents() {
		return agentsExcept(notAcceptedOrUnavailable);
	}

	private int agentsExcept(Set<String> statusExclusionList) {
		reloadPage();
		int count = 0;
		for (int i = 0; i < 100; i++) {
			ElementStub element = browser.cell(String.format("status[%d]", i));
			if (!element.exists())
				break;
			if (!statusExclusionList.contains(element.getText())) {
				count++;
			}
		}
		return count;
	}

	@com.thoughtworks.gauge.Step("Sort column <column>")
	public void sortColumn(final String column) throws Exception {
		elementColumnHeaderLink(column).click();
	}

	private ElementStub elementColumnHeaderLink(String column) {
		return browser.link(0).in(browser.tableHeader(column));
	}

	@com.thoughtworks.gauge.Step("Verify agents in column <column> have order <order>")
	public void verifyAgentsInColumnHaveOrder(String column, String order)
			throws Exception {
		List<String> uniqueConsequtiveValues = new ArrayList<String>();
		for (String value : getColumnValues(column)) {
			if (uniqueConsequtiveValues.isEmpty()
					|| !uniqueConsequtiveValues.get(
							uniqueConsequtiveValues.size() - 1).equals(value)) {
				uniqueConsequtiveValues.add(value);
			}
		}
		List<String> expectedOrder = Arrays.asList(order.replaceAll(",\\s*",
				",").split(","));
		Assert.assertThat(uniqueConsequtiveValues, Is.is(expectedOrder));
	}

	private List<String> getColumnValues(String column) {
		List<String> values = new ArrayList<String>();
		ElementStub table = browser.byId("agent_details");
		for (int i = 1; i < 100; i++) {
			ElementStub element = browser.cell(table, i,
					AGENT_TABLE_HEADERS.indexOf(column.toUpperCase()));
			if (!element.exists())
				break;
			values.add(element.getText());
		}
		return values;
	}

	// retains query params
	@com.thoughtworks.gauge.Step("Reload page")
	public void reloadPage() {
		browserWrapper.reload();
	}

	// reloads the agents page with the autoRefresh setting only (needed because
	// IE sometimes returns an empty string for driver.getCurrentUrl())
	private void reloadUrl() {
		reload(url());
	}

	private void reload(String url) {
		if (!autoRefresh) {
			browser.navigateTo(url, true);
		}
	}

	@com.thoughtworks.gauge.Step("Verify url contains <s>")
	public void verifyUrlContains(String s) throws Exception {
		assertThat(browserWrapper.getCurrentUrl(), containsString(s));
	}

	@com.thoughtworks.gauge.Step("Verify url does not contain <s>")
	public void verifyUrlDoesNotContain(String s) throws Exception {
		assertThat(browserWrapper.getCurrentUrl(), not(containsString(s)));
	}

	@com.thoughtworks.gauge.Step("Verify the pipeline is building only on agents in <environmentName>")
	public void verifyThePipelineIsBuildingOnlyOnAgentsIn(String environmentName)
			throws Exception {
		Set<String> agentsUnderEnvironment = scenarioState
				.agentsUnderEnvironment(environmentName);
		waitForAgentToShowStatus("building");
		List<ElementStub> buildingAgents = buildingAgents();
		assertThat(buildingAgents.size(), Matchers.greaterThan(0));
		for (ElementStub row : buildingAgents) {
			Assert.assertTrue(agentsUnderEnvironment.contains(row.fetch("id")));
		}
	}

	@com.thoughtworks.gauge.Step("Verify none of the agents are building")
	public void verifyNoneOfTheAgentsAreBuilding() throws Exception {
		Assertions.assertOverTime(Timeout.THIRTY_SECONDS,
				new Function<Boolean>() {
					@Override
					public Boolean call() {
						reloadPage();
						return buildingAgents().size() == 0;
					}
				});
	}

	private List<ElementStub> buildingAgents() {
		return agentsByStatus("Building");
	}

	private List<ElementStub> agentsByStatus(String status) {
		reloadPage();
		List<ElementStub> list = new ArrayList<ElementStub>();
		for (int i = 0; i < 100; i++) {
			ElementStub row = browser.row(String.format("/agent_instance/[%d]",
					i));
			if (!row.exists())
				break;

			if (row.fetch("className").contains(status)) {
				list.add(row);
			}
		}
		return list;
	}

	public List<String> idleAgentUuids() {
		List<ElementStub> agentsByStatus = agentsByStatus("Idle");
		List<String> uuids = new ArrayList<String>();
		for (ElementStub row : agentsByStatus) {
			uuids.add(row.fetch("id"));
		}
		return uuids;
	}

	public void verifyAllAgentsHaveForResources(String resource)
			throws Exception {
		for (ElementStub resourceElement : elementsAgentResources()) {
			assertThat(resourceElement.getText(), Is.is(resource));
		}
	}

	private List<ElementStub> elementsAgentResources() {
		return browserWrapper.collectIn("span", "", browser.cell("resources"));
	}

	@com.thoughtworks.gauge.Step("Verify the <status> agent has <freeSpace> free space")
	public void verifyTheAgentHasFreeSpace(String status, String freeSpace)
			throws Exception {
		assertThat(elementUsableSpaceFor(status).getText(), Is.is(freeSpace));
	}

	private ElementStub elementUsableSpaceFor(String status) {
		ElementStub usableSpace = browser.cell("usable_space").near(
				browser.cell("/" + status + "/"));
		return usableSpace;
	}



	private ElementStub elementAgentCountForStatus(String configStatus) {
		reloadUrl();
		return browser.listItem(
				String.format("/%s/", configStatus.toLowerCase())).in(
				browser.list("/agent_counts/"));
	}

	@com.thoughtworks.gauge.Step("Verify has <count> idle agents")
	public void verifyHasIdleAgents(final Integer count) throws Exception {
		Assertions.waitUntil(Timeout.ONE_MINUTE, new Assertion<Integer>() {
			public Integer actual() {
				try {
					reloadPage();
					return idleAgentUuids().size();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			public Integer expected() {
				return count;
			}
		});
	}

	private void bulkEdit(ElementStub element, String operation) {
		selectAgent(element);
		browser.submit(operation).click();
	}

	@com.thoughtworks.gauge.Step("Select agent <element> - On Agents Page")
	public void selectAgent(ElementStub element) {
		final ElementStub checkbox = agentCheckbox(element);
		assertTrue(element.exists());
		Assertions.waitUntil(Timeout.TEN_SECONDS, new Predicate() {
			public boolean call() throws Exception {
				checkbox.check();
				return checkbox.checked();
			}
		});
	}

	private ElementStub agentCheckbox(ElementStub element) {
		return browser.checkbox("/selected/").in(element);
	}

	@com.thoughtworks.gauge.Step("Enable an agent showing status <status>")
	public void enableAnAgentShowingStatus(String status) throws Exception {
		bulkEdit(elementAgentRowWithStatus(status), "Enable");
	}

	@com.thoughtworks.gauge.Step("Disable an agent showing status <status>")
	public void disableAnAgentShowingStatus(String status) throws Exception {
		bulkEdit(elementAgentRowWithStatus(status), "Disable");
	}


	@com.thoughtworks.gauge.Step("On Agents Page")
	public void goToAgentsPage() throws Exception {
		navigateToURL();
	}

	@com.thoughtworks.gauge.Step("Disable agent <agentIndex>")
	public void disableAgent(Integer agentIndex) throws Exception {
		int prevDisabled = disabledAgentCount();
		AgentLauncher agent = createdAgents.get(agentIndex);
		ElementStub element = elementAgentRowWithUuid(agent.getUuid());
		bulkEdit(element, "Disable");
		Assert.assertThat("incorrect disabled agents count",
				disabledAgentCount(), Matchers.is(prevDisabled + 1));
	}

	@com.thoughtworks.gauge.Step("Delete a disabled agent")
	public void deleteADisabledAgent() {
		bulkEdit(elementAgentRowWithStatus("disabled"), "Delete");
	}

	@com.thoughtworks.gauge.Step("Delete an idle agent")
	public void deleteAnIdleAgent() {
		bulkEdit(elementAgentRowWithStatus("idle"), "Delete");
	}

	private int disabledAgentCount() {
		return countFrom(elementAgentCountForStatus("DISABLED").getText());
	}

	private int enabledAgentCount() {
		return countFrom(elementAgentCountForStatus("ENABLED").getText());
	}

	private int countFrom(String text) {
		String s = text.substring(text.indexOf(": ") + 1).trim();
		return Integer.parseInt(s);
	}

	@com.thoughtworks.gauge.Step("Enable agent <agentIndex>")
	public void enableAgent(Integer agentIndex) throws Exception {
		int prevEnabled = enabledAgentCount();
		AgentLauncher agent = createdAgents.get(agentIndex);
		bulkEdit(elementAgentRowWithUuid(agent.getUuid()), "Enable");
		Assert.assertThat("incorrect enabled agents count",
				enabledAgentCount(), Matchers.is(prevEnabled + 1));
	}

	@com.thoughtworks.gauge.Step("Add resource <resource> to all agents")
	public void addResourceToAllAgents(String resource) throws Exception {
		selectAllAgents();
		clickResourcesButton();
		addResource(resource, false);
		assertAllAgentsHaveResource(resource);
	}

	private void assertAllAgentsHaveResource(String resource) {
		List<ElementStub> afterAgentRows = allAgentRows();
		for (int i = 0; i < afterAgentRows.size(); i++) {
			assertAgentHasResource(i, resource);
		}
	}

	@com.thoughtworks.gauge.Step("Select all agents")
	public void selectAllAgents() {
		ElementStub selectAllAgents = browser.checkbox("select_all_agents");
		selectAllAgents.check();
	}

	private void assertVisible(final String xpath) {
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
			public boolean call() {
				return browser.div(xpath).isVisible();
			}
		});
	}

	private ElementStub elementAgentRowWithUuid(String uuid) {
		return browser.row(uuid);
	}

	private ElementStub elementAgentRowWithStatus(String status) {
		return browser.cell(status).parentNode("TR");
	}

	@com.thoughtworks.gauge.Step("Verify agents show operating system")
	public void verifyAgentsShowOperatingSystem() throws Exception {
		boolean showOS = false;
		for(String os : getColumnValues("OS")){
				if(os.matches("CentOS (\\d+\\.?)+ Final"))
					showOS = true;
		}

		Assert.assertTrue(showOS);
	}

	private void enableUsingApi(String id) {
		agentsApi.enable(id);
	}



	private String agentRowIdFor(final String status, final int idx) {
		AgentRowFinderPredicate agentRowIdFinder = new AgentRowFinderPredicate(
				status, idx);
		Assertions.waitUntil(Timeout.FIVE_SECONDS, agentRowIdFinder);
		return agentRowIdFinder.id;
	}


	@com.thoughtworks.gauge.Step("Add resource <resource> to agent <oneBasedIndex>")
	public void addResourceToAgent(String resource, Integer oneBasedIndex)
			throws Exception {
		addResourceToAgent(resource, oneBasedIndex, false);
	}

	private void addResourceToAgent(String resource, Integer oneBasedIndex,
			boolean pressReturn) {
		unselectAllAgents();
		selectAgent(oneBasedIndex);
		browser.waitFor(3000);
		clickResourcesButton();
		addResource(resource, pressReturn);
		assertAgentHasResource(oneBasedIndex - 1, resource);
	}

	@com.thoughtworks.gauge.Step("Add environment <environmentName> to agents <oneBasedIndexes>")
	public void addEnvironmentToAgents(String environmentName,
			String oneBasedIndexes) throws Exception {
		setEnvironmentTo(environmentName, oneBasedIndexes, "add");

		for (String index : oneBasedIndexes.split(",")) {
			assertAgentHasEnvironment(Integer.valueOf(index.trim()) - 1,
					environmentName);
		}
	}

	@com.thoughtworks.gauge.Step("Remove environment <environmentName> from agents <oneBasedIndexes>")
	public void removeEnvironmentFromAgents(String environmentName,
			String oneBasedIndexes) throws Exception {
		setEnvironmentTo(environmentName, oneBasedIndexes, "remove");
	}

	private void setEnvironmentTo(String environmentName,
			String oneBasedIndexes, String action) throws Exception {
		selectAgents(oneBasedIndexes);
		setOnEnvironmentPopup(environmentName, action);
	}

	private void setOnEnvironmentPopup(String environmentName, String action)
			throws Exception {
		onEnvironmentPopupSet(environmentName + ":" + action);
	}

	private void unselectAllAgents() {
		List<ElementStub> allAgentRows = allAgentRows();
		for (ElementStub agentRow : allAgentRows) {
			unselectAgent(agentRow);
		}
	}

	private void unselectAgent(ElementStub agentRow) {
		ElementStub checkbox = agentCheckbox(agentRow);
		if (checkbox.checked()) {
			checkbox.uncheck();
		}
		assertThat("agent row could not be unselected", checkbox.checked(),
				Matchers.is(false));
	}

	private void assertAgentHasEnvironment(final int index,
			final String environmentName) {
		assertThat(browser.cell("environments").in(getAgentRow(index + 1))
				.getText(), containsString(environmentName));
	}

	private void assertAgentHasResource(final Integer index,
			final String resource) {
		Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
			@Override
			public boolean call() throws Exception {
				reloadUrl();
				return browser.cell("resources").in(getAgentRow(index + 1))
						.getText().contains(resource);
			}
		});
	}

	public AgentInformation[] getAllAgentInformationOnUI() {
		List<AgentInformation> agentInformationList = new ArrayList<AgentInformation>();
		List<ElementStub> allAgentRows = allAgentRows();
		for (ElementStub agentRow : allAgentRows) {
			String uuid = agentRow.fetch("id");
			String agent_name = browser.cell("hostname").in(agentRow).getText();
			String ipAddress = browser.cell("ip_address").in(agentRow)
					.getText();
			String status = browser.cell("status").in(agentRow).getText();
			String sandbox = browser.cell("location wrapped_word").in(agentRow).getText();
			String os = browser.cell("operating_system").in(agentRow).getText();
			String free_space = browser.cell("usable_space").in(agentRow)
					.getText();
			String resourceList = browser.cell("resources").in(agentRow)
					.getText();
			List<String> resources = new ArrayList<String>();
			if (resourceList != null
					&& !resourceList.trim().isEmpty()
					&& !resourceList.trim().equalsIgnoreCase(
							"no resources specified")) {
				resources = Arrays.asList(resourceList.split(" | "));
			}
			String environmentList = browser.cell("environments").in(agentRow)
					.getText();
			List<String> environments = new ArrayList<String>();
			if (environmentList != null
					&& !environmentList.trim().isEmpty()
					&& !environmentList.trim().equalsIgnoreCase(
							"no environments specified")) {
				environments = Arrays.asList(environmentList.split(" | "));
			}
			agentInformationList.add(new AgentInformation(uuid, null,
					agent_name, ipAddress, "", status, sandbox, os, free_space,
					resources, environments));
		}
		return agentInformationList
				.toArray(new AgentInformation[agentInformationList.size()]);
	}

	private List<ElementStub> allAgentRows() {
		List<ElementStub> list = new ArrayList<ElementStub>();
		for (int i = 0; i < 100; i++) {
			ElementStub row = browser.row(String.format("/agent_instance/[%d]",
					i));
			if (!row.exists())
				break;
			list.add(row);
		}
		return list;
	}

	private void addResource(String resource, boolean pressReturn) {
		ElementStub addResourceTextbox = browser.textbox("add_resource");
		addResourceTextbox.setValue("");
		if (pressReturn) {
			addResourceTextbox.setValue(resource);
			browser.execute("_sahi._keyPress(" + addResourceTextbox + ", 13)");
		} else {
			addResourceTextbox.setValue(resource);
			applyAgentEdit(SCOPE_RESOURCES_PANEL);
		}
	}

	private void applyAgentEdit(final String scope) {
		final ElementStub applyButtonBeforePost = applyButtonForPanel(scope);
		applyButtonBeforePost.click();
		browser.waitFor(5000);
	}

	private ElementStub applyButtonForPanel(String scope) {
		return browser.submit("/submit/").in(browser.div(scope));
	}

	private void selectAgent(Integer oneBasedIndex) {
		selectAgent(getAgentRow(oneBasedIndex));
	}

	private ElementStub getAgentRow(Integer oneBasedIndex) {
		return browser.row(String.format("/agent_instance/[%s]",
				oneBasedIndex - 1));
	}

	private void clickResourcesButton() {
		browser.byId(RESOURCES_EDIT_BUTTON).click();
		Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
			public boolean call() throws Exception {
				assertPanelVisible(SCOPE_RESOURCES_PANEL);
				return true;
			}
		});
	}

	private void clickEnvironmentButton() {
		browser.byId(ENVIRONMENT_EDIT_BUTTON).click();
		assertPanelVisible(SCOPE_ENVIRONMENT_PANEL);
	}

	private void assertPanelVisible(String scope) {
		assertVisible(scope);
	}

	@com.thoughtworks.gauge.Step("Select agents <indexes>")
	public void selectAgents(String indexes) throws Exception {
		unselectAllAgents();
		for (String index : indexes.split(", ")) {
			selectAgent(Integer.parseInt(index));
		}
	}

	@com.thoughtworks.gauge.Step("Verify resource popup shows <resourceStateMap>")
	public void verifyResourcePopupShows(String resourceStateMap)
			throws Exception {
		clickResourcesButton();
		assertAgentEditPopopShows(SCOPE_RESOURCES_PANEL, resourceStateMap);
		closeMicrocontentPanel();
	}

	private void assertAgentEditPopopShows(String scope, String triStatesMap)
			throws Exception {
		TriStateScope triStateScope = new TriStateScope(browser, scope);
		triStateScope.assertShows(triStatesMap);
	}

	@com.thoughtworks.gauge.Step("Verify environment popup shows <environmentStateMap>")
	public void verifyEnvironmentPopupShows(String environmentStateMap)
			throws Exception {
		clickEnvironmentButton();
		assertAgentEditPopopShows(SCOPE_ENVIRONMENT_PANEL, environmentStateMap);
		closeMicrocontentPanel();
	}

	private void closeMicrocontentPanel() {
		browser.div("edit_panel").click();
	}

	@com.thoughtworks.gauge.Step("On resource popup verify <resourceName> transitions between <states>")
	public void onResourcePopupVerifyTransitionsBetween(
			final String resourceName, String states) throws Exception {
		clickResourcesButton();
		assertTriStateTransition(SCOPE_RESOURCES_PANEL, resourceName, states);
	}

	@com.thoughtworks.gauge.Step("On environment popup verify <environmentName> transitions between <states>")
	public void onEnvironmentPopupVerifyTransitionsBetween(
			final String environmentName, String states) throws Exception {
		clickEnvironmentButton();
		assertTriStateTransition(SCOPE_ENVIRONMENT_PANEL, environmentName,
				states);
	}

	private void assertTriStateTransition(String scope,
			final String triStateName, String states) throws Exception {
		TriStateScope triStateScope = new TriStateScope(browser, scope);
		triStateScope.assertTransitions(triStateName, states);
	}

	@com.thoughtworks.gauge.Step("On resource popup set <resourceStateMap>")
	public void onResourcePopupSet(String resourceStateMap) throws Exception {
		clickResourcesButton();
		setAgentEditTristateState(SCOPE_RESOURCES_PANEL, resourceStateMap);
		applyAgentEdit(SCOPE_RESOURCES_PANEL);
	}

	@com.thoughtworks.gauge.Step("On environment popup set <environmentStateMap>")
	public void onEnvironmentPopupSet(String environmentStateMap)
			throws Exception {
		clickEnvironmentButton();
		setAgentEditTristateState(SCOPE_ENVIRONMENT_PANEL, environmentStateMap);
		applyAgentEdit(SCOPE_ENVIRONMENT_PANEL);
	}

	private void setAgentEditTristateState(String scope, String resourceStateMap)
			throws Exception {
		TriStateScope triStateScope = new TriStateScope(browser, scope);
		triStateScope.set(resourceStateMap);
	}

	@com.thoughtworks.gauge.Step("Verify agent <oneBasedIndex> has resources <resourceFieldValue>")
	public void verifyAgentHasResources(Integer oneBasedIndex,
			String resourceFieldValue) throws Exception {
		assertThat(elementCellWithClass(oneBasedIndex, "resources").getText(),
				Matchers.is(resourceFieldValue));
	}

	@com.thoughtworks.gauge.Step("Verify agent <oneBasedIndex> has environments <environmentFieldValue>")
	public void verifyAgentHasEnvironments(Integer oneBasedIndex,
			String environmentFieldValue) throws Exception {
		assertThat(elementCellWithClass(oneBasedIndex, "environments")
				.getText(), Matchers.is(environmentFieldValue));

	}

	private ElementStub elementCellWithClass(Integer oneBasedIndex,
			String cssClass) {
		ElementStub agentRow = getAgentRow(oneBasedIndex);
		ElementStub resourcesCell = browser.cell(cssClass).in(agentRow);
		return resourcesCell;
	}

	@com.thoughtworks.gauge.Step("Turn on autoRefresh")
	public void turnOnAutoRefresh() throws Exception {
		autoRefresh = true;
		open();
	}

	private ElementStub agentCheckbox(int oneBasedIndex) {
		return browser.checkbox(
				String.format("agent_select[%d]", oneBasedIndex)).in(
				browser.byId("ajax_agents_table"));
	}

	public void selectAgent(final int oneBasedIndex) throws Exception {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			public boolean call() {
				ElementStub checkbox = agentCheckbox(oneBasedIndex);
				if (!checkbox.checked()) {
					checkbox.check();
					return true;
				}
				return false;
			}

			@Override
			public String toString() {
				return "Could not select agent " + oneBasedIndex
						+ " should be selected";
			}
		});
	}

	public void verifyAgentIsSelected(final int index) throws Exception {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			public boolean call() {
				ElementStub checkbox = agentCheckbox(index);
				return checkbox.checked();
			}

			@Override
			public String toString() {
				return "Agent " + index + " should be selected";
			}
		});
	}

	@com.thoughtworks.gauge.Step("Type new resource <resource> for agent <oneBasedIndex> and press return")
	public void typeNewResourceForAgentAndPressReturn(String resource,
			Integer oneBasedIndex) throws Exception {
		addResourceToAgent(resource, oneBasedIndex, true);
	}

	public void verifyRedirectedToServerDetailPage() throws Exception {
		super.verifyRedirectedToServerDetailPage();
	}

	public void verifyNotRedirected() throws Exception {
		super.verifyNotRedirected();
	}

	public void verifyLinkIsNotPresent(String linkText) throws Exception {
		ElementStub link = browser.link(linkText);
		if (link.exists())
			Assert.fail(String.format(
					"Element with text %s% should not be present.", linkText));
	}

	@com.thoughtworks.gauge.Step("Verify cruise footer - On Agents Page")
	@Override
	public void verifyCruiseFooter() throws Exception {
		super.verifyCruiseFooter();
	}

	@com.thoughtworks.gauge.Step("Verify <canDo> operate on agents")
	public void verifyOperateOnAgents(String canDo) throws Exception {
		assertThat(browser.div("edit_panel").exists(),
				Is.is(Can.CAN.matches(canDo)));
	}

	enum Can {

		CAN, CANNOT;

		boolean matches(String can) {
			return name().equalsIgnoreCase(can);
		}
	}

	@com.thoughtworks.gauge.Step("Click on the live agent to go to the details page")
	public void clickOnTheLiveAgentToGoToTheDetailsPage() throws Exception {
		ElementStub agentRow = elementAgentRowWithStatus("idle");
		String localAgentHostName = InetAddress.getLocalHost().getHostName();
		ElementStub agentLink = browser.link(localAgentHostName).in(agentRow);
		Assert.assertThat(
				"Could not navigate to agent details page as no link exists",
				agentLink.exists(), Is.is(true));
		agentLink.click();
		currentPageState.currentPageIs(Page.AGENT_DETAILS);
	}

	public void filterWith(String filterCriteria) throws Exception {
		enterFilterValue(filterCriteria);
		performFilter();
	}

	@com.thoughtworks.gauge.Step("Perform filter")
	public void performFilter() {
		ElementStub filterButton = browser.submit("/Filter/i").in(
				browser.byId("agents_filter_form"));
		filterButton.click();
	}

	@com.thoughtworks.gauge.Step("Enter filter value <filterCriteria>")
	public void enterFilterValue(String filterCriteria) {
		ElementStub filterTextBox = browser.textbox("filter_text");
		filterTextBox.setValue(filterCriteria);
	}

	@com.thoughtworks.gauge.Step("Verify total agent count is <totalAgents>")
	public void verifyTotalAgentCountIs(Integer totalAgents) throws Exception {
		ElementStub row = browser.row(Regex.startsWith("agent_instance"));
		int count = row.countSimilar();
		assertThat(count, Is.is(totalAgents));
	}

	public void clearFilter() throws Exception {
		browser.link("Clear").click();
	}

	public void verifySuggestionsShowUp(int numberOfSuggestions)
			throws Exception {
		AutoCompleteSuggestions suggestions = new AutoCompleteSuggestions(
				browser, browserWrapper);
		assertThat(suggestions.allSuggestion(0).size(),
				Is.is(numberOfSuggestions));
	}

	@com.thoughtworks.gauge.Step("Auto completes should show suggestion <suggestion>")
	public void autoCompletesShouldShowSuggestion(String suggestion)
			throws Exception {
		AutoCompleteSuggestions suggestions = new AutoCompleteSuggestions(
				browser, browserWrapper);
		suggestions.autoCompletesShouldShowSuggestion(suggestion);
	}

	@com.thoughtworks.gauge.Step("Auto completes should show suggestions <expectedSuggestions>")
	public void autoCompletesShouldShowSuggestions(String expectedSuggestions)
			throws Exception {
		AutoCompleteSuggestions suggestions = new AutoCompleteSuggestions(
				browser, browserWrapper);
		String[] expected = expectedSuggestions.split(",");
		for (String suggestion : expected) {
			suggestions.autoCompletesShouldShowSuggestion(suggestion.trim());
		}
		assertThat(suggestions.allSuggestion(0).size(), Is.is(expected.length));
	}

	public void selectOption(int index) throws Exception {
		AutoCompleteSuggestions suggestions = new AutoCompleteSuggestions(
				browser, browserWrapper);
		suggestions.selectFirstOption();
	}

	public void verifyAgentUpgradeMessageDoesNotExist() throws Exception {
		Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate() {
			public boolean call() {
				return !agentUpgradeMessageExists();
			}
		});
	}

	public void verifyThatNoAgentsNeedToBeUpgraded() throws Exception {
		assertThat(browser.image("info_icon").exists(), Is.is(false));
	}

	public void verifyAgentUpgradeMessageExists() throws Exception {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			public boolean call() {
				reloadPage();
				return agentUpgradeMessageExists();
			}
		});
	}

	private boolean agentUpgradeMessageExists() {
		return browser.div("bootstraper_version_warning").exists();
	}

	public void verifyThatAgent2_4NeedsToBeUpgraded() throws Exception {
		Assertions.waitUntil(Timeout.TWENTY_SECONDS, new Predicate() {
			public boolean call() {
				reloadPage();
				ElementStub cell = browser.cell("hostname").in(
						browser.row(Regex.wholeWord("agent_2_4")));
				ElementStub spanWithInfoIcon = browser.span(
						Regex.wholeWord("bootstrap-old")).in(cell);
				return spanWithInfoIcon.exists();
			}
		});
	}

	public void verifyThatAgentWithResourceHasStatus(String resource,
			String status) throws Exception {
		ElementStub row = browser.row(Regex.wholeWord(resource));
		ElementStub cell = browser.cell("status").in(row);
		assertThat(browser.span(status).in(cell).exists(), Is.is(true));
	}

	public void waitForAgentsToShowUp() throws Exception {
		Thread.sleep(Timeout.TWENTY_SECONDS.inMillis());
	}

	@com.thoughtworks.gauge.Step("Verify agents <expectedAgents> show up in results")
	public void verifyAgentsShowUpInResults(String expectedAgents)
			throws Exception {
		String[] expected = expectedAgents.split(",");
		for (String expectedAgent : expected) {
			ElementStub cell = browser.cell("hostname").in(
					browser.row(Regex.wholeWord(expectedAgent.trim())));
			assertThat(cell.exists(), Is.is(true));
		}

		int actualNoOfAgents = browser.row(Regex.startsWith("agent_instance"))
				.countSimilar();
		assertThat(actualNoOfAgents, Is.is(expected.length));
	}


	@com.thoughtworks.gauge.Step("Verify data in agent listing api is same as data on agents tab")
	public void verifyDataInAgentListingApiIsSameAsDataOnAgentsTab()
			throws Exception {
		// agents listing on UI
		List<AgentInformation> agentListOnUI = Arrays.asList(getAllAgentInformationOnUI());
		Collections.sort(agentListOnUI, new AgentSorterBasedOnUUID());

		// agents listing in API
		List<AgentInformation> agentListInAPI = Arrays.asList(agentsApi.listInformationOfAllAgents());
		Collections.sort(agentListInAPI, new AgentSorterBasedOnUUID());

		assertTrue(agentListOnUI.equals(agentListInAPI));
	}

	@com.thoughtworks.gauge.Step("Verify delete error message <errorMessage>")
	public void verifyDeleteErrorMessage(String errorMessage) throws Exception {
		Assert.assertThat(browser.div("message_pane").getText(),
				Is.is(errorMessage));

	}

	class AgentSorterBasedOnUUID implements Comparator<AgentInformation> {
		@Override
		public int compare(AgentInformation o1, AgentInformation o2) {
			return o1.getUuid().compareTo(o2.getUuid());
		}
	}

	public void printSummary() {
		List<AgentInformation> agentListOnUI = Arrays.asList(getAllAgentInformationOnUI());
		System.out.println(String.format("%20s %20s %20s %20s %20s %20s", "Agent Name", "Status", "Environments", "Free Space", "Resources", "Sandbox"));
		for(AgentInformation info : agentListOnUI) {
			System.err.println(String.format("%20s %20s %20s %20s %20s %20s",
					info.getAgent_name(),
					info.getAgent_state(),
					info.getEnvironments(),
					info.getFree_space(),
					info.getResources(),
					info.getSandbox()));
		}
	}
}
