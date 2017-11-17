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

import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.util.FileUtil;
import com.thoughtworks.cruise.util.SystemUtil;
import com.thoughtworks.cruise.util.TestFileUtil;
import com.thoughtworks.cruise.util.command.CommandLine;
import org.dom4j.Element;

import java.io.File;
import java.util.UUID;

import static com.thoughtworks.cruise.util.command.CommandLine.createCommandLine;

public class TfsRepository extends AbstractRepository {

    private File repositoryFolder;
    private String workspaceName;

    public TfsRepository(Element material) {
        super(material);
        workspaceName = "test_workspace_" + UUID.randomUUID();
        initializeTestWorkspace();
        mapTestDirectory();
        getFiles();
    }

    private void initializeTestWorkspace() {
        CommandLine cmd = createCommandLine(tfCommand()).withArgs("workspace", "-new", "-noprompt", serverArg(), loginArg(), workspaceName + ";"+ TfsServer.getUsername());
        cmd.getArguments();
        cmd.runOrBomb();
    }

    private void mapTestDirectory() {
        // create workspace and map folder
        repositoryFolder = TestFileUtil.prepareFolderInsideTempFolder("testTfsFolder");
        FileUtil.mkdirs(repositoryFolder);
        createCommandLine(tfCommand()).withArgs("workfold", "-map", "-workspace:" + workspaceName, serverArg(), loginArg(), getMaterialAttribute("projectPath"), repositoryFolder.getAbsolutePath())
                .runOrBomb();
    }

    private void getFiles() {
        createCommandLine(tfCommand()).withWorkingDir(repositoryFolder)
        .withArgs("get", repositoryFolder.getAbsolutePath(), "-recursive", "-noprompt", "-all", serverArg(), loginArg(), getMaterialAttribute("projectPath"))
        .runOrBomb();
        createCommandLine(tfCommand()).withArgs("checkout", "*", loginArg()).withWorkingDir(repositoryFolder).runOrBomb();
    }
    
    private String loginArg() {
        return String.format("-login:%s,%s", TfsServer.getUsername(), TfsServer.getPassword());
    }

    private String serverArg() {
        return "-server:" + getUrl();
    }

    @Override
    public String getUrl() {
        return TfsServer.getDefaultTfsCollectionUrl();
    }

    @Override
    public void setOtherAttributes(Element element) {
        super.setOtherAttributes(element);
        if (element.attribute("username") != null) {
            element.attribute("username").setValue(TfsServer.getUsername());
        }
        if (element.attribute("password") != null) {
            element.attribute("password").setValue(TfsServer.getPassword());
        }
    }

    @Override
    public void cleanup() {
        // unmap workspace
        createCommandLine(tfCommand())
        .withArgs("workfold", "-unmap", "-workspace:" + workspaceName, serverArg(), loginArg(), repositoryFolder.getAbsolutePath()).runOrBomb(); 
        // delete folder
        RepositoryHelper.cleanup(repositoryFolder);
        // delete workspace
        createCommandLine(tfCommand())
        .withArgs("workspace", "-delete", workspaceName, serverArg(), loginArg()).runOrBomb(); 

    }

    @Override
    public void createNewFile(String fileName, String userName) {
        tfsAdd(RepositoryHelper.createFileIn(repositoryFolder, fileName));

    }

    private void tfsAdd(File file) {
        createCommandLine(tfCommand()).withArgs("add", file.getAbsolutePath(), loginArg()).withWorkingDir(repositoryFolder).runOrBomb();
    }

    @Override
    public void checkin(String message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void checkin(String username, String message) {
        createCommandLine(tfCommand()).withArgs("checkin", "-comment:" + message, "-noprompt", loginArg()).withWorkingDir(repositoryFolder).runOrBomb();
    }

    @Override
    public String getRevisionFromComment(String checkinComment) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Revision latestRevision() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String latestRevisionString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void modifyFile(String fileName, String userName) {
    	// checkout for edit
    	createCommandLine(tfCommand()).withArgs("checkout", fileName, loginArg()).withWorkingDir(repositoryFolder).runOrBomb();
        // then edit the file.
        RepositoryHelper.modifyFileInFolderWithText(repositoryFolder, fileName, "adding text " + UUID.randomUUID());
    }

    @Override
    public void deleteFile(String fileName, String userName) {
        throw new UnsupportedOperationException();

    }

    public static String tfCommand() {
        if(SystemUtil.isWindows()){
            return RuntimePath.absolutePathFor("tfs-tool") + "\\tf.cmd";
        }
        else{
            return RuntimePath.absolutePathFor("tfs-tool") + "/tf";
        }
    }

    @Override
    public void initatePostCommitHook(TalkToCruise talkToCruise) {
        throw new UnsupportedOperationException();
    }

}
