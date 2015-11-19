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

package com.thoughtworks.cruise.preconditions;

import com.thoughtworks.cruise.materials.TfsRepository;
import com.thoughtworks.cruise.materials.TfsServer;
import com.thoughtworks.cruise.tfs.WorkingFolder;
import com.thoughtworks.cruise.tfs.Workspace;
import com.thoughtworks.cruise.tfs.Workspaces;
import com.thoughtworks.cruise.util.XmlFormatTfsOutputParser;
import com.thoughtworks.cruise.util.command.ConsoleResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;

import static com.thoughtworks.cruise.util.command.CommandLine.createCommandLine;

public class CleanupTfsWorkspaces implements DisposableBean, InitializingBean {
    
    public CleanupTfsWorkspaces(){
        
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        cleanAllWorkSpaces();
    }

    @Override
    public void destroy() throws Exception {
//        cleanAllWorkSpaces();
    }

    private void cleanAllWorkSpaces() {
        TFSServerConnInfo connectInfo = new TFSServerConnInfo(new TfsServer().getDefaultTfsCollectionUrl(), TfsRepository.DEFAULT_USER_DOMAIN, TfsRepository.DEFAULT_PASSWORD);
        cleanWorkspaces(listWorkspaces(connectInfo), connectInfo);
    }

    private void cleanWorkspaces(Workspaces allWorkspaces, TFSServerConnInfo connectInfo) {
        if (allWorkspaces.getWorkspaces() != null) {
            for (Workspace ws : allWorkspaces.getWorkspaces()) {
                createCommandLine(TfsRepository.tfCommand()).withArgs("workspace", "-delete", "-noprompt", ws.getName(),
                        connectInfo.serverArg(), connectInfo.loginArg()).runOrBomb();
                if (ws.getWorkingFolders() != null && !ws.getWorkingFolders().isEmpty()) {
                    for (WorkingFolder wf : ws.getWorkingFolders()) {
                        FileUtils.deleteQuietly(new File(wf.getLocalFolder()));
                    }
                }
            }
        }
    }
    
    private Workspaces listWorkspaces(TFSServerConnInfo connectInfo) {
        ConsoleResult result = createCommandLine(TfsRepository.tfCommand()).withArgs("workspaces", connectInfo.serverArg(), "-format:xml", connectInfo.loginArg()).runOrBomb();
        return new XmlFormatTfsOutputParser().parseWorkspaces(result);
    }


}
