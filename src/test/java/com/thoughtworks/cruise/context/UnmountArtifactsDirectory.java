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

package com.thoughtworks.cruise.context;

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.artifacts.ArtifactRepository;
import com.thoughtworks.cruise.state.ArtifactFilesystem;
import net.sf.sahi.client.Browser;
import org.apache.commons.collections.map.SingletonMap;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;

public class UnmountArtifactsDirectory {

    private Browser browser;
    private final ArtifactFilesystem artifactFilesystem;
    private final Configuration configuration;

    public UnmountArtifactsDirectory(Browser browser, Configuration configuration, ArtifactFilesystem artifactFilesystem) {
        this.browser = browser;
        this.configuration = configuration;
        this.artifactFilesystem = artifactFilesystem;
    }

    @com.thoughtworks.gauge.Step("Unmount artifacts directory - setup")
	public void setUp() throws Exception {
    }

    @com.thoughtworks.gauge.Step("Unmount artifacts directory - teardown")
	public void tearDown() throws Exception {
        if (artifactFilesystem.fileSystem.exists()) {
            ArtifactRepository.run(new SingletonMap("SUDO_ASKPASS", new File(RuntimePath.pathFor("../manual-testing/twist/echo_password.sh")).getAbsolutePath()), 
                    new ByteArrayInputStream("".getBytes()), 
                    "sudo", "-A", "/bin/umount", "-l", RuntimePath.getArtifactPath(configuration.provideDom().getArtifactsDir()));
            FileUtils.deleteQuietly(artifactFilesystem.fileSystem);
        }
    }
}
