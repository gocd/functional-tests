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

package com.thoughtworks.cruise.artifacts;

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.preconditions.NewStreamPumper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ArtifactRepository {

    public ArtifactRepository() {
    }
    
    public static void fileWithSize(int sizeOfArtifactInMB, File artifact) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(artifact));
        byte[] block = block();
        for (int i = 0; i < sizeOfArtifactInMB * 1024 / 4; i++) {
            out.write(block);
            out.flush();
        }
        out.close();
    }

    private static byte[] block() {
        byte [] bytes = new byte[4 * 1024];
        for (int i=0; i < 4 * 1024; i++) bytes[i] = 0;
        return bytes;
    }

    public static void run(InputStream processInput, String...commandAndArgs) throws IOException, InterruptedException {
        run(new HashMap<String, String>(), processInput, commandAndArgs);
    }

    public static void run(Map<String, String> envVars, InputStream processInput, String...commandAndArgs) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(commandAndArgs);
        for(Entry<String, String> envVar : envVars.entrySet()) {
            processBuilder.environment().put(envVar.getKey(), envVar.getValue());
        }
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        NewStreamPumper.pump(process.getInputStream(), "out", System.out);
        OutputStream outputStream = process.getOutputStream();
        IOUtils.copy(processInput, outputStream);
        outputStream.close();
        int returnCode = process.waitFor();
        assertThat(String.format("Failed executing commad: %s", StringUtils.join(commandAndArgs, " ")), returnCode, is(0));
    }

    public static void run(String... commandAndArgs) throws IOException, InterruptedException {
        run(new ByteArrayInputStream("".getBytes()), commandAndArgs);
    }

}
