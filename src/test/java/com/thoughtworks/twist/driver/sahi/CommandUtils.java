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

package com.thoughtworks.twist.driver.sahi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandUtils {
	public static CommandResult execute(String[] command) throws IOException, InterruptedException {
		Runtime r = Runtime.getRuntime();
		Process p = r.exec(command);

		// We need to loop through the stream otherwise waitFor() method hangs.
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";
		CommandResult fromProcess = CommandResult.fromProcess(p);
		p.waitFor();
		input.close();
		return fromProcess;
	}
}
