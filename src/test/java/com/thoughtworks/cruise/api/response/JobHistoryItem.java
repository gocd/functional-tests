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

package com.thoughtworks.cruise.api.response;

public class JobHistoryItem {
	private int id;
	private String name;
	private String state;
	private String result;
	private long scheduled_date;

	public JobHistoryItem(int id, String name, String state, String result,
			long scheduledDate) {
		super();
		this.id = id;
		this.name = name;
		this.state = state;
		this.result = result;
		this.scheduled_date = scheduledDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public long getScheduledDate() {
		return scheduled_date;
	}

	public void setScheduledDate(long scheduledDate) {
		this.scheduled_date = scheduledDate;
	}
}
