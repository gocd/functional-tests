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

public class StageInstanceModel {
	private int id;
	private String name;
	private String counter;
	private String pipeline_name;
	private int pipeline_counter;
	private String result;
	private String approval_type;
	private String approved_by;
	private int rerun_of_counter;
	private List<JobInstance> jobs;

	public StageInstanceModel(int id, String name, String counter,
			String pipeline_name, int pipeline_counter, String result,
			String approval_type, String approved_by, int rerun_of_counter,
			List<JobInstance> jobs) {
		super();
		this.id = id;
		this.name = name;
		this.counter = counter;
		this.pipeline_name = pipeline_name;
		this.pipeline_counter = pipeline_counter;
		this.result = result;
		this.approval_type = approval_type;
		this.approved_by = approved_by;
		this.rerun_of_counter = rerun_of_counter;
		this.jobs = jobs;
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

	public String getCounter() {
		return counter;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}

	public String getPipelineName() {
		return pipeline_name;
	}

	public void setPipelineName(String pipeline_name) {
		this.pipeline_name = pipeline_name;
	}

	public int getPipelineCounter() {
		return pipeline_counter;
	}

	public void setPipelineCounter(int pipeline_counter) {
		this.pipeline_counter = pipeline_counter;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getApproval_type() {
		return approval_type;
	}

	public void setApproval_type(String approval_type) {
		this.approval_type = approval_type;
	}

	public String getApproved_by() {
		return approved_by;
	}

	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}

	public int getRerun_of_counter() {
		return rerun_of_counter;
	}

	public void setRerun_of_counter(int rerun_of_counter) {
		this.rerun_of_counter = rerun_of_counter;
	}

	public List<JobInstance> getJobs() {
		return jobs;
	}

	public void setJobs(List<JobInstance> jobs) {
		this.jobs = jobs;
	}
}
