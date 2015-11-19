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

public class BuildCause {
	private boolean trigger_forced;
	private String trigger_message;
	private String approver;
	private List<MaterialRevision> material_revisions;

	public BuildCause(boolean triggerForced, String triggerMessage,
			String approver, List<MaterialRevision> materialRevisions) {
		super();
		this.trigger_forced = triggerForced;
		this.trigger_message = triggerMessage;
		this.approver = approver;
		this.material_revisions = materialRevisions;
	}

	public boolean isTriggerForced() {
		return trigger_forced;
	}

	public void setTriggerForced(boolean triggerForced) {
		this.trigger_forced = triggerForced;
	}

	public String getTriggerMessage() {
		return trigger_message;
	}

	public void setTriggerMessage(String triggerMessage) {
		this.trigger_message = triggerMessage;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public List<MaterialRevision> getMaterialRevisions() {
		return material_revisions;
	}

	public void setMaterialRevisions(List<MaterialRevision> materialRevisions) {
		this.material_revisions = materialRevisions;
	}
}
