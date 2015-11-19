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

import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.state.ScenarioState;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class YumHttpRepository {
    private static final String DEFAULT_HTTP_REPO_URL_NAME_IN_CONFIG = "http_repo1";

    private File pathOfYumRepoDirectory = new File(RuntimePath.pathFor("tools/jetty-8/webapps"));
    private File jettyRootDir = new File(RuntimePath.pathFor("tools/jetty-8/"));
    private Process jettyServer;

    private ScenarioState scenarioState;

    public YumHttpRepository(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }

    public void create(String repoName) throws Exception {
        File repoLocation = repoLocation(repoName);

        FileUtils.deleteQuietly(repoLocation);
        cleanupRepoqueryCacheDirectory();
        copyFilesFromDirectory("test-repos/yumrepo/revision-one", repoLocation);
        execute(repoLocation, "/usr/bin/createrepo", ".");
        jettyServer = startJettyServer();
    }

    private File repoLocation(String repoName) {
        return new File(pathOfYumRepoDirectory, repoName);
    }

    @com.thoughtworks.gauge.Step("Publish new artifact")
	public void publishNewArtifact() throws Exception {
        publishNewArtifactInRepo(scenarioState.packageRepositoryHttpRepoNamed(DEFAULT_HTTP_REPO_URL_NAME_IN_CONFIG));
    }

    public void publishNewArtifactInRepo(String repoName) throws Exception {
        copyFilesFromDirectory("test-repos/yumrepo/revision-two", repoLocation(repoName));
        execute(repoLocation(repoName), "/usr/bin/createrepo", "--update", ".");
    }

    public void remove(String repoName) throws Exception {
        if (jettyServer != null)
            jettyServer.destroy();
        FileUtils.deleteQuietly(new File(repoLocation(repoName).getPath()));
    }

    private void cleanupRepoqueryCacheDirectory() throws IOException {
        File[] cacheFiles = new File("/var/tmp/").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String currentUser = System.getProperty("user.name") != null ? System.getProperty("user.name") : "go";
                return name.startsWith(String.format("yum-%s-", currentUser));
            }
        });
        for (File cacheFile : cacheFiles) {
            FileUtils.forceDelete(cacheFile);
        }
    }

    private Process startJettyServer() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "start.jar", "jetty.port=8081");
        processBuilder.directory(jettyRootDir);
        return processBuilder.start();
    }

    private void copyFilesFromDirectory(String sourceDirectory, File yumRepoLocation) throws IOException {
        File sourceDir = new File(RuntimePath.absolutePathFor(sourceDirectory));
        Iterator rpms = FileUtils.iterateFiles(sourceDir, new String[] { "rpm" }, false);
        while (rpms.hasNext()) {
            File file = (File) rpms.next();
            FileUtils.copyFileToDirectory(file, yumRepoLocation);
        }
    }

    private void execute(File workingDir, String... args) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        processBuilder.directory(workingDir);
        Process process = processBuilder.start();
        process.waitFor();
        if (process.exitValue() != 0) {
            throw new RuntimeException("ERROR: Process failed with exit code (" + process.exitValue() + "): " + Arrays.asList(args));
        }
    }
}
