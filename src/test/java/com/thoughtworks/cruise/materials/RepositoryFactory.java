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
import org.dom4j.Element;

public class RepositoryFactory {

    public static Repository create(Element material) {
        String type = material.getName();
        if ("hg".equals(type)) {
            return "hg-remote".equals(material.attributeValue("dest")) ? 
                    new HgRemoteRepository(material, RuntimePath.pathFor("test-repos/real_connectfour.hgbundle")) : 
                    new HgRepository(material, RuntimePath.pathFor("test-repos/real_connectfour.hgbundle"));
        } else if ("git".equals(type)) {
            if ("git-submodule".equals(material.attributeValue("dest")) ||
                "git-submodule".equals(material.attributeValue("materialName"))) {
                return new GitSubmoduleRepository(material, RuntimePath.pathFor("test-repos/git/git-3-revisions.git"), RuntimePath
                        .pathFor("test-repos/git/referenced-submodule.git"));
            }
            return new GitRepository(material, RuntimePath.pathFor("test-repos/git/git-3-revisions.git"));
        } else if ("svn".equals(type)) {
        	if ("true".equals(material.attributeValue("checkexternals"))) {
                return new SvnExternalRepository(material, RuntimePath.pathFor("test-repos/svnrepo/end2end"), RuntimePath.pathFor("test-repos/svnrepo/connect4.net"));
            }
            return new SvnRepository(material, RuntimePath.pathFor("test-repos/svnrepo/end2end"));
        } else if ("p4".equals(type)) {
            return new P4Repository(material, RuntimePath.pathFor("test-repos/p4repo"));
        } else if("tfs".equals(type)){
            return new TfsRepository(material);
        }
        else {
            throw new UnsupportedOperationException("Implement me");
        }
    }

    
    public static boolean isRepository(String type) {
        return !type.equals("pipeline") && !type.equals("package");
    }
}
