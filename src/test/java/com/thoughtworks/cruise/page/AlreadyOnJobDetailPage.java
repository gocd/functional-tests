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
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.materials.Repository;
import com.thoughtworks.cruise.materials.Revision;
import com.thoughtworks.cruise.state.CurrentPageState;
import com.thoughtworks.cruise.state.CurrentPageState.Page;
import com.thoughtworks.cruise.state.JobState;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.ExceptionUtils;
import com.thoughtworks.cruise.util.SystemUtil;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Function;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.RuntimeVariableSubstituter;
import com.thoughtworks.cruise.utils.Timeout;
import junit.framework.Assert;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.hamcrest.text.StringContains;

import java.io.File;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.StringContains.containsString;

public class AlreadyOnJobDetailPage extends CruisePage{
    private final RepositoryState repositoryState;
    private final TalkToCruise talkToCruise;
    private final CurrentPageState currentPageState;

    public AlreadyOnJobDetailPage(CurrentPageState currentPageState, ScenarioState scenarioState, RepositoryState repositoryState,TalkToCruise talkToCruise, Browser browser) {
        super(scenarioState, true, browser);
        this.currentPageState = currentPageState;
        this.repositoryState = repositoryState;
        this.talkToCruise = talkToCruise;
        currentPageState.assertCurrentPageIs(CurrentPageState.Page.JOB_DETAILS);
    }

    @com.thoughtworks.gauge.Step("Verify console contains <string>")
    public void verifyConsoleContains(String string) throws Exception {
        String expandedString = scenarioState.expand(string);
        expandedString = expandedString.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        assertThat(consoleContent(), containsString(expandedString));
    }

    @com.thoughtworks.gauge.Step("Verify console does not contain <string>")
    public void verifyConsoleDoesNotContain(String string) throws Exception {
        assertThat(consoleContent(), not(containsString(string)));
    }

    @com.thoughtworks.gauge.Step("Verify material tab contains revision <text>")
    public void verifyMaterialTabContainsRevision(String text) throws Exception {
        openMaterialTab();
        assertThat(elementRevision().getText().trim(), StringContains.containsString(text));
    }

    @com.thoughtworks.gauge.Step("Verify material tab contains revision for package materials <joinedMaterialAndRevisions>")
    public void verifyMaterialTabContainsRevisionForPackageMaterials(String joinedMaterialAndRevisions) throws Exception {
        openMaterialTab();
        String[] materialRevisionPairs = joinedMaterialAndRevisions.split(",");
        for (int index = 0; index < materialRevisionPairs.length ; index++) {
            String[] materialAndRevision = materialRevisionPairs[index].split("=");
            String materialName = materialAndRevision[0];
            String revision = materialAndRevision[1];
            ElementStub location = browser.span(String.format("revision_location[%s]", index)).in(browser.div("tab-content-of-materials"));
            ElementStub materialRevision = location.parentNode().parentNode();
            assertThat(materialRevision.getText(), containsString(materialName));
            assertThat(materialRevision.getText(), containsString(revision));
        }
    }

    private void openMaterialTab() {
        openTab("materials");
    }

    private ElementStub elementRevision() {
        return browser.span("/revision_information/").in(browser.table("modifications").in(browser.div("tab-content-of-materials")));
    }

    @com.thoughtworks.gauge.Step("Verify console shows <which> commit for material <materialName> for <pipelineName>")
    public void verifyConsoleShowsCommitForMaterialFor(String which, String materialName, String pipelineName) throws Exception {
        String runtimePipelineName = scenarioState.pipelineNamed(pipelineName);
        String revision = repositoryState.commitRevision(which, materialName, runtimePipelineName);
        verifyConsoleHasEnvironmentVariableWithValue(materialNameEnvironmentVariable(materialName), revision, false);
    }

    private String materialNameEnvironmentVariable(String materialName) {
        String environmentVar = "GO_REVISION_" + materialName.toUpperCase().replaceAll("-", "_");
        return environmentVar;
    }

    @com.thoughtworks.gauge.Step("Verify console shows remembered revision <rememberedAlias> for material <materialName>")
    public void verifyConsoleShowsRememberedRevisionForMaterial(String rememberedAlias, String materialName) throws Exception {
        verifyConsoleHasEnvironmentVariableWithValueOfRememberedRevision(materialNameEnvironmentVariable(scenarioState.expand(materialName)), rememberedAlias);
    }

    @com.thoughtworks.gauge.Step("Verify console has environment variable <envVar> with value of remembered revision <rememberedAlias>")
    public void verifyConsoleHasEnvironmentVariableWithValueOfRememberedRevision(String envVar, String rememberedAlias) throws Exception {
        String revision = repositoryState.getRevisionFromAlias(rememberedAlias);
        verifyConsoleHasEnvironmentVariableWithValue(envVar, revision, false);
    }

    @com.thoughtworks.gauge.Step("Verify console has environment variable <variable> with value <value>")
    public void verifyConsoleHasEnvironmentVariableWithValue(String variable, String value) throws Exception {
        verifyConsoleHasEnvironmentVariableWithValue(variable, value, true);
    }

    private void verifyConsoleHasEnvironmentVariableWithValue(String variable, String value, boolean formatAsEnvVar) throws Exception {
        String runtimeVariable = replaceRuntimeVariables(variable);
        runtimeVariable = formatAsEnvVar ? formatAsEnvironmentVariable(runtimeVariable) : runtimeVariable;
        String runtimeValue = replaceRuntimeVariables(value);
        try {
            verifyConsoleContains(String.format("setting environment variable '%s' to value '%s'", runtimeVariable, runtimeValue));
        } catch (AssertionError e) {
            verifyConsoleContains(String.format("overriding environment variable '%s' with value '%s'", runtimeVariable, runtimeValue));
        }
    }

    @com.thoughtworks.gauge.Step("Verify console has environment variable <variable> matches revision with comment with value <checkinComment> for material in dest folder <materialDestFolder>")
    public void verifyConsoleHasEnvironmentVariableMatchesRevisionWithCommentWithValueForMaterialInDestFolder(String variable, String checkinComment, String materialDestFolder) throws Exception {
        String value = fetchRevision(materialDestFolder, checkinComment);
        verifyConsoleContains(String.format("environment variable '%s' to value '%s'", formatAsEnvironmentVariable(replaceRuntimeVariables(variable)), replaceRuntimeVariables(value)));
    }

    private String fetchRevision(String dest, String checkinComment) {
        return  repositoryState.getRepositoryByDestinationFolder(dest).getRevisionFromComment(checkinComment);
    }

    private String formatAsEnvironmentVariable(String s) {
        return s.toUpperCase().replaceAll("[^\\w]", "_");
    }

    private String replaceRuntimeVariables(final String value) {
        return new RuntimeVariableSubstituter(new RuntimeVariableSubstituter.Replacer() {
            @Override
            public String replacementFor(String variable) {
                if ("localhost".equals(variable)) {
                    return SystemUtil.getLocalhostName();
                } else {
                    return scenarioState.replacementFor(variable);
                }
            }
        }).replaceRuntimeVariables(value);

    }

    @com.thoughtworks.gauge.Step("Verify material tab contains <mustHave> and not <mustNotHave>")
    public void verifyMaterialTabContainsAndNot(final String mustHave, final String mustNotHave) throws Exception {
        String materialTabContent = Assertions.waitFor(Timeout.THIRTY_SECONDS, new Function<String>() {
            public String call() {
                browser.byId("tab-link-of-materials").click();
                return browser.byId("tab-content-of-materials").getText().trim();
            }
        });
        assertThat(materialTabContent, StringContains.containsString(mustHave));
        assertThat(materialTabContent, Matchers.not(StringContains.containsString(mustNotHave)));
    }

    @com.thoughtworks.gauge.Step("Verify console contains <mustHave> and not <mustNotHave>")
    public void verifyConsoleContainsAndNot(String mustHave, String mustNotHave) throws Exception {
        verifyConsoleContains(mustHave);
        verifyConsoleDoesNotContain(mustNotHave);
    }

    public ElementStub artifactLinkFor(String fileName) {
        return browser.link(fileName).in(artifactTab());
    }

    public ElementStub artifactDirectoryLinkFor(String dir) {
        return browser.link(String.format("/%s/", dir)).in(browser.span("directory").in(browser.div("files").in(artifactTab())));
    }

    @com.thoughtworks.gauge.Step("Verify artifacts tab contains file <fileName>")
    public void verifyArtifactsTabContainsFile(String fileName) throws Exception {
        assertThat(artifactLinkFor(fileName).exists(), Matchers.is(true));
        assertThat(artifactLinkFor(fileName).fetch("href"), Matchers.containsString(fileName));
    }

    @com.thoughtworks.gauge.Step("Verify artifacts tab contains file <fileName> in dir <dir>")
    public void verifyArtifactsTabContainsFileInDir(String fileName, String dir) throws Exception {
        artifactDirectoryLinkFor(dir).click();
        verifyArtifactsTabContainsFile(fileName);
    }

    @com.thoughtworks.gauge.Step("Verify artifacts tab does not contain file <fileName>")
    public void verifyArtifactsTabDoesNotContainFile(String fileName) throws Exception {
        assertThat(artifactTab().getText(), Matchers.not(Matchers.containsString(fileName)));
    }

    private ElementStub artifactTab() {
        browser.byId("tab-link-of-artifacts").click();
        return browser.byId("tab-content-of-artifacts");
    }

    @com.thoughtworks.gauge.Step("Verify artifact <artifactName> contains text <text>")
    public void verifyArtifactContainsText(String artifactName, String text) throws Exception {
        String href = artifactLinkFor(artifactName).fetch("href");
        CruiseResponse cruiseResponse = talkToCruise.get(Urls.urlFor(href));
        assertThat(cruiseResponse.getBody(), Matchers.containsString(text));
    }

    private ElementStub propertiesTab() {
        browser.byId("tab-link-of-properties").click();
        return browser.byId("tab-content-of-properties");
    }

    public void reloadPage() {
        browserWrapper.reload();
    }

    @com.thoughtworks.gauge.Step("Verify properties tab shows value <value> for property <property>")
    public void verifyPropertiesTabShowsValueForProperty(String value, final String property) throws Exception {
        Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {
            public boolean call() throws Exception {
                //reloadPage();
                String jobDetailsurl = url();
                browser.navigateTo(Urls.urlFor(OnServerDetailPage.URL), true);
                browser.navigateTo(jobDetailsurl);
                return browser.row(String.format("property-of-%s", property)).in(propertiesTab()).exists();
            }
        });
        assertThat(browser.cell(1).in(browser.row(String.format("property-of-%s", property)).in(propertiesTab())).getText(), Matchers.is(value));
    }

    public void verifyConsoleContainsPattern(String pattern) throws Exception {
        assertThat(consoleContent(), Assertions.containsPattern(pattern));
    }

    @com.thoughtworks.gauge.Step("Verify console contains artifact from <src> to <dest>")
    public void verifyConsoleContainsArtifactFromTo(String src, String dest) throws Exception {
        verifyConsoleContainsPattern(String.format("Uploading artifacts from .*%s to %s" , src.replaceAll("/", "[/\\\\\\\\]") , dest));
    }

    @com.thoughtworks.gauge.Step("Verify console contains <pattern> twice")
    public void verifyConsoleContainsTwice(String pattern) throws Exception {
        String content = consoleContent();
        assertHasContentNTimes(pattern, content, 2);
        assertThat(content, containsString(pattern) );
    }

    private void assertHasContentNTimes(String pattern, String content, int times) {
        if (times == 0) return;
        int firstOccurence = content.indexOf(pattern);
        if (firstOccurence == -1) {
            Assert.fail(String.format("couldn't find string '%s' in '%s'", pattern, content));
        }
        assertHasContentNTimes(pattern, content.substring(firstOccurence), times -1);
    }

    private String buildCompleteDate() {
        return browser.byId("build_completed_date").getText();
    }

    @com.thoughtworks.gauge.Step("Store the job completed time stamp")
    public void storeTheJobCompletedTimeStamp() throws Exception {
        JobState.jobCompletedTime = buildCompleteDate();
    }

    @com.thoughtworks.gauge.Step("Verify job completed time stamp is different from the stored value")
    public void verifyJobCompletedTimeStampIsDifferentFromTheStoredValue()
            throws Exception {
        assertThat(buildCompleteDate(), Matchers.not(JobState.jobCompletedTime));
    }

    @com.thoughtworks.gauge.Step("Verify the uRL contain <string1>")
    public void verifyTheURLContain(String string1) throws Exception {
        browserWrapper.getCurrentUrl().contains(string1);
    }

    @com.thoughtworks.gauge.Step("Verify revision <revision> is marked as changed")
    public void verifyRevisionIsMarkedAsChanged(String revision) throws Exception {
        verifyRevisionChanged(revision, true);
   }

    private void verifyRevisionChanged(String revision, boolean changed) {
        openMaterialTab();
        ElementStub element = browser.span(Regex.matches(revision)).in(browser.div("tab-content-of-materials")).parentNode();
        assertThat(element.fetch("className"), StringContains.containsString("highlight-" + changed));
    }

    public void verifyRevisionIsNotMarkedAsChanged(String revision) throws Exception {
        verifyRevisionChanged(revision, false);
    }

    @com.thoughtworks.gauge.Step("Verify latest revision for material with name <materialName> and destination folder <destFolder> is not marked as changed")
    public void verifyLatestRevisionForMaterialWithNameAndDestinationFolderIsNotMarkedAsChanged(String materialName, String destFolder) throws Exception {
        Repository repo = repositoryState.getRepositoryByDestinationFolder(destFolder);
        Revision latestRevision = repo.latestRevision();
        String revision = String.format("Revision: %s, modified by %s", latestRevision.revisionNumber(), latestRevision.author());
        verifyRevisionChanged(revision, false);
    }

    @com.thoughtworks.gauge.Step("Open properties tab")
    public void openPropertiesTab() throws Exception {
        openTab("properties");
    }

    @com.thoughtworks.gauge.Step("Open console tab")
    public void openConsoleTab() throws Exception {
        openTab("console");
    }

    private void openTab(final String tabName) {
        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            public boolean call() {
                browser.byId("tab-link-of-" + tabName).click();
                return true;
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify console contains submodule status for dir <dir> and ref <ref>")
    public void verifyConsoleContainsSubmoduleStatusForDirAndRef(String dir, String ref) throws Exception {
        boolean matchFound = Pattern.compile(String.format(".* [a-fA-F0-9]{40} %s \\(%s\\).*", dir, ref), Pattern.MULTILINE | Pattern.DOTALL).matcher(consoleContent()).matches();
        assertThat(matchFound, Is.is(true));
    }

    @Override
    protected String url() {
        return browserWrapper.getCurrentUrl();
    }

    @com.thoughtworks.gauge.Step("Wait for log <logEntry>")
    public void waitForLog(final String logEntry) throws Exception {

        Assertions.waitUntil(Timeout.ONE_MINUTE, new Predicate(){
            @Override
            public boolean call() throws Exception {
                String anything = "[\\w\\W]*";
                return runningConsoleContent().matches(anything + "\\b"+ logEntry + "\\b" + anything);
            }

            private String runningConsoleContent() {
                return getContentFromConsole();
            }

            public String toString(){
                String expected = "Expected string containing: " + logEntry;
                try{
                    return expected + " but was " + runningConsoleContent();
                }
                catch(Exception ex){
                    return expected + " unable to fetch actuals: " + ExceptionUtils.stackTrace(ex);
                }
            }
        });
    }

    @com.thoughtworks.gauge.Step("Verify in console that artifact <artifactPath> was uploaded to <destinationPath>")
    public void verifyInConsoleThatArtifactWasUploadedTo(String artifactPath, String destinationPath) throws Exception {
        verifyConsoleContains(new File(artifactPath).getPath() + " to " + destinationPath);
    }

    @com.thoughtworks.gauge.Step("Verify breadcrumb contains pipeline <pipeline>")
    public void verifyBreadcrumbContainsPipeline(String pipeline) throws Exception {
        assertThat(elementBreadcrumbLink(scenarioState.pipelineNamed(pipeline)).exists(), is(true));
    }

    @com.thoughtworks.gauge.Step("Verify breadcrumb contains link to pipeline <pipeline> settings page")
    public void verifyBreadcrumbContainsLinkToPipelineSettings(String pipeline) throws Exception {
        assertThat(elementBreadcrumbLink("icon16 setting").getAttribute("href"), containsString("/go/admin/pipelines/" + scenarioState.pipelineNamed(pipeline) + "/general"));
    }

    @com.thoughtworks.gauge.Step("Verify breadcrumb contains pipeline label <label>")
    public void verifyBreadcrumbContainsPipelineLabel(String label) throws Exception {
        assertThat(elementBreadcrumbSpan(label).exists(), is(true));
    }

    private ElementStub elementBreadcrumbLink(String label) {
        return browser.link(label).in(browser.div(Regex.wholeWord("page_header")));
    }

    private ElementStub elementBreadcrumbSpan(String label) {
        return browser.span(label).in(browser.div(Regex.wholeWord("page_header")));
    }

    @com.thoughtworks.gauge.Step("Verify breadcrumb contains stage run <stageRun>")
    public void verifyBreadcrumbContainsStageRun(String stageRun) throws Exception {
        assertThat(elementBreadcrumbLink(stageRun).exists(), is(true));
    }

    @com.thoughtworks.gauge.Step("Click on bread crumb <breadCrumbLink>")
    public void clickOnBreadCrumb(String breadCrumbLink) throws Exception {
        browser.link(breadCrumbLink).click();
        currentPageState.currentPageIs(Page.STAGE_DETAILS);
    }

    @com.thoughtworks.gauge.Step("Click on pipeline bread crumb <pipelineName>")
    public void clickOnPipelineBreadCrumb(String pipelineName) throws Exception {
        browser.link(scenarioState.pipelineNamed(pipelineName)).click();
        currentPageState.currentPageIs(Page.PIPELINE_HISTORY);
    }

    @com.thoughtworks.gauge.Step("Verify displaying job <oneBoundJobIndex>")
    public void verifyDisplayingJob(Integer oneBoundJobIndex) throws Exception {
        ElementStub buildlistContainer = browser.div("buildlist-container");
        ElementStub job = browser.listItem("build_list_" + oneBoundJobIndex).in(buildlistContainer);
        assertThat(job.fetch("className"), containsString("current"));
    }

    @com.thoughtworks.gauge.Step("Verify historical job <oneBoundJobIndex> is not a copy")
    public void verifyHistoricalJobIsNotACopy(Integer oneBoundJobIndex) throws Exception {
        assertThat(colorCodeElement(oneBoundJobIndex).fetch("className"), not(containsString("copied_job")));
    }

    private ElementStub colorCodeElement(Integer oneBoundJobIndex) {
        ElementStub job = jobInJobHistory(oneBoundJobIndex);
        ElementStub colorCode = browser.div("/color_code_small/").in(job);
        return colorCode;
    }

    private ElementStub jobInJobHistory(Integer oneBoundJobIndex) {
        ElementStub buildlistContainer = browser.div("buildlist-container");
        return browser.listItem("build_list_" + oneBoundJobIndex).in(buildlistContainer);
    }

    @com.thoughtworks.gauge.Step("Verify console has environment variable <name> set to value <value>")
    public void verifyConsoleHasEnvironmentVariableSetToValue(String name, String value) throws Exception {
        String setting = scenarioState.expand(String.format("[go] setting environment variable '%s' to value '%s'", name, value));
        String consoleContent = consoleContent();
        assertConsoleEnvironmentVariable(consoleContent, setting);
    }

    @com.thoughtworks.gauge.Step("Verify console has overridden environment variable <name> set to value <value>")
    public void verifyConsoleHasOverriddenEnvironmentVariableSetToValue(String name, String value) throws Exception {
        String overriding = scenarioState.expand(String.format("[go] overriding environment variable '%s' with value '%s'", name, value));
        String consoleContent = consoleContent();
        assertConsoleEnvironmentVariable(consoleContent, overriding);
    }

    @com.thoughtworks.gauge.Step("Verify environment variable <name> is not overridden")
    public void verifyEnvironmentVariableIsNotOverridden(String name) throws Exception {
        String overriding = scenarioState.expand(String.format("[go] overriding environment variable '%s' with value", name));
        String consoleContent = consoleContent();
        assertThat(consoleContent.contains(overriding), is(false));
    }

    private void assertConsoleEnvironmentVariable(String consoleContent, String setting) {
        assertThat(String.format("String [ %s ] expected to contain either [ %s ]", consoleContent, setting),
                consoleContent.contains(setting),
                is(true));
    }

    @com.thoughtworks.gauge.Step("Click on the agent the job ran on")
    public void clickOnTheAgentTheJobRanOn() throws Exception {
        ElementStub agentLink = browser.link("agent_name");
        agentLink.click();
        currentPageState.currentPageIs(Page.AGENT_DETAILS);
    }

    @com.thoughtworks.gauge.Step("Verify console says that artifact <artifactName> was fetched from <stageLocator> for path <pathFromAncestor> from <stageJob>")
    public void verifyConsoleSaysThatArtifactWasFetchedFromForPath(String artifactName, String stageLocator, String pathFromAncestor, String stage_job)
            throws Exception {
        String resolvedPathFromAncestor = resolvePathFromAncestor(pathFromAncestor);
        String resolvedStageLocator = scenarioState.expand(stageLocator);
        String consoleContent = consoleContent();

        String taskLine = String.format("fetch artifact [%s] =&gt; [] from [%s]", artifactName, resolvedPathFromAncestor + "/" + stage_job);
        assertThat(String.format("Could not find \"%s\" in console output:\n%s", taskLine, consoleContent), consoleContent.contains(taskLine), is(true));

        String fetchLine = String.format("Fetching artifact [%s] from [%s]", artifactName, resolvedStageLocator);
        assertThat(String.format("Could not find \"%s\" in console output:\n%s", fetchLine, consoleContent), consoleContent.contains(fetchLine), is(true));
    }

    private String resolvePathFromAncestor(String pathFromAncestor) {
        String[] pipelineNames = pathFromAncestor.split("/");
        String resolvedPath = "";
        for (int i = 0; i < pipelineNames.length; i++) {
            resolvedPath += scenarioState.pipelineNamed(pipelineNames[i]);
            if (i != pipelineNames.length - 1) {
                resolvedPath += "/";
            }
        }
        return resolvedPath;
    }

    public void addPropertyWithValue(String propertyName, String propertyValue) throws Exception {
        browser.submit("add-new-property").click();
        ElementStub propertyKeyTextBox = browser.textbox("property-key").in(browser.row("new-property")).in(propertiesTab());
        ElementStub propertyValueTextBox = browser.textbox("property-value").in(browser.row("new-property")).in(propertiesTab());
        propertyKeyTextBox.setValue(propertyName);
        propertyValueTextBox.setValue(propertyValue);
    }

    public void saveProperty() throws Exception {
        browser.submit("new-property-save-button").in(propertiesTab()).click();
    }

    public void verifyThatSaveFailed() throws Exception {
        String lastAlert = browser.lastAlert();
        assertThat(lastAlert, Is.is("Save failed!"));
    }

    @com.thoughtworks.gauge.Step("Verify that unauthorized access message is shown - Already On Job Detail Page")
    public void verifyThatUnauthorizedAccessMessageIsShown() throws Exception {
        super.verifyThatUnauthorizedAccessMessageIsShown();
    }

    @com.thoughtworks.gauge.Step("Click on revision <revision> on materials tab")
    public void clickOnRevisionOnMaterialsTab(String revision) throws Exception {
        openMaterialTab();
        ElementStub link = browser.link(scenarioState.expand(revision)).in(browser.div(Regex.matches("dependency_revision_highlight")));
        link.click();
    }

    @com.thoughtworks.gauge.Step("Remember latest revision for material with destination folder <materialDestFolder> as <revisionAlias>")
    public void rememberLatestRevisionForMaterialWithDestinationFolderAs(
            String materialDestFolder, String revisionAlias) throws Exception {
        Repository repo = repositoryState.getRepositoryByDestinationFolder(materialDestFolder);
        repositoryState.rememberVersionAs(repo.latestRevision().revisionNumber(), revisionAlias);
    }

    @com.thoughtworks.gauge.Step("Verify console has environment variable <variableName> with value from alias <alias>")
    public void verifyConsoleHasEnvironmentVariableWithValueFromAlias(String variableName, String alias) throws Exception {
        verifyConsoleHasEnvironmentVariableWithValue(variableName, repositoryState.getRevisionFromAlias(alias));
    }

    @com.thoughtworks.gauge.Step("Verify breadcrumb contains link to value stream map on pipeline label <pipelineLabel> for pipeline <pipelineName> for counter <pipelineCounter>")
    public void verifyBreadcrumbContainsLinkToValueStreamMapOnPipelineLabelForPipelineForCounter(
        String pipelineLabel, String pipelineName, String pipelineCounter) throws Exception {
        String url = browser.link("VSM").in(browser.div(Regex.wholeWord("page_header"))).fetch("href");
        String runtimePipelineName = scenarioState.pipelineNamed(pipelineName);
        assertThat(url.endsWith("/pipelines/value_stream_map/"+runtimePipelineName+"/"+ pipelineCounter), is(true));
    }

    private String consoleContent() {
        String content = getContentFromConsole();
        if (contentIsAvailable(content)) {
            return content;
        }

        Assertions.waitUntil(Timeout.THIRTY_SECONDS, new Predicate() {
            public boolean call() {
                return contentIsAvailable(getContentFromConsole());
            }
        });

        return getContentFromConsole();
    }

    private boolean contentIsAvailable(String content) {
        return content != null && !"".equals(content);
    }

    private String getContentFromConsole() {
        return browser.preformatted(Regex.wholeWord("buildoutput_pre")).in(browser.div("tab-content-of-console")).fetch("innerHTML");
    }
}
