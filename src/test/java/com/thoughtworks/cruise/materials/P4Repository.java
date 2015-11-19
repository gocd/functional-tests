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

package com.thoughtworks.cruise.materials;

import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.matchers.ResourceMatcher;
import com.thoughtworks.cruise.util.ArrayUtil;
import com.thoughtworks.cruise.util.TestFileUtil;
import com.thoughtworks.cruise.utils.Timeout;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.junit.matchers.TypeSafeMatcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import static com.thoughtworks.cruise.util.ExceptionUtils.bomb;
import static com.thoughtworks.cruise.util.ExceptionUtils.bombIf;
import static com.thoughtworks.cruise.utils.Assertions.assertWillHappen;
import static org.hamcrest.core.Is.is;

public class P4Repository extends AbstractRepository {    
    private static final TypeSafeMatcher<Boolean> PORT_OCCUPIED_MATCHER = ResourceMatcher.portIsOccupied(1666);
	private final String bundlePath;
    private File clientFolder;
    private Process p4dProcess;
    private File repositoryFolder;
	private String clientName;

    public P4Repository(Element material, String bundlePath) {
        super(material);
        this.bundlePath = bundlePath;
        prepare();
    }

    public void checkin(String message) {
        checkin("twist", message);
    }

    public void checkin(String username, String message) {
        try {
			runP4Command("Submit with message " + message, "-u", username, "submit", "-d", message);
		} catch (Exception e) {
			bomb("Commit failed", e);
		}
    }

    public void cleanup() {
        p4dProcess.destroy();
        try {
            p4dProcess.waitFor();
        } catch (InterruptedException e) {
            bomb(e);
        } finally {
            RepositoryHelper.cleanup(repositoryFolder);
            RepositoryHelper.cleanup(clientFolder);
        }
    }

    public void createNewFile(String file, String userName) {	
		p4add(RepositoryHelper.createFileIn(clientFolder, file),userName);
    }

    private void p4add(File file,String userName) {
    	try {
			
			runP4Command("Adding file " + file,"-u",userName, "add", "-t", "text", file.getAbsolutePath());
		} catch (Exception e) {
			bomb("Add failed", e);
		}
	}

	public String getRevisionFromComment(String checkinComment) {
        throw new UnsupportedOperationException("TODO");
    }

    public String getUrl() {
        return repositoryFolder.getAbsolutePath();
    }

    private void prepare() {
        try {        	
        	clientName = "p4test_1" + UUID.randomUUID();
            repositoryFolder = TestFileUtil.prepareFolderInsideTempFolder("testP4Repo");
            FileUtils.copyDirectory(new File(bundlePath), repositoryFolder);
            p4dProcess = startP4d();
            waitForP4dToStartup();
            createClient();
            sync();
        } catch (Exception e) {
            bomb("Error creating p4 client", e);
        }
    }

    private void sync() throws Exception {
        runP4Command("Failed to sync!\nError: ", "sync");
    }

    private String clientConfig(String clientName) {
        return "Client: " + clientName + "\n\n" + "Owner: cruise\n\n" + "Root: " + clientFolder.getAbsolutePath() + "\n\n"
                + "Options: rmdir\n\n" + "LineEnd: local\n\n" + "View:\n" + "\t//depot/... //" + clientName + "/...\n";

    }

    private void closeStreams(Process process) throws IOException {
        process.getErrorStream().close();
        process.getInputStream().close();
        process.getOutputStream().close();
    }

    private void createClient() throws Exception {
        clientFolder = TestFileUtil.prepareFolderInsideTempFolder("p4Client");
        String prefix = "Failed to create p4 client!\nError: ";
        runP4CommandWithInput(clientConfig(clientName), prefix, "client", "-i");
    }

    

	private Process executeCommand(String... command) throws IOException {
        return Runtime.getRuntime().exec(command, null);
    }

    private Process executeP4Command(String... args) throws IOException {
        return executeCommand(p4Command(args));
    }

    private String getProcessError(Process process) throws IOException {
        process.getInputStream().close();
        process.getOutputStream().close();
        InputStream errorStream = process.getErrorStream();
        String error = IOUtils.toString(errorStream);
        errorStream.close();
        return error;
    }

    private String[] p4Command(String... args) {
        ArrayList<String> array = new ArrayList<String>();
        array.add("p4");
        array.add("-p");
        array.add("localhost:1666");
        array.add("-c");
        array.add(clientName);
        for (String arg : args) {
            array.add(arg);
        }
        return array.toArray(new String[0]);
    }

    private void runP4Command(String errorMessagePrefix, String... command) throws Exception {
        runP4CommandWithInput("", errorMessagePrefix, command);
    }

    private void runP4CommandWithInput(String input, String errorMessagePrefix, String... command) throws Exception {
    	Process process = executeP4Command(command);
        if (!StringUtils.isBlank(input)) {
            IOUtils.copy(IOUtils.toInputStream(input), process.getOutputStream());
        }
        String error = getProcessError(process);
        bombIf(process.waitFor() != 0, errorMessagePrefix + " Error running command [" + ArrayUtil.join(command, " ") + "]" + error);
    }

    private Process startP4d() throws IOException {
        assertWillHappen(false, is(PORT_OCCUPIED_MATCHER), Timeout.FIVE_SECONDS);
        Process process = executeCommand("p4d", "-C0", "-r", repositoryFolder.getAbsolutePath(), "-p", String.valueOf(1666));
        closeStreams(process);
        return process;
    }

	private void waitForP4dToStartup() throws Exception {
        assertWillHappen(true, is(PORT_OCCUPIED_MATCHER), Timeout.TWENTY_SECONDS);
    }
    
    @Override
	public Revision latestRevision() {
		throw new RuntimeException("implement me");
	}
    
    @Override 
    public void setUrl(Element element){    	
        element.selectSingleNode("view").setText("//depot/... //" + clientName + "/...");
    }

	public String clientName() {	
		return clientName;
	}
	

	@Override
	public void modifyFile(String fileName, String userName) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void deleteFile(String fileName, String userName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String latestRevisionString() {
		return latestRevision().revisionNumber();
	}

    @Override
    public void initatePostCommitHook(TalkToCruise talkToCruise) {
        throw new UnsupportedOperationException();
    }
}
