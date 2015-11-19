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

import com.thoughtworks.cruise.utils.CommandLine;
import com.thoughtworks.cruise.utils.Console;
import com.thoughtworks.cruise.utils.EnvironmentVariableContext;
import com.thoughtworks.cruise.utils.SystemOutStreamConsumer;
import com.thoughtworks.cruise.utils.matchers.HttpMatcher;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Element;

import static com.thoughtworks.cruise.utils.Assertions.assertWillHappen;

public class HgRemoteRepository extends HgRepository {

	private Console hgDaemon; 

	public HgRemoteRepository(Element material, String bundlePath) {
		super(material, bundlePath);
		serve();
	}

	@Override
	public void cleanup() {
		stopHgServe();
		super.cleanup();
	}



	public void serve() {
		hgDaemon = CommandLine.createCommandLine("hg").withArgs("serve", "--port", "8008").
			withWorkingDir(repositoryFolder.getAbsoluteFile()).
			execute(new SystemOutStreamConsumer(HgRemoteRepository.class), new EnvironmentVariableContext());
		serverIsStarted();
	}
	
    public void serverIsStarted() {
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(getUrl());
        assertWillHappen(client, HttpMatcher.get200(get));
    }

	@Override
	public String getUrl() {
		return "http://username:password@localhost:8008/";
	}

    public void stopHgServe() {
        hgDaemon.kill();
    }
}
