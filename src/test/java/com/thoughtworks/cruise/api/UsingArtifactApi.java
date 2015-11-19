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

package com.thoughtworks.cruise.api;

import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.artifacts.ArtifactRepository;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.ScenarioState;
import com.thoughtworks.cruise.util.TestFileUtil;
import org.apache.commons.httpclient.HttpException;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UsingArtifactApi extends JobDetailApi {
	
	public UsingArtifactApi(TalkToCruise talkToCruise, ScenarioState scenarioState) {		
		super(talkToCruise, scenarioState);
	}
	
	@com.thoughtworks.gauge.Step("Create artifact <artifactName>")
	public void createArtifact(String artifactName) throws Exception {
		File file = new File(RuntimePath.pathFor(String.format("material-files/%s", artifactName)));
		upload(artifactName, file);
	}

    public void upload(String artifactName, File file) throws HttpException, IOException {
        cruiseResponse = talkToCruise.post(fileUrl(artifactName), file, "file");
    }
	
	private String fileUrl(String artifactName) {
		return Urls.urlFor(String.format("/files/%s/%s", locator, artifactName));
	}
	
	@com.thoughtworks.gauge.Step("Append <text> to artifact <artifactName>")
	public void appendToArtifact(String text, String artifactName) throws Exception {
		cruiseResponse = talkToCruise.put(fileUrl(artifactName), text, "file");
	}

	@com.thoughtworks.gauge.Step("Verify return code is <status> - Using Artifact Api")
	public void verifyReturnCodeIs(int status) {
		super.verifyReturnCodeIs(status);
	}

	@com.thoughtworks.gauge.Step("For pipeline <pipelineName> label <pipelineLabel> stage <stageName> counter <stageCounter> job <jobName>")
	public void forPipelineLabelStageCounterJob(String pipelineName, String pipelineLabel, String stageName, String stageCounter, String jobName) throws Exception {
		super.forPipelineLabelStageCounterJob(pipelineName, pipelineLabel, stageName, stageCounter, jobName);
	}

    public void uploadCreatedArtifact(String artifactHandle) throws Exception {
        File artifact = scenarioState.artifactNamed(artifactHandle);
        upload(artifactHandle, artifact);
    }

    public void createArtifactOfSizeMB(String artifactName, int sizeOfArtifactInMB) throws Exception {
        File artifact = TestFileUtil.createTempFile(artifactName);
        ArtifactRepository.fileWithSize(sizeOfArtifactInMB, artifact);
        scenarioState.storeArtifact(artifactName, artifact);
    }

    public void successfullyUploadFor(String artifactName, String jobLocator) throws Exception {
        setupState(jobLocator);
        uploadCreatedArtifact(artifactName);
        verifyReturnCodeIs(201);
    }

    public void setupState(String jobLocator) throws Exception {
        String[] parts = jobLocator.split("/");
        forPipelineLabelStageCounterJob(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    public void verifyArtifactDoesNotExistInAnyOf(String artifactName, String jobLocator) throws Exception {
        setupState(jobLocator);
        CruiseResponse response = talkToCruise.get(fileUrl(artifactName));
        assertThat(response.getStatus(), is(404));
    }

    public void verifyArtifactExistsFor(String artifactName, String jobLocator) throws Exception {
        setupState(jobLocator);
        CruiseResponse response = talkToCruise.get(fileUrl(artifactName));
        assertThat(response.isSuccess(), is(true));
    }
}
