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

public class AgentInformation {
	private String uuid;
	private String build_locator;
	private String agent_name;
	private String ip_address;
	private String status;
	private String sandbox;
	private String os;
	private String free_space;
	private List<String> resources;
	private List<String> environments;

	public AgentInformation(String uuid, String build_locator,
			String agent_name, String ip_address, String status, String sandbox,
			String os, String free_space, List<String> resources,
			List<String> environments) {
		super();
		this.uuid = uuid;
		this.build_locator = build_locator;
		this.agent_name = agent_name;
		this.ip_address = ip_address;
		this.status = status;
		this.sandbox = sandbox;
		this.os = os;
		this.free_space = free_space;
		this.resources = resources;
		this.environments = environments;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBuild_locator() {
		return build_locator;
	}

	public void setBuild_locator(String build_locator) {
		this.build_locator = build_locator;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSandbox() {
		return sandbox;
	}

	public void setSandbox(String sandbox) {
		this.sandbox = sandbox;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getFree_space() {
		return free_space;
	}

	public void setFree_space(String free_space) {
		this.free_space = free_space;
	}

	public List<String> getResources() {
		return resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	public List<String> getEnvironments() {
		return environments;
	}

	public void setEnvironments(List<String> environments) {
		this.environments = environments;
	}

	@Override
	public String toString() {
		return "AgentInformation [uuid=" + uuid + ", buildLocator="
				+ build_locator + ", agent_name=" + agent_name + ", ipAddress="
				+ ip_address + ", status=" + status + ", sandbox=" + sandbox
				+ ", os=" + os + ", free_space=" + free_space + ", resources="
				+ resources + ", environments=" + environments + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((agent_name == null) ? 0 : agent_name.hashCode());
		result = prime * result
				+ ((environments == null) ? 0 : environments.hashCode());
		result = prime * result
				+ ((free_space == null) ? 0 : free_space.hashCode());
		result = prime * result
				+ ((ip_address == null) ? 0 : ip_address.hashCode());
		result = prime * result + ((os == null) ? 0 : os.hashCode());
		result = prime * result
				+ ((resources == null) ? 0 : resources.hashCode());
		result = prime * result + ((sandbox == null) ? 0 : sandbox.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentInformation other = (AgentInformation) obj;
		if (agent_name == null) {
			if (other.agent_name != null)
				return false;
		} else if (!agent_name.equalsIgnoreCase(other.agent_name))
			return false;
		if (environments == null) {
			if (other.environments != null)
				return false;
		} else if (!environments.equals(other.environments))
			return false;
		if (free_space == null) {
			if (other.free_space != null)
				return false;
		} else if (!free_space.equalsIgnoreCase(other.free_space))
			return false;
		if (ip_address == null) {
			if (other.ip_address != null)
				return false;
		} else if (!ip_address.equalsIgnoreCase(other.ip_address))
			return false;
		if (os == null) {
			if (other.os != null)
				return false;
		} else if (!os.equalsIgnoreCase(other.os))
			return false;
		if (resources == null) {
			if (other.resources != null)
				return false;
		} else if (!resources.equals(other.resources))
			return false;
		if (sandbox == null) {
			if (other.sandbox != null)
				return false;
		} else if (!sandbox.equalsIgnoreCase(other.sandbox))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equalsIgnoreCase(other.status))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equalsIgnoreCase(other.uuid))
			return false;
		return true;
	}

}
