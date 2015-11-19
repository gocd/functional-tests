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

package com.thoughtworks.cruise.api;

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import com.thoughtworks.cruise.state.CurrentUsernameProvider;
import com.thoughtworks.cruise.state.RepositoryState;
import com.thoughtworks.cruise.state.ScenarioState;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class UsingBackupServerApi {
	
	private ScenarioState state;
	private CruiseResponse cruiseResponse;
	
	protected UsingBackupServerApi(ScenarioState state, RepositoryState repositoryState) {
		this.state = state;
	}

	@com.thoughtworks.gauge.Step("Take backup as <userName>")
	public void takeBackupAs(final String userName) throws Exception {
		String url = Urls.urlFor(String.format("/api/admin/start_backup"));
		System.err.println("posting to " + url);
		
		TalkToCruise talkToCruise = new TalkToCruise(new CurrentUsernameProvider() {
			@Override
			public String loggedInUser() {
				return userName;
			}
		});
		
		cruiseResponse = talkToCruise.post(url);
	}

	@com.thoughtworks.gauge.Step("Verify backup is successful")
	public void verifyBackupIsSuccessful(){
				Assert.assertThat(cruiseResponse.isSuccess(), Is.is(true));
	}
	
	@com.thoughtworks.gauge.Step("Verify user is not authorized to take backups")
	public void verifyUserIsNotAuthorizedToTakeBackups(){
		Assert.assertThat(cruiseResponse.isForbidden(), Is.is(true));
	}

}
