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

public class JobInstance {
	private int id;
	private String name;
	private String pipeline_name;
	private int pipeline_counter;
	private String stage_name;
	private String stage_counter;
	private String state;
	private String result;
	private long scheduled_date;
	private boolean rerun;
	private int original_job_id;
	private String agent_uuid;
	private List<JobStateTransition> job_state_transitions;

	public JobInstance(int id, String name, String pipelineName,
			int pipelineCounter, String stageName, String stageCounter,
			String state, String result, long scheduledDate, boolean rerun,
			int originalJobId, String agentUuid,
			List<JobStateTransition> jobStateTransitions) {
		super();
		this.id = id;
		this.name = name;
		this.pipeline_name = pipelineName;
		this.pipeline_counter = pipelineCounter;
		this.stage_name = stageName;
		this.stage_counter = stageCounter;
		this.state = state;
		this.result = result;
		this.scheduled_date = scheduledDate;
		this.rerun = rerun;
		this.original_job_id = originalJobId;
		this.agent_uuid = agentUuid;
		this.job_state_transitions = jobStateTransitions;
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

	public String getStageName() {
		return stage_name;
	}

	public void setStageName(String stageName) {
		this.stage_name = stageName;
	}

	public String getStageCounter() {
		return stage_counter;
	}

	public void setStageCounter(String stageCounter) {
		this.stage_counter = stageCounter;
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

	public boolean isRerun() {
		return rerun;
	}

	public void setRerun(boolean rerun) {
		this.rerun = rerun;
	}

	public int getOriginalJobId() {
		return original_job_id;
	}

	public void setOriginalJobId(int originalJobId) {
		this.original_job_id = originalJobId;
	}

	public String getAgentUuid() {
		return agent_uuid;
	}

	public void setAgentUuid(String agentUuid) {
		this.agent_uuid = agentUuid;
	}

	public List<JobStateTransition> getJobStateTransitions() {
		return job_state_transitions;
	}

	public void setJobStateTransitions(
			List<JobStateTransition> jobStateTransitions) {
		this.job_state_transitions = jobStateTransitions;
	}
}
