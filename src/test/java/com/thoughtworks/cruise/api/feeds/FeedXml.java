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

package com.thoughtworks.cruise.api.feeds;

import com.thoughtworks.cruise.api.ApiHelper;
import com.thoughtworks.cruise.state.ScenarioState;
import org.dom4j.Document;

public abstract class FeedXml {
	protected final ScenarioState state;
	protected final ApiHelper apiHelper;
	private Document feed;

	protected FeedXml(ScenarioState state){
		this.state = state;
		this.apiHelper = new ApiHelper(state);
	}

	protected Document feed() {

		try {
			feed = apiHelper.loadXmlDocumentFromUrl(feedUrl());
			System.out.println(this.getClass().getSimpleName() + ": ");
			System.out.println(feed.asXML());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return feed;
	}

	protected abstract String feedUrl();

}
