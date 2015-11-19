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
import com.thoughtworks.cruise.state.ScenarioState;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class UsingCommandSnippetCacheApi {
	
	private ScenarioState state;
	private CruiseResponse cruiseResponse;
	
	protected UsingCommandSnippetCacheApi(ScenarioState state) {
		this.state = state;
	}

	@com.thoughtworks.gauge.Step("Reload cache as <userName>")
	public void reloadCacheAs(final String userName) throws Exception {
		String url = Urls.urlFor("/api/admin/command-repo-cache/reload");
		System.err.println("posting to " + url);
		
		TalkToCruise talkToCruise = new TalkToCruise(new CurrentUsernameProvider() {
			@Override
			public String loggedInUser() {
				return userName;
			}
		});
		
		cruiseResponse = talkToCruise.post(url);
	}

	@com.thoughtworks.gauge.Step("Verify reload was successful")
	public void verifyReloadWasSuccessful(){
				Assert.assertThat(cruiseResponse.isSuccess(), Is.is(true));
	}
	
	@com.thoughtworks.gauge.Step("Verify user is not authorized to reload cache")
	public void verifyUserIsNotAuthorizedToReloadCache(){
		Assert.assertThat(cruiseResponse.isForbidden(), Is.is(true));
	}

}
