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

import java.util.List;

public class StageHistory {
	List<StageInstance> stages;
	Pagination pagination;

	public StageHistory(List<StageInstance> stages, Pagination pagination) {
		super();
		this.stages = stages;
		this.pagination = pagination;
	}

	public List<StageInstance> getStages() {
		return stages;
	}

	public void setStages(List<StageInstance> stages) {
		this.stages = stages;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
