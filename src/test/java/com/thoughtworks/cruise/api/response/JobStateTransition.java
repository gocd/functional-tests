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

public class JobStateTransition {
	private int id;
	private String state;
	private long state_change_time;

	public JobStateTransition(int id, String state, long stateChangeTime) {
		super();
		this.id = id;
		this.state = state;
		this.state_change_time = stateChangeTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getStateChangeTime() {
		return state_change_time;
	}

	public void setStateChangeTime(long stateChangeTime) {
		this.state_change_time = stateChangeTime;
	}
}
