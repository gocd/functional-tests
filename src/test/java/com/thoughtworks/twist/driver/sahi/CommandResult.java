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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandResult {

	private final List<String> input;
	private final List<String> error;

	private CommandResult(List<String> input, List<String> error) {
		this.input = input;
		this.error = error;
	}

	public static CommandResult fromProcess(Process p) {
		List<String> input = getStringsFromStream(p.getInputStream());
		List<String> error = getStringsFromStream(p.getErrorStream());
		return new CommandResult(input,error);
	}

	private static List<String> getStringsFromStream(InputStream stream) {
		BufferedReader errorStreamBufferedReader = new BufferedReader(new InputStreamReader(stream));
		List<String> output = new ArrayList<String>();
		String line = "";
		try {
			while ((line = errorStreamBufferedReader.readLine()) != null) {
				output.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	public boolean contains(String textToSearchFor) {
		return containsTextWithin(input, textToSearchFor) || containsTextWithin(error, textToSearchFor);
	}

	private boolean containsTextWithin(List<String> listOfStrings, String textToSearchFor) {
		if (listOfStrings.isEmpty()) {
			return false;
		}
		for (String stdoutString : listOfStrings) {
			if (stdoutString.contains(textToSearchFor)) {
				return true;
			}
		}
		return false;
	}

	public List<String> getInput() {
		return input;
	}

	public List<String> getError() {
		return error;
	}

}