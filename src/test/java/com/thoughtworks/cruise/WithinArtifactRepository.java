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

package com.thoughtworks.cruise;

// JUnit Assert framework can be used for verification

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;
import com.thoughtworks.cruise.context.Configuration;
import net.sf.sahi.client.Browser;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;

public class WithinArtifactRepository {

	private final Configuration configuration;
	private final Browser browser;

	public WithinArtifactRepository(Configuration configuration, Browser browser) {
		this.configuration = configuration;
		this.browser = browser;
	}

	@com.thoughtworks.gauge.Step("Verify the <directory> directory exists")
	public void verifyTheDirectoryExists(String directory) throws Exception {
		File backupDir = backupDir(directory);
		Assert.assertThat(backupDir.exists(), is(true));
		Assert.assertThat(backupDir.isDirectory(), is(true));
	}

	private File backupDir(String directory) {
		return new File(RuntimePath.getArtifactPath(configuration.provideDomAsAdmin().getArtifactsDir()), directory);
	}

	@com.thoughtworks.gauge.Step("Verify the <directory> directory contains <backupName> file in the tree")
	public void verifyTheDirectoryContainsFileInTheTree(String directory, String backupName) throws Exception {
		File found = getBackupFile(directory, backupName);	
		Assert.assertThat(found, is(Matchers.not(Matchers.nullValue())));
	}

	private File getBackupFile(String directory, String backupName) {
		Collection<File> allFiles = FileUtils.listFiles(backupDir(directory), null, true);
		for (File file : allFiles) {
			if (file.getName().equals(backupName)) {
				return file;
			}
		}
		return null;
	}

	@com.thoughtworks.gauge.Step("Verify the <directory> directory contains file named <backupName> which has running go version")
	public void verifyTheDirectoryContainsFileNamedWhichHasRunningGoVersion(String directory, String backupName) throws Exception {

		RestAssured.defaultParser = Parser.JSON;
		String version = serverVersion().then()
				.extract().path("full_version");

		String backedUpVersion = FileUtils.readFileToString(getBackupFile(directory, backupName));
		Assert.assertThat(backedUpVersion, is(version));

	}


	private Response serverVersion(){
		HashMap<String, String> headers = new HashMap<String, String>();

		headers.put("Accept", "application/vnd.go.cd.v1+json");
		return RestAssured.given().
				headers(headers).
				when().get(Urls.urlFor("/go/api/version"));
	}
}
