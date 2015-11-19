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

package com.thoughtworks.cruise.api;

import com.thoughtworks.cruise.state.ScenarioState;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jaxen.JaxenException;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.dom4j.Dom4jXPath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class ApiHelper {
	private final ScenarioState scenarioState;

	public ApiHelper(ScenarioState scenarioState) {
		this.scenarioState = scenarioState;
	}
	
	public Document loadXmlDocumentFromUrl(String feedUrl) throws DocumentException, MalformedURLException {
		SAXReader saxReader = new SAXReader();
		byte[] contents = scenarioState.getApiTransportScheme().getContentsForPage(feedUrl, scenarioState);
		InputStream read = new ByteArrayInputStream(contents);
		return saxReader.read(read);	
	}

	public Node fetchNode(Document doc, String xpathPattern) throws JaxenException {
		Dom4jXPath dom4jXPath = new Dom4jXPath(xpathPattern);
		Map<String, String> map = new HashMap<String, String>();
		map.put("atom", "http://www.w3.org/2005/Atom");
		map.put("go", "http://www.thoughtworks-studios.com/ns/go");
		dom4jXPath.setNamespaceContext(new SimpleNamespaceContext(map));
		return (Node) dom4jXPath.selectSingleNode(doc);
	}
}
