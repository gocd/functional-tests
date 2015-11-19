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

public class StageHistoryItem {
	private int id;
	private String name;
	private String counter;
	private String result;
	private String approval_type;
	private String approved_by;
	private boolean can_run;
	private boolean operate_permission;
	private boolean scheduled;
	private int rerun_of_counter;
	private List<JobHistoryItem> jobs;

	public StageHistoryItem(int id, String name, String counter, String result,
			String approvalType, String approvedBy, boolean canRun,
			boolean operatePermission, boolean scheduled, int rerunOfCounter,
			List<JobHistoryItem> jobs) {
		super();
		this.id = id;
		this.name = name;
		this.counter = counter;
		this.result = result;
		this.approval_type = approvalType;
		this.approved_by = approvedBy;
		this.can_run = canRun;
		this.operate_permission = operatePermission;
		this.scheduled = scheduled;
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

	public boolean isCanRun() {
		return can_run;
	}

	public void setCanRun(boolean canRun) {
		this.can_run = canRun;
	}

	public boolean isOperatePermission() {
		return operate_permission;
	}

	public void setOperatePermission(boolean operatePermission) {
		this.operate_permission = operatePermission;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
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
