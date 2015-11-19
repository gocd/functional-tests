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

import net.sf.sahi.client.Browser;

public class ValidateLabelTemplateField {

	private Browser browser;

	private String enteredValue;

	private String message;

	public ValidateLabelTemplateField(Browser browser) {
		this.browser = browser;
	}

	public void setEnteredValue(String enteredValue) throws Exception {
		this.enteredValue = enteredValue;
	}

	public void setMessage(String message) throws Exception {
		this.message = message;
	}

	public void setUp() throws Exception {
		//Put the code to be executed before execution of each row
	}

	public void tearDown() throws Exception {
		//Put the code to be executed after execution of each row
	}

}