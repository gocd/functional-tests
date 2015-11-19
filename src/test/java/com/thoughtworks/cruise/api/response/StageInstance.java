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

public class StageInstance {
	private int id;
	private String name;
	private String counter;
	private String pipeline_name;
	private int pipeline_counter;
	private String result;
	private String approval_type;
	private String approved_by;
	private int rerun_of_counter;
	private List<JobHistoryItem> jobs;

	public StageInstance(int id, String name, String counter,
			String pipelineName, int pipelineCounter, String result,
			String approvalType, String approvedBy, int rerunOfCounter,
			List<JobHistoryItem> jobs) {
		super();
		this.id = id;
		this.name = name;
		this.counter = counter;
		this.pipeline_name = pipelineName;
		this.pipeline_counter = pipelineCounter;
		this.result = result;
		this.approval_type = approvalType;
		this.approved_by = approvedBy;
		this.rerun_of_counter = rerunOfCounter;
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

	public void setPipelineName(String pipelineName) {
		this.pipeline_name = pipelineName;
	}

	public int getPipelineCounter() {
		return pipeline_counter;
	}

	public void setPipelineCounter(int pipelineCounter) {
		this.pipeline_counter = pipelineCounter;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getApprovalType() {
		return approval_type;
	}

	public void setApprovalType(String approvalType) {
		this.approval_type = approvalType;
	}

	public String getApprovedBy() {
		return approved_by;
	}

	public void setApprovedBy(String approvedBy) {
		this.approved_by = approvedBy;
	}

	public int getRerunOfCounter() {
		return rerun_of_counter;
	}

	public void setRerunOfCounter(int rerunOfCounter) {
		this.rerun_of_counter = rerunOfCounter;
	}

	public List<JobHistoryItem> getJobs() {
		return jobs;
	}

	public void setJobs(List<JobHistoryItem> jobs) {
		this.jobs = jobs;
	}
}
