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

package com.thoughtworks.cruise.util;

import org.apache.commons.httpclient.URI;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class URL {

	private URI uri;

	public URL(String url) {
		try {
			uri = new URI(url, true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void addParameter(String param, String value) {
		Map<String, String> paramToValues = parameters();
		paramToValues.put(param, value);
		setQuery(paramToValues);
	}

	private void setQuery(Map<String, String> paramToValues) {
		try {
			String query = "";
			for (Entry<String, String> entry : paramToValues.entrySet()) {
				query += "&" + entry.getKey() + "=" + entry.getValue();
			}
			uri.setQuery(query.substring(1));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, String> parameters() {
		try {
			Map<String, String> paramToValues = new HashMap<String, String>();
			String query = uri.getQuery();
			if (query == null) {
				return paramToValues;
			}
			String[] paramValues = query.split("&");
			for (String paramValue : paramValues) {
				String[] paramAndValue = paramValue.split("=");
				paramToValues.put(paramAndValue[0], paramAndValue[1]);
			}
			return paramToValues;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String toString() {
		return uri.getEscapedURI();
	}
}
