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

package com.thoughtworks.cruise.plugin;

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.ScenarioState;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LogNotificationPlugin {
	private final ScenarioState scenarioState;
	private final TalkToCruise talkToCruise;
	private String[] newEntries;

	public LogNotificationPlugin(ScenarioState scenarioState, TalkToCruise talkToCruise) {
		this.scenarioState = scenarioState;
		this.talkToCruise = talkToCruise;
	}

	@com.thoughtworks.gauge.Step("Store current state")
	public void storeCurrentState() throws Exception {
		File pluginLogFile = null;
		CruiseResponse response = talkToCruise.get(Urls.apiSupportURL(), true);
		String[] responseParts = response.getBody().split("\n");
		for (String responseLine : responseParts) {
			if (responseLine.startsWith("loc.log.root.0")) {
				pluginLogFile = new File(responseLine.split(" ")[1], "plugin-log.notifier.log");
				scenarioState.putValueToStore("LOG_NOTIFICATION_PLUGIN_FILE_PATH", pluginLogFile.getAbsolutePath());
				break;
			}
		}
		if (pluginLogFile == null) {
			throw new RuntimeException("could not find 'loc.log.root.0' in /go/api/support");
		}
		if (pluginLogFile.exists()) {
			String fileContents = FileUtils.readFileToString(pluginLogFile);
			if (fileContents != null && !fileContents.trim().isEmpty()) {
				int numberOfLines = fileContents.split("\n").length;
				scenarioState.putValueToStore("LOG_NOTIFICATION_PLUGIN_FILE_LENGTH", new Integer(numberOfLines).toString());
			}
		}
	}

	@com.thoughtworks.gauge.Step("Wait a moment")
	public void waitAMoment() {
		try {
			Thread.sleep(60 * 1000);
		} catch (Exception e) {
			// ignore
		}
	}

	@com.thoughtworks.gauge.Step("Verify has <count> new entries")
	public void verifyHasNewEntries(String count) throws Exception {
		String filePath = scenarioState.getValueFromStore("LOG_NOTIFICATION_PLUGIN_FILE_PATH");
		String fileLength = scenarioState.getValueFromStore("LOG_NOTIFICATION_PLUGIN_FILE_LENGTH");
		int numberOfLines = (fileLength == null || fileLength.trim().isEmpty()) ? 0 : Integer.valueOf(fileLength);
		File pluginLogFile = new File(filePath);
		String fileContents = FileUtils.readFileToString(pluginLogFile);
		String[] fileContentsParts = fileContents.split("\n");
		int currentNumberOfLines = fileContentsParts.length;
		assertThat(currentNumberOfLines, is(numberOfLines + 6));
		newEntries = Arrays.copyOfRange(fileContentsParts, numberOfLines, fileContentsParts.length);
	}

	@com.thoughtworks.gauge.Step("Verify entry <lineNumber> <pipelineName> <pipelineCounter> <stageName> <stageCounter> <state> <result>")
	public void verifyEntry(String lineNumber, String pipelineName,String pipelineCounter,
			String stageName, String stageCounter, String state, String result) {
		String line = newEntries[Integer.parseInt(lineNumber)];
		line = line.split(" - ")[1].trim();
		line = line.substring(1, line.length() - 1);
		String[] lineParts = line.split("\\|");
		assertThat(lineParts[0], is(scenarioState.pipelineNamed(pipelineName)));
		assertThat(lineParts[1], is(pipelineCounter));
		assertThat(lineParts[2], is(stageName));
		assertThat(lineParts[3], is(stageCounter));
		assertThat(lineParts[4], is(state));
		assertThat(lineParts[5], is(result));
	}
}
