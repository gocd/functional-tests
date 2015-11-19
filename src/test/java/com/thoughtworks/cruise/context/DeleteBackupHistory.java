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

// JUnit Assert framework can be used for verification

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.ScenarioState;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class DeleteBackupHistory {

	private final ScenarioState state;

	public DeleteBackupHistory(ScenarioState state) {
		this.state = state;
	}

	@com.thoughtworks.gauge.Step("Delete backup history - setup")
	public void setUp() throws Exception {
		deleteAllBackups();
	}

	private void deleteAllBackups() {
		CruiseResponse response = new TalkToCruise(state).delete(Urls.deleteAllBackupExtries());
		Assert.assertThat(response.isSuccess(), Is.is(true));
	}

	@com.thoughtworks.gauge.Step("Delete backup history - teardown")
	public void tearDown() throws Exception {
		deleteAllBackups();
	}
}
