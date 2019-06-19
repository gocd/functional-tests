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

import com.thoughtworks.cruise.RuntimePath;
import com.thoughtworks.cruise.preconditions.ProcessIsRunning;
import com.thoughtworks.cruise.utils.Assertions;
import com.thoughtworks.cruise.utils.Assertions.Predicate;
import com.thoughtworks.cruise.utils.Timeout;
import net.sf.sahi.client.Browser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GadgetRenderingServerIsRunning {

	private Browser browser;
	private GadgetRenderingServer gadgetRenderingServer;

	public GadgetRenderingServerIsRunning(Browser browser) {
		this.browser = browser;
	}

	@com.thoughtworks.gauge.Step("Gadget rendering server is running - setup")
	public void setUp() throws Exception {
		gadgetRenderingServer = new GadgetRenderingServer();
		gadgetRenderingServer.start();
	}

	@com.thoughtworks.gauge.Step("Gadget rendering server is running - teardown")
	public void tearDown() throws Exception {
		gadgetRenderingServer.stop();
	}

	private static class GadgetRenderingServer extends ProcessIsRunning {

		@Override
		protected boolean pumpOutputStream() {
			return true;
		}

		@Override
		protected boolean pumpErrorStream() {
			return true;
		}

		@Override
		protected boolean isProcessStopped() {
			try {
				return !isServerRunning();
			} catch (RuntimeException e) {
				return true;
			}
		}

		private boolean isServerRunning() {
			return get("http://localhost:3000").getReturnCode() < 400;
		}

		protected String pidFile() throws IOException {
			return new File(getWorkingDir(), "go-gadgets.pid").getCanonicalPath();

		}

		@Override
		protected String startCommand() {
			return "./start_server.sh";
		}

		@Override
		protected String stopCommand() {
			return "./stop_server.sh";
		}

		@Override
		protected String getWorkingDir() {
			return RuntimePath.pathFor("gadget_renderer");
		}

		@Override
		protected Map<String, String> getStartEnvVariables() {
			return new HashMap<String, String>();
		}

		@Override
		public void start() throws Exception {
			stop();
			waitTillServerIsStopped();
			execute("./db_reset.sh", "", new HashMap<String, String>());
			super.start();
			waitTillServerIsStarted();
			System.out.println("Gadget server started");
		}

		private void waitTillServerIsStopped() {
			Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {
				public boolean call() throws Exception {
					return isProcessStopped();
				}
			});
		}

		private void waitTillServerIsStarted() {
			Assertions.waitUntil(Timeout.TWO_MINUTES, new Predicate() {
				public boolean call() throws Exception {
					return !isProcessStopped();
				}
			});
		}
	}
}
