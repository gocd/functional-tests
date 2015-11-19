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

import com.thoughtworks.cruise.Urls;
import com.thoughtworks.cruise.client.TalkToCruise;
import com.thoughtworks.cruise.client.TalkToCruise.CruiseResponse;
import org.hamcrest.core.Is;
import org.junit.Assert;

public class WithNoUsers {

    private final TalkToCruise talkToCruise;

    public WithNoUsers(TalkToCruise talkToCruise) {
        this.talkToCruise = talkToCruise;
	}

	@com.thoughtworks.gauge.Step("With no users - setup")
	public void setUp() throws Exception {
	    CruiseResponse response = talkToCruise.delete(Urls.urlFor("/admin/users/delete_all"));
	    Assert.assertThat(response.getStatus(), Is.is(200));
	}

	@com.thoughtworks.gauge.Step("With no users - teardown")
	public void tearDown() throws Exception {
	}
}
